package com.lance.view.rest.user;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.CompanyVOImpl;
import com.lance.model.vo.CompanyVORowImpl;
import com.lance.view.util.LanceRestUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("user/company")
public class CompanyResource extends BaseRestResource {

    public static final String[] ATTR_FILTER_BY_NAME = { "Name" };

    public CompanyResource() {
    }

    /**
     * 传入一个包含公司名Name的JSON，如果公司存在则返回该公司ID，不存在则创建一条并返回ID
     *
     * 例子
     * POST http://localhost:7101/lance/res/user/company/mergeByName
     {
         "CompanyName" : "百度"
     }

     *
     * @param json
     * @return 32位公司ID
     * @throws JSONException
     */
    @POST
    @Path("mergeByName")
    @Consumes(MediaType.APPLICATION_JSON)
    public String mergeCompanyByName(JSONObject json) throws JSONException {
        String uuid = mergeCompanyByName(json.getString("Name"));
        LanceRestAMImpl am = LanceRestUtil.findLanceAM();
        am.getDBTransaction().commit();
        return uuid;
    }

    /**
     * 同上，但不提交数据
     * @param companyName
     * @return
     */
    public String mergeCompanyByName(String companyName) {
        System.out.println("mergeCompanyByName");
        LanceRestAMImpl am = LanceRestUtil.findLanceAM();
        CompanyVOImpl vo = am.getCompany1();
        vo.setApplyViewCriteriaName("FindByNameVC");
        vo.setpName(companyName);
        vo.executeQuery();
        vo.setApplyViewCriteriaName(null);

        if (vo.getRowCount() > 0) {
            return (String) vo.first().getAttribute("Uuid");
        } else {
            CompanyVORowImpl row = (CompanyVORowImpl) vo.createRow();
            vo.insertRow(row);
            row.setName(companyName);
            return (String) row.getAttribute("Uuid");
        }
    }

    /**
     * 查找公司名相近的公司，用于输入公司名时的快速提示功能
     * 建议前台控制达到2个字符时再执行
     * 中间模糊匹配
     * 最多返回10条
     *
     * GET http://localhost:7101/lance/res/user/company/filter/name/{companyName}
     *
     * @example
     * GET http://localhost:7101/lance/res/user/company/filter/name/百度
     *
       @return
         [
            {
                "Name" : "百度"
            },
            {
                "Name" : "百度2"
            }
        ]

     * @param name
     * @return
     * @throws JSONException
     */
    @GET
    @Path("filter/name/{name}")
    public JSONArray filterCompanyByName(@PathParam("name") String name) throws JSONException {
        LanceRestAMImpl am = LanceRestUtil.findLanceAM();
        CompanyVOImpl vo = am.getCompany1();
        vo.setApplyViewCriteriaName("filterByNameVC");
        vo.setpName(name);
        vo.executeQuery();
        System.out.println(vo.getQuery());
        vo.setApplyViewCriteriaName(null);
        return this.convertRowsToJsonArray(vo.getAllRowsInRange(), ATTR_FILTER_BY_NAME);
    }

}
