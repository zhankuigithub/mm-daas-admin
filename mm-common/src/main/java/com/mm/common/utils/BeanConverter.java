package com.mm.common.utils;

import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class BeanConverter {
    public static <T> T toBean(Object source, Class<T> tClass) {
        return source != null ? BeanUtil.copyProperties(source, tClass) : null;
    }

    public static <T> List<T> toList(List sourceList, Class<T> tClass) {
        List<T> list = null;
        if (sourceList != null) {
            list = new ArrayList();
            Iterator var3 = sourceList.iterator();

            while (var3.hasNext()) {
                Object val = var3.next();
                list.add(BeanUtil.copyProperties(val, tClass));
            }
        }

        return list;
    }

}
