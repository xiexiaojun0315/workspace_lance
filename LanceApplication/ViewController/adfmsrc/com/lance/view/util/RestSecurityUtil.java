package com.lance.view.util;

import com.zngh.platform.front.core.model.util.ConstantUtil;

import oracle.adf.share.ADFContext;

import oracle.jbo.Row;


public class RestSecurityUtil {
    public RestSecurityUtil() {
        super();
    }


    /**
     * 适用于只有自己和超级管理员可以操作的记录
     * @param row
     * @return
     */
    public static boolean isOwner(Row row) {
        if (isSuperMode()) {
            return true;
        }

        String curUser = ADFContext.getCurrent().getSecurityContext().getUserName();
        String creator = (String) row.getAttribute("CreateBy");
        if (creator.equals(curUser)) {
            return true;
        }
        return false;
    }


    public static boolean isSuperMode() {
        if ("true".equals(ConstantUtil.initContextMap.get("debug_mode"))) {
            return true;
        }
        if (ADFContext.getCurrent().getSecurityContext().isUserInRole("SuperAdmin")) {
            return true;
        }
        return false;
    }
}
