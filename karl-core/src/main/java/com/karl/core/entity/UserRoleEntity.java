package com.karl.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.karl.base.annotation.ExcelSheet;
import com.karl.base.model.BaseEntity;
import com.karl.base.validator.group.AddGroup;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author karl
 */
@Data
@Accessors(chain = true)
@TableName("T_SYS_USER_ROLE_R")
@Table(name = "T_SYS_USER_ROLE_R")
@ExcelSheet("系统用户")
public class UserRoleEntity extends BaseEntity {

    @TableField("USER_ID")
    @Column(name = "USER_ID", comment = "用户ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    @NotBlank(message = "用户不能为空", groups = AddGroup.class)
    @IsNotNull
    private String userId;

    @TableField("ROLE_ID")
    @Column(name = "ROLE_ID", comment = "角色ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    @NotBlank(message = "角色不能为空", groups = AddGroup.class)
    @IsNotNull
    private String roleId;


}