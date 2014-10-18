package com.lance.view.rest.template;

import com.lance.model.LanceRestAMImpl;
import com.lance.model.vo.SkillsVOImpl;
import com.lance.view.util.LUtil;

import com.zngh.platform.front.core.view.BaseRestResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

/**
 * JOB_TEMPLATE
 * 查询全部，根据类别查询
 * JOB_CATEGORY
 * JOB_SUB_CATEGORY
 *
 */
@Path("template/job")
public class JobTemplateResource extends BaseRestResource {
    public static final String[] ATTR_GET_JOB_CATEGORY = { "Uuid","NameEn","NameCn","Display" };
    public static final String[] ATTR_GET_JOB_SUB_CATEGORY = { "Uuid","Name","CategoryId" };
    public static final String[] ATTR_GET_JOB_TEMPLATE = { "Uuid","JobCategoryId","NameEn","NameCn","DescriptionCn","DescriptionEn","Tips","Type" };
    public static final String[] ATTR_GET_SKILL = { "Name" };
    
    public JobTemplateResource() {
    }

    @GET
    @Path("jobCategory")
    public JSONArray getJobCategory() throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObject vo1 = am.getJobCategory1();
        return this.convertVoToJsonArray(vo1, ATTR_GET_JOB_CATEGORY);
    }

    @GET
    @Path("jobSubCategory/{categoryId}")
    public JSONArray getJobSubCategory(@PathParam("categoryId") String categoryId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObject vo1 = am.getJobCategory1();
        if(vo1.getCurrentRow()==null || !categoryId.equals(vo1.getCurrentRow().getAttribute("Uuid")) ){
           Row row1 = vo1.findByKey(new Key(new Object[]{categoryId}), 1)[0];
           vo1.setCurrentRow(row1);
        }
        
        ViewObject vo2 = am.getJobSubCategory1();
        return this.convertVoToJsonArray(vo2, ATTR_GET_JOB_SUB_CATEGORY);
    }
    
    @GET
    @Path("jobTemplate/{categoryId}")
    public JSONArray getJobTemplate(@PathParam("categoryId") String categoryId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObject vo1 = am.getJobCategory1();
        if(vo1.getCurrentRow()==null || !categoryId.equals(vo1.getCurrentRow().getAttribute("Uuid")) ){
           Row row1 = vo1.findByKey(new Key(new Object[]{categoryId}), 1)[0];
           vo1.setCurrentRow(row1);
        }
        
        ViewObject vo3 = am.getJobTemplate1();
        return this.convertVoToJsonArray(vo3, ATTR_GET_JOB_TEMPLATE);
    }

    @GET
    @Path("specificSkill/{skill}")
    public JSONArray filterSkills(@PathParam("skill") String skill) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        SkillsVOImpl vo4 = am.getSkills1();
        vo4.setpName(skill);
        vo4.setApplyViewCriteriaName("FilterByNameVC");
        vo4.executeQuery();
        vo4.removeApplyViewCriteriaName("FilterByNameVC");
        return  this.convertVoToJsonArray(vo4, this.ATTR_GET_SKILL);
    }
    
}
