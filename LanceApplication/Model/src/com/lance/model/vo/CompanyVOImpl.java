package com.lance.model.vo;

import com.zngh.platform.front.core.model.BaseViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Oct 01 18:02:28 CST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CompanyVOImpl extends BaseViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public CompanyVOImpl() {
    }

    /**
     * Returns the variable value for pName.
     * @return variable value for pName
     */
    public String getpName() {
        return (String) ensureVariableManager().getVariableValue("pName");
    }

    /**
     * Sets <code>value</code> for variable pName.
     * @param value value to bind as pName
     */
    public void setpName(String value) {
        ensureVariableManager().setVariableValue("pName", value);
    }

    /**
     * Returns the variable value for pUuid.
     * @return variable value for pUuid
     */
    public String getpUuid() {
        return (String) ensureVariableManager().getVariableValue("pUuid");
    }

    /**
     * Sets <code>value</code> for variable pUuid.
     * @param value value to bind as pUuid
     */
    public void setpUuid(String value) {
        ensureVariableManager().setVariableValue("pUuid", value);
    }
}

