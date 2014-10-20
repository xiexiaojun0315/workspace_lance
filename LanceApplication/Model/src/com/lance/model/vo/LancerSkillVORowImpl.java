package com.lance.model.vo;

import com.zngh.platform.front.core.model.BaseEntityImpl;
import com.zngh.platform.front.core.model.BaseViewRowImpl;

import java.math.BigDecimal;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Oct 04 01:10:12 CST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LancerSkillVORowImpl extends BaseViewRowImpl {
    public static final int ENTITY_LANCERSKILLEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Uuid,
        Name,
        Tested,
        Display,
        ShowOrder,
        MasterLevel,
        ResumeId;
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
    public static final int TESTED = AttributesEnum.Tested.index();
    public static final int DISPLAY = AttributesEnum.Display.index();
    public static final int SHOWORDER = AttributesEnum.ShowOrder.index();
    public static final int MASTERLEVEL = AttributesEnum.MasterLevel.index();
    public static final int RESUMEID = AttributesEnum.ResumeId.index();

    /**
     * This is the default constructor (do not remove).
     */
    public LancerSkillVORowImpl() {
    }

    /**
     * Gets LancerSkillEO entity object.
     * @return the LancerSkillEO
     */
    public BaseEntityImpl getLancerSkillEO() {
        return (BaseEntityImpl) getEntity(ENTITY_LANCERSKILLEO);
    }

    /**
     * Gets the attribute value for UUID using the alias name Uuid.
     * @return the UUID
     */
    public String getUuid() {
        return (String) getAttributeInternal(UUID);
    }

    /**
     * Sets <code>value</code> as attribute value for UUID using the alias name Uuid.
     * @param value value to set the UUID
     */
    public void setUuid(String value) {
        setAttributeInternal(UUID, value);
    }

    /**
     * Gets the attribute value for NAME using the alias name Name.
     * @return the NAME
     */
    public String getName() {
        return (String) getAttributeInternal(NAME);
    }

    /**
     * Sets <code>value</code> as attribute value for NAME using the alias name Name.
     * @param value value to set the NAME
     */
    public void setName(String value) {
        setAttributeInternal(NAME, value);
    }

    /**
     * Gets the attribute value for TESTED using the alias name Tested.
     * @return the TESTED
     */
    public Integer getTested() {
        return (Integer) getAttributeInternal(TESTED);
    }

    /**
     * Sets <code>value</code> as attribute value for TESTED using the alias name Tested.
     * @param value value to set the TESTED
     */
    public void setTested(Integer value) {
        setAttributeInternal(TESTED, value);
    }

    /**
     * Gets the attribute value for DISPLAY using the alias name Display.
     * @return the DISPLAY
     */
    public Integer getDisplay() {
        return (Integer) getAttributeInternal(DISPLAY);
    }

    /**
     * Sets <code>value</code> as attribute value for DISPLAY using the alias name Display.
     * @param value value to set the DISPLAY
     */
    public void setDisplay(Integer value) {
        setAttributeInternal(DISPLAY, value);
    }

    /**
     * Gets the attribute value for SHOW_ORDER using the alias name ShowOrder.
     * @return the SHOW_ORDER
     */
    public Integer getShowOrder() {
        return (Integer) getAttributeInternal(SHOWORDER);
    }

    /**
     * Sets <code>value</code> as attribute value for SHOW_ORDER using the alias name ShowOrder.
     * @param value value to set the SHOW_ORDER
     */
    public void setShowOrder(Integer value) {
        setAttributeInternal(SHOWORDER, value);
    }

    /**
     * Gets the attribute value for MASTER_LEVEL using the alias name MasterLevel.
     * @return the MASTER_LEVEL
     */
    public Integer getMasterLevel() {
        return (Integer) getAttributeInternal(MASTERLEVEL);
    }

    /**
     * Sets <code>value</code> as attribute value for MASTER_LEVEL using the alias name MasterLevel.
     * @param value value to set the MASTER_LEVEL
     */
    public void setMasterLevel(Integer value) {
        setAttributeInternal(MASTERLEVEL, value);
    }

    /**
     * Gets the attribute value for RESUME_ID using the alias name ResumeId.
     * @return the RESUME_ID
     */
    public String getResumeId() {
        return (String) getAttributeInternal(RESUMEID);
    }

    /**
     * Sets <code>value</code> as attribute value for RESUME_ID using the alias name ResumeId.
     * @param value value to set the RESUME_ID
     */
    public void setResumeId(String value) {
        setAttributeInternal(RESUMEID, value);
    }
}
