package com.lance.model.vo;

import com.zngh.platform.front.core.model.BaseEntityImpl;
import com.zngh.platform.front.core.model.BaseViewRowImpl;

import java.math.BigDecimal;

import java.sql.Timestamp;

import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Nov 12 14:02:52 CST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LocationCityVORowImpl extends BaseViewRowImpl {

    public static final int ENTITY_LOCATIONCITYEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Uuid,
        CityCode,
        CityName,
        ProvinceCode,
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
    public static final int CITYCODE = AttributesEnum.CityCode.index();
    public static final int CITYNAME = AttributesEnum.CityName.index();
    public static final int PROVINCECODE = AttributesEnum.ProvinceCode.index();
    public static final int CREATEBY = AttributesEnum.CreateBy.index();
    public static final int CREATEON = AttributesEnum.CreateOn.index();
    public static final int MODIFYBY = AttributesEnum.ModifyBy.index();
    public static final int MODIFYON = AttributesEnum.ModifyOn.index();
    public static final int VERSION = AttributesEnum.Version.index();

    /**
     * This is the default constructor (do not remove).
     */
    public LocationCityVORowImpl() {
    }

    /**
     * Gets LocationCityEO entity object.
     * @return the LocationCityEO
     */
    public EntityImpl getLocationCityEO() {
        return (EntityImpl) getEntity(ENTITY_LOCATIONCITYEO);
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
     * Gets the attribute value for CITY_CODE using the alias name CityCode.
     * @return the CITY_CODE
     */
    public String getCityCode() {
        return (String) getAttributeInternal(CITYCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for CITY_CODE using the alias name CityCode.
     * @param value value to set the CITY_CODE
     */
    public void setCityCode(String value) {
        setAttributeInternal(CITYCODE, value);
    }

    /**
     * Gets the attribute value for CITY_NAME using the alias name CityName.
     * @return the CITY_NAME
     */
    public String getCityName() {
        return (String) getAttributeInternal(CITYNAME);
    }

    /**
     * Sets <code>value</code> as attribute value for CITY_NAME using the alias name CityName.
     * @param value value to set the CITY_NAME
     */
    public void setCityName(String value) {
        setAttributeInternal(CITYNAME, value);
    }

    /**
     * Gets the attribute value for PROVINCE_CODE using the alias name ProvinceCode.
     * @return the PROVINCE_CODE
     */
    public String getProvinceCode() {
        return (String) getAttributeInternal(PROVINCECODE);
    }

    /**
     * Sets <code>value</code> as attribute value for PROVINCE_CODE using the alias name ProvinceCode.
     * @param value value to set the PROVINCE_CODE
     */
    public void setProvinceCode(String value) {
        setAttributeInternal(PROVINCECODE, value);
    }

    /**
     * Gets the attribute value for CREATE_BY using the alias name CreateBy.
     * @return the CREATE_BY
     */
    public String getCreateBy() {
        return (String) getAttributeInternal(CREATEBY);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATE_BY using the alias name CreateBy.
     * @param value value to set the CREATE_BY
     */
    public void setCreateBy(String value) {
        setAttributeInternal(CREATEBY, value);
    }

    /**
     * Gets the attribute value for CREATE_ON using the alias name CreateOn.
     * @return the CREATE_ON
     */
    public Timestamp getCreateOn() {
        return (Timestamp) getAttributeInternal(CREATEON);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATE_ON using the alias name CreateOn.
     * @param value value to set the CREATE_ON
     */
    public void setCreateOn(Timestamp value) {
        setAttributeInternal(CREATEON, value);
    }

    /**
     * Gets the attribute value for MODIFY_BY using the alias name ModifyBy.
     * @return the MODIFY_BY
     */
    public String getModifyBy() {
        return (String) getAttributeInternal(MODIFYBY);
    }

    /**
     * Sets <code>value</code> as attribute value for MODIFY_BY using the alias name ModifyBy.
     * @param value value to set the MODIFY_BY
     */
    public void setModifyBy(String value) {
        setAttributeInternal(MODIFYBY, value);
    }

    /**
     * Gets the attribute value for MODIFY_ON using the alias name ModifyOn.
     * @return the MODIFY_ON
     */
    public Timestamp getModifyOn() {
        return (Timestamp) getAttributeInternal(MODIFYON);
    }

    /**
     * Sets <code>value</code> as attribute value for MODIFY_ON using the alias name ModifyOn.
     * @param value value to set the MODIFY_ON
     */
    public void setModifyOn(Timestamp value) {
        setAttributeInternal(MODIFYON, value);
    }

    /**
     * Gets the attribute value for VERSION using the alias name Version.
     * @return the VERSION
     */
    public BigDecimal getVersion() {
        return (BigDecimal) getAttributeInternal(VERSION);
    }

    /**
     * Sets <code>value</code> as attribute value for VERSION using the alias name Version.
     * @param value value to set the VERSION
     */
    public void setVersion(BigDecimal value) {
        setAttributeInternal(VERSION, value);
    }
}

