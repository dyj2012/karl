package com.karl.module.wristband.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * 手环表
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
@TableName("T_CO_WRISTBAND")
public class WristbandEntity extends BaseEntity {

    @TableField("PEOPLE_ID")
    private String peopleId;

    @TableField("ROUTE_ID")
    private String routeId;

    @TableField("STATUS")
    private String status;
}
