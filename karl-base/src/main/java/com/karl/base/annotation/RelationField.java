package com.karl.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 关联字段
 *
 * @author duyj
 * @date 2016年8月10日 下午5:32:22
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RelationField {
    /**
     * 关联字段
     * 关联表的哪个字段
     *
     * @return String
     */
    String refField();

    /**
     * 外键字段
     * <p>
     * 依赖本表的哪个字段做外键关联
     *
     * @return String
     */
    String foreignField();
}
