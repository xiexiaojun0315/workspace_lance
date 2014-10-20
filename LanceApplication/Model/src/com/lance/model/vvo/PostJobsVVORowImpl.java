package com.lance.model.vvo;

import com.zngh.platform.front.core.model.BaseViewRowImpl;

import java.math.BigDecimal;

import java.sql.Timestamp;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Oct 20 09:42:45 CST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PostJobsVVORowImpl extends BaseViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Uuid,
        Name,
        Brief,
        Attach,
        WorkSubcategory,
        Skills,
        Postform,
        HourlyPayMin,
        WeeklyHours,
        DurationMin,
        FixedPayMin,
        JobVisibility,
        AllowSearchEngines,
        FixedLocation,
        LocationId,
        LocationDesc,
        WorkCategory,
        SpecificSkillA,
        SpecificSkillB,
        SpecificSkillC,
        SpecificSkillD,
        SpecificSkillE,
        SpecificSkillF,
        SpecificSkillG,
        HourlyPayMax,
        DayPayMax,
        DayPayMin,
        FixedPayMax,
        DurationMax,
        Status,
        PostJobDateStart,
        PostJobDateEnd,
        LocationCountry,
        LocationProvince,
        LocationCity,
        CreateBy,
        CreateOn,
        ModifiedBy,
        ModifiedOn,
        Version;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }

    public static final int UUID = AttributesEnum.Uuid.index();
    public static final int NAME = AttributesEnum.Name.index();
    public static final int BRIEF = AttributesEnum.Brief.index();
    public static final int ATTACH = AttributesEnum.Attach.index();
    public static final int WORKSUBCATEGORY = AttributesEnum.WorkSubcategory.index();
    public static final int SKILLS = AttributesEnum.Skills.index();
    public static final int POSTFORM = AttributesEnum.Postform.index();
    public static final int HOURLYPAYMIN = AttributesEnum.HourlyPayMin.index();
    public static final int WEEKLYHOURS = AttributesEnum.WeeklyHours.index();
    public static final int DURATIONMIN = AttributesEnum.DurationMin.index();
    public static final int FIXEDPAYMIN = AttributesEnum.FixedPayMin.index();
    public static final int JOBVISIBILITY = AttributesEnum.JobVisibility.index();
    public static final int ALLOWSEARCHENGINES = AttributesEnum.AllowSearchEngines.index();
    public static final int FIXEDLOCATION = AttributesEnum.FixedLocation.index();
    public static final int LOCATIONID = AttributesEnum.LocationId.index();
    public static final int LOCATIONDESC = AttributesEnum.LocationDesc.index();
    public static final int WORKCATEGORY = AttributesEnum.WorkCategory.index();
    public static final int SPECIFICSKILLA = AttributesEnum.SpecificSkillA.index();
    public static final int SPECIFICSKILLB = AttributesEnum.SpecificSkillB.index();
    public static final int SPECIFICSKILLC = AttributesEnum.SpecificSkillC.index();
    public static final int SPECIFICSKILLD = AttributesEnum.SpecificSkillD.index();
    public static final int SPECIFICSKILLE = AttributesEnum.SpecificSkillE.index();
    public static final int SPECIFICSKILLF = AttributesEnum.SpecificSkillF.index();
    public static final int SPECIFICSKILLG = AttributesEnum.SpecificSkillG.index();
    public static final int HOURLYPAYMAX = AttributesEnum.HourlyPayMax.index();
    public static final int DAYPAYMAX = AttributesEnum.DayPayMax.index();
    public static final int DAYPAYMIN = AttributesEnum.DayPayMin.index();
    public static final int FIXEDPAYMAX = AttributesEnum.FixedPayMax.index();
    public static final int DURATIONMAX = AttributesEnum.DurationMax.index();
    public static final int STATUS = AttributesEnum.Status.index();
    public static final int POSTJOBDATESTART = AttributesEnum.PostJobDateStart.index();
    public static final int POSTJOBDATEEND = AttributesEnum.PostJobDateEnd.index();
    public static final int LOCATIONCOUNTRY = AttributesEnum.LocationCountry.index();
    public static final int LOCATIONPROVINCE = AttributesEnum.LocationProvince.index();
    public static final int LOCATIONCITY = AttributesEnum.LocationCity.index();
    public static final int CREATEBY = AttributesEnum.CreateBy.index();
    public static final int CREATEON = AttributesEnum.CreateOn.index();
    public static final int MODIFIEDBY = AttributesEnum.ModifiedBy.index();
    public static final int MODIFIEDON = AttributesEnum.ModifiedOn.index();
    public static final int VERSION = AttributesEnum.Version.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PostJobsVVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Uuid.
     * @return the Uuid
     */
    public String getUuid() {
        return (String) getAttributeInternal(UUID);
    }

    /**
     * Gets the attribute value for the calculated attribute Name.
     * @return the Name
     */
    public String getName() {
        return (String) getAttributeInternal(NAME);
    }

    /**
     * Gets the attribute value for the calculated attribute Brief.
     * @return the Brief
     */
    public String getBrief() {
        return (String) getAttributeInternal(BRIEF);
    }

    /**
     * Gets the attribute value for the calculated attribute Attach.
     * @return the Attach
     */
    public String getAttach() {
        return (String) getAttributeInternal(ATTACH);
    }

    /**
     * Gets the attribute value for the calculated attribute WorkSubcategory.
     * @return the WorkSubcategory
     */
    public String getWorkSubcategory() {
        return (String) getAttributeInternal(WORKSUBCATEGORY);
    }

    /**
     * Gets the attribute value for the calculated attribute Skills.
     * @return the Skills
     */
    public String getSkills() {
        return (String) getAttributeInternal(SKILLS);
    }

    /**
     * Gets the attribute value for the calculated attribute Postform.
     * @return the Postform
     */
    public BigDecimal getPostform() {
        return (BigDecimal) getAttributeInternal(POSTFORM);
    }

    /**
     * Gets the attribute value for the calculated attribute HourlyPayMin.
     * @return the HourlyPayMin
     */
    public BigDecimal getHourlyPayMin() {
        return (BigDecimal) getAttributeInternal(HOURLYPAYMIN);
    }

    /**
     * Gets the attribute value for the calculated attribute WeeklyHours.
     * @return the WeeklyHours
     */
    public BigDecimal getWeeklyHours() {
        return (BigDecimal) getAttributeInternal(WEEKLYHOURS);
    }

    /**
     * Gets the attribute value for the calculated attribute DurationMin.
     * @return the DurationMin
     */
    public BigDecimal getDurationMin() {
        return (BigDecimal) getAttributeInternal(DURATIONMIN);
    }

    /**
     * Gets the attribute value for the calculated attribute FixedPayMin.
     * @return the FixedPayMin
     */
    public BigDecimal getFixedPayMin() {
        return (BigDecimal) getAttributeInternal(FIXEDPAYMIN);
    }

    /**
     * Gets the attribute value for the calculated attribute JobVisibility.
     * @return the JobVisibility
     */
    public BigDecimal getJobVisibility() {
        return (BigDecimal) getAttributeInternal(JOBVISIBILITY);
    }

    /**
     * Gets the attribute value for the calculated attribute AllowSearchEngines.
     * @return the AllowSearchEngines
     */
    public BigDecimal getAllowSearchEngines() {
        return (BigDecimal) getAttributeInternal(ALLOWSEARCHENGINES);
    }

    /**
     * Gets the attribute value for the calculated attribute FixedLocation.
     * @return the FixedLocation
     */
    public BigDecimal getFixedLocation() {
        return (BigDecimal) getAttributeInternal(FIXEDLOCATION);
    }

    /**
     * Gets the attribute value for the calculated attribute LocationId.
     * @return the LocationId
     */
    public String getLocationId() {
        return (String) getAttributeInternal(LOCATIONID);
    }

    /**
     * Gets the attribute value for the calculated attribute LocationDesc.
     * @return the LocationDesc
     */
    public String getLocationDesc() {
        return (String) getAttributeInternal(LOCATIONDESC);
    }

    /**
     * Gets the attribute value for the calculated attribute WorkCategory.
     * @return the WorkCategory
     */
    public String getWorkCategory() {
        return (String) getAttributeInternal(WORKCATEGORY);
    }

    /**
     * Gets the attribute value for the calculated attribute SpecificSkillA.
     * @return the SpecificSkillA
     */
    public String getSpecificSkillA() {
        return (String) getAttributeInternal(SPECIFICSKILLA);
    }

    /**
     * Gets the attribute value for the calculated attribute SpecificSkillB.
     * @return the SpecificSkillB
     */
    public String getSpecificSkillB() {
        return (String) getAttributeInternal(SPECIFICSKILLB);
    }

    /**
     * Gets the attribute value for the calculated attribute SpecificSkillC.
     * @return the SpecificSkillC
     */
    public String getSpecificSkillC() {
        return (String) getAttributeInternal(SPECIFICSKILLC);
    }

    /**
     * Gets the attribute value for the calculated attribute SpecificSkillD.
     * @return the SpecificSkillD
     */
    public String getSpecificSkillD() {
        return (String) getAttributeInternal(SPECIFICSKILLD);
    }

    /**
     * Gets the attribute value for the calculated attribute SpecificSkillE.
     * @return the SpecificSkillE
     */
    public String getSpecificSkillE() {
        return (String) getAttributeInternal(SPECIFICSKILLE);
    }

    /**
     * Gets the attribute value for the calculated attribute SpecificSkillF.
     * @return the SpecificSkillF
     */
    public String getSpecificSkillF() {
        return (String) getAttributeInternal(SPECIFICSKILLF);
    }

    /**
     * Gets the attribute value for the calculated attribute SpecificSkillG.
     * @return the SpecificSkillG
     */
    public String getSpecificSkillG() {
        return (String) getAttributeInternal(SPECIFICSKILLG);
    }

    /**
     * Gets the attribute value for the calculated attribute HourlyPayMax.
     * @return the HourlyPayMax
     */
    public BigDecimal getHourlyPayMax() {
        return (BigDecimal) getAttributeInternal(HOURLYPAYMAX);
    }

    /**
     * Gets the attribute value for the calculated attribute DayPayMax.
     * @return the DayPayMax
     */
    public BigDecimal getDayPayMax() {
        return (BigDecimal) getAttributeInternal(DAYPAYMAX);
    }

    /**
     * Gets the attribute value for the calculated attribute DayPayMin.
     * @return the DayPayMin
     */
    public BigDecimal getDayPayMin() {
        return (BigDecimal) getAttributeInternal(DAYPAYMIN);
    }

    /**
     * Gets the attribute value for the calculated attribute FixedPayMax.
     * @return the FixedPayMax
     */
    public BigDecimal getFixedPayMax() {
        return (BigDecimal) getAttributeInternal(FIXEDPAYMAX);
    }

    /**
     * Gets the attribute value for the calculated attribute DurationMax.
     * @return the DurationMax
     */
    public BigDecimal getDurationMax() {
        return (BigDecimal) getAttributeInternal(DURATIONMAX);
    }

    /**
     * Gets the attribute value for the calculated attribute Status.
     * @return the Status
     */
    public Integer getStatus() {
        return (Integer) getAttributeInternal(STATUS);
    }

    /**
     * Gets the attribute value for the calculated attribute PostJobDateStart.
     * @return the PostJobDateStart
     */
    public Timestamp getPostJobDateStart() {
        return (Timestamp) getAttributeInternal(POSTJOBDATESTART);
    }

    /**
     * Gets the attribute value for the calculated attribute PostJobDateEnd.
     * @return the PostJobDateEnd
     */
    public Timestamp getPostJobDateEnd() {
        return (Timestamp) getAttributeInternal(POSTJOBDATEEND);
    }

    /**
     * Gets the attribute value for the calculated attribute LocationCountry.
     * @return the LocationCountry
     */
    public String getLocationCountry() {
        return (String) getAttributeInternal(LOCATIONCOUNTRY);
    }

    /**
     * Gets the attribute value for the calculated attribute LocationProvince.
     * @return the LocationProvince
     */
    public String getLocationProvince() {
        return (String) getAttributeInternal(LOCATIONPROVINCE);
    }

    /**
     * Gets the attribute value for the calculated attribute LocationCity.
     * @return the LocationCity
     */
    public String getLocationCity() {
        return (String) getAttributeInternal(LOCATIONCITY);
    }

    /**
     * Gets the attribute value for the calculated attribute CreateBy.
     * @return the CreateBy
     */
    public String getCreateBy() {
        return (String) getAttributeInternal(CREATEBY);
    }

    /**
     * Gets the attribute value for the calculated attribute CreateOn.
     * @return the CreateOn
     */
    public Timestamp getCreateOn() {
        return (Timestamp) getAttributeInternal(CREATEON);
    }

    /**
     * Gets the attribute value for the calculated attribute ModifiedBy.
     * @return the ModifiedBy
     */
    public String getModifiedBy() {
        return (String) getAttributeInternal(MODIFIEDBY);
    }

    /**
     * Gets the attribute value for the calculated attribute ModifiedOn.
     * @return the ModifiedOn
     */
    public Timestamp getModifiedOn() {
        return (Timestamp) getAttributeInternal(MODIFIEDON);
    }

    /**
     * Gets the attribute value for the calculated attribute Version.
     * @return the Version
     */
    public BigDecimal getVersion() {
        return (BigDecimal) getAttributeInternal(VERSION);
    }
}

