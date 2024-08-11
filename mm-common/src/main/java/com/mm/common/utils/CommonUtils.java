package com.mm.common.utils;

import cn.hutool.core.text.CharSequenceUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * druid解析工具
 * </p>
 *
 * @author
 * @date 2023-06-05
 */
public class CommonUtils {

    /**
     * <p> 将key转成驼峰命名 </p>
     *
     * @param list java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     **/
    public static List<Map<String, Object>> transformationHump(List<Map<String, Object>> list) {
        List<Map<String, Object>> newList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            Map<String, Object> newMap = new LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                newMap.put(CharSequenceUtil.toCamelCase(entry.getKey()), entry.getValue());
            }
            newList.add(newMap);
        }
        return newList;
    }

    /**
     * <p> 将key转成驼峰命名 </p>
     *
     * @param map java.util.Map<java.lang.String,java.lang.Object>
     * @return java.util.Map<java.lang.String, java.lang.Object>
     **/
    public static Map<String, Object> transformationHump(Map<String, Object> map) {
        Map<String, Object> newMap = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            newMap.put(CharSequenceUtil.toCamelCase(entry.getKey()), entry.getValue());
        }
        return newMap;
    }

}
