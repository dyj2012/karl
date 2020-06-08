package com.karl.base.annotation;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2020/6/5
 */

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 查询swagger注解
 */
@ApiImplicitParams({
        @ApiImplicitParam(name = "query", value = "查询条件, 如: query=name:like:2,code:like:2", dataType = "String", paramType = "query", example = "name:like:2,code:like:2"),
        @ApiImplicitParam(name = "field", value = "查询列, 如: field=name,email", dataType = "String", paramType = "query", example = "name,email"),
        @ApiImplicitParam(name = "page", value = "查询条件, 如: page=total:true,current:2,size:2 或者 page=all", dataType = "String", paramType = "query", example = "all"),
        @ApiImplicitParam(name = "orderBy", value = "查询列, 如: orderBy=name,code:asc", dataType = "String", paramType = "query", example = "name,code:asc")
})
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SelectSwagger {

}
