package com.lance.view.rest.job;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.LancerVOImpl;
import com.lance.model.vvo.PostJobsVVOImpl;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * URL分批查询规则
 * 例如
 * GET http://localhost:7101/lance/res/search/postJob/latest?limit=5&start=1
 * start:1和start=0时都从第一条开始返回
 * limit=5&start=1 不传此参数时，默认返回1~25条
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
     * 近期提交
     * GET http://localhost:7101/lance/res/search/postJob/latest?limit=5&start=1
     * start:1和start=0时都从第一条开始返回
     * limit=5&start=1 不传此参数时，默认返回1~25条
     *
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
        return this.packViewObject(vvo, null, null);
        //todo 分页返回
        //        return this.convertVoToJsonArray(vvo, ATTR_SEARCH_JOB);
    }

    /**
     * 根据关键词查询
     * @param keyword
     * @return
     * @throws JSONException
     */
    @GET
    @Path("postJob/searchJob/{keyword}")
    public JSONArray searchJobs(@PathParam("keyword") String keyword) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        PostJobsVVOImpl vvo = am.getPostJobsV1();
        vvo.setApplyViewCriteriaName("FindByKeywordVC");
        vvo.setpName(keyword);
        vvo.setpBrief(keyword);
        vvo.setpSkill(keyword);
        vvo.setpStatus(2); //已发布
        vvo.executeQuery();
        vvo.removeApplyViewCriteriaName("FindByKeywordVC");
        //todo 分页返回
        return this.convertVoToJsonArray(vvo, ATTR_SEARCH_JOB);
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
    public JSONArray searchLancer(@PathParam("keyword") String keyword) throws JSONException {
        if (keyword.length() < 2) {
            throw new RuntimeException("输入2个字符后开始查询");
        }
        LanceRestAMImpl am = LUtil.findLanceAM();
        LancerVOImpl vo = am.getLancer1();
        vo.setApplyViewCriteriaName("FindByNameVC");
        vo.setpName(keyword);
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("FindByNameVC");
        //todo 分页返回
        return this.convertVoToJsonArray(vo, ATTR_SEARCH_LANCER);
    }

}
