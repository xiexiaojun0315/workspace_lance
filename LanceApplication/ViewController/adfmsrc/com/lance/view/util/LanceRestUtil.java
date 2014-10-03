package com.lance.view.util;

import com.lance.model.LanceRestAMImpl;

import com.zngh.platform.front.core.view.RestUtil;

import oracle.jbo.Row;
import oracle.jbo.server.RowImpl;
import oracle.jbo.server.ViewObjectImpl;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


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

    public static void transJsonToRow(JSONObject json, Row row, String[] attrs) throws JSONException {
        for (String attr : attrs) {
            if (json.has(attr)) {
                row.setAttribute(attr, json.get(attr));
            }
        }
    }


}
