package com.zngh.platform.front.core.model.util;

public class ExcelUtil {

    /**
     * 强制转换为Excel中的字符串
     *
     * @param input
     * @return
     */
    public static String convertToTextV1(String input) {
        if (input == null || "".equals(input.trim())) {
            return input;
        }

        boolean r = isNumber(input);
        if (!r) {
            return input;
        }

        //判断是否0开头的字符串
        if (isZeroBeginning(input)) {
            String format = "0";
            for (int i = 1; i < input.length(); i++) {
                format += "0";
            }
            input = "=TEXT(\"" + input + "\",\"" + format + "\")";
        } else {
            input = "=TEXT(\"" + input + "\",\"#\")";
        }

        return input;
    }

    /**
     * 判断传入的字符串是否为纯数字组成
     * @param input
     * @return
     */
    public static boolean isNumber(String input) {
        return input.matches("[0-9]+");
    }

    /**
     * 字符是否以0开头
     *
     * @param input
     * @return
     */
    public static boolean isZeroBeginning(String input) {
        return "0".equals(input.substring(0, 1));
    }


}
