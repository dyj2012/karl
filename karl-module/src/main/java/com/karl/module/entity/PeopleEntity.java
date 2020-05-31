package com.karl.module.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.karl.base.annotation.ExcelName;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * 人
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
@TableName("T_SC_PEOPLE")
@Table(name = "T_SC_PEOPLE")
@ExcelName("人员")
public class PeopleEntity extends BaseEntity {

    @TableField("NAME")
    @Column(name = "NAME", comment = "名称", type = MySqlTypeConstant.VARCHAR, length = 128)
    private String name;

    @TableField("SEX")
    private String sex;

    @TableField("ROLE_ID")
    private String roleId;

    @TableField("SCHOOL_ID")
    @Column(name = "SCHOOL_ID", comment = "学校ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    @Index
    private String schoolId;

    @TableField("CLASSROOM_ID")
    @Column(name = "CLASSROOM_ID", comment = "教室ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    @Index
    private String classroomId;

    @TableField("STATUS")
    @Column(name = "STATUS", comment = "状态", type = MySqlTypeConstant.VARCHAR, length = 32)
    private String status;
}
