package com.lance.model.vo;

import com.zngh.platform.front.core.model.BaseEntityImpl;
import com.zngh.platform.front.core.model.BaseViewRowImpl;

import java.math.BigDecimal;

import java.sql.Timestamp;

import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Feb 12 09:54:28 CST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class JobSubCategoryVORowImpl extends BaseViewRowImpl {

    public static final int ENTITY_JOBSUBCATEGORYEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Uuid,
        CategoryId,
        Name,
        CreateBy,
        CreateOn,
        ModifyBy,
        ModifyOn,
        Version;
        static AttributesEnum[] vals = null;
        ;
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
    public static final int CATEGORYID = AttributesEnum.CategoryId.index();
    public static final int NAME = AttributesEnum.Name.index();
    public static final int CREATEBY = AttributesEnum.CreateBy.index();
    public static final int CREATEON = AttributesEnum.CreateOn.index();
    public static final int MODIFYBY = AttributesEnum.ModifyBy.index();
    public static final int MODIFYON = AttributesEnum.ModifyOn.index();
    public static final int VERSION = AttributesEnum.Version.index();

    /**
     * This is the default constructor (do not remove).
     */
    public JobSubCategoryVORowImpl() {
    }

    /**
     * Gets JobSubCategoryEO entity object.
     * @return the JobSubCategoryEO
     */
    public BaseEntityImpl getJobSubCategoryEO() {
        return (BaseEntityImpl) getEntity(ENTITY_JOBSUBCATEGORYEO);
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
     * Gets the attribute value for CATEGORY_ID using the alias name CategoryId.
     * @return the CATEGORY_ID
     */
    public String getCategoryId() {
        return (String) getAttributeInternal(CATEGORYID);
    }

    /**
     * Sets <code>value</code> as attribute value for CATEGORY_ID using the alias name CategoryId.
     * @param value value to set the CATEGORY_ID
     */
    public void setCategoryId(String value) {
        setAttributeInternal(CATEGORYID, value);
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
     * Gets the attribute value for CREATE_BY using the alias name CreateBy.
     * @return the CREATE_BY
     */
    public String getCreateBy() {
        return (String) getAttributeInternal(CREATEBY);
    }

    /**
     * Gets the attribute value for CREATE_ON using the alias name CreateOn.
     * @return the CREATE_ON
     */
    public Timestamp getCreateOn() {
        return (Timestamp) getAttributeInternal(CREATEON);
    }

    /**
     * Gets the attribute value for MODIFY_BY using the alias name ModifyBy.
     * @return the MODIFY_BY
     */
    public String getModifyBy() {
        return (String) getAttributeInternal(MODIFYBY);
    }

    /**
     * Gets the attribute value for MODIFY_ON using the alias name ModifyOn.
     * @return the MODIFY_ON
     */
    public Timestamp getModifyOn() {
        return (Timestamp) getAttributeInternal(MODIFYON);
    }

    /**
     * Gets the attribute value for VERSION using the alias name Version.
     * @return the VERSION
     */
    public BigDecimal getVersion() {
        return (BigDecimal) getAttributeInternal(VERSION);
    }
}

