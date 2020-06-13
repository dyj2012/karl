package com.karl.base.util;

/**
 * <description>
 *
 * @author karl
 * @date 2020/5/29
 */
public class CamelUtils {

    /**
     * 驼峰转下划线，abcAbcBc->abc_Abc_Bc
     *
     * @param str
     * @return
     */
    public static String toUnderline(String str) {
        return str.replaceAll("[A-Z]", "_$0");
    }

    /**
     * 下划线转驼峰，abc_abca_bc->abcAbcaBc
     *
     * @param name
     * @return
     */
    public static String toCamel(String name) {
        StringBuilder result = new StringBuilder();
        if ((name == null) || (name.isEmpty())) {
            return "";
        }
        if (!name.contains("_")) {
            return name.toLowerCase();
        }
        String[] camels = name.split("_");
        for (String camel : camels) {
            if (!camel.isEmpty()) {
                if (result.length() == 0) {
                    result.append(camel.toLowerCase());
                } else {
                    result.append(camel.substring(0, 1).toUpperCase());
                    result.append(camel.substring(1).toLowerCase());
                }
            }
        }
        return result.toString();
    }
}
