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
@Path("jobTemplate")
public class JobTemplateResource extends BaseRestResource {
    public static final String[] ATTR_GET_JOB_CATEGORY = { "Uuid", "NameEn", "NameCn", "Display" };
    public static final String[] ATTR_GET_JOB_SUB_CATEGORY = { "Uuid", "Name", "CategoryId" };
    public static final String[] ATTR_GET_JOB_TEMPLATE = {
        "Uuid", "JobCategoryId", "NameEn", "NameCn", "DescriptionCn", "DescriptionEn", "Tips", "Type"
    };
    public static final String[] ATTR_GET_SKILL = { "Name" };

    public JobTemplateResource() {
    }

    /**
     * 获取工作大类
     * GET http://localhost:7101/lance/res/jobTemplate/jobCategory
     *
     * [
        {
            "Uuid" : "10184",
            "NameEn" : "Design & Multimedia"
        },
        {
            "Uuid" : "14000",
            "NameEn" : "Engineering & Manufacturing"
        }
      ]
     *
     * @return
     * @throws JSONException
     */
    @GET
    @Path("jobCategory")
    public JSONArray getJobCategory() throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObject vo1 = am.getJobCategory1();
        return this.convertVoToJsonArray(vo1, ATTR_GET_JOB_CATEGORY);
    }

    /**
     *
     * 根据工作大类ID获取工作子类
     * GET http://localhost:7101/lance/res/jobTemplate/jobSubCategory/10184
     *
     * [
        {
            "Uuid" : "14126",
            "Name" : "Animation",
            "CategoryId" : "10184"
        },
        {
            "Uuid" : "14166",
            "Name" : "Art",
            "CategoryId" : "10184"
        },
        {
            "Uuid" : "10232",
            "Name" : "Banner Ads",
            "CategoryId" : "10184"
        }
    ]
     *
     *
     * @param categoryId
     * @return
     * @throws JSONException
     */
    @GET
    @Path("jobSubCategory/{categoryId}")
    public JSONArray getJobSubCategory(@PathParam("categoryId") String categoryId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObject vo1 = am.getJobCategory1();
        if (vo1.getCurrentRow() == null || !categoryId.equals(vo1.getCurrentRow().getAttribute("Uuid"))) {
            Row row1 = vo1.findByKey(new Key(new Object[] { categoryId }), 1)[0];
            vo1.setCurrentRow(row1);
        }

        ViewObject vo2 = am.getJobSubCategory1();
        return this.convertVoToJsonArray(vo2, ATTR_GET_JOB_SUB_CATEGORY);
    }

    /**
     * 根据工作大类ID，获取工作模版
     * http://localhost:7101/lance/res/template/jobTemplate/10184
     *
     * [
        {
            "Uuid" : "1002",
            "JobCategoryId" : "10184",
            "NameEn" : "Brochure Design Project",
            "DescriptionEn" : "What type of brochure or pamphlet are you looking for?\nBi-Fold\nTri-Fold\nGate-Fold\nZ-Fold\nSingle Page Flyer\nLeaflet\nOther\nWhat is the primary message(s) of your brochure?",
            "Tips" : "Get a brochure designed for your business by utilizing this template. ",
            "Type" : 2
        },
        {
            "Uuid" : "1021",
            "JobCategoryId" : "10184",
            "NameEn" : "Business Card Design Project",
            "DescriptionEn" : "Please describe what will appear on your business card.\n\nWhat type of style are you looking for? \nSuggestions: Corporate, Clean, Modern, Classic, Noisy, Fun, Funky, Elegant, High Tech\n\nDo you have any preferred colors? \nCommon color associations:\nRed: Love, Passion, Vigor, Courage, Anger\nYellow: Cheer, Joy, Freshness, Lightheartedness\nOrange: Enthusiasm, Fascination, Happiness, Creativity\nGreen: Growth, Health, Peace, Safety, Success\nBlue: Stability, Trust, Loyalty, Confidence, Faith\nBlack: Mystery, Power, Elegance, Unknown, Fear\nPurple: Royalty, Nobility, Luxury, Ambition\nWhite: Innocence, Purity, Cleanliness, Simplicity\n\nAny additional comments?",
            "Tips" : "Use this guide to get your next set of business cards designed.",
            "Type" : 2
        }
      ]
     *
     * @param categoryId
     * @return
     * @throws JSONException
     */
    @GET
    @Path("jobTemplate/{categoryId}")
    public JSONArray getJobTemplate(@PathParam("categoryId") String categoryId) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        ViewObject vo1 = am.getJobCategory1();
        if (vo1.getCurrentRow() == null || !categoryId.equals(vo1.getCurrentRow().getAttribute("Uuid"))) {
            Row row1 = vo1.findByKey(new Key(new Object[] { categoryId }), 1)[0];
            vo1.setCurrentRow(row1);
        }

        ViewObject vo3 = am.getJobTemplate1();
        vo3.executeQuery();
        return this.convertVoToJsonArray(vo3, ATTR_GET_JOB_TEMPLATE);
    }

    /**
     * 为快速提示提供技能查询功能（建议输入3个字符后再提示）
     * GET http://localhost:7101/lance/res/jobTemplate/specificSkill/{skillName}
     *
     * Example
     * GET http://localhost:7101/lance/res/jobTemplate/specificSkill/ml
     * [
        {
            "Name" : "DHTML"
        },
        {
            "Name" : "FBML"
        },
        {
            "Name" : "HAML"
        },
        {
            "Name" : "HTML"
        }
    ]
     *
     * @param skill
     * @return
     * @throws JSONException
     */
    @GET
    @Path("specificSkill/{skill}")
    public JSONArray filterSkills(@PathParam("skill") String skill) throws JSONException {
        LanceRestAMImpl am = LUtil.findLanceAM();
        SkillsVOImpl vo4 = am.getSkills1();
        vo4.setpName(skill);
        vo4.setApplyViewCriteriaName("FilterByNameVC");
        vo4.executeQuery();
        vo4.removeApplyViewCriteriaName("FilterByNameVC");
        return this.convertVoToJsonArray(vo4, this.ATTR_GET_SKILL);
    }

}
