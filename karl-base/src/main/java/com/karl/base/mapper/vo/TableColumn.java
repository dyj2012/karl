package com.karl.base.mapper.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.ToString;

/**
 * 表列信息
 *
 * @author 杜永军
 * @date 2020/5/29
 */
@Data
@ToString
public class TableColumn {
    @TableField("COLUMN_NAME")
    private String columnName;
    @TableField("COLUMN_TYPE")
    private String columnType;
    @TableField("IS_NULLABLE")
    private String isNullable;
    @TableField("COLUMN_DEFAULT")
    private String columnDefault;
    @TableField("COLUMN_COMMENT")
    private String columnComment;
    @TableField("CHARACTER_MAXIMUM_LENGTH")
    private int length;
    @TableField("NUMERIC_PRECISION")
    private int precision;
    @TableField("NUMERIC_SCALE")
    private int scale;
    @TableField("ORDINAL_POSITION")
    private int position;
}
