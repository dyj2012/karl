package com.karl.module.view;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.annotation.ExcelCol;
import com.karl.base.model.BaseView;
import lombok.Data;

/**
 * 手环视图
 *
 * @author 杜永军
 * @date 2020/6/5
 */
@Data
@TableName(value = "T_CO_WRISTBAND A LEFT JOIN T_SC_PEOPLE B ON A.PEOPLE_ID = B.OBJECT_ID")
public class WristbandView extends BaseView {

    @TableField("A.PEOPLE_ID")
    @ExcelCol(value = "人员ID", order = 1)
    private String peopleId;

    @TableField("B.NAME")
    @ExcelCol(value = "人员名称", order = 2)
    private String peopleName;

    @TableField("A.ROUTE_ID")
    @ExcelCol(value = "路由ID", order = 3)
    private String routeId;

    @TableField("A.STATUS")
    @ExcelCol(value = "状态", order = 10)
    private String status;
}
