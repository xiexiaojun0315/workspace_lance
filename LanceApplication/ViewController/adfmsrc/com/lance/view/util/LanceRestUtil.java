package com.lance.view.util;

import com.lance.model.LanceRestAMImpl;

import com.zngh.platform.front.core.view.RestUtil;

import oracle.jbo.server.RowImpl;
import oracle.jbo.server.ViewObjectImpl;


public class LanceRestUtil {
    public LanceRestUtil() {
        super();
    }

    public static LanceRestAMImpl findLanceAM() {
        try {
            LanceRestAMImpl am = (LanceRestAMImpl) RestUtil.findAmFromBinding("LanceRestAMDataControl");
            return am;
        } catch (Exception e) {
            System.err.println("获取AM失败，请关闭浏览器重试");
            e.printStackTrace();
        }
        return null;
    }

    public static RowImpl createInsertRow(ViewObjectImpl vo) {
        RowImpl row = (RowImpl) vo.createRow();
        vo.insertRow(row);
        return row;
    }

}
