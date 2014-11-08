package com.lance.view.rest.job;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.LancerVOImpl;
import com.lance.model.vvo.PostJobsVVOImpl;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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
    public static int DEFAULT_LIMIT = 50;

    public static final String[] ATTR_SEARCH_JOB = {
        "Uuid", "Brief", "DayPayMax", "DayPayMin", "DurationMax", "DurationMin", "FixedLocation", "FixedPayMax",
        "FixedPayMin", "HourlyPayMax", "HourlyPayMin", "LocationId", "Name", "Postform", "Skills", "SpecificSkillA",
        "SpecificSkillB", "SpecificSkillC", "SpecificSkillD", "SpecificSkillE", "SpecificSkillF", "SpecificSkillG",
        "Status", "WeeklyHours", "WorkCategory", "WorkSubcategory", "CreateOn", "CreateBy", "ModifiedOn"
    };

    public static final String[] ATTR_SEARCH_LANCER = { "Uuid" };

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
    @GET
    @Path("postJob/latest")
    public JSONObject getLatestPosted() throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        PostJobsVVOImpl vvo = am.getPostJobsV1();
        vvo.setApplyViewCriteriaName("FindLatestPostedVC");
        vvo.executeQuery();
        vvo.removeApplyViewCriteriaName("FindLatestPostedVC");
        return this.packViewObject(vvo, null, null, ATTR_SEARCH_JOB);
        //todo 分页返回
        //        return this.convertVoToJsonArray(vvo, ATTR_SEARCH_JOB);
    }

    /**
     * 根据关键词查询
     * 关键词：工作名，简介，技能
     * 附加条件：JobVisiable：1 可见，Status：2 已发布
     * http://localhost:7101/lance/res/search/postJob/searchJob/{keyword}
     * 
     * http://localhost:7101/lance/res/search/postJob/searchJob/说明  
     * http://localhost:7101/lance/res/search/postJob/searchJob/XML
     * 
     * 
     * 
     * @param keyword
     * @return
     * @throws JSONException
     */
    @GET
    @Path("postJob/searchJob/{keyword}")
    public JSONObject searchJobs(@PathParam("keyword") String keyword) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        PostJobsVVOImpl vvo = am.getPostJobsV1();
        vvo.setApplyViewCriteriaName("FindByKeywordVC");
        vvo.setpName(keyword);
        vvo.setpBrief(keyword);
        vvo.setpSkill(keyword);
        vvo.setpStatus(2); //已发布
        vvo.executeQuery();
        System.out.println(keyword);
        System.out.println(vvo.getQuery());
        vvo.removeApplyViewCriteriaName("FindByKeywordVC");
        return this.packViewObject(vvo, null, null, ATTR_SEARCH_JOB);
        //todo 分页返回
        //        return this.convertVoToJsonArray(vvo, ATTR_SEARCH_JOB);
    }

    /**
     * 寻找Lancer
     * 模糊查询，开头匹配UserName（输入2个字符后开始查询）
     * 返回UserName，（返回UserName同Uuid）
     * @param keyword
     * @return
     * @throws JSONException
     */
    @GET
    @Path("lancer/searchLancer/{keyword}")
    public JSONObject searchLancer(@PathParam("keyword") String keyword) throws JSONException {
        if (keyword.length() < 2) {
            throw new RuntimeException("输入2个字符后开始查询");
        }
        LanceRestAMImpl am = LUtil.findLanceAM();
        LancerVOImpl vo = am.getLancer1();
        vo.setApplyViewCriteriaName("FindByNameVC");
        vo.setpName(keyword);
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("FindByNameVC");
        return this.packViewObject(vo, null, null, ATTR_SEARCH_LANCER);
        //todo 分页返回
        //        return this.convertVoToJsonArray(vo, ATTR_SEARCH_LANCER);
    }

}
