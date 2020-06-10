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
import javax.validation.constraints.NotNull;

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
    @NotBlank(message = "名字不能为空", groups = AddGroup.class)
    private String name;

    @TableField("ICON")
    @Column(name = "ICON", comment = "图标", type = MySqlTypeConstant.VARCHAR, length = 256)
    private String icon;

    @TableField("URL")
    @Column(name = "URL", comment = "路径", type = MySqlTypeConstant.VARCHAR, length = 256)
    private String url;

    @TableField("POSITION")
    @Column(name = "POSITION", comment = "位置", type = MySqlTypeConstant.BIGINT)
    private Long position;

    @TableField("TYPE")
    @Column(name = "TYPE", comment = "类型{0：目录   1：菜单   2：按钮}", type = MySqlTypeConstant.INT)
    @NotNull(message = "类型不能为空", groups = AddGroup.class)
    private Integer type;

    @TableField("PARENT_ID")
    @Column(name = "PARENT_ID", comment = "父节点ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String parentId;
}
