package com.lance.model.vo;

import com.zngh.platform.front.core.model.BaseViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Oct 01 15:04:47 CST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LoginUserVOImpl extends BaseViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public LoginUserVOImpl() {
    }

    /**
     * Returns the variable value for pUserName.
     * @return variable value for pUserName
     */
    public String getpUserName() {
        return (String) ensureVariableManager().getVariableValue("pUserName");
    }

    /**
     * Sets <code>value</code> for variable pUserName.
     * @param value value to bind as pUserName
     */
    public void setpUserName(String value) {
        ensureVariableManager().setVariableValue("pUserName", value);
    }
}

