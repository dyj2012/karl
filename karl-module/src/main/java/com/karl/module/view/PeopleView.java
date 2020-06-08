package com.karl.module.view;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.annotation.ExcelCol;
import com.karl.base.annotation.ExcelSheet;
import com.karl.base.model.BaseView;
import lombok.Data;

/**
 * 人
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
@TableName("T_SC_PEOPLE A " +
        "left join T_SYS_ROLE B on A.ROLE_ID = B.OBJECT_ID " +
        "left join T_SC_SCHOOL C on A.SCHOOL_ID = C.OBJECT_ID")
@ExcelSheet("人员")
public class PeopleView extends BaseView {

    @TableField("A.NAME")
    @ExcelCol("名称")
    private String name;

    @TableField("A.SEX")
    @ExcelCol(value = "性别", comment = "男/女")
    private String sex;

    @TableField("A.AGE")
    @ExcelCol("年龄")
    private Integer age;

    @TableField("A.ROLE_ID")
    @ExcelCol("角色ID")
    private String roleId;

    @TableField("B.NAME")
    @ExcelCol("角色名称")
    private String roleName;

    @TableField("A.SCHOOL_ID")
    @ExcelCol("学校ID")
    private String schoolId;

    @TableField("A.NAME")
    @ExcelCol("学校ID")
    private String schoolName;

    @TableField("A.CLASSROOM_ID")
    @ExcelCol("教室ID")
    private String classroomId;

    @TableField("B.NAME")
    @ExcelCol("教室ID")
    private String classroomName;

    @TableField("A.STATUS")
    @ExcelCol(value = "状态", comment = "正常/失效")
    private String status;
}
