package com.karl.core.menu.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * 菜单例表
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
@TableName("T_SYS_MENU")
public class MenuEntity extends BaseEntity {

    @TableField("NAME")
    private String name;

    @TableField("URL")
    private String url;

    @TableField("POSITION")
    private Long position;
}
