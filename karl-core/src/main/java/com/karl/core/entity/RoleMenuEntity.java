package com.karl.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.karl.base.model.BaseEntity;
import com.karl.base.validator.group.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 角色
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
@TableName("T_SYS_ROLE_MENU_R")
@Table(name = "T_SYS_ROLE_MENU_R")
public class RoleMenuEntity extends BaseEntity {

    @TableField("ROLE_ID")
    @Column(name = "ROLE_ID", comment = "角色ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    @NotBlank(message = "角色不能为空", groups = AddGroup.class)
    @IsNotNull
    private String roleId;

    @TableField("MENU_ID")
    @Column(name = "MENU_ID", comment = "菜单ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    @NotBlank(message = "菜单不能为空", groups = AddGroup.class)
    @IsNotNull
    private String menuId;
}
