package com.karl.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * 角色
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
@TableName("T_SYS_ROLE")
@Table(name = "T_SYS_ROLE")
public class RoleEntity extends BaseEntity {

    @TableField("NAME")
    @Column(name = "NAME", comment = "名称", type = MySqlTypeConstant.VARCHAR, length = 64)
    @IsNotNull
    private String name;

    @TableField("CODE")
    @Column(name = "CODE", comment = "编码", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String code;
}
