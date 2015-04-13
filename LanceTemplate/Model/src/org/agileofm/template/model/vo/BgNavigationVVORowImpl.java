package org.agileofm.template.model.vo;

import com.zngh.platform.front.core.model.BaseViewRowImpl;

import java.math.BigDecimal;

import java.sql.Timestamp;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Mar 31 13:32:49 CST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class BgNavigationVVORowImpl extends BaseViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Uuid,
        ParentId,
        Name,
        Description,
        NavLevel,
        Url,
        Icon1,
        Icon2,
        Icon3,
        ShowOrder,
        Target,
        Deleted,
        SysKey,
        CreateBy,
        CreateOn,
        UpdateBy,
        UpdateOn,
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
    public static final int PARENTID = AttributesEnum.ParentId.index();
    public static final int NAME = AttributesEnum.Name.index();
    public static final int DESCRIPTION = AttributesEnum.Description.index();
    public static final int NAVLEVEL = AttributesEnum.NavLevel.index();
    public static final int URL = AttributesEnum.Url.index();
    public static final int ICON1 = AttributesEnum.Icon1.index();
    public static final int ICON2 = AttributesEnum.Icon2.index();
    public static final int ICON3 = AttributesEnum.Icon3.index();
    public static final int SHOWORDER = AttributesEnum.ShowOrder.index();
    public static final int TARGET = AttributesEnum.Target.index();
    public static final int DELETED = AttributesEnum.Deleted.index();
    public static final int SYSKEY = AttributesEnum.SysKey.index();
    public static final int CREATEBY = AttributesEnum.CreateBy.index();
    public static final int CREATEON = AttributesEnum.CreateOn.index();
    public static final int UPDATEBY = AttributesEnum.UpdateBy.index();
    public static final int UPDATEON = AttributesEnum.UpdateOn.index();
    public static final int VERSION = AttributesEnum.Version.index();

    /**
     * This is the default constructor (do not remove).
     */
    public BgNavigationVVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Uuid.
     * @return the Uuid
     */
    public String getUuid() {
        return (String) getAttributeInternal(UUID);
    }

    /**
     * Gets the attribute value for the calculated attribute ParentId.
     * @return the ParentId
     */
    public String getParentId() {
        return (String) getAttributeInternal(PARENTID);
    }

    /**
     * Gets the attribute value for the calculated attribute Name.
     * @return the Name
     */
    public String getName() {
        return (String) getAttributeInternal(NAME);
    }

    /**
     * Gets the attribute value for the calculated attribute Description.
     * @return the Description
     */
    public String getDescription() {
        return (String) getAttributeInternal(DESCRIPTION);
    }

    /**
     * Gets the attribute value for the calculated attribute NavLevel.
     * @return the NavLevel
     */
    public BigDecimal getNavLevel() {
        return (BigDecimal) getAttributeInternal(NAVLEVEL);
    }

    /**
     * Gets the attribute value for the calculated attribute Url.
     * @return the Url
     */
    public String getUrl() {
        return (String) getAttributeInternal(URL);
    }

    /**
     * Gets the attribute value for the calculated attribute Icon1.
     * @return the Icon1
     */
    public String getIcon1() {
        return (String) getAttributeInternal(ICON1);
    }

    /**
     * Gets the attribute value for the calculated attribute Icon2.
     * @return the Icon2
     */
    public String getIcon2() {
        return (String) getAttributeInternal(ICON2);
    }

    /**
     * Gets the attribute value for the calculated attribute Icon3.
     * @return the Icon3
     */
    public String getIcon3() {
        return (String) getAttributeInternal(ICON3);
    }

    /**
     * Gets the attribute value for the calculated attribute ShowOrder.
     * @return the ShowOrder
     */
    public BigDecimal getShowOrder() {
        return (BigDecimal) getAttributeInternal(SHOWORDER);
    }

    /**
     * Gets the attribute value for the calculated attribute Target.
     * @return the Target
     */
    public BigDecimal getTarget() {
        return (BigDecimal) getAttributeInternal(TARGET);
    }

    /**
     * Gets the attribute value for the calculated attribute Deleted.
     * @return the Deleted
     */
    public BigDecimal getDeleted() {
        return (BigDecimal) getAttributeInternal(DELETED);
    }

    /**
     * Gets the attribute value for the calculated attribute SysKey.
     * @return the SysKey
     */
    public String getSysKey() {
        return (String) getAttributeInternal(SYSKEY);
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
     * Gets the attribute value for the calculated attribute UpdateBy.
     * @return the UpdateBy
     */
    public String getUpdateBy() {
        return (String) getAttributeInternal(UPDATEBY);
    }

    /**
     * Gets the attribute value for the calculated attribute UpdateOn.
     * @return the UpdateOn
     */
    public Timestamp getUpdateOn() {
        return (Timestamp) getAttributeInternal(UPDATEON);
    }

    /**
     * Gets the attribute value for the calculated attribute Version.
     * @return the Version
     */
    public BigDecimal getVersion() {
        return (BigDecimal) getAttributeInternal(VERSION);
    }
}
