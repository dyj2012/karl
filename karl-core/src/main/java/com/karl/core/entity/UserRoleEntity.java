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

/**
 * @author karl
 */
@Data
@TableName("T_SYS_USER_ROLE_R")
public class UserRoleEntity extends BaseEntity {

    @TableField("USER_ID")
    @Column(comment = "用户ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    @NotBlank(message = "用户不能为空", groups = AddGroup.class)
    @IsNotNull
    private String userId;

    @TableField("ROLE_ID")
    @Column(comment = "角色ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    @NotBlank(message = "角色不能为空", groups = AddGroup.class)
    @IsNotNull
    private String roleId;


}