package com.karl.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.karl.base.annotation.ExcelName;
import com.karl.base.model.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Think
 */
@Data
@Accessors(chain = true)
@TableName("T_SYS_USER")
@Table(name = "T_SYS_USER")
@ExcelName("系统用户")
public class UserEntity extends BaseEntity {

    @TableField("NAME")
    @Column(name = "NAME", comment = "名字", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name;

    @TableField("LOGIN_NAME")
    @Column(name = "LOGIN_NAME", comment = "登录名", type = MySqlTypeConstant.VARCHAR, length = 64)
    @Unique(columns = "LOGIN_NAME")
    private String loginName;

    @TableField(value = "PASSWORD")
    @JsonIgnore
    @Column(name = "PASSWORD", comment = "密码", type = MySqlTypeConstant.VARCHAR, length = 128)
    private String password;

    @TableField("EMAIL")
    @Column(name = "EMAIL", comment = "邮箱", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String email;

    @TableField("PHONE_NUMBER")
    @Column(name = "PHONE_NUMBER", comment = "电话", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String phoneNumber;

}