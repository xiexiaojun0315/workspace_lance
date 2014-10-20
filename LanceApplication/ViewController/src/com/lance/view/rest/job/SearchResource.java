package com.lance.view.rest.job;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.PostJobsVOImpl;
import com.lance.model.vvo.PostJobsVVOImpl;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import javax.ws.rs.PathParam;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

@Path("search")
public class SearchResource extends BaseRestResource {
    
    public static final String[] ATTR_SEARCH = {"Uuid", "Brief", "DayPayMax", "DayPayMin", "DurationMax", "DurationMin",
        "FixedLocation", "FixedPayMax", "FixedPayMin", "HourlyPayMax", "HourlyPayMin", 
        "LocationId", "Name", "Postform", "Skills", "SpecificSkillA", "SpecificSkillB", "SpecificSkillC",
        "SpecificSkillD", "SpecificSkillE", "SpecificSkillF", "SpecificSkillG", "Status", "WeeklyHours", "WorkCategory",
        "WorkSubcategory","CreateOn","CreateBy","ModifiedOn"};
        
    public SearchResource() {
    }
    
    /**
     * 近期提交
     * @return
     */
    @GET
    @Path("postJob/latest")
    public JSONArray getLatestPosted() throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        PostJobsVVOImpl vvo =am.getPostJobsV1();
        vvo.setApplyViewCriteriaName("FindLatestPostedVC");
        vvo.executeQuery();
        vvo.removeApplyViewCriteriaName("FindLatestPostedVC");
        return this.convertVoToJsonArray(vvo, ATTR_SEARCH);
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
        PostJobsVVOImpl vvo =am.getPostJobsV1();
        vvo.setApplyViewCriteriaName("FindByKeywordVC");
        vvo.setpName(keyword);
        vvo.setpBrief(keyword);
        vvo.setpSkill(keyword);
        vvo.setpStatus(2);//已发布
        vvo.executeQuery();
        vvo.removeApplyViewCriteriaName("FindByKeywordVC");
        return this.convertVoToJsonArray(vvo, ATTR_SEARCH);
    }
    
    public JSONArray searchLancer(@PathParam("keyword") String keyword) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        PostJobsVVOImpl vvo =am.getPostJobsV1();
        vvo.setApplyViewCriteriaName("FindByKeywordVC");
        vvo.setpName(keyword);
        vvo.setpBrief(keyword);
        vvo.setpSkill(keyword);
        vvo.setpStatus(2);//已发布
        vvo.executeQuery();
        vvo.removeApplyViewCriteriaName("FindByKeywordVC");
        return this.convertVoToJsonArray(vvo, ATTR_SEARCH);
    }
    
}
