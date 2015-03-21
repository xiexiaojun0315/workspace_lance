//package com.lance.view.rest.user;
//
//import com.lance.model.LanceRestAMImpl;
//import com.lance.view.rest.uuser.UserEducationResource;
//import com.lance.view.rest.uuser.UserSkillResource;
//import com.lance.view.util.LUtil;
//
//import com.zngh.platform.front.core.view.BaseRestResource;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//
//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;
//
///**
// * 1 查询全部个人信息Overview查询
// * 2 查询、修改BasicInfo
// * 3 查询、修改ContactInfo
// */
//@Path("user/lancer/profile")
//public class LancerProfileResource extends BaseRestResource{
//
//    public LancerProfileResource() {
//    }
//
//    /**
//     * 修改个人BasicProfile（基本信息）
//     * 同时对Lancer与LancerResume进行merge操作
//     * POST http://localhost:7101/lance/res/lancer/profile/basicInfo/merge/{lancerId}
//     * 已知bug：暂时不支持在这里修改CompanyName，不影响业务
//     *
//     *
//     * 传入的json结构如下
//     * {
//     * lancer:{},//用于lancer个人信息修改
//     * lancerResume:{}//用于lancerResume修改
//     * }
//     *
//     * @example
//     * POST http://localhost:7101/lance/res/lancer/profile/basicInfo/merge/muhongdi
//     *
//     * {
//            "lancerResume" : {
//                "ChargeRate" : 102.13,
//                "HourlyRate" : 101,
//                "Keywords" : "专家,Test，replace中文逗号为英文逗号",
//                "Overview" : "这是overview",
//                "PaymentTermsTxt" : "这是支付方式描述",
//                "ServiceDescriptionTxt" : "这是服务描述",
//                "Tagline" : "这是tagLine",
//                "Uuid" : "1",
//                "LancerId" : "muhongdi"
//            },
//            "lancer" : {
//                "Uuid" : "muhongdi",
//                "UserName" : "muhongdi",
//                "Email" : "muhongdi@qq.com",
//                "DisplayName" : "天涯月",
//                "Country" : "4",
//                "TrueName" : "牟宏迪",
//                "AccountType" : 1,
//                "CompanyId" : "beb3b27ea5df4fd8b48b85d6ffbee986",
//                "CompanyName" : "才才网"
//            }
//        }
//
//     *
//     * @param json
//     * @param lancerId
//     * @return
//     * @throws JSONException
//     */
//    @POST
//    @Path("basicInfo/merge/{lancerId}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String mergeBasicProfile(JSONObject json, @PathParam("lancerId") String lancerId) throws JSONException {
//        LanceRestAMImpl am = LUtil.findLanceAM();
//        System.out.println(json.has("lancerResume"));
//        System.out.println(json.isNull("lancer"));
//        if (json.has("lancer")) {
//            new LancerResource().updateLancerFn(lancerId, json.getJSONObject("lancer"), am);
//        }
//        if (json.has("lancerResume")) {
//            new LancerResumeResource().mergeLancerResumeFn(json.getJSONObject("lancerResume"), lancerId, am);
//        }
//        am.getDBTransaction().commit();
//        return "ok";
//    }
//
//    /**
//     *
//     * 根据Lancer获取BasicProfile（基本信息）
//     * 包括Lancer的个人信息与Resume基本简历信息
//     * 如果用户尚未创建过简历，lancerResume:{}
//     *
//     * GET http://localhost:7101/lance/res/user/lancer/profile/basicInfo/{lancerId}
//     *
//     * @example
//     *
//     * GET http://localhost:7101/lance/res/user/lancer/profile/basicInfo/muhongdi
//     *
//     {
//        "lancerResume" : {
//            "ChargeRate" : 100.13,
//            "HourlyRate" : 100,
//            "Keywords" : "专家,Test，replace中文逗号为英文逗号",
//            "Overview" : "这是overview",
//            "PaymentTermsTxt" : "这是支付方式描述",
//            "ServiceDescriptionTxt" : "这是服务描述",
//            "Tagline" : "这是tagLine",
//            "Uuid" : "1",
//            "LancerId" : "muhongdi"
//        },
//        "lancer" : {
//            "Uuid" : "muhongdi",
//            "UserName" : "muhongdi",
//            "Email" : "muhongdi@qq.com",
//            "DisplayName" : "天涯月",
//            "Country" : "44",//中国
//            "TrueName" : "牟宏迪",
//            "AccountType" : 1,
//            "CompanyId" : "beb3b27ea5df4fd8b48b85d6ffbee986",
//            "CompanyName" : "才才网"
//        }
//    }
//
//     *
//     * @param lancerId
//     * @return
//     * @throws JSONException
//     */
//    @GET
//    @Path("basicInfo/{lancerId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public JSONObject getBasicProfile(@PathParam("lancerId") String lancerId) throws JSONException {
//        LanceRestAMImpl am = LUtil.findLanceAM();
//        JSONObject json = new JSONObject();
//        json.put("lancerResume", new LancerResumeResource().findLancerResumeByLancerIdFn(lancerId, am));
//        json.put("lancer", new LancerResource().findLancerByIdFn(lancerId, am));
//        return json;
//    }
//
//    public JSONObject getBasicProfile4CurUser() throws JSONException {
//        String n=this.findCurrentUserId();
//        System.out.println(n);
//        return getBasicProfile(n);
//    }
//
//
//    /**
//     * 获取指定Lancer的全部个人Profile
//     * 用于个人首页展示
//     * 
//     *
//     * GET http://localhost:7101/lance/res/user/lancer/profile/allProfile/{lancerId}
//     *
//     * Example
//     * GET http://localhost:7101/lance/res/user/lancer/profile/allProfile/muhongdi
//     {
//         "lancerResume" : {
//             "ChargeRate" : 102.13,
//             "HourlyRate" : 101,
//             "Keywords" : "专家,Test，replace中文逗号为英文逗号",
//             "Overview" : "这是overview",
//             "PaymentTermsTxt" : "这是支付方式描述",
//             "ServiceDescriptionTxt" : "这是服务描述",
//             "Tagline" : "这是tagLine",
//             "Uuid" : "e735964d8dc84cc087e8b607b92b4594",
//             "LancerId" : "muhongdi"
//         },
//         "lancer" : {
//             "Uuid" : "muhongdi",
//             "UserName" : "muhongdi",
//             "Email" : "muhongdi@qq.com",
//             "DisplayName" : "天涯月",
//             "Country" : "44",
//             "TrueName" : "牟宏迪",
//             "AccountType" : 1,
//             "CompanyId" : "79fbae75257946e89d2a22a8d2d38031",
//             "CompanyName" : "才才网"
//         },
//         "lancerEducations" : [
//             {
//                 "InstitutionName" : "sy ooxx citi",
//                 "DegreeType" : "学位x",
//                 "StartDate" : "2011-11-12 00:00:00",
//                 "EndDate" : "2015-12-15 00:00:00"
//             },
//             {
//                 "InstitutionName" : "sy ooxx citi",
//                 "DegreeType" : "学位1",
//                 "StartDate" : "2012-12-12 00:00:00",
//                 "EndDate" : "2014-12-12 00:00:00"
//             }
//         ],
//         "lancerSkills" : [
//             {
//                 "Name" : "XML",
//                 "Display" : 1,
//                 "ShowOrder" : 1,
//                 "Uuid" : "83e79321cc4e4ffca2197648ef29454d"
//             },
//             {
//                 "Name" : "CSS",
//                 "Display" : 1,
//                 "ShowOrder" : 1,
//                 "Uuid" : "ee048187830a47b5870ccd41aa2e0435"
//             }
//         ]
//     }
//     *
//     * @param lancerId
//     * @return
//     * @throws JSONException
//     */
//    @GET
//    @Path("allProfile/{lancerId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public JSONObject findSelfProfile(@PathParam("lancerId") String lancerId) throws JSONException {
//        LanceRestAMImpl am = LUtil.findLanceAM();
//        JSONObject json = new JSONObject();
//        json.put("lancerResume", new LancerResumeResource().findLancerResumeByLancerIdFn(lancerId, am));
//        json.put("lancer", new LancerResource().findLancerByIdFn(lancerId, am));
//        json.put("lancerEducations", new UserEducationResource().findAllUserEducationFn(lancerId, am));
//        json.put("lancerSkills", new UserSkillResource().findUserSkillsFn(lancerId, am));
//        return json;
//    }
//    
//    public JSONObject findSelfProfile4CurUser() throws JSONException {
//        String n=this.findCurrentUserId();
//        System.out.println(n);
//        return findSelfProfile(n);
//    }
//
//    /**
//     * 查询联系信息ContactInfo
//     * GET http://localhost:7101/lance/res/user/lancer/profile/contactInfo/{lancerId}
//     * 
//     * Example
//     * GET http://localhost:7101/lance/res/user/lancer/profile/contactInfo/muhongdi
//     *
//     * {
//        "lancer" : {
//            "Uuid" : "muhongdi",
//            "UserName" : "muhongdi",
//            "Email" : "muhongdi@qq.com",
//            "DisplayName" : "天涯月",
//            "Country" : "44",
//            "TrueName" : "牟宏迪",
//            "AccountType" : 1,
//            "CompanyId" : "79fbae75257946e89d2a22a8d2d38031",
//            "CompanyName" : "才才网",
//            "LocationA" : {
//                "Uuid" : "a643d4c3f23a4be59d79a906e237351d",
//                "LancerId" : "muhongdi"
//            },
//            "LocationB" : {
//                "Uuid" : "0f400e0f26424d5f8c147effdef36e93",
//                "LancerId" : "muhongdi"
//            }
//        },
//        "setting" : {
//            "LancerId" : "muhongdi",
//            "AddressDisplay" : 1,
//            "ContactInfo" : 1
//        }
//    }
//     *
//     * @param lancerId
//     * @return
//     * @throws JSONException
//     */
//    @GET
//    @Path("contactInfo/{lancerId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public JSONObject findContactInfo(@PathParam("lancerId") String lancerId) throws JSONException {
//        LanceRestAMImpl am = LUtil.findLanceAM();
//        JSONObject json = new JSONObject();
//        json.put("lancer", new LancerResource().findLancerWithLocationFn(lancerId, am));
//        json.put("setting", new SettingResource().getLancerSettingFn(lancerId, am));
//        return json;
//    }
//    
//    public JSONObject findContactInfo4CurUser() throws JSONException {
//        String n=this.findCurrentUserId();
//        System.out.println(n);
//        return findContactInfo(n);
//    }
//
//    /**
//     * 修改联系信息ContactInfo
//     * POST http://localhost:7101/lance/res/user/lancer/profile/contactInfo/merge/{lancerId}
//     * 
//     * Example
//     * POST POST http://localhost:7101/lance/res/user/lancer/profile/contactInfo/merge/muhongdi
//     * {
//        "lancer" : {
//            "Uuid" : "muhongdi",
//            "UserName" : "muhongdi",
//            "Email" : "muhongdi@qq.com",
//            "DisplayName" : "天涯月",
//            "Country" : "44",
//            "TrueName" : "牟宏迪",
//            "AccountType" : 1,
//            "CompanyId" : "79fbae75257946e89d2a22a8d2d38031",
//            "CompanyName" : "才才网",
//            "LocationA" : {
//                "Uuid" : "a643d4c3f23a4be59d79a906e237351d",
//                "LancerId" : "muhongdi"
//            },
//            "LocationB" : {
//                "Uuid" : "0f400e0f26424d5f8c147effdef36e93",
//                "AreaId" : "10",
//                "CityId" : "1110",
//                "CountryId" : "1110",
//                "CurrentIn" : 1,
//                "FaxNumber" : "12121210",
//                "LancerId" : "muhongdi",
//                "ProvinceId" : "1110",
//                "RegionId" : "1",
//                "Telphone" : "121212120",
//                "ZipCode" : "11110",
//                "DetailLoc" : "xx00街xx00路"
//            }
//        },
//        "setting" : {
//            "LancerId" : "muhongdi",
//            "AddressDisplay" : 2,
//            "ContactInfo" : 3
//        }
//    }
//     *
//     * @param json
//     * @param lancerId
//     * @return
//     * @throws JSONException
//     */
//    @POST
//    @Path("contactInfo/merge/{lancerId}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String mergeContactInfo(JSONObject json, @PathParam("lancerId") String lancerId) throws JSONException {
//        LanceRestAMImpl am = LUtil.findLanceAM();
//        if (json.has("lancer")) {
//            new LancerResource().updateLancerFn(lancerId, json.getJSONObject("lancer"), am);
//        }
//        if (json.has("setting")) {
//            new SettingResource().updateLancerSettingFn(lancerId, json.getJSONObject("setting"), am);
//        }
//        am.getDBTransaction().commit();
//        return "ok";
//    }
//
//}
//
//
