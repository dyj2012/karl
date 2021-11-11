package com.karl.base.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.karl.base.actable.annotation.Column;
import com.karl.base.actable.constants.MySqlTypeConstant;
import com.karl.base.annotation.ExcelCol;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

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
    @Column(comment = "主键", type = MySqlTypeConstant.VARCHAR, isKey = true, length = 64)
    @ExcelCol
    @ApiModelProperty(position = -1)
    private String objectId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    @Column(comment = "创建时间", type = MySqlTypeConstant.DATETIME)
    @ExcelCol(order = Integer.MAX_VALUE - 3)
    @ApiModelProperty(hidden = true)
    private LocalDateTime createTime;

    @TableField(value = "CREATE_BY", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    @Column(comment = "创建人", type = MySqlTypeConstant.VARCHAR, length = 64)
    @ExcelCol(order = Integer.MAX_VALUE - 2)
    @ApiModelProperty(hidden = true)
    private String createBy;

    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    @Column(comment = "更新日期", type = MySqlTypeConstant.DATETIME)
    @ExcelCol(order = Integer.MAX_VALUE - 1)
    @ApiModelProperty(hidden = true)
    private LocalDateTime updateTime;

    @TableField(value = "UPDATE_BY", fill = FieldFill.INSERT_UPDATE)
    @Column(comment = "更新人", type = MySqlTypeConstant.VARCHAR, length = 64)
    @ExcelCol(order = Integer.MAX_VALUE)
    @ApiModelProperty(hidden = true)
    private String updateBy;

}
