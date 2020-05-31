package com.karl.module.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
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
@Table(name = "T_CO_WRISTBAND")
public class WristbandEntity extends BaseEntity {

    @TableField("PEOPLE_ID")
    @Column(name = "PEOPLE_ID", comment = "人员ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String peopleId;

    @TableField("ROUTE_ID")
    @Column(name = "ROUTE_ID", comment = "路由ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String routeId;

    @TableField("STATUS")
    @Column(name = "STATUS", comment = "状态", type = MySqlTypeConstant.VARCHAR, length = 32)
    private String status;
}
