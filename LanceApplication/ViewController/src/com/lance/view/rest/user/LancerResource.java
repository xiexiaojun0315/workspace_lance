//package com.lance.view.rest.user;
//
//import com.lance.model.LanceRestAMImpl;
//import com.lance.model.vo.LancerVOImpl;
//import com.lance.model.vo.LancerVORowImpl;
//import com.lance.model.vo.LoginUserRoleGrantsVOImpl;
//import com.lance.model.vo.LoginUserRoleGrantsVORowImpl;
//import com.lance.model.vo.LoginUserVOImpl;
//import com.lance.model.vo.LoginUserVORowImpl;
//import com.lance.view.rest.uuser.UserLocationListResource;
//import com.lance.view.util.LUtil;
//
//import com.zngh.platform.front.core.view.BaseRestResource;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.core.MediaType;
//
//import oracle.jbo.Key;
//import oracle.jbo.Row;
//
//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;
//
///**
// * Lancer（独立工作者/供应商）
// * 包含注册，更新，删除，修改
// *
// * update 2014年10月5日 为Lancer增加LocationA、LocationB两个字段；新增加Lancer时，会初始化setting和2个location表记录
// *
// */
//@Path("user/lancer")
//public class LancerResource extends BaseRestResource {
//
//    /**
//     * 字段说明
//     * TrueName真名
//     * DisplayName显示名（可修改）
//     * Country国家（44中国）
//     * AccountType账户类型：0独立，1供应商（可填写公司）
//     * CompanyId 公司ID（新增人员和修改公司时不需要提供，在提供公司Name后会自动生成）
//     * CompanyName 公司名
//     */
//    public static final String[] ATTR_CREATE = {
//        "UserName", "Email", "Password", "DisplayName", "Country", "TrueName", "AccountType", "CompanyName",
//        "PhoneNumber", "WebsiteUrl", "ImNumberA", "ImNumberB", "ImNumberC", "ImTypeA", "ImTypeB", "ImTypeC"
//    };
//    public static final String[] ATTR_UPDATE = {
//        "Email", "DisplayName", "Country", "AccountType", "CompanyName", "TrueName", "CompanyId", "LocationA",
//        "LocationB", "PhoneNumber", "WebsiteUrl", "ImNumberA", "ImNumberB", "ImNumberC", "ImTypeA", "ImTypeB", "ImTypeC"
//    };
//    public static final String[] ATTR_GET = {
//        "Uuid", "UserName", "Email", "DisplayName", "Country", "TrueName", "AccountType", "CompanyId", "CompanyName",
//        "PhoneNumber", "WebsiteUrl", "ImNumberA", "ImNumberB", "ImNumberC", "ImTypeA", "ImTypeB", "ImTypeC"
//    };
//    public static final String[] ATTR_GET_A = {
//        "Uuid", "UserName", "Email", "DisplayName", "Country", "TrueName", "AccountType", "CompanyId", "CompanyName",
//        "LocationA", "LocationB", "PhoneNumber", "WebsiteUrl", "ImNumberA", "ImNumberB", "ImNumberC", "ImTypeA",
//        "ImTypeB", "ImTypeC"
//    };
//
//    public LancerResource() {
//    }
//
//    /**
//     * 新增Lancer 用户注册
//     * POST: http://localhost:7101/lance/res/user/lancer
//     * 新增用户的ID不再使用32位UUID，改为与用户登录名相同
//     *
//     * AccountType：0 独立；1 公司
//     * 当AccountType=1时，必须录入公司名
//     * 公司名CompanyName，会在后台执行merge操作（不存在则创建）
//     *
//     * @example
//        {
//         "UserName" : "muhongdi",
//         "Email" : "muhongdi@qq.com",
//         "DisplayName" : "天涯月",
//         "Country" : "44",
//         "TrueName" : "牟宏迪",
//         "AccountType" : 1,//为0时后台会忽略CompanyName
//          "Password" : "welcome1",
//         "CompanyName" : "驻才网"
//        }
//     *
//     * @param json
//     * @return muhongdi
//     * @throws JSONException
//     */
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String createNewLancer(JSONObject json) throws JSONException {
//        System.out.println("createNewLancer");
//        LanceRestAMImpl am = LUtil.findLanceAM();
//        LancerVOImpl lancerVO = am.getLancer1();
//        Row lancerRow = lancerVO.createRow();
//        lancerVO.insertRow(lancerRow);
//        for (String attr : ATTR_CREATE) {
//            if ("Password,CompanyName".indexOf(attr) > -1) {
//                continue;
//            }
//            if (json.has(attr)) {
//                lancerRow.setAttribute(attr, json.get(attr));
//            }
//        }
//
//        //新增用户的ID不再使用32位UUID，改为与用户登录名相同
//        String uuid = json.getString("UserName");
//        lancerRow.setAttribute("Uuid", uuid);
//
//        //创建登录信息
//        LoginUserVOImpl loginUserVO = am.getLoginUser1();
//        LoginUserVORowImpl loginUserRow = (LoginUserVORowImpl) loginUserVO.createRow();
//        loginUserVO.insertRow(loginUserRow);
//        loginUserRow.setUserName(json.getString("UserName"));
//        loginUserRow.setType(0); //0:Lancer/供应商  1：:需求方
//        loginUserRow.setPassword(json.getString("Password"));
//        loginUserRow.setDisplayName(json.getString("DisplayName"));
//
//        //授权角色
//        LoginUserRoleGrantsVOImpl grantsVo = am.getLoginUserRoleGrants1();
//        LoginUserRoleGrantsVORowImpl grantsRow = (LoginUserRoleGrantsVORowImpl) grantsVo.createRow();
//        grantsRow.setUserName(json.getString("UserName"));
//        if ("0".equals(json.getString("AccountType"))) {
//            grantsRow.setRoleName("lancer");
//        } else if ("1".equals(json.getString("AccountType"))) {
//            grantsRow.setRoleName("company");
//        } else {
//            throw new RuntimeException("无法识别的AccountType：" + json.getString("AccountType"));
//        }
//
//        grantsVo.insertRow(grantsRow);
//
//        //如果lancer属于供应商（公司），则merge（存在返回，不存在创建）该公司，并设置注册用户公司id
//        int accountType = (Integer) json.get("AccountType"); //0独立，1供应商
//        if (accountType == 1) {
//            String companyId = new CompanyResource().mergeCompanyByName(json.getString("CompanyName"));
//            lancerRow.setAttribute("CompanyId", companyId);
//        }
//
//        //创建该用户的联系信息（地址）
//        //创建两条空地址信息
//        UserLocationListResource loc = new UserLocationListResource();
//        lancerRow.setAttribute("LocationA", loc.createLocationFn(uuid, new JSONObject(), am));
//        lancerRow.setAttribute("LocationB", loc.createLocationFn(uuid, new JSONObject(), am));
//
//        //创建个人设置记录
//        LUtil.createInsertRow(am.getLancerSetting1());
//
//        am.getDBTransaction().commit();
//        return (String) lancerRow.getAttribute("Uuid"); //返回新增记录的ID
//    }
//
//    /**
//     * 根据userId获取用户
//     * GET http://localhost:7101/lance/res/user/lancer/{userId}
//     *
//     * @example
//     *例子1
//     GET http://localhost:7101/lance/res/user/lancer/unknowuserid
//     return
//      {
//            "err" : "找不到用户:unknowuserid"
//      }
//
//    例子2
//     GET: http://localhost:7101/lance/res/user/lancer/muhongdi
//
//     return
//     {
//         "Uuid" : "muhongdi",
//         "UserName" : "muhongdi",
//         "Email" : "muhongdi@qq.com",
//         "DisplayName" : "天涯月",
//         "Country" : "44",
//         "TrueName" : "牟宏迪",
//         "AccountType" : 1,
//         "CompanyId" : "79fbae75257946e89d2a22a8d2d38031",
//         "CompanyName" : "驻才网",
//         "PhoneNumber" : "1xxxxxxxxxx",
//         "WebsiteUrl" : "www.xxx.ccc",
//         "ImNumberA" : "123456123",
//         "ImNumberB" : "sdfa231133",
//         "ImNumberC" : "lkjlsadofuo123123",
//         "ImTypeA" : "qq",
//         "ImTypeB" : "msn",
//         "ImTypeC" : "skypet"
//     }
//
//     * @param userId
//     * @return
//     * @throws JSONException
//     */
//    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("{userId}")
//    public JSONObject findLancerById(@PathParam("userId") String userId) throws JSONException {
//        LanceRestAMImpl am = LUtil.findLanceAM();
//        return findLancerByIdFn(userId, am);
//    }
//
//    public static JSONObject findLancerByIdFn(String userId, LanceRestAMImpl am) throws JSONException {
//        System.out.println("findLancerById:" + userId); //获取不到id？
//
//        LancerVOImpl lancerVO = am.getLancer1();
//        lancerVO.setApplyViewCriteriaName("FindByUuidVC");
//        lancerVO.setpUuid(userId);
//        lancerVO.executeQuery();
//        lancerVO.setApplyViewCriteriaName(null);
//
//        if (lancerVO.first() == null) {
//            JSONObject res = new JSONObject();
//            res.put("err", "找不到用户:" + userId);
//            return res;
//        }
//        Row lancerRow = lancerVO.first();
//        return convertRowToJsonObject(lancerRow, ATTR_GET);
//    }
//
//    public JSONObject findLancerWithLocationFn(String userId, LanceRestAMImpl am) throws JSONException {
//        System.out.println("findLancerById:" + userId); //获取不到id？
//
//        LancerVOImpl lancerVO = am.getLancer1();
//        lancerVO.setApplyViewCriteriaName("FindByUuidVC");
//        lancerVO.setpUuid(userId);
//        lancerVO.executeQuery();
//        lancerVO.setApplyViewCriteriaName(null);
//
//        if (lancerVO.first() == null) {
//            JSONObject res = new JSONObject();
//            res.put("err", "找不到用户:" + userId);
//            return res;
//        }
//
//        //处理位置信息
//        Row lancerRow = lancerVO.first();
//        JSONObject json = this.convertRowToJsonObject(lancerRow, ATTR_GET_A);
//        UserLocationListResource loc = new UserLocationListResource();
//        json.put("LocationA", loc.findLocation(userId, json.getString("LocationA")));
//        json.put("LocationB", loc.findLocation(userId, json.getString("LocationB")));
//        return json;
//    }
//
//
//    /**
//     * 删除用户
//     * POST http://localhost:7101/lance/res/user/lancer/delete/{userId}
//     *
//     * @param userId
//     * @return ok 删除成功
//     */
//    @POST
//    @Path("delete/{userId}")
//    public String deleteLancer(@PathParam("userId") String userId) {
//        System.out.println("deleteLancer:" + userId);
//        LanceRestAMImpl am = LUtil.findLanceAM();
//        LancerVOImpl lancerVO = am.getLancer1();
//        lancerVO.setApplyViewCriteriaName("FindByUuidVC");
//        lancerVO.setpUuid(userId);
//        lancerVO.executeQuery();
//        lancerVO.setApplyViewCriteriaName(null);
//
//        if (lancerVO.first() == null) {
//            return "error:找不到用户:" + userId;
//        }
//
//        Row lancerRow = lancerVO.first();
//        lancerVO.setCurrentRow(lancerRow);
//        lancerVO.removeCurrentRow();
//
//        //删除登陆表用户
//        LoginUserVOImpl vo2 = am.getLoginUser1();
//        vo2.setpUserName(userId);
//        vo2.setApplyViewCriteriaName("FindByUserIdVC");
//        vo2.executeQuery();
//        vo2.removeApplyViewCriteriaName("FindByUserIdVC");
//        Row row2 = vo2.first();
//        vo2.setCurrentRow(row2);
//        vo2.removeCurrentRow();
//
//        am.getDBTransaction().commit();
//        return "ok";
//    }
//
//    /**
//     * 更新用户
//     * POST http://localhost:7101/lance/res/user/lancer/update/{userId}
//     *
//     * 修改公司时需要清除CompanyId,如果CompanyId为空程序会根据传入的CompanyName执行merge Company操作
//     * AccountType为0时会清空Company信息，为1时才会处理Company
//     *
//     * @example
//     * POST http://localhost:7101/lance/res/user/lancer/update/muhongdi
//     *
//     {
//         "Uuid" : "muhongdi",
//         "UserName" : "muhongdi",
//         "Email" : "muhongdi@qq.com",
//         "DisplayName" : "天涯月",
//         "Country" : "44",
//         "TrueName" : "牟宏迪",
//         "AccountType" : 1,
//         "CompanyId" : "79fbae75257946e89d2a22a8d2d38031",
//         "CompanyName" : "驻才网",
//         "PhoneNumber" : "12323xxx",
//         "WebsiteUrl" : "www1233cc",
//         "ImNumberA" : "123456123",
//         "ImNumberB" : "sdfa231133",
//         "ImNumberC" : "111123123",
//         "ImTypeA" : "qq",
//         "ImTypeB" : "msn",
//         "ImTypeC" : "skypet"
//     }
//
//     *
//     * @param userId
//     * @param json
//     * @return ok 更新成功
//     * @throws JSONException
//     */
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("update/{userId}")
//    public String updateLancer(@PathParam("userId") String userId, JSONObject json) throws JSONException {
//        System.out.println("updateLancer:" + userId);
//        LanceRestAMImpl am = LUtil.findLanceAM();
//        updateLancerFn(userId, json, am);
//        am.getDBTransaction().commit();
//        return "ok";
//    }
//
//
//    public String updateLancerFn(@PathParam("userId") String userId, JSONObject json,
//                                 LanceRestAMImpl am) throws JSONException {
//        LancerVOImpl lancerVO = am.getLancer1();
//        lancerVO.setApplyViewCriteriaName("FindByUuidVC");
//        lancerVO.setpUuid(userId);
//        lancerVO.executeQuery();
//        lancerVO.setApplyViewCriteriaName(null);
//
//        if (lancerVO.first() == null) {
//            return "error:找不到用户:" + userId;
//        }
//
//        LancerVORowImpl lancerRow = (LancerVORowImpl) lancerVO.first();
//        for (String attr : ATTR_UPDATE) {
//            if ("CompanyId,CompanyName,LocationA,LocationB".indexOf(attr) != -1) {
//                continue;
//            }
//            if (json.has(attr)) {
//                lancerRow.setAttribute(attr, json.get(attr));
//            }
//        }
//
//        //如果lancer属于供应商（公司）,且公司ID被清除（修改过公司名），则merge（存在返回，不存在创建）该公司，并设置注册用户公司id
//        if (lancerRow.getAccountType() == 1 && json.isNull("CompanyId") && json.has("CompanyName")) {
//            String companyId = new CompanyResource().mergeCompanyByName(json.getString("CompanyName"));
//            lancerRow.setAttribute("CompanyId", companyId);
//        } else if (lancerRow.getAccountType() == 0) {
//            lancerRow.setAttribute("CompanyId", null);
//        }
//
//        //同时修改地址信息
//        UserLocationListResource loc = new UserLocationListResource();
//        if (json.has("LocationA")) {
//            loc.updateLocationFn(userId, json.getJSONObject("LocationA"), am);
//        }
//        if (json.has("LocationB")) {
//            loc.updateLocationFn(userId, json.getJSONObject("LocationB"), am);
//        }
//
//        //同时修改登录信息
//        if (json.has("DisplayName")) {
//            LoginUserVOImpl loginUserVO = am.getLoginUser1();
//            LoginUserVORowImpl loginUserRow = (LoginUserVORowImpl) loginUserVO.findByKey(new Key(new Object[] {
//                                                                                                 userId }), 1)[0];
//            loginUserRow.setDisplayName(json.getString("DisplayName"));
//        }
//        
//        return "ok";
//    }
//
//}
