/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.karl.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.actable.annotation.Column;
import com.karl.base.actable.constants.MySqlTypeConstant;
import com.karl.base.model.BaseEntity;
import lombok.Data;


/**
 * 系统日志
 *
 * @author karl
 */
@Data
@TableName("T_SYS_OPERATION_LOG")
public class OperationLogEntity extends BaseEntity {
    @TableField("MODULE")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String module;

    @TableField("OPERATION")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String operation;

    @TableField("METHOD")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 128)
    private String method;

    @TableField("PARAMS")
    @Column(type = MySqlTypeConstant.LONGTEXT)
    private String params;

    @TableField("EXECUTE_TIME")
    @Column(type = MySqlTypeConstant.BIGINT, length = 20)
    private Long executeTime;

    @TableField("CLIENT_IP")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 20)
    private String clientIp;

}
