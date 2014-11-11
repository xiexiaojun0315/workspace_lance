package com.lance.model;

import com.lance.model.vo.ClientUserVOImpl;
import com.lance.model.vo.CompanyVOImpl;
import com.lance.model.vo.JobTemplateVOImpl;
import com.lance.model.vo.LancerLocationListVOImpl;
import com.lance.model.vo.LancerResumeVOImpl;
import com.lance.model.vo.LancerSkillVOImpl;
import com.lance.model.vo.LancerVOImpl;
import com.lance.model.vo.LoginUserRoleGrantsVOImpl;
import com.lance.model.vo.LoginUserRoleVOImpl;
import com.lance.model.vo.LoginUserVOImpl;

import com.lance.model.vo.PostJobsVOImpl;
import com.lance.model.vo.SkillsVOImpl;
import com.lance.model.vo.SpreadHeardFromVOImpl;
import com.lance.model.vvo.LocationCountryVVOImpl;

import com.lance.model.vvo.PostJobsVVOImpl;

import com.zngh.platform.front.core.model.BaseApplicationModuleImpl;


import com.zngh.platform.front.core.model.BaseViewObjectImpl;

import oracle.jbo.server.ViewLinkImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Sep 27 21:29:14 CST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LanceRestAMImpl extends BaseApplicationModuleImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public LanceRestAMImpl() {
    }

    /**
     * Container's getter for Lancer1.
     * @return Lancer1
     */
    public LancerVOImpl getLancer1() {
        return (LancerVOImpl) findViewObject("Lancer1");
    }

    /**
     * Container's getter for ClientUser1.
     * @return ClientUser1
     */
    public ClientUserVOImpl getClientUser1() {
        return (ClientUserVOImpl) findViewObject("ClientUser1");
    }


    /**
     * Container's getter for LoginUser1.
     * @return LoginUser1
     */
    public LoginUserVOImpl getLoginUser1() {
        return (LoginUserVOImpl) findViewObject("LoginUser1");
    }

    /**
     * Container's getter for Company1.
     * @return Company1
     */
    public CompanyVOImpl getCompany1() {
        return (CompanyVOImpl) findViewObject("Company1");
    }

    /**
     * Container's getter for LocationCountryV2.
     * @return LocationCountryV2
     */
    public LocationCountryVVOImpl getLocationCountryV2() {
        return (LocationCountryVVOImpl) findViewObject("LocationCountryV2");
    }

    /**
     * Container's getter for SpreadHeardFrom1.
     * @return SpreadHeardFrom1
     */
    public SpreadHeardFromVOImpl getSpreadHeardFrom1() {
        return (SpreadHeardFromVOImpl) findViewObject("SpreadHeardFrom1");
    }

    /**
     * Container's getter for LancerResume1.
     * @return LancerResume1
     */
    public LancerResumeVOImpl getLancerResume1() {
        return (LancerResumeVOImpl) findViewObject("LancerResume1");
    }


    /**
     * Container's getter for LancerSkill1.
     * @return LancerSkill1
     */
    public LancerSkillVOImpl getLancerSkill1() {
        return (LancerSkillVOImpl) findViewObject("LancerSkill1");
    }

    /**
     * Container's getter for LancerResume_SkillVL1.
     * @return LancerResume_SkillVL1
     */
    public ViewLinkImpl getLancerResume_SkillVL1() {
        return (ViewLinkImpl) findViewLink("LancerResume_SkillVL1");
    }

    /**
     * Container's getter for LancerEducation1.
     * @return LancerEducation1
     */
    public BaseViewObjectImpl getLancerEducation1() {
        return (BaseViewObjectImpl) findViewObject("LancerEducation1");
    }

    /**
     * Container's getter for LancerResume_EducationVL1.
     * @return LancerResume_EducationVL1
     */
    public ViewLinkImpl getLancerResume_EducationVL1() {
        return (ViewLinkImpl) findViewLink("LancerResume_EducationVL1");
    }

    /**
     * Container's getter for LancerLocationList1.
     * @return LancerLocationList1
     */
    public LancerLocationListVOImpl getLancerLocationList1() {
        return (LancerLocationListVOImpl) findViewObject("LancerLocationList1");
    }

    /**
     * Container's getter for Lancer_LocationListVL1.
     * @return Lancer_LocationListVL1
     */
    public ViewLinkImpl getLancer_LocationListVL1() {
        return (ViewLinkImpl) findViewLink("Lancer_LocationListVL1");
    }

    /**
     * Container's getter for LancerSetting1.
     * @return LancerSetting1
     */
    public BaseViewObjectImpl getLancerSetting1() {
        return (BaseViewObjectImpl) findViewObject("LancerSetting1");
    }

    /**
     * Container's getter for Lancer_LancerSettingVL1.
     * @return Lancer_LancerSettingVL1
     */
    public ViewLinkImpl getLancer_LancerSettingVL1() {
        return (ViewLinkImpl) findViewLink("Lancer_LancerSettingVL1");
    }

    /**
     * Container's getter for JobCategory1.
     * @return JobCategory1
     */
    public BaseViewObjectImpl getJobCategory1() {
        return (BaseViewObjectImpl) findViewObject("JobCategory1");
    }

    /**
     * Container's getter for JobSubCategory1.
     * @return JobSubCategory1
     */
    public BaseViewObjectImpl getJobSubCategory1() {
        return (BaseViewObjectImpl) findViewObject("JobSubCategory1");
    }

    /**
     * Container's getter for JobCategory_SubCategoryVL1.
     * @return JobCategory_SubCategoryVL1
     */
    public ViewLinkImpl getJobCategory_SubCategoryVL1() {
        return (ViewLinkImpl) findViewLink("JobCategory_SubCategoryVL1");
    }

    /**
     * Container's getter for JobTemplate1.
     * @return JobTemplate1
     */
    public JobTemplateVOImpl getJobTemplate1() {
        return (JobTemplateVOImpl) findViewObject("JobTemplate1");
    }

    /**
     * Container's getter for JobTemplateJobCategoryFk1VL1.
     * @return JobTemplateJobCategoryFk1VL1
     */
    public ViewLinkImpl getJobTemplateJobCategoryFk1VL1() {
        return (ViewLinkImpl) findViewLink("JobTemplateJobCategoryFk1VL1");
    }

    /**
     * Container's getter for Skills1.
     * @return Skills1
     */
    public SkillsVOImpl getSkills1() {
        return (SkillsVOImpl) findViewObject("Skills1");
    }

    /**
     * Container's getter for PostJobs1.
     * @return PostJobs1
     */
    public PostJobsVOImpl getPostJobs1() {
        return (PostJobsVOImpl) findViewObject("PostJobs1");
    }

    /**
     * Container's getter for LocationProvince1.
     * @return LocationProvince1
     */
    public BaseViewObjectImpl getLocationProvince1() {
        return (BaseViewObjectImpl) findViewObject("LocationProvince1");
    }


    /**
     * Container's getter for LocationCity1.
     * @return LocationCity1
     */
    public BaseViewObjectImpl getLocationCity1() {
        return (BaseViewObjectImpl) findViewObject("LocationCity1");
    }

    /**
     * Container's getter for LocationProvince_CityVL1.
     * @return LocationProvince_CityVL1
     */
    public ViewLinkImpl getLocationProvince_CityVL1() {
        return (ViewLinkImpl) findViewLink("LocationProvince_CityVL1");
    }

    /**
     * Container's getter for PostJobsV1.
     * @return PostJobsV1
     */
    public PostJobsVVOImpl getPostJobsV1() {
        return (PostJobsVVOImpl) findViewObject("PostJobsV1");
    }

    /**
     * Container's getter for LoginUserRole1.
     * @return LoginUserRole1
     */
    public LoginUserRoleVOImpl getLoginUserRole1() {
        return (LoginUserRoleVOImpl) findViewObject("LoginUserRole1");
    }

    /**
     * Container's getter for LoginUserRoleGrants1.
     * @return LoginUserRoleGrants1
     */
    public LoginUserRoleGrantsVOImpl getLoginUserRoleGrants1() {
        return (LoginUserRoleGrantsVOImpl) findViewObject("LoginUserRoleGrants1");
    }
}

