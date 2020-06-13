package com.karl.base.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.karl.base.annotation.ExcelCol;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID;

/**
 * Entity基类
 *
 * @author karl
 * @date 2020/5/25
 */
@Data
public class BaseEntity extends Model<BaseEntity> {

    @TableId(value = "OBJECT_ID", type = ASSIGN_UUID)
    @Column(name = "OBJECT_ID", comment = "主键ID", type = MySqlTypeConstant.VARCHAR, isKey = true, length = 64)
    @ExcelCol("主键")
    @ApiModelProperty(position = -1)
    private String objectId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    @Column(name = "CREATE_TIME", comment = "创建时间", type = MySqlTypeConstant.DATETIME)
    @ExcelCol(value = "创建时间", order = Integer.MAX_VALUE - 3)
    @ApiModelProperty(hidden = true)
    private LocalDateTime createTime;

    @TableField(value = "CREATE_BY", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    @Column(name = "CREATE_BY", comment = "创建人", type = MySqlTypeConstant.VARCHAR, length = 64)
    @ExcelCol(value = "创建人", order = Integer.MAX_VALUE - 2)
    @ApiModelProperty(hidden = true)
    private String createBy;

    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    @Column(name = "UPDATE_TIME", comment = "更新日期", type = MySqlTypeConstant.DATETIME)
    @ExcelCol(value = "更新日期", order = Integer.MAX_VALUE - 1)
    @ApiModelProperty(hidden = true)
    private LocalDateTime updateTime;

    @TableField(value = "UPDATE_BY", fill = FieldFill.INSERT_UPDATE)
    @Column(name = "UPDATE_BY", comment = "更新人", type = MySqlTypeConstant.VARCHAR, length = 64)
    @ExcelCol(value = "更新人", order = Integer.MAX_VALUE)
    @ApiModelProperty(hidden = true)
    private String updateBy;

}
