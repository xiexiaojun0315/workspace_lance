package com.lance.view.rest.job;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.user.vo.UUserVOImpl;
import com.lance.model.vo.PostJobsVOImpl;
import com.lance.model.vo.PostJobsVORowImpl;
import com.lance.model.vvo.PostJobsVVOImpl;
import com.lance.model.vvo.UserSearchVVOImpl;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

import oracle.jbo.RowSetIterator;

import org.apache.commons.lang.StringUtils;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * URL分批查询规则
 * 例如
 * GET http://localhost:7101/lance/res/search/postJob/latest?limit=5&start=1
 * start:1和start=0时都从第一条开始返回
 * limit=5&start=1 不传此参数时，默认返回1~25条
 *
 * http://localhost:7101/lance/res/search/postJob/latest 返回最近25条
 * http://localhost:7101/lance/res/search/postJob/latest?limit=5&start=10 从第10条开始，返回5条
 *
 * 为空时返回
 * {
      "count" : 0,
      "data" : [
      ]
   }
 *
 */
@Path("search")
public class SearchResource extends BaseRestResource {
    public static int DEFAULT_LIMIT = 25;


    public static final String[] POST_JOB_VO_ATTR_ALL = {
        "Uuid", "AllowSearchEngines", "Attach", "Brief", "DurationMax", "DurationMin", "FixedLocation", "FixedPayMax",
        "FixedPayMin", "HourlyPayMax", "HourlyPayMin", "JobVisibility", "LocationDesc", "Name", "Postform",
        "SpecificSkillA", "SpecificSkillB", "SpecificSkillC", "SpecificSkillD", "SpecificSkillE", "SpecificSkillF",
        "SpecificSkillG", "Status", "StatusDesc", "WeeklyHours", "WorkCategory", "WorkSubcategory", "PostJobDateStart",
        "PostJobDateEnd", "LocationRegion", "LocationCountry", "LocationProvince", "LocationCity", "IndexSkills",
        "IndexLocation", "IndexWorkCategorys", "SignBy", "CreateBy", "CreateOn", "ModifyBy", "ModifyOn", "Version",
        "CreateByName", "BriefShort"
    };

    /**
     * 字段同PostJobs
     */
    public static final String[] POST_JOB_SEARCH_FIELD = {
        "Uuid", "BriefShort", "DurationMax", "DurationMin", "FixedLocation", "FixedPayMax", "FixedPayMin",
        "HourlyPayMax", "HourlyPayMin", "LocationDesc", "Name", "Postform", "SpecificSkillA", "SpecificSkillB",
        "SpecificSkillC", "SpecificSkillD", "SpecificSkillE", "SpecificSkillF", "SpecificSkillG", "WeeklyHours",
        "WorkCategory", "WorkSubcategory", "PostJobDateStart", "PostJobDateEnd", "IndexSkills", "IndexLocation",
        "IndexWorkCategorys", "SignBy", "CreateBy", "CreateOn", "ModifyBy", "ModifyOn", "Version", "CreateByName"
    };

    public static final String[] ATTR_SEARCH_LANCER = {
        "Uuid", "ChargeRate", "HourlyRate", "Overview", "Tagline", "Video", "VideoUrl", "UserAccountType",
        "UserCountry", "UserDisplayName", "UserImg", "UserLocationA", "UserLocationB"
    };

    public static final String[] ATTR_SEARCH_LANCER_NAME = { "Uuid", "DisplayName" };

    public SearchResource() {
    }

    /**
     * 搜索近期发布的招聘信息
     * GET http://localhost:7101/lance/res/search/postJob/latest?limit=5&start=1
     * @param
     * start:1和start=0时都从第一条开始返回
     * limit=5&start=1 不传此参数时，默认返回1~25条
     *
     * http://localhost:7101/lance/res/search/postJob/latest 返回最近25条
     * http://localhost:7101/lance/res/search/postJob/latest?limit=5&start=10 从第10条开始，返回5条
     *
     * 返回结果为空时返回
     *
     * {
            "count" : 0,
            "data" : [
            ]
        }


     * {
    "count" : 5,
    "data" : [
        {
            "Uuid" : "test9",
            "Brief" : "工作说明9",
            "DurationMax" : 9,
            "DurationMin" : 100,
            "FixedLocation" : 1,
            "FixedPayMax" : 200,
            "FixedPayMin" : 100,
            "HourlyPayMax" : 200,
            "HourlyPayMin" : 100,
            "Name" : "工作测试9",
            "Postform" : 1,
            "SpecificSkillA" : "DHTML",
            "SpecificSkillB" : "DOS",
            "SpecificSkillC" : "XML",
            "SpecificSkillD" : "JAVA",
            "SpecificSkillE" : "BPM",
            "Status" : 1,
            "WeeklyHours" : 100,
            "WorkCategory" : "10183",
            "WorkSubcategory" : "14174"
        },
        {
            "Uuid" : "test10",
            "Brief" : "工作说明10",
            "DurationMax" : 9,
            "DurationMin" : 100,
            "FixedLocation" : 1,
            "FixedPayMax" : 200,
            "FixedPayMin" : 100,
            "HourlyPayMax" : 200,
            "HourlyPayMin" : 100,
            "Name" : "工作测试10",
            "Postform" : 1,
            "SpecificSkillA" : "DHTML",
            "SpecificSkillB" : "DOS",
            "SpecificSkillC" : "XML",
            "SpecificSkillD" : "JAVA",
            "SpecificSkillE" : "BPM",
            "Status" : 1,
            "WeeklyHours" : 100,
            "WorkCategory" : "10183",
            "WorkSubcategory" : "14174"
        }
    ......
     ]
     *
     * @return
     */
    //    @GET
    //    @Path("postJob/latest")
    //    public JSONObject getLatestPosted() throws JSONException {
    //        LanceRestAMImpl am = LUtil.findLanceAM();
    //        PostJobsVVOImpl vvo = am.getPostJobsV1();
    //        vvo.setApplyViewCriteriaName("FindPostedVC");
    //        vvo.executeQuery();
    //        vvo.removeApplyViewCriteriaName("FindPostedVC");
    //        JSONObject data = this.packViewObject(vvo, null, null, POST_JOB_SEARCH_FIELD);
    //        return data;
    //    }

    @GET
    @Path("postJob/latest")
    public JSONObject searchLatestPosted() throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        PostJobsVOImpl vo = am.getPostJobs1();
        vo.setApplyViewCriteriaName("FindPostedVC");
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("FindPostedVC");
        JSONObject data = this.packViewObject(vo, null, null, POST_JOB_SEARCH_FIELD);
        return data;
    }

    /**
     * 重新建立索引字段
     * @return
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("postJob/reIndex")
    public String reIndexPostJob() {
        LanceRestAMImpl am = LUtil.findLanceAM();
        PostJobsVOImpl vo = am.getPostJobs1();
        vo.executeQuery();
        PostJobsVORowImpl row;
        RowSetIterator it = vo.createRowSetIterator(null);
        while (it.hasNext()) {
            row = (PostJobsVORowImpl) it.next();
            row.updateSearchIndex();
        }
        it.closeRowSetIterator();
        am.getDBTransaction().commit();
        return "ok";
    }


    /**
     * 根据关键词查询
     * 关键词：工作名，简介，技能，地点，分类
     * 只搜索：STATUS = 'posted' AND JOB_VISIBILITY = 'public'
     * 
     * http://localhost:7101/lance/res/search/postJob/keyword/{keyword}
     *
     * http://localhost:7101/lance/res/search/postJob/keyword/说明
     * http://localhost:7101/lance/res/search/postJob/keyword/XML
     *
     * @param keyword
     * @return
     * @throws JSONException
     */
    @GET
    @Path("postJob/keyword/{keyword}")
    public JSONObject searchJobs(@PathParam("keyword") String keyword) throws JSONException {
        
        if (StringUtils.isBlank(keyword) || keyword.length() < 2) {
            JSONObject res=new JSONObject();
            res.put("msg", "输入2个字符后开始查询");
            return res;
        }
        
        LanceRestAMImpl am = LUtil.findLanceAM();
        PostJobsVOImpl vo = am.getPostJobs1();
        
        StringBuffer sb=new StringBuffer(" STATUS = 'posted' AND JOB_VISIBILITY = 'public' ");
        String[] sps = splitKeyword(keyword); //根据空格分隔
        for (String sp : sps) {
            sb.append(" AND upper(INDEX_ALL_META_INFO) like '%" + sp.toUpperCase() + "%'");
        }
        vo.setWhereClause(sb.toString());
        vo.executeQuery();
        System.out.println(vo.getQuery());
        vo.setWhereClause(null);
        
        return this.packViewObject(vo, null, null, POST_JOB_SEARCH_FIELD);
    }


    /**
     * 寻找Lancer
     * 模糊查询，开头匹配UserName（输入2个字符后开始查询）
     *
     * 返回UserName，（返回UserName同Uuid）
     * @param keyword
     * @return
     * @throws JSONException
     */
    @GET
    @Path("lancer/nameStartWith/{keyword}")
    public JSONObject searchLancer4Name(@PathParam("keyword") String keyword) throws JSONException {
        if (keyword.length() < 2) {
            JSONObject res=new JSONObject();
            res.put("msg", "输入2个字符后开始查询");
            return res;
        }
        LanceRestAMImpl am = LUtil.findLanceAM();
        UUserVOImpl vo = am.getUUser1();
        vo.setApplyViewCriteriaName("FindByNameVC");
        vo.setpUserName(keyword);
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("FindByNameVC");
        return this.packViewObject(vo, null, null, ATTR_SEARCH_LANCER_NAME);
    }


    /**
     * 2015年1月13日更新
     * 待实现
     *
     * 根据Overview，Tagline，Keyword,skill查询UUser
     * 适用于Client根据技术查找UUser
     *
     * 模糊查询（关键字查询）
     * GET http://localhost:7101/lance/res/search/userByKeyword/{userType}/{keyword}?category={category}
     * start:1和start=0时都从第一条开始返回
     * limit=5&start=1 不传此参数时，默认返回1~25条
     *
     * userType:lancer/contract/client列出对应类型的人员,如果为空则列出所有类型人员
     * keyword：关键词，支持空格逗号分隔
     *
     * 其它查询条件（用问号表达式方式）
     * category:工作大类，例10183 （IT & Programming）
     * country：国家ID
     * province:省ID
     * city:城市ID
     * skill:技能，多个用空格分隔
     * HourlyRateMin：最小时薪
     * HourlyRateMax：最大时薪
     *
     * 优先级：svip>vip>普通
     * keyword>Tagline>skill>Overview
     * 识别地理位置
     *
     * 示例URL  GET http://localhost:7101/lance/res/search/userByKeyword/lancer/Test%20overview?city=1&category=10183&skill=xml%20html
     * %20是空格
     *
     * 例子：
     * 查询 Test overview
     * GET http://localhost:7101/lance/res/search/userByKeyword/lancer/Test%20overview
     *
     * 返回前25条
     {
         "count" : 4,
         "data" : [
             {
                 "Uuid" : "c0d5b7a060b6448fa8bb68a3c1b3ee28",
                 "ChargeRate" : 102.13,
                 "HourlyRate" : 101,
                 "Overview" : "这是overview",
                 "Tagline" : "这是tagLine",
                 "UserAccountType" : 1,
                 "UserCountry" : "44",
                 "UserDisplayName" : "天涯月",
                 "UserLocationA" : {
                 },
                 "UserLocationB" : {
                     "CountryId" : "44",
                     "ProvinceId" : "1",
                     "CityId" : "1",
                     "CountryName" : "中国",
                     "ProvinceName" : "北京市",
                     "CityName" : "北京市"
                 }
             },
             {
                 "Uuid" : "531ca069aa0e41aea7c114cf11cb7be6",
                 "ChargeRate" : 102.13,
                 "HourlyRate" : 101,
                 "Overview" : "这是overview",
                 "Tagline" : "这是tagLine",
                 "UserAccountType" : 1,
                 "UserCountry" : "44",
                 "UserDisplayName" : "天涯月",
                 "UserLocationA" : {
                 },
                 "UserLocationB" : {
                     "CountryId" : "44",
                     "ProvinceId" : "1",
                     "CityId" : "1",
                     "CountryName" : "中国",
                     "ProvinceName" : "北京市",
                     "CityName" : "北京市"
                 }
             },
             {
                 "Uuid" : "ce5e37bdc4fe4608a971d0b58637b6cf",
                 "ChargeRate" : 102.13,
                 "HourlyRate" : 101,
                 "Overview" : "这是overview",
                 "Tagline" : "这是tagLine",
                 "UserAccountType" : 1,
                 "UserCountry" : "44",
                 "UserDisplayName" : "天涯月",
                 "UserLocationA" : {
                 },
                 "UserLocationB" : {
                     "CountryId" : "44",
                     "ProvinceId" : "1",
                     "CityId" : "1",
                     "CountryName" : "中国",
                     "ProvinceName" : "北京市",
                     "CityName" : "北京市"
                 }
             },
             {
                 "Uuid" : "e735964d8dc84cc087e8b607b92b4594",
                 "ChargeRate" : 102.13,
                 "HourlyRate" : 101,
                 "Overview" : "这是overview",
                 "Tagline" : "这是tagLine",
                 "UserAccountType" : 1,
                 "UserCountry" : "44",
                 "UserDisplayName" : "天涯月",
                 "UserLocationA" : {
                 },
                 "UserLocationB" : {
                     "CountryId" : "44",
                     "ProvinceId" : "1",
                     "CityId" : "1",
                     "CountryName" : "中国",
                     "ProvinceName" : "北京市",
                     "CityName" : "北京市"
                 }
             }
         ]
     }
     *
     * @param keyword
     * @return
     * @throws JSONException
     */
    @GET
    @Path("/userByKeyword/{userType}/{keyword}")
    public JSONObject searchLancer4Job(@PathParam("userType") String userType,
                                       @PathParam("keyword") String keyword) throws JSONException {

        if (StringUtils.isNotBlank(userType) && "lancer,company,client".indexOf(userType) != -1) {
        } else {
            throw new RuntimeException("User Type is undefined");
        }

        if (StringUtils.isBlank(keyword) || keyword.length() < 2) {
            throw new RuntimeException("输入2个字符后开始查询");
        }

        LanceRestAMImpl am = LUtil.findLanceAM();
        UserSearchVVOImpl vo = am.getUserSearchV1();

        //查询算法
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(userType)) {
            sb.append(" ROLE_NAME = '" + userType + "' ");
        }

        String[] sps = this.splitKeyword(keyword); //根据空格分隔
        for (String sp : sps) {
            sb.append(" AND upper(INDEX_FIELD) like '%" + sp.toUpperCase() + "%'");
        }
        vo.setWhereClause(sb.toString());
        vo.executeQuery();

        //处理位置信息
        JSONObject data = this.packViewObject(vo, null, null, ATTR_SEARCH_LANCER);
        return data;
    }

    /**
     * 将各种分隔符格式化为标准空格分隔的关键词,并进行split操作
     * @param keyword
     * @return
     */
    public String[] splitKeyword(String keyword) {
        keyword = keyword.trim().replaceAll(",", " ").replaceAll("，", " ");
        return keyword.split(" ");
    }

}
