package com.zngh.platform.front.core.model.util;

import java.util.HashMap;
import java.util.Map;


public class MessageTransfer {

    public static final String MESSAGE_TYPE_INFO = "INFO";
    public static final String MESSAGE_TYPE_ERROR = "ERROR";

    private MessageTransfer() {
    }

    public static Map createInfo(Object msg) {
        Map map = new HashMap();
        map.put(MESSAGE_TYPE_INFO, msg);
        return map;
    }

    public static Map createError(Object msg) {
        Map map = new HashMap();
        map.put(MESSAGE_TYPE_ERROR, msg);
        return map;
    }

    public static String getErrorMessage(Object res) {
        if (res == null) {
            return null;
        }
        
        if (!res.getClass().getName().equals("java.util.HashMap")) {
            throw new RuntimeException("getErrorMessage传入的Object应该是由MessageTransfer生成的HashMap！");
        }

        Map map = (Map)res;
        if (map.containsKey(MESSAGE_TYPE_ERROR)) {
            return "" + map.get(MESSAGE_TYPE_ERROR);
        } else {
            return null;
        }
    }

}
