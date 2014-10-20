package com.lance.view.rest.job;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.PostJobsVOImpl;
import com.lance.model.vo.PostJobsVORowImpl;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
     * HourlyPayMax，HourlyPayMin：时薪。记录hourly rate的范围。稍后决定时可以为空。必须大于等于10，最大值不能小于最小值。错误提示不需要链接，直接提示如下信息即可
     * 根据2014年8月；均为最低档标准，最低工资不能低于9.9元每小时。即不能少于10元。
     * WeeklyHours 每周工作时间：>0
     * DurationMax,DurationMin 项目周期：以周的方式记录。稍后决定时可以为空
     * FixedPayMax，FixedPayMin 支付价格，选择固定价格时出现。工作价值不得少于80元，最大值不能小于最小值。
     * JobVisibility 隐私设置：public（公开——开放给所有驻才网用户），private（私有——不要公开显示，只有被我邀请的候选人才能看到）。1公开（默认），0私有。
     * AllowSearchEngines：是否允许此信息出现在百度、谷歌等搜索引擎上，0不许，1允许（默认）
     *
     * I prefer candidates from certain location(s) 这句话说的是从指定位置选择候选人。我们只在国内，因此修改如下
     * Preferred Candidate Location 改为：是否需要到达现场进行工作
     * I prefer candidates from certain location(s) 改为：固定工作地点（默认不选中）
     * 当用户选择 固定工作地点 时，出现地点选择组件（国家，省份，城市），并且外加一段关于工作地点的描述输入框
     *
     * "LocationCity", "LocationCountry", "LocationProvince":保存地点的ID，先固定中国即可，地点从LocationResource获取
     *
     * PostJobDateStart,PostJobDateEnd 信息发布有效期(年月日)，不可大于90天。UI建议采用elance的形式，立即生效时需要设置开始时间为当前天
     *
     * Status :1:草稿，2：发布，0：删除
     * 返回的JSON中Status等于1为保存草稿，等于2为发布工作需求（会执行校验）
     *
     * 补充：
     * Location, Privacy and Other Options，默认显示即可，不需要Hide，但需要框起来
     * 
     * 
     * 
     * 测试数据
     * 查询：GET http://localhost:7101/lance/res/user/client/postJob/3d0df8be66d54e25a49ed25e8ec82f45
     * 修改：POST http://localhost:7101/lance/res/user/client/postJob/3d0df8be66d54e25a49ed25e8ec82f45
     * 
     * {
        "Uuid" : "3d0df8be66d54e25a49ed25e8ec82f45",
        "Name" : "工作测试",
        "Brief" : "工作说明",
        "WorkCategory" : "10183",
        "WorkSubcategory" : "14174",
        "SpecificSkillA" : "DHTML",
        "SpecificSkillB" : "DOS",
        "SpecificSkillC" : "XML",
        "SpecificSkillD" : "JAVA",
        "SpecificSkillE" : "BPM",
        "Postform" : 1,
        "HourlyPayMax" : 200,
        "HourlyPayMin" : 100,
        "WeeklyHours" : 100,
        "DurationMax" : 9,
        "DurationMin" : 100,
        "FixedPayMax" : 200,
        "FixedPayMin" : 100,
        "JobVisibility" : 1,
        "AllowSearchEngines" : 1,
        "FixedLocation" : 1,
        "LocationDesc" : "地点描述",
        "Status" : 1,
        "PostJobDateStart" : "2014-10-11 00:00:00",
        "PostJobDateEnd" : "2014-12-11 00:00:00",
        "LocationCity" : "1",
        "LocationCountry" : "1",
        "LocationProvince" : "1"
    }
     *
     */
    public static final String[] ATTR_GET = {
        "Uuid", "Name", "Brief", "Attach", "WorkCategory", "WorkSubcategory", "SpecificSkillA", "SpecificSkillB",
        "SpecificSkillC", "SpecificSkillD", "SpecificSkillE", "Postform", "HourlyPayMax", "HourlyPayMin", "WeeklyHours",
        "DurationMax", "DurationMin", "FixedPayMax", "FixedPayMin", "JobVisibility", "AllowSearchEngines",
        "FixedLocation", "LocationDesc", "LocationId", "Skills", "Status", "PostJobDateStart", "PostJobDateEnd",
        "LocationCity", "LocationCountry", "LocationProvince"
    };

    public static final String[] ATTR_UPDATE = {
        "Name", "Brief", "Attach", "WorkCategory", "WorkSubcategory", "SpecificSkillA", "SpecificSkillB",
        "SpecificSkillC", "SpecificSkillD", "SpecificSkillE", "Postform", "HourlyPayMax", "HourlyPayMin", "WeeklyHours",
        "DurationMax", "DurationMin", "FixedPayMax", "FixedPayMin", "JobVisibility", "AllowSearchEngines",
        "FixedLocation", "LocationDesc", "LocationId", "Skills", "Status", "PostJobDateStart", "PostJobDateEnd",
        "LocationCity", "LocationCountry", "LocationProvince"
    };

    /**
     * 发布工作时，不可为空的字段
     * 保存草稿时，不验证非空字段
     */
    public static final String[] ATTR_POST_REQUIRED = {
        "Uuid", "Name", "Brief", "WorkCategory", "WorkSubcategory", "Postform", "Status", "PostJobDateStart",
        "PostJobDateEnd"
    };


    public PostJobResource() {
    }

    //发布工作信息
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createPostJob(JSONObject json) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        PostJobsVOImpl vo = am.getPostJobs1();
        PostJobsVORowImpl row = (PostJobsVORowImpl) vo.createRow();
        vo.setCurrentRow(row);
        updatePostJobFn(am, row, json);
        am.getDBTransaction().commit();
        return row.getUuid();
    }

    //发布工作信息
    @POST
    @Path("update/{postJobId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updatePostJob(@PathParam("postJobId") String postJobId, JSONObject json) throws JSONException {
        System.out.println("updatePostJob:"+postJobId);
        LanceRestAMImpl am = LUtil.findLanceAM();
        PostJobsVOImpl vo = am.getPostJobs1();
        PostJobsVORowImpl row = (PostJobsVORowImpl) vo.getCurrentRow();
        if (row == null || !row.getUuid().equals(postJobId)) {
            vo.setpUuid(postJobId);
            vo.setApplyViewCriteriaName("FindByIdVC");
            vo.executeQuery();
            vo.removeApplyViewCriteriaName("FindByIdVC");
            row = (PostJobsVORowImpl) vo.first();
            vo.setCurrentRow(row);
        }
        updatePostJobFn(am, row, json);
        am.getDBTransaction().commit();
        return "ok";
    }

    public void updatePostJobFn(LanceRestAMImpl am, PostJobsVORowImpl row, JSONObject json) throws JSONException {
        System.out.println("updatePostJobFn:"+json.get("Status"));
        if (json.get("Status").equals(1)) {
            System.out.println("只保存草稿");
            saveDraftPostJobFn(am, row, json);
        } else if (json.get("Status").equals(2)) {
            System.out.println("发布");
            sendPostJobFn(am, row, json);
        }
    }
    
    public void saveDraftPostJobFn(LanceRestAMImpl am, PostJobsVORowImpl row, JSONObject json) throws JSONException {
        System.out.println("saveDraftPostJobFn");
        LUtil.transJsonToRow(json, row, ATTR_UPDATE);
    }

    /**
     * 发布工作
     * 后台验证只做最关键验证
     *
     * @param am
     * @param row
     * @param json
     */
    public JSONObject sendPostJobFn(LanceRestAMImpl am, PostJobsVORowImpl row, JSONObject json) throws JSONException {
        System.out.println("sendPostJobFn");
        JSONObject res = new JSONObject();
        LUtil.jsonHasNullAttrs(json, ATTR_POST_REQUIRED);
        //非空
        if (json.has("HourlyPayMin") && json.getDouble("HourlyPayMin") < 10) {
            res.put("error", "时薪不能小于10元");
            return res;
        }
        if (json.has("FixedPayMin") && json.getDouble("FixedPayMin") < 80) {
            res.put("error", "支付价格不能小于80元");
            return res;
        }
        //
        LUtil.transJsonToRow(json, row, ATTR_UPDATE);
        return res;
    }

    /**
     * 删除草稿
     * 发布后的会假删除
     * @param postJobId
     * @return
     */
    @POST
    @Path("delete/{postJobId}")
    public String deletePostJob(@PathParam("postJobId") String postJobId) {
        LanceRestAMImpl am = LUtil.findLanceAM();
        PostJobsVOImpl vo = am.getPostJobs1();
        vo.setApplyViewCriteriaName("FindByIdVC");
        vo.setpUuid(postJobId);
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("FindByIdVC");
        PostJobsVORowImpl row = (PostJobsVORowImpl) vo.first();
        if (row.getStatus().equals(1)) {
            //草稿
            row.remove();
        } else if (row.getStatus().equals(2)) {
            //发布
            row.setStatus(0);
        }
        am.getDBTransaction().commit();
        return "ok";
    }

    /**
     *
     *Example
     * 
     *
     * @param postJobId
     * @return
     * @throws JSONException
     */
    @GET
    @Path("{postJobId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getPostJob(@PathParam("postJobId") String postJobId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        PostJobsVOImpl vo = am.getPostJobs1();
        vo.setpUuid(postJobId);
        vo.setApplyViewCriteriaName("FindByIdVC");
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("FindByIdVC");
        Row row = vo.first();

        return this.convertRowToJsonObject(row, ATTR_GET);
    }


}
