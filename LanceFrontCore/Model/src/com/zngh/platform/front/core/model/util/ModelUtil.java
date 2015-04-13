package com.zngh.platform.front.core.model.util;

import java.util.ArrayList;
import java.util.List;

import oracle.jbo.ViewObject;

public class ModelUtil {

    /**
     * 根据一个属性，查询多种值
     * 自动拼装sql in语句
     * @param attr
     * @param vo
     * @param values
     */
    public static void findByValues(String attr, ViewObject vo, List values) {
        if (values == null || values.size() == 0) {
            vo.setWhereClause(" 1=2 ");
            vo.executeQuery();
            return;
        }

        List list = new ArrayList();
        for (Object o : values) {
            list.add("'" + o + "'");
        }
        String str = list.toString();
        str = attr + " in (" + str.substring(1, str.length() - 1) + ")";
        vo.setWhereClause(str);
        vo.executeQuery();
    }

}
