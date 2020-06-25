package com.karl.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
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
@Table(name = "T_TEST")
public class TestEntity extends BaseEntity {

    @TableField("NAME1")
    @Column(name = "NAME1", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name1;

    @TableField("NAME2")
    @Column(name = "NAME2", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name2;

    @TableField("NAME3")
    @Column(name = "NAME3", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name3;

    @TableField("NAME4")
    @Column(name = "NAME4", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name4;

    @TableField("NAME5")
    @Column(name = "NAME5", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name5;

    @TableField("NAME6")
    @Column(name = "NAME6", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name6;

    @TableField("NAME7")
    @Column(name = "NAME7", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name7;

    @TableField("NAME8")
    @Column(name = "NAME8", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name8;

    @TableField("NAME9")
    @Column(name = "NAME9", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name9;

    @TableField("NAME10")
    @Column(name = "NAME10", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name10;

    @TableField("NAME11")
    @Column(name = "NAME11", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name11;

    @TableField("NAME12")
    @Column(name = "NAME12", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name12;

    @TableField("NAME13")
    @Column(name = "NAME13", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name13;

    @TableField("NAME14")
    @Column(name = "NAME14", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name14;

    @TableField("NAME15")
    @Column(name = "NAME15", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name15;

    @TableField("NAME16")
    @Column(name = "NAME16", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name16;

    @TableField("NAME17")
    @Column(name = "NAME17", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name17;

    @TableField("NAME18")
    @Column(name = "NAME18", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name18;

    @TableField("NAME19")
    @Column(name = "NAME19", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name19;

    @TableField("NAME20")
    @Column(name = "NAME20", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name20;

    @TableField("NAME21")
    @Column(name = "NAME21", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name21;


}
