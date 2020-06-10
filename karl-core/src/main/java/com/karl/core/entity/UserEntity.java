package com.karl.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.karl.base.annotation.ExcelSheet;
import com.karl.base.model.BaseEntity;
import com.karl.base.validator.group.AddGroup;
import com.karl.base.validator.group.UpdateGroup;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Think
 */
@Data
@Accessors(chain = true)
@TableName("T_SYS_USER")
@Table(name = "T_SYS_USER")
@ExcelSheet("系统用户")
public class UserEntity extends BaseEntity {

    @TableField("NAME")
    @Column(name = "NAME", comment = "名字", type = MySqlTypeConstant.VARCHAR, length = 64)
    @IsNotNull
    @NotBlank(message = "名字不能为空", groups = AddGroup.class)
    private String name;

    @TableField("LOGIN_NAME")
    @Column(name = "LOGIN_NAME", comment = "登录名", type = MySqlTypeConstant.VARCHAR, length = 64)
    @Unique(columns = "LOGIN_NAME")
    @IsNotNull
    @NotBlank(message = "登录名不能为空", groups = AddGroup.class)
    private String loginName;

    @TableField(value = "PASSWORD")
    @JsonIgnore
    @Column(name = "PASSWORD", comment = "密码", type = MySqlTypeConstant.VARCHAR, length = 128)
    @NotBlank(message = "密码不能为空", groups = AddGroup.class)
    private String password;

    @TableField("EMAIL")
    @Column(name = "EMAIL", comment = "邮箱", type = MySqlTypeConstant.VARCHAR, length = 64)
    @NotBlank(message = "邮箱不能为空", groups = AddGroup.class)
    @Email(message = "邮箱格式不正确", groups = AddGroup.class)
    private String email;

    @TableField("PHONE_NUMBER")
    @Column(name = "PHONE_NUMBER", comment = "电话", type = MySqlTypeConstant.VARCHAR, length = 64)
    @NotBlank(message = "电话号码不能为空", groups = AddGroup.class)
    private String phoneNumber;

}