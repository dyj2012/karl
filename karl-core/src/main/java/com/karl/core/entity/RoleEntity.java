package com.karl.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.karl.base.model.BaseEntity;
import com.karl.base.validator.group.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 角色
 *
 * @author karl
 * @date 2020/5/25
 */
@Data
@TableName("T_SYS_ROLE")
@Table(name = "T_SYS_ROLE")
public class RoleEntity extends BaseEntity {

    @TableField("NAME")
    @Column(name = "NAME", comment = "名称", type = MySqlTypeConstant.VARCHAR, length = 64)
    @IsNotNull
    @NotBlank(message = "名字不能为空", groups = AddGroup.class)
    private String name;

    @TableField("CODE")
    @Column(name = "CODE", comment = "编码", type = MySqlTypeConstant.VARCHAR, length = 64)
    @IsNotNull
    @NotBlank(message = "编码不能为空", groups = AddGroup.class)
    private String code;

    @TableField("REMARK")
    @Column(name = "REMARK", comment = "备注", type = MySqlTypeConstant.VARCHAR)
    private String remark;
}
