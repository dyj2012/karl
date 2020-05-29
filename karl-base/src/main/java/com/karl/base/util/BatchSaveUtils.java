package com.karl.base.util;

import java.util.List;
import java.util.function.Consumer;

/**
 * 批量插入工具类
 *
 * @author 杜永军
 * @date 2018/07/26
 */
public class BatchSaveUtils {

    private BatchSaveUtils() {

    }

    /**
     * 批量分组插入
     *
     * @param l        需要插入的list
     * @param perCount 每组数量
     * @param c        回调函数
     * @param <T>      集合泛型
     */
    public static <T> void batchSave(List<T> l, int perCount, Consumer<List<T>> c) {
        if (perCount <= 0) {
            throw new IllegalArgumentException("perCount 必须大于0");
        }
        int startIndex = 0;
        while (startIndex < l.size()) {
            int endIndex = startIndex + perCount > l.size() ? l.size() : startIndex + perCount;
            c.accept(l.subList(startIndex, endIndex));
            startIndex += perCount;
        }
    }
}
