package com.lance.model;

import com.lance.model.common.LanceAdminAM;
import com.lance.model.user.vo.UUserVOImpl;
import com.lance.model.user.vo.UserEducationVOImpl;
import com.lance.model.user.vo.UserLocationListVOImpl;
import com.lance.model.user.vo.UserRoleGrantsVOImpl;
import com.lance.model.user.vo.UserRoleVOImpl;
import com.lance.model.user.vo.UserSkillVOImpl;
import com.lance.model.vo.CompanyVOImpl;
import com.lance.model.vo.JobCategoryVOImpl;
import com.lance.model.vo.JobSubCategoryExpertsVOImpl;
import com.lance.model.vo.JobSubCategoryVOImpl;
import com.lance.model.vo.JobTemplateVOImpl;
import com.lance.model.vo.SysCalendarVOImpl;
import com.lance.model.vo.SysCalendarVORowImpl;

import com.lance.model.vo.UserJobCategoryVOImpl;

import com.zngh.platform.front.core.model.BaseApplicationModuleImpl;

import com.zngh.platform.front.core.model.BaseViewObjectImpl;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import oracle.jbo.server.ViewLinkImpl;

import org.apache.commons.lang.StringUtils;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Mar 04 15:20:01 CST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LanceAdminAMImpl extends BaseApplicationModuleImpl implements LanceAdminAM {
    /**
     * This is the default constructor (do not remove).
     */
    public LanceAdminAMImpl() {
    }

    public String findJobTemplate(String category, String subCategory,String attr1,String attr2) {
        JobTemplateVOImpl vo = this.getJobTemplate1();
        if(StringUtils.isNotBlank(category)){
            vo.setApplyViewCriteriaName("FindByCategoryVC");
            vo.setpJobCategoryId(category);
            vo.setpCreateBy(this.findCurrentUserId());
        }
        vo.executeQuery();
        vo.removeApplyViewCriteriaName("FindByCategoryVC");
        return null;
    }

    /**
     * 创建当前日期往后指定时间内的时间。
     * @return
     */
    public String createSysCalendarDates(Integer start, Integer end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        SysCalendarVOImpl vo = this.getSysCalendar1();
        SysCalendarVORowImpl row;
        for (int i = start; i <= end; i++) {
            row = (SysCalendarVORowImpl) vo.createRow();
            c.add(Calendar.DAY_OF_YEAR, 1);
            Date date = c.getTime();
            System.out.println(sdf.format(date) + ":" + getWeekOfDate(date));
            row.setSysDateRec(sdf.format(date));
            row.setDay(getWeekOfDate(date));
            row.setShowOrder((long) i);
            if (row.getDay().equals(7) || row.getDay().equals(6)) {
                row.setDateType("SDT_WEEKEND");
            } else {
                row.setDateType("SDT_WORK");
            }
            vo.insertRow(row);
        }
        return "ok";
    }


    public Integer getWeekOfDate(Date dt) {
        Integer[] weekDays = { 7, 1, 2, 3, 4, 5, 6 };
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * Container's getter for JobCategory1.
     * @return JobCategory1
     */
    public JobCategoryVOImpl getJobCategory1() {
        return (JobCategoryVOImpl) findViewObject("JobCategory1");
    }

    /**
     * Container's getter for JobTemplate1.
     * @return JobTemplate1
     */
    public JobTemplateVOImpl getJobTemplate1() {
        return (JobTemplateVOImpl) findViewObject("JobTemplate1");
    }

    /**
     * Container's getter for JobTemplate2.
     * @return JobTemplate2
     */
    public JobTemplateVOImpl getJobTemplate2() {
        return (JobTemplateVOImpl) findViewObject("JobTemplate2");
    }

    /**
     * Container's getter for JobSubCategory1.
     * @return JobSubCategory1
     */
    public JobSubCategoryVOImpl getJobSubCategory1() {
        return (JobSubCategoryVOImpl) findViewObject("JobSubCategory1");
    }

    /**
     * Container's getter for SysCalendar1.
     * @return SysCalendar1
     */
    public SysCalendarVOImpl getSysCalendar1() {
        return (SysCalendarVOImpl) findViewObject("SysCalendar1");
    }

    /**
     * Container's getter for JobTemplateJobCategoryFk1VL1.
     * @return JobTemplateJobCategoryFk1VL1
     */
    public ViewLinkImpl getJobTemplateJobCategoryFk1VL1() {
        return (ViewLinkImpl) findViewLink("JobTemplateJobCategoryFk1VL1");
    }

    /**
     * Container's getter for JobCategory_SubCategoryVL1.
     * @return JobCategory_SubCategoryVL1
     */
    public ViewLinkImpl getJobCategory_SubCategoryVL1() {
        return (ViewLinkImpl) findViewLink("JobCategory_SubCategoryVL1");
    }

    /**
     * Container's getter for UUser1.
     * @return UUser1
     */
    public UUserVOImpl getUUser1() {
        return (UUserVOImpl) findViewObject("UUser1");
    }


    /**
     * Container's getter for Company1.
     * @return Company1
     */
    public CompanyVOImpl getCompany1() {
        return (CompanyVOImpl) findViewObject("Company1");
    }

    /**
     * Container's getter for UUser_CompanyVL1.
     * @return UUser_CompanyVL1
     */
    public ViewLinkImpl getUUser_CompanyVL1() {
        return (ViewLinkImpl) findViewLink("UUser_CompanyVL1");
    }

    /**
     * Container's getter for UserEducation1.
     * @return UserEducation1
     */
    public UserEducationVOImpl getUserEducation1() {
        return (UserEducationVOImpl) findViewObject("UserEducation1");
    }

    /**
     * Container's getter for UserEducationUUserFk1VL1.
     * @return UserEducationUUserFk1VL1
     */
    public ViewLinkImpl getUserEducationUUserFk1VL1() {
        return (ViewLinkImpl) findViewLink("UserEducationUUserFk1VL1");
    }

    /**
     * Container's getter for UserLocationList1.
     * @return UserLocationList1
     */
    public UserLocationListVOImpl getUserLocationList1() {
        return (UserLocationListVOImpl) findViewObject("UserLocationList1");
    }

    /**
     * Container's getter for UserLocationListUUserFk1VL1.
     * @return UserLocationListUUserFk1VL1
     */
    public ViewLinkImpl getUserLocationListUUserFk1VL1() {
        return (ViewLinkImpl) findViewLink("UserLocationListUUserFk1VL1");
    }

    /**
     * Container's getter for UserSkill1.
     * @return UserSkill1
     */
    public UserSkillVOImpl getUserSkill1() {
        return (UserSkillVOImpl) findViewObject("UserSkill1");
    }

    /**
     * Container's getter for UserSkillUUserFk1VL1.
     * @return UserSkillUUserFk1VL1
     */
    public ViewLinkImpl getUserSkillUUserFk1VL1() {
        return (ViewLinkImpl) findViewLink("UserSkillUUserFk1VL1");
    }

    /**
     * Container's getter for UserRole1.
     * @return UserRole1
     */
    public UserRoleVOImpl getUserRole1() {
        return (UserRoleVOImpl) findViewObject("UserRole1");
    }

    /**
     * Container's getter for UserRoleGrants1.
     * @return UserRoleGrants1
     */
    public UserRoleGrantsVOImpl getUserRoleGrants1() {
        return (UserRoleGrantsVOImpl) findViewObject("UserRoleGrants1");
    }

    /**
     * Container's getter for JobTemplate3.
     * @return JobTemplate3
     */
    public JobTemplateVOImpl getJobTemplate3() {
        return (JobTemplateVOImpl) findViewObject("JobTemplate3");
    }

    /**
     * Container's getter for JobTemplateCategory_TemplateSubCategoryVL1.
     * @return JobTemplateCategory_TemplateSubCategoryVL1
     */
    public ViewLinkImpl getJobTemplateCategory_TemplateSubCategoryVL1() {
        return (ViewLinkImpl) findViewLink("JobTemplateCategory_TemplateSubCategoryVL1");
    }

    /**
     * Container's getter for JobSubCategoryExperts1.
     * @return JobSubCategoryExperts1
     */
    public JobSubCategoryExpertsVOImpl getJobSubCategoryExperts1() {
        return (JobSubCategoryExpertsVOImpl) findViewObject("JobSubCategoryExperts1");
    }

    /**
     * Container's getter for JobSubCategory_ExportsVL1.
     * @return JobSubCategory_ExportsVL1
     */
    public ViewLinkImpl getJobSubCategory_ExportsVL1() {
        return (ViewLinkImpl) findViewLink("JobSubCategory_ExportsVL1");
    }

    /**
     * Container's getter for JobSubCategorySubmit1.
     * @return JobSubCategorySubmit1
     */
    public BaseViewObjectImpl getJobSubCategorySubmit1() {
        return (BaseViewObjectImpl) findViewObject("JobSubCategorySubmit1");
    }

    /**
     * Container's getter for JobSubCategory_SubCategorySubmitVL1.
     * @return JobSubCategory_SubCategorySubmitVL1
     */
    public ViewLinkImpl getJobSubCategory_SubCategorySubmitVL1() {
        return (ViewLinkImpl) findViewLink("JobSubCategory_SubCategorySubmitVL1");
    }

    /**
     * Container's getter for UserJobCategory1.
     * @return UserJobCategory1
     */
    public UserJobCategoryVOImpl getUserJobCategory1() {
        return (UserJobCategoryVOImpl) findViewObject("UserJobCategory1");
    }
}

