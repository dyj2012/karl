package com.karl.module.people.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class PeopleEntity extends BaseEntity {

    @TableField("NAME")
    private String name;

    @TableField("SEX")
    private String sex;

    @TableField("ROLE_ID")
    private String roleId;

    @TableField("SCHOOL_ID")
    private String schoolId;

    @TableField("CLASSROOM_ID")
    private String classroomId;
}
