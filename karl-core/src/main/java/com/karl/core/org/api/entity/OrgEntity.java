package com.karl.core.org.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
@TableName("T_SYS_ORG")
public class OrgEntity extends BaseEntity {

    @TableField("NAME")
    private String name;
    @TableField("CODE")
    private String code;
}
