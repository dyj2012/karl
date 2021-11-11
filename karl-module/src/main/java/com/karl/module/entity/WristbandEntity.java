package com.karl.module.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.actable.annotation.Column;
import com.karl.base.actable.annotation.Index;
import com.karl.base.actable.constants.MySqlTypeConstant;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * 手环表
 *
 * @author karl
 * @date 2020/5/25
 */
@Data
@TableName("T_CO_WRISTBAND")
public class WristbandEntity extends BaseEntity {

    @TableField("PEOPLE_ID")
    @Column(comment = "人员ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    @Index(columns = "PEOPLE_ID")
    private String peopleId;

    @TableField("ROUTE_ID")
    @Column(comment = "路由ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String routeId;

    @TableField("STATUS")
    @Column(comment = "状态", type = MySqlTypeConstant.VARCHAR, length = 32)
    private String status;
}
