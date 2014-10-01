package com.lance.view.rest.user;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.LancerVOImpl;
import com.lance.model.vo.LancerVORowImpl;
import com.lance.model.vo.LoginUserVOImpl;
import com.lance.model.vo.LoginUserVORowImpl;
import com.lance.view.util.LanceRestUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import oracle.jbo.Row;

import org.apache.commons.lang.StringUtils;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Lancer（独立工作者/供应商）
 * 包含注册，更新，删除，修改
 * 
 */
@Path("user/lancer")
public class LancerResource extends BaseRestResource {

    /**
     * 字段说明
     * TrueName真名（不可修改）
     * DisplayName显示名（可修改）
     * Country国家（44中国）
     * AccountType账户类型：0独立，1供应商（可填写公司）
     * CompanyId 公司ID（新增人员和修改公司时不需要提供，在提供公司Name后会自动生成）
     * CompanyName 公司名
     */
    public static final String[] ATTR_CREATE = {
        "UserName", "Email", "Password", "DisplayName", "Country", "TrueName", "AccountType", "CompanyName"
    };
    public static final String[] ATTR_UPDATE = { "Email", "DisplayName", "Country", "AccountType", "CompanyName" };
    public static final String[] ATTR_GET = {
        "Uuid", "UserName", "Email", "DisplayName", "Country", "TrueName", "AccountType", "CompanyId", "CompanyName"
    };

    public LancerResource() {
    }

    /**
     * 新增Lancer 用户注册
     * POST: http://localhost:7101/lance/res/user/lancer
     * 
     * AccountType：0 独立；1 公司
     * 当AccountType=1时，必须录入公司名
     * 公司名CompanyName，会在后台执行merge操作（不存在则创建）
     *
     * @example
     *  {
            "UserName" : "huateng",
            "Email" : "tencent@qq.com",
            "DisplayName" : "疼讯",
            "Country" : "1",
            "TrueName" : "小疼",
            "AccountType" : 1,
            "CompanyName" : "深圳腾讯"
        }
     *
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createNewLancer(JSONObject json) throws JSONException {
        System.out.println("createNewLancer");
        LanceRestAMImpl am = LanceRestUtil.findLanceAM();
        LancerVOImpl lancerVO = am.getLancer1();
        Row lancerRow = lancerVO.createRow();
        lancerVO.insertRow(lancerRow);
        for (String attr : ATTR_CREATE) {
            if ("Password,CompanyName".indexOf(attr) > 0) {
                continue;
            }
            if (json.has(attr)) {
                lancerRow.setAttribute(attr, json.get(attr));
            }
        }

        //创建登录信息
        LoginUserVOImpl loginUserVO = am.getLoginUser1();
        LoginUserVORowImpl loginUserRow = (LoginUserVORowImpl) loginUserVO.createRow();
        loginUserVO.insertRow(loginUserRow);
        loginUserRow.setUserName(json.getString("UserName"));
        loginUserRow.setType(0);//0:Lancer/供应商  1：:需求方
        loginUserRow.setUserId((String) lancerRow.getAttribute("Uuid"));

        //如果lancer属于供应商（公司），则merge（存在返回，不存在创建）该公司，并设置注册用户公司id
        int accountType = (Integer) json.get("AccountType");//0独立，1供应商
        if (accountType == 1) {
            JSONObject companyJson=new JSONObject();
            companyJson.put("Name", json.get("CompanyName"));
            String companyId = new CompanyResource().mergeCompanyByName(companyJson);
            lancerRow.setAttribute("CompanyId", companyId);
        }

        am.getDBTransaction().commit();
        return (String) lancerRow.getAttribute("Uuid"); //返回新增记录的ID
    }

    /**
     * 根据userId获取用户
     * GET http://localhost:7101/lance/res/user/lancer/{userId}
     * 
     * @example
     *例子1
     GET http://localhost:7101/lance/res/user/lancer/unknowuserid
      {
            "err" : "找不到用户:unknowuserid"
      }

    例子2
     GET: http://localhost:7101/lance/res/user/lancer/92163f9001cc41ed8e572a5aa46934ce
     {
         "Uuid" : "92163f9001cc41ed8e572a5aa46934ce",
         "UserName" : "muhongdi",
         "Email" : "muhongdi@qq.com",
         "DisplayName" : "天涯月",
         "Country" : "1",
         "TrueName" : "牟宏迪",
         "AccountType" : 0
     }

     * @param userId
     * @return
     * @throws JSONException
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{userId}")
    public JSONObject findLancerById(@PathParam("userId") String userId) throws JSONException {
        System.out.println("findLancerById:" + userId); //获取不到id？
        LanceRestAMImpl am = LanceRestUtil.findLanceAM();
        LancerVOImpl lancerVO = am.getLancer1();
        lancerVO.setApplyViewCriteriaName("FindByUuidVC");
        lancerVO.setpUuid(userId);
        lancerVO.executeQuery();
        lancerVO.setApplyViewCriteriaName(null);

        if (lancerVO.first() == null) {
            JSONObject res = new JSONObject();
            res.put("err", "找不到用户:" + userId);
            return res;
        }
        Row lancerRow = lancerVO.first();
        return this.convertRowToJsonObject(lancerRow, ATTR_GET);
    }

    /**
     * 删除用户
     * POST http://localhost:7101/lance/res/user/lancer/delete/{userId}
     * 
     * @param userId
     * @return ok 删除成功
     */
    @POST
    @Path("delete/{userId}")
    public String deleteLancer(@PathParam("userId") String userId) {
        System.out.println("deleteLancer:" + userId);
        LanceRestAMImpl am = LanceRestUtil.findLanceAM();
        LancerVOImpl lancerVO = am.getLancer1();
        lancerVO.setApplyViewCriteriaName("FindByUuidVC");
        lancerVO.setpUuid(userId);
        lancerVO.executeQuery();
        lancerVO.setApplyViewCriteriaName(null);

        if (lancerVO.first() == null) {
            return "error:找不到用户:" + userId;
        }

        Row lancerRow = lancerVO.first();
        lancerVO.setCurrentRow(lancerRow);
        lancerVO.removeCurrentRow();
        am.getDBTransaction().commit();
        return "ok";
    }

    /**
     * 更新用户
     * POST http://localhost:7101/lance/res/user/lancer/update/{userId}
     * 
     * 修改公司时需要清除CompanyId,如果CompanyId为空程序会根据传入的CompanyName执行merge Company操作
     * AccountType为0时会清空Company信息，为1时才会处理Company
     * 
     * @param userId
     * @param json
     * @return ok 更新成功
     * @throws JSONException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("update/{userId}")
    public String updateLancer(@PathParam("userId") String userId, JSONObject json) throws JSONException {
        System.out.println("updateLancer:" + userId);
        LanceRestAMImpl am = LanceRestUtil.findLanceAM();
        LancerVOImpl lancerVO = am.getLancer1();
        lancerVO.setApplyViewCriteriaName("FindByUuidVC");
        lancerVO.setpUuid(userId);
        lancerVO.executeQuery();
        lancerVO.setApplyViewCriteriaName(null);

        if (lancerVO.first() == null) {
            return "error:找不到用户:" + userId;
        }

        LancerVORowImpl lancerRow = (LancerVORowImpl) lancerVO.first();
        for (String attr : ATTR_UPDATE) {

            if (json.has(attr)) {
                lancerRow.setAttribute(attr, json.get(attr));
            }
        }

        //如果lancer属于供应商（公司）,且公司ID被清除（修改过公司名），则merge（存在返回，不存在创建）该公司，并设置注册用户公司id
        if (lancerRow.getAccountType() == 1 && json.isNull("CompanyId")  ) {
            JSONObject companyJson=new JSONObject();
            companyJson.put("Name", json.get("CompanyName"));
            String companyId = new CompanyResource().mergeCompanyByName(companyJson);
            lancerRow.setAttribute("CompanyId", companyId);
        } else if (lancerRow.getAccountType() == 0) {
            lancerRow.setAttribute("CompanyId", null);
        }

        am.getDBTransaction().commit();
        return "ok";
    }


}
