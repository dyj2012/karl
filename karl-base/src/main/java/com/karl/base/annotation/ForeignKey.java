package com.karl.base.annotation;

import com.karl.base.model.BaseEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 外键注解
 *
 * @author duyj
 * @date 2016年12月29日 下午4:53:27
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ForeignKey {

    /**
     * 关联其他表的表名
     *
     * @return String
     */
    Class<? extends BaseEntity> value();

    /**
     * 默认主键关联
     *
     * @return String
     */
    String conditionField() default "OBJECT_ID";


}
