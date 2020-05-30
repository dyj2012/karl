package com.karl.module.route.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * 手环路由发送器表
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
@TableName("T_CO_ROUTE")
public class RouteEntity extends BaseEntity {

    @TableField("NAME")
    private String name;

    @TableField("CODE")
    private String code;

    @TableField("STATUS")
    private String status;
}
