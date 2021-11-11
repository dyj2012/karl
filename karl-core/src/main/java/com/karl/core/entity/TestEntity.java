package com.karl.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.actable.annotation.Column;
import com.karl.base.actable.constants.MySqlTypeConstant;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * 菜单例表
 *
 * @author karl
 * @date 2020/5/25
 */
@Data
@TableName("T_TEST")
public class TestEntity extends BaseEntity {

    @TableField("NAME1")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name1;

    @TableField("NAME2")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name2;

    @TableField("NAME3")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name3;

    @TableField("NAME4")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name4;

    @TableField("NAME5")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name5;

    @TableField("NAME6")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name6;

    @TableField("NAME7")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name7;

    @TableField("NAME8")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name8;

    @TableField("NAME9")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name9;

    @TableField("NAME10")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name10;

    @TableField("NAME11")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name11;

    @TableField("NAME12")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name12;

    @TableField("NAME13")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name13;

    @TableField("NAME14")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name14;

    @TableField("NAME15")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name15;

    @TableField("NAME16")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name16;

    @TableField("NAME17")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name17;

    @TableField("NAME18")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name18;

    @TableField("NAME19")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name19;

    @TableField("NAME20")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name20;

    @TableField("NAME21")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name21;


}
