package com.karl.module.view;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.model.BaseView;
import lombok.Data;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2020/6/5
 */
@TableName(value = "T_CO_WRISTBAND A LEFT JOIN T_SC_PEOPLE B ON A.PEOPLE_ID = B.OBJECT_ID")
@Data
public class WristbandView extends BaseView {

    @TableField("A.PEOPLE_ID")
    private String peopleId;

    @TableField("B.NAME")
    private String peopleName;

    @TableField("A.ROUTE_ID")
    private String routeId;

    @TableField("A.STATUS")
    private String status;
}
