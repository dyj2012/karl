package com.karl.core.user.api.entity;

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
@TableName("T_SYS_USERS")
public class UserEntity extends BaseEntity {

    /**
     * 名称
     */
    @TableField("NAME")
    private String name;
    @TableField("LOGIN_NAME")
    private String LoginName;
    @TableField("PASSWORD")
    @JsonIgnore
    private String password;
    @TableField("EMAIL")
    private String email;
    @TableField("PHONE_NUMBER")
    private String phoneNumber;

}