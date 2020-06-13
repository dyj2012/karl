package com.karl.module.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
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
@Table(name = "T_SC_PEOPLE")
@ExcelSheet("人员")
public class PeopleEntity extends BaseEntity {

    @TableField("NAME")
    @Column(name = "NAME", comment = "名称", type = MySqlTypeConstant.VARCHAR, length = 128)
    @IsNotNull
    @ExcelCol("名称")
    private String name;

    @TableField("SEX")
    @Column(name = "SEX", comment = "性别", type = MySqlTypeConstant.VARCHAR, length = 10)
    @ExcelCol(value = "性别", comment = "男/女")
    private String sex;

    @TableField("AGE")
    @Column(name = "AGE", comment = "年龄", type = MySqlTypeConstant.INT)
    @ExcelCol("年龄")
    private Integer age;

    @TableField("ROLE_ID")
    @Column(name = "ROLE_ID", comment = "角色ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    @ExcelCol("角色ID")
    private String roleId;

    @TableField("SCHOOL_ID")
    @Column(name = "SCHOOL_ID", comment = "学校ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    @Index(columns = "SCHOOL_ID")
    @ExcelCol("学校ID")
    private String schoolId;

    @TableField("CLASSROOM_ID")
    @Column(name = "CLASSROOM_ID", comment = "教室ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    @Index(columns = "CLASSROOM_ID")
    @ExcelCol("教室ID")
    private String classroomId;

    @TableField("STATUS")
    @Column(name = "STATUS", comment = "状态", type = MySqlTypeConstant.VARCHAR, length = 32)
    @ExcelCol(value = "状态", comment = "正常/失效")
    private String status;
}
