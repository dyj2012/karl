package com.karl.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 杜永军
 * @date 2018年12月29日 下午1:44:56
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelSheet {
    /**
     * excel sheet名称,文件名
     *
     * @return
     */
    String value();
}
