package com.karl.module.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.actable.annotation.Column;
import com.karl.base.actable.annotation.Index;
import com.karl.base.actable.annotation.IsNotNull;
import com.karl.base.actable.constants.MySqlTypeConstant;
import com.karl.base.annotation.ExcelCol;
import com.karl.base.annotation.ExcelSheet;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * 人
 *
 * @author karl
 * @date 2020/5/25
 */
@Data
@TableName("T_SC_PEOPLE")
@ExcelSheet("人员")
public class PeopleEntity extends BaseEntity {

    @TableField("NAME")
    @Column(comment = "名称", type = MySqlTypeConstant.VARCHAR, length = 128)
    @IsNotNull
    @ExcelCol
    private String name;

    @TableField("SEX")
    @Column(comment = "性别", type = MySqlTypeConstant.VARCHAR, length = 10)
    @ExcelCol(cellComment = "男/女")
    private String sex;

    @TableField("AGE")
    @Column(comment = "年龄", type = MySqlTypeConstant.INT)
    @ExcelCol
    private Integer age;

    @TableField("ROLE_ID")
    @Column(comment = "角色ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    @ExcelCol
    private String roleId;

    @TableField("SCHOOL_ID")
    @Column(comment = "学校ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    @Index(columns = "SCHOOL_ID")
    @ExcelCol
    private String schoolId;

    @TableField("CLASSROOM_ID")
    @Column(comment = "教室ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    @Index(columns = "CLASSROOM_ID")
    @ExcelCol
    private String classroomId;

    @TableField("STATUS")
    @Column(comment = "状态", type = MySqlTypeConstant.VARCHAR, length = 32)
    @ExcelCol(cellComment = "正常/失效")
    private String status;
}
