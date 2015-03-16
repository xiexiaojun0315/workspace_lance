package com.lance.model.util;

public class LocationUtil {
    public LocationUtil() {
        super();
    }
    
    public static String formatLocationName(String loc) {
        System.out.println("replaceLocationName:" + loc);
        if (loc.indexOf("北京市;北京市;") >= 0) {
            loc = loc.replace("北京市;北京市;", "北京市");
        } else if (loc.indexOf("上海市;上海市;") >= 0) {
            loc = loc.replace("上海市;上海市;", "上海市");
        } else if (loc.indexOf("重庆市;重庆市;") >= 0) {
            loc = loc.replace("重庆市;重庆市;", "重庆市");
        }
        System.out.println(loc);
        return loc;
    }
    
    
}
