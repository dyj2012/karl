package com.karl.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.actable.annotation.Column;
import com.karl.base.actable.annotation.IsNotNull;
import com.karl.base.actable.constants.MySqlTypeConstant;
import com.karl.base.model.BaseEntity;
import com.karl.base.validator.group.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 菜单例表
 *
 * @author karl
 * @date 2020/5/25
 */
@Data
@TableName("T_SYS_MENU")
public class MenuEntity extends BaseEntity {

    @TableField("NAME")
    @Column(comment = "名称", type = MySqlTypeConstant.VARCHAR, length = 64)
    @IsNotNull
    @NotBlank(message = "名字不能为空", groups = AddGroup.class)
    private String name;

    @TableField("ICON")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 256)
    private String icon;

    @TableField("URL")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 256)
    private String url;

    @TableField("POSITION")
    @Column(type = MySqlTypeConstant.BIGINT)
    private Long position;

    @TableField("TYPE")
    @Column(type = MySqlTypeConstant.INT)
    @NotNull(message = "类型不能为空", groups = AddGroup.class)
    private Integer type;

    @TableField("PARENT_ID")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String parentId;
}
