package com.karl.module.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
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
@Table(name = "T_CO_ROUTE")
public class RouteEntity extends BaseEntity {

    @TableField("NAME")
    @Column(name = "NAME", comment = "名称", type = MySqlTypeConstant.VARCHAR, length = 128)
    private String name;

    @TableField("CODE")
    @Column(name = "CODE", comment = "编码", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String code;

    @TableField("STATUS")
    @Column(name = "STATUS", comment = "状态", type = MySqlTypeConstant.VARCHAR, length = 32)
    private String status;
}
