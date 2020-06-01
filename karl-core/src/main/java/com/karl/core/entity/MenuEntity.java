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
 * 菜单例表
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
@TableName("T_SYS_MENU")
@Table(name = "T_SYS_MENU")
public class MenuEntity extends BaseEntity {

    @TableField("NAME")
    @Column(name = "NAME", comment = "名称", type = MySqlTypeConstant.VARCHAR, length = 64)
    @IsNotNull
    private String name;

    @TableField("URL")
    @Column(name = "URL", comment = "路径", type = MySqlTypeConstant.VARCHAR, length = 256)
    private String url;

    @TableField("POSITION")
    @Column(name = "POSITION", comment = "位置", type = MySqlTypeConstant.BIGINT)
    private Long position;
}
