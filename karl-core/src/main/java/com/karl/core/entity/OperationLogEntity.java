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
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.karl.base.model.BaseEntity;
import lombok.Data;


/**
 * 系统日志
 *
 * @author karl
 */
@Data
@TableName("T_SYS_OPERATION_LOG")
@Table(name = "T_SYS_OPERATION_LOG")
public class OperationLogEntity extends BaseEntity {
    @TableField("MODULE")
    @Column(name = "MODULE", comment = "模块", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String module;

    @TableField("OPERATION")
    @Column(name = "OPERATION", comment = "操作", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String operation;

    @TableField("METHOD")
    @Column(name = "METHOD", comment = "请求方法名", type = MySqlTypeConstant.VARCHAR, length = 128)
    private String method;

    @TableField("PARAMS")
    @Column(name = "PARAMS", comment = "请求参数", type = MySqlTypeConstant.LONGTEXT)
    private String params;

    @TableField("EXECUTE_TIME")
    @Column(name = "EXECUTE_TIME", comment = "执行时间", type = MySqlTypeConstant.BIGINT, length = 20)
    private Long executeTime;

    @TableField("CLIENT_IP")
    @Column(name = "CLIENT_IP", comment = "客户端IP", type = MySqlTypeConstant.VARCHAR, length = 20)
    private String clientIp;

}
