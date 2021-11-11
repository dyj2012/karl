package com.karl.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel列注解
 *
 * @author karl
 * @date 2018年12月29日 下午1:44:56
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelCol {
    /**
     * excel列名称
     *
     * @return String
     */
    String value() default "";

    /**
     * 导出列的顺序,数字越小,越靠左
     *
     * @return int
     */
    int order() default 0;

    /**
     * 注释
     *
     * @return String
     */
    String cellComment() default "";

    /**
     * 必填
     *
     * @return boolean
     */
    boolean required() default false;
}