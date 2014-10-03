package com.lance.view.rest.user;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.ClientUserVOImpl;
import com.lance.model.vo.ClientUserVORowImpl;
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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("user/client")
public class ClientResource extends BaseRestResource {
    
    /**
     * 字段说明
     * TrueName真名（不可修改）
     * DisplayName显示名（可修改）
     * Brief：简介
     * （开发注册页面时先忽略公司信息）
     */
    public static final String[] ATTR_CREATE = { "TrueName", "Email", "UserName", "DisplayName", "Password" };
    public static final String[] ATTR_UPDATE = { "DisplayName" };
    public static final String[] ATTR_GET = { "Uuid", "TrueName", "Email", "UserName", "DisplayName" };

    public ClientResource() {
    }

    /**
     * 新建Client
     *
     * POST http://localhost:7101/lance/res/user/client
     *
     * @example
         {
             "TrueName" : "souhu",
             "Email" : "test@.souhu.com",
             "UserName" : "souhu",
             "DisplayName" : "搜狐"
         }
     * Return
     * 2b34c8b5e6944202a6a2b86f9e0d3e0e
     *
     *
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createNewClient(JSONObject json) throws JSONException {
        System.out.println("createNewClient");
        LanceRestAMImpl am = LanceRestUtil.findLanceAM();
        ClientUserVOImpl vo = am.getClientUser1();
        ClientUserVORowImpl row = (ClientUserVORowImpl) vo.createRow();
        vo.insertRow(row);
        for (String attr : ATTR_CREATE) {
            if ("Password,CompanyName".indexOf(attr) > -1) {
                continue;
            }
            if (json.has(attr)) {
                row.setAttribute(attr, json.get(attr));
            }
        }

        LoginUserVOImpl loginUserVO = am.getLoginUser1();
        LoginUserVORowImpl loginUserRow = (LoginUserVORowImpl) loginUserVO.createRow();
        loginUserVO.insertRow(loginUserRow);
        loginUserRow.setUserName(json.getString("UserName"));
        loginUserRow.setType(1); //0:Lancer/供应商  1：:需求方
        loginUserRow.setUserId((String) row.getAttribute("Uuid"));

        am.getDBTransaction().commit();
        return (String) row.getAttribute("Uuid"); //返回新增记录的ID
    }

    /**
     * 查询Client
     * http://localhost:7101/lance/res/user/client/{clientId}
     *
     * @example
     * GET http://localhost:7101/lance/res/user/client/1
     * return
     * {
        "Uuid" : "1",
        "TrueName" : "sina.com",
        "Email" : "test@.sina.com",
        "UserName" : "sina",
        "DisplayName" : "新浪"
        }
     *
     * @param userId
     * @return
     * @throws JSONException
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{userId}")
    public JSONObject findClientById(@PathParam("userId") String userId) throws JSONException {
        System.out.println("findClientById:" + userId);
        LanceRestAMImpl am = LanceRestUtil.findLanceAM();
        ClientUserVOImpl vo = am.getClientUser1();
        vo.setApplyViewCriteriaName("FindByUuidVC");
        vo.setpUuid(userId);
        vo.executeQuery();
        vo.setApplyViewCriteriaName(null);

        if (vo.first() == null) {
            JSONObject res = new JSONObject();
            res.put("err", "找不到用户:" + userId);
            return res;
        }
        Row row = vo.first();
        return this.convertRowToJsonObject(row, ATTR_GET);
    }

    /**
     * 删除Client
     * http://localhost:7101/lance/res/user/client/delete/{clientId}
     * 
     * @example
     * POST http://localhost:7101/lance/res/user/client/delete/1
     * Return
     * ok
     * 
     * @param userId
     * @return
     */
    @POST
    @Path("delete/{userId}")
    public String deleteClient(@PathParam("userId") String userId) {
        System.out.println("deleteClient:" + userId);
        LanceRestAMImpl am = LanceRestUtil.findLanceAM();
        ClientUserVOImpl vo = am.getClientUser1();
        vo.setApplyViewCriteriaName("FindByUuidVC");
        vo.setpUuid(userId);
        vo.executeQuery();
        vo.setApplyViewCriteriaName(null);

        if (vo.first() == null) {
            return "error:找不到用户:" + userId;
        }

        Row row = vo.first();
        vo.setCurrentRow(row);
        vo.removeCurrentRow();
        am.getDBTransaction().commit();
        return "ok";
    }

    /**
     *更新Client
     *POST http://localhost:7101/lance/res/user/client/update/{clientId}
     *
     *@example
     *POST http://localhost:7101/lance/res/user/client/update/1
      {
            "DisplayName" : "新浪2"
        }

     Return
      ok
     *
     * @param userId
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("update/{userId}")
    public String updateClient(@PathParam("userId") String userId, JSONObject json) throws JSONException {
        System.out.println("updateClient:" + userId);
        LanceRestAMImpl am = LanceRestUtil.findLanceAM();
        ClientUserVOImpl vo = am.getClientUser1();
        vo.setApplyViewCriteriaName("FindByUuidVC");
        vo.setpUuid(userId);
        vo.executeQuery();
        vo.setApplyViewCriteriaName(null);

        if (vo.first() == null) {
            return "error:找不到用户:" + userId;
        }

        ClientUserVORowImpl row = (ClientUserVORowImpl) vo.first();
        for (String attr : ATTR_UPDATE) {
            if (json.has(attr)) {
                row.setAttribute(attr, json.get(attr));
            }
        }

        am.getDBTransaction().commit();
        return "ok";
    }

}
