package com.karl.module.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * 学校
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
@TableName("T_SC_SCHOOL")
@Table(name = "T_SC_SCHOOL")
public class SchoolEntity extends BaseEntity {

    @TableField("NAME")
    @Column(name = "NAME", comment = "名称", type = MySqlTypeConstant.VARCHAR, length = 128)
    private String name;

    @TableField("CODE")
    @Column(name = "CODE", comment = "编码", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String code;

    @TableField("ADDRESS")
    @Column(name = "ADDRESS", comment = "地址", type = MySqlTypeConstant.VARCHAR, length = 256)
    private String address;
}
