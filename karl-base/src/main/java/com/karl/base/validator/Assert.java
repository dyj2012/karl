/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.karl.base.validator;


import com.karl.base.exception.BaseException;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据校验
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new BaseException(101, message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new BaseException(101, message);
        }
    }
}
