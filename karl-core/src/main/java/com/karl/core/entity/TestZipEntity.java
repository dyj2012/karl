package com.karl.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.actable.annotation.Column;
import com.karl.base.actable.constants.MySqlTypeConstant;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * 菜单例表
 *
 * @author karl
 * @date 2020/5/25
 */
@Data
@TableName("T_TEST_ZIP")
public class TestZipEntity extends BaseEntity {

    @TableField("zip")
    @Column(type = MySqlTypeConstant.LONGBLOB)
    private byte[] zip;


}
