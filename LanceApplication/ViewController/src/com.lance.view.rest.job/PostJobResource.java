package com.lance.view.rest.job;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.PostJobsVOImpl;
import com.lance.model.vo.PostJobsVORowImpl;
import com.lance.view.template.JobTemplateResource;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import oracle.jbo.Row;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("user/client/postJob")
public class PostJobResource extends BaseRestResource {

    //
    public static final String[] ATTR_ALL = {
        "Uuid", "AllowSearchEngines", "Attach", "Brief", "DayPayMax", "DayPayMin", "DurationMax", "DurationMin",
        "FixedLocation", "FixedPayMax", "FixedPayMin", "HourlyPayMax", "HourlyPayMin", "JobVisibility", "LocationDesc",
        "LocationId", "Name", "Postform", "Skills", "SpecificSkillA", "SpecificSkillB", "SpecificSkillC",
        "SpecificSkillD", "SpecificSkillE", "SpecificSkillF", "SpecificSkillG", "Status", "WeeklyHours", "WorkCategory",
        "WorkSubcategory"
    };

    //SpecificSkillG,SpecificSkillF为Vip用
    /**
     * Name：Name your job。工作名称
     * Brief：Describe it。描述
     * Attach：附件的链接
     * WorkCategory：工作大类，从JobTemplate接口获取。存储ID
     * WorkSubcategory：工作详细类别，从JobTemplate接口获取。存储ID
     * SpecificSkillA~SpecificSkillE：用来存储Request specific skills or groups (optional)（所需指定技能）。对输入提供快速提示，明文存储，允许输入提示中不存在的技能。普通用户最多输入5项。
     * Postform 工作安排： Set work arrangement (optional)下的Hourly、FixedPrice
     * HourlyPayMax，HourlyPayMin：记录hourly rate的范围。稍后决定时可以为空。必须大于等于10，最大值不能小于最小值。错误提示不需要链接，直接提示如下信息即可
     * 根据2014年8月；均为最低档标准，最低工资不能低于9.9元每小时。即不能少于10元。
     * WeeklyHours 每周工作时间：>0
     * DurationMax,DurationMin 项目周期：以周的方式记录。稍后决定时可以为空
     * FixedPayMax，FixedPayMin 预算范围，选择固定价格时出现。工作价值不得少于80元，最大值不能小于最小值。
     * JobVisibility 隐私设置：public（公开——开放给所有驻才网用户），private（私有——不要公开显示，只有被我邀请的候选人才能看到）。1公开，0私有
     * AllowSearchEngines：是否允许此信息出现在百度、谷歌等搜索引擎上，0不许，1允许
     * 
     * I prefer candidates from certain location(s) 这句话说的是从指定位置选择候选人。我们只在国内，因此改为：是否需要
     * Preferred Candidate Location 改为：是否需要到达现场进行工作
     * I prefer candidates from certain location(s) 改为：固定工作地点
     * 当用户选择 固定工作地点 时，出现地点选择组件（国家，省份，城市），并且外加一段关于工作地点的描述输入框
     * 
     * 关于地点
     * todo
     * 
     * PostJobDateStart,PostJobDateEnd 信息发布有效期(年月日)，不可大于90天。UI建议采用elance的形式，立即生效时需要设置开始时间为当前天
     * 
     * 补充：
     * Location, Privacy and Other Options，默认显示即可，不需要Hide，但需要框起来
     * 
     * 
     */
    public static final String[] ATTR_GET = {
        "Uuid", "Name","Brief","Attach","WorkCategory", "WorkSubcategory","SpecificSkillA", "SpecificSkillB", "SpecificSkillC",
        "SpecificSkillD", "SpecificSkillE","Postform","HourlyPayMax", "HourlyPayMin", "WeeklyHours","DurationMax", "DurationMin", "FixedPayMax", "FixedPayMin","JobVisibility","AllowSearchEngines",
        "FixedLocation",   "LocationDesc",
        "LocationId",   "Skills",  "Status","PostJobDateStart","PostJobDateEnd"
    };
    
    public static final String[] ATTR_CREATE = {
         "AllowSearchEngines", "Attach", "Brief", "DayPayMax", "DayPayMin", "DurationMax", "DurationMin",
        "FixedLocation", "FixedPayMax", "FixedPayMin", "HourlyPayMax", "HourlyPayMin", "JobVisibility", "LocationDesc",
        "LocationId", "Name", "Postform", "Skills", "SpecificSkillA", "SpecificSkillB", "SpecificSkillC",
        "SpecificSkillD", "SpecificSkillE", "SpecificSkillF", "SpecificSkillG", "Status", "WeeklyHours", "WorkCategory",
        "WorkSubcategory"
    };
    

    public PostJobResource() {
    }

    //发布工作信息
    @POST
    public String createPostJob(JSONObject json) {
        LanceRestAMImpl am = LUtil.findLanceAM();

        throw new UnsupportedOperationException();
    }

    //发布工作信息
    @POST
    @Path("update/{postJobId}")
    public String updatePostJob(@PathParam("postJobId") String postJobId, JSONObject json) {
        LanceRestAMImpl am = LUtil.findLanceAM();
        // Provide method implementation.
        // TODO

        throw new UnsupportedOperationException();
    }

    @POST
    @Path("delete/{postJobId}")
    public String deletePostJob(@PathParam("postJobId") String postJobId) {
        LanceRestAMImpl am = LUtil.findLanceAM();
        PostJobsVOImpl vo = am.getPostJobs1();
        vo.setpUuid(postJobId);
        vo.setApplyViewCriteriaName("PostJobsVOImpl");
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("PostJobsVOImpl");
        PostJobsVORowImpl row = (PostJobsVORowImpl) vo.first();
        //        if(row.getStatus()){}

        throw new UnsupportedOperationException();
    }

    @GET
    @Path("{postJobId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getPostJob(@PathParam("postJobId") String postJobId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        PostJobsVOImpl vo = am.getPostJobs1();
        for (String a : this.findAllVOAttributes(vo)) {
            System.out.println(a);
        }
        vo.setpUuid(postJobId);
        vo.setApplyViewCriteriaName("PostJobsVOImpl");
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("PostJobsVOImpl");
        Row row = vo.first();

        return this.convertRowToJsonObject(row, ATTR_GET);
    }


}
