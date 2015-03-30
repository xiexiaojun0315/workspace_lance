package com.lance.view.rest.uuser;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.CompanyVOImpl;
import com.lance.model.vo.CompanyVORowImpl;
import com.lance.view.util.LUtil;

import com.lance.view.util.RestSecurityUtil;

import com.zngh.platform.front.core.view.BaseRestResource;
import com.zngh.platform.front.core.view.RestUtil;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.Row;
import oracle.jbo.server.RowImpl;
import oracle.jbo.server.ViewObjectImpl;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("company")
public class CompanyResource extends BaseRestResource {
    public final ADFLogger LOGGER = ADFLogger.createADFLogger(CompanyResource.class);

    public static final String[] ATTR_FILTER_BY_NAME = { "Name" };

    public static final String[] ATTR_ALL = {
        "Uuid", "Name", "EnterpriseProperty", "EmployeeNumberGrade", "FoundYear", "AboutCompany", "ServiceDesc",
        "Location", "Video", "Logo"
    };

    public static final String[] ATTR_GET = {
        "Uuid", "Name", "EnterpriseProperty", "EmployeeNumberGrade", "FoundYear", "AboutCompany", "ServiceDesc",
        "Location", "Video", "Logo"
    }; 

    public static final String[] ATTR_UPDATE = {
        "Name", "EnterpriseProperty", "EmployeeNumberGrade", "FoundYear", "AboutCompany", "ServiceDesc", "Location",
        "Video", "Logo"
    };
    public static final String[] ATTR_CREATE = {
        "Name", "EnterpriseProperty", "EmployeeNumberGrade", "FoundYear", "AboutCompany", "ServiceDesc", "Location",
        "Video", "Logo"
    }; 

    public static final boolean CAN_DELETE = true;


    public CompanyResource() {
    }
    
    public ViewObjectImpl getCompanyFromAM(LanceRestAMImpl am) {
        return am.getCompany1();
    }

    public Row findCompanyById(String id, ViewObjectImpl vo, LanceRestAMImpl am) {
        vo.setApplyViewCriteriaName("FindByUuidVC");
        vo.ensureVariableManager().setVariableValue("pUuid", id);//todo changed
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("FindByUuidVC"); //todo
        return vo.first();
    }

    public String returnParamAfterCreate(Row row) {
        return (String) row.getAttribute("Uuid");
    }
    

    /**
     * 传入一个包含公司名Name的JSON，如果公司存在则返回该公司ID，不存在则创建一条并返回ID
     *
     * 例子
     * POST http://localhost:7101/lance/res/company/mergeByName
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
        LanceRestAMImpl am = LUtil.findLanceAM();
        am.getDBTransaction().commit();
        return uuid;
    }

    /**
     * 同上，但不提交数据
     * @param companyName
     * @return
     */
    public String mergeCompanyByName(String companyName) {
        LanceRestAMImpl am = LUtil.findLanceAM();
        CompanyVOImpl vo = am.getCompany1();
        vo.setApplyViewCriteriaName("FindByNameVC");
        vo.setpName(companyName);
        vo.executeQuery();
        vo.setApplyViewCriteriaName(null);

        if (vo.getRowCount() > 0) {
            return (String) vo.first().getAttribute("Uuid");
        } else {
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "新建公司 mergeCompanyByName（" + companyName + "）");
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
     * GET http://localhost:7101/lance/res/company/filter/name/{companyName}
     *
     * @example
     * GET http://localhost:7101/lance/res/company/filter/name/度
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
        LanceRestAMImpl am = LUtil.findLanceAM();
        CompanyVOImpl vo = am.getCompany1();
        vo.setApplyViewCriteriaName("filterByNameVC");
        vo.setpName(name);
        vo.executeQuery();
        System.out.println(vo.getQuery());
        vo.setApplyViewCriteriaName(null);
        return this.convertRowsToJsonArray(vo.getAllRowsInRange(), ATTR_FILTER_BY_NAME);
    }

    
    //------------------------------以下是标准代码--------------------------

    /**
     * 根据ID查询公司
     * @param companyId
     * @return
     * @throws JSONException
     */
    @GET
    @Path("{companyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCompanyById(@PathParam("companyId") String companyId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getCompanyFromAM(am);
        Row row = findCompanyById(companyId, vo, am);

        if (row == null) {
            String msg = "公司（" + companyId + "）对应的记录不存在或已被删除";
            JSONObject res = new JSONObject();
            res.put("msg", msg);
            return res;
        }

        return RestUtil.convertRowToJsonObject(vo, row, this.ATTR_GET);
    }


    /**
     * 更新
     * 
     * POST  http://localhost:7101/lance/res/company/update/507f2cc755e84001858f0dea85ecec7d
        {
        "Name" : "驻才网3.5",
        "EnterpriseProperty" : "2",
        "EmployeeNumberGrade" : "1"
        }
        返回 ok
     * @param companyId
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Path("update/{companyId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateCompany(@PathParam("companyId") String companyId, JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getCompanyFromAM(am);
        Row row = findCompanyById(companyId, vo, am);

        if (row == null) {
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "尝试修改公司" + companyId + "但此记录不存在");
            return "msg:can't find Company by id " + companyId;
        }
        if (!RestSecurityUtil.isOwner(row)) {
            String msg = "您没有修改此记录的权限";
            return "msg:" + msg;
        }

        RestUtil.copyJsonObjectToRow(json, vo, row, this.ATTR_UPDATE);
        am.getDBTransaction().commit();

        LOGGER.log(LOGGER.NOTIFICATION,
                   findCurrentUserName() + "修改公司(" + companyId + ") 为：" +
                   RestUtil.convertRowToJsonObject(vo, row, this.ATTR_ALL));
        return "ok";
    }

    /**
     * 创建
     * POST  http://localhost:7101/lance/res/company
        {
            "Name" : "驻才网3",
        "EnterpriseProperty" : "1",
        "EmployeeNumberGrade" : "3"
        }
        返回 507f2cc755e84001858f0dea85ecec7d
     * 
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createCompany(JSONObject json) throws JSONException {
        LOGGER.log(LOGGER.NOTIFICATION, "create Company");
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getCompanyFromAM(am);
        RowImpl row = LUtil.createInsertRow(vo);
        RestUtil.copyJsonObjectToRow(json, vo, row, this.ATTR_CREATE);
        am.getDBTransaction().commit();
        LOGGER.log(LOGGER.TRACE, "copyJsonObjectToRow :" + this.ATTR_CREATE);
        String res = returnParamAfterCreate(row);
        LOGGER.log(LOGGER.NOTIFICATION, "Company created by return :" + res);
        return res;
    }

    /**
     * 删除
     * POST  http://localhost:7101/lance/res/company/{companyId}
     * return "ok"
     * 
     * @param companyId
     * @return
     * @throws JSONException
     */
    @POST
    @Path("delete/{companyId}")
    public String deleteCompany(@PathParam("companyId") String companyId) throws JSONException {
        if (!CAN_DELETE) {
            return "msg:此类记录无法被删除";
        }

        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObjectImpl vo = getCompanyFromAM(am);
        Row row = findCompanyById(companyId, vo, am);
        if (row != null) {
            //权限判断
            if (!RestSecurityUtil.isOwner(row)) {
                String msg = findCurrentUserName() + "无删除公司" + companyId + "的权限";
                LOGGER.log(LOGGER.WARNING, msg);
                return "msg:" + msg;
            }

            vo.setCurrentRow(row);
            vo.removeCurrentRow();
            am.getDBTransaction().commit();
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "删除了公司" + companyId);
        } else {
            LOGGER.log(LOGGER.NOTIFICATION, findCurrentUserName() + "尝试删除公司" + companyId + "但此记录不存在");
        }
        return "ok";
    }

}
