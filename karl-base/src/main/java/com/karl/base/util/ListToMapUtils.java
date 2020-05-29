package com.karl.base.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合拆分工具类
 *
 * @author 杜永军
 * @date 2017年8月18日 下午5:03:44
 */
public class ListToMapUtils {

    public static <T, R> Map<R, List<T>> group(List<T> all, Function<T, R> splitKey) {
        if (CollectionUtils.isEmpty(all)) {
            return new HashMap<>(0);
        }
        return all.stream().collect(Collectors.groupingBy(splitKey));
    }

    public static <T, R> Map<R, T> toMap(List<T> all, Function<T, R> splitKey) {
        if (CollectionUtils.isEmpty(all)) {
            return new HashMap<>(0);
        }
        return all.stream().collect(Collectors.toMap(splitKey, a -> a, (k1, k2) -> k1));
    }

}
