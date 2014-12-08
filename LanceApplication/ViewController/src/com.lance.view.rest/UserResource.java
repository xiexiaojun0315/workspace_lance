package com.lance.view.rest;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.LoginUserRoleGrantsVOImpl;
import com.lance.model.vo.LoginUserRoleGrantsVORowImpl;
import com.lance.model.vo.UUserVOImpl;
import com.lance.model.vo.UUserVORowImpl;
import com.lance.view.rest.user.CompanyResource;
import com.lance.view.rest.user.LancerLocationResource;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("user")
public class UserResource extends BaseRestResource {

    //todo 完善字段
    public static final String[] ATTR_CREATE = {
        "UserName", "Email", "Password", "DisplayName", "Country", "TrueName", "AccountType", "CompanyName",
        "PhoneNumber", "WebsiteUrl", "ImNumberA", "ImNumberB", "ImNumberC", "ImTypeA", "ImTypeB", "ImTypeC"
    };

    public static final String[] ATTR_UPDATE = {
        "Email", "DisplayName", "Country", "AccountType", "CompanyName", "TrueName", "CompanyId", "LocationA",
        "LocationB", "PhoneNumber", "WebsiteUrl", "ImNumberA", "ImNumberB", "ImNumberC", "ImTypeA", "ImTypeB", "ImTypeC"
    };

    public static final String[] ATTR_GET = {
        "Uuid", "UserName", "Email", "DisplayName", "Country", "TrueName", "AccountType", "CompanyId", "CompanyName",
        "PhoneNumber", "WebsiteUrl", "ImNumberA", "ImNumberB", "ImNumberC", "ImTypeA", "ImTypeB", "ImTypeC"
    };

    public static final String[] ATTR_GET_A = {
        "Uuid", "UserName", "Email", "DisplayName", "Country", "TrueName", "AccountType", "CompanyId", "CompanyName",
        "LocationA", "LocationB", "PhoneNumber", "WebsiteUrl", "ImNumberA", "ImNumberB", "ImNumberC", "ImTypeA",
        "ImTypeB", "ImTypeC"
    };

    public UserResource() {
    }

    /**
     * 用户注册
     * todo 验证码 记录ip
     *
     *
     * @param json
     * @return
     * @throws JSONException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createNewUser(JSONObject json) throws JSONException {
        System.out.println("createNewUser");
        LanceRestAMImpl am = LUtil.findLanceAM();
        UUserVOImpl vo = am.getUUser1();
        UUserVORowImpl row = (UUserVORowImpl) vo.createRow();
        vo.insertRow(row);
        for (String attr : ATTR_CREATE) {
            if ("CompanyName".equals(attr)) {
                //如果存在CompanyName，则执行merge操作
                String companyId = new CompanyResource().mergeCompanyByName(json.getString("CompanyName"));
                row.setAttribute("CompanyId", companyId);
                continue;
            } else if (json.has(attr)) {
                row.setAttribute(attr, json.get(attr));
            }
        }

        String userName = json.getString("UserName");

        //创建该用户的联系信息（地址）
        //创建两条空地址信息
        LancerLocationResource loc = new LancerLocationResource();
        row.setAttribute("LocationA", loc.createLocationFn(userName, new JSONObject(), am));
        row.setAttribute("LocationB", loc.createLocationFn(userName, new JSONObject(), am));

        //授权角色
        if ("client,lancer".indexOf(json.getString("DefaultRole")) != -1) {
            LoginUserRoleGrantsVOImpl grantsVo = am.getLoginUserRoleGrants1();
            LoginUserRoleGrantsVORowImpl grantsRow = (LoginUserRoleGrantsVORowImpl) grantsVo.createRow();
            grantsRow.setUserName(json.getString("UserName"));
            //DefaultRole只允许设置基础角色
            grantsRow.setRoleName(json.getString("DefaultRole"));
            grantsVo.insertRow(grantsRow);
        }

        am.getDBTransaction().commit();
        return json.getString("UserName"); //返回新增记录的ID
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{userName}")
    public JSONObject findUserById(@PathParam("userName") String userName) throws JSONException {
        System.out.println("findUserById:" + userName);
        LanceRestAMImpl am = LUtil.findLanceAM();
        return findUserByNameInJsonFn(userName, am);
    }

    public static JSONObject findUserByNameInJsonFn(String userName, LanceRestAMImpl am) throws JSONException {
        System.out.println("findUserByIdFn:" + userName);
        UUserVORowImpl row = findUserByNameFn(userName, am);

        if (row == null) {
            JSONObject res = new JSONObject();
            res.put("err", "找不到用户:" + userName);
            return res;
        }

        //处理位置信息
        JSONObject json = convertRowToJsonObject(row, ATTR_GET_A);
        LancerLocationResource loc = new LancerLocationResource();
        json.put("LocationA", loc.findLocation(userName, json.getString("LocationA")));
        json.put("LocationB", loc.findLocation(userName, json.getString("LocationB")));

        return convertRowToJsonObject(row, ATTR_GET);
    }

    public static UUserVORowImpl findUserByNameFn(String userName, LanceRestAMImpl am) {
        UUserVOImpl vo = am.getUUser1();
        vo.setApplyViewCriteriaName("FindByUserNameVC");
        vo.setpUserName(userName);
        vo.executeQuery();
        vo.setApplyViewCriteriaName(null);
        return (UUserVORowImpl) vo.first();
    }

    @POST
    @Path("delete/{userName}")
    public String deleteUser(@PathParam("userName") String userName) {
        System.out.println("deleteUser:" + userName);
        LanceRestAMImpl am = LUtil.findLanceAM();
        UUserVOImpl vo = am.getUUser1();

        UUserVORowImpl row = findUserByNameFn(userName, am);
        if (row == null) {
            return "error:找不到用户:" + userName;
        }
        vo.removeCurrentRow();

        am.getDBTransaction().commit();
        return "ok";
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("update/{userName}")
    public String updateUser(@PathParam("userName") String userName, JSONObject json) throws JSONException {
        System.out.println("updateUser:" + userName);
        LanceRestAMImpl am = LUtil.findLanceAM();

        UUserVORowImpl row = findUserByNameFn(userName, am);
        if (row == null) {
            return "error:找不到用户:" + userName;
        }

        for (String attr : ATTR_UPDATE) {
            if ("CompanyId,CompanyName,LocationA,LocationB".indexOf(attr) != -1) {
                continue;
            } else if (json.has(attr)) {
                row.setAttribute(attr, json.get(attr));
            }
        }

        /**
         * 如果公司信息没变，可返回原来的CompanyId或不传CompanyId
         * 
         * 如果返回了CompanyId，且与原来的CompanyId不同，则做如下判断：
         * 如果没有CompanyName，则代表没有公司，清空当前用户的CompanyId
         * 如果有CompanyName，则重新merge CompanyName
         */
        if (json.has("CompanyId")) {
            //如果公司ID与原来的值不同，则重新merge companyName
            if (!json.getString("CompanyId").equals(row.getCompanyId())) {
                String newName = json.getString("CompanyName");
                if (StringUtils.isBlank(newName)) {
                    //如果没有CompanyName，则代表没有公司
                    row.setCompanyName(null);
                    //todo 定期清理没有使用的公司
                } else {
                    String newCompanyId = new CompanyResource().mergeCompanyByName(json.getString("CompanyName"));
                    row.setAttribute("CompanyId", newCompanyId);
                }
            }
        }

        /**
         * 如果地址信息没变，可返回原来的LocationA、B或不传LocationA、B
         * 如果返回了LocationA、B，且
         * 地址Uuid为"chagned"，则
         */
        LancerLocationResource loc = new LancerLocationResource();
        if (json.has("LocationA")) {
            loc.updateLocationFn(userName, json.getJSONObject("LocationA"), am);
        }
        if (json.has("LocationB")) {
            loc.updateLocationFn(userName, json.getJSONObject("LocationB"), am);
        }

        am.getDBTransaction().commit();
        return "ok";
    }
    
    
}
