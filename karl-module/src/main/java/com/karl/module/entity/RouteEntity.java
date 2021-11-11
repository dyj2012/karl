package com.karl.module.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.actable.annotation.Column;
import com.karl.base.actable.constants.MySqlTypeConstant;
import com.karl.base.annotation.ExcelSheet;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * 手环路由发送器表
 *
 * @author karl
 * @date 2020/5/25
 */
@Data
@TableName("T_CO_ROUTE")
@ExcelSheet("路由")
public class RouteEntity extends BaseEntity {

    @TableField("NAME")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 128)
    private String name;

    @TableField("CODE")
    @Column(comment = "编码", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String code;

    @TableField("STATUS")
    @Column(comment = "状态", type = MySqlTypeConstant.VARCHAR, length = 32)
    private String status;
}
