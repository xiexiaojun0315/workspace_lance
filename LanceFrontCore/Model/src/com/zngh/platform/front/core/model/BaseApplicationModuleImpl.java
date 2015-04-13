package com.zngh.platform.front.core.model;

import oracle.adf.share.ADFContext;

import oracle.jbo.server.ApplicationModuleImpl;

public class BaseApplicationModuleImpl extends ApplicationModuleImpl {
    public BaseApplicationModuleImpl() {
        super();
    }

    public String findCurrentUserId() {
        ADFContext adfctx = ADFContext.getCurrent();
        String user = adfctx.getSecurityContext().getUserPrincipal().getName();
        return user;
    }

    public String[] findCurrentUserRoles() {
        ADFContext adfctx = ADFContext.getCurrent();
        String[] roles = adfctx.getSecurityContext().getUserRoles();
        return roles;
    }

    public boolean isUserInRole(String role) {
        ADFContext adfctx = ADFContext.getCurrent();
        boolean o = adfctx.getSecurityContext().isUserInRole(role);
        return o;
    }

}
