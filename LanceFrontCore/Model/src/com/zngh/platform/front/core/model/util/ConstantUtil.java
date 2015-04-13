package com.zngh.platform.front.core.model.util;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("oracle.jdeveloper.java.unrestricted-field-access")
public class ConstantUtil {


    /**
     * 用于项目启动时获取web.xml中配置的常量信息
     */
    public static Map configMap = new HashMap();

    /**
     * 用于项目启动时获取当前环境对应信息
     * 功能类似部署计划
     */
    public static Map initContextMap = new HashMap();

    public static final String HOST_NAME = null;

    public static final String HOST_PORT = null;

    public static void setConfigMap(Map configMap) {
        ConstantUtil.configMap = configMap;
    }

    public static Map getConfigMap() {
        return configMap;
    }

    /**
     * 从ConfigMap中查找指定变量
     */
    public static Object findParamsConfigMap(String param) {
        Object o1 = getConfigMap().get(param);
        if (o1 == null) {
            Object o2 = getConfigMap().get(param);
            return o2;
        }
        return o1;
    }

}
