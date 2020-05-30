package com.karl.core.user.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.karl.base.model.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Think
 */
@Data
@Accessors(chain = true)
@TableName("T_SYS_USER")
public class UserEntity extends BaseEntity {

    @TableField("NAME")
    private String name;

    @TableField("LOGIN_NAME")
    private String loginName;

    @TableField(value = "PASSWORD", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private String password;

    @TableField("EMAIL")
    private String email;

    @TableField("PHONE_NUMBER")
    private String phoneNumber;

}