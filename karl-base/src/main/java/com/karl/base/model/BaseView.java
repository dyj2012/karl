package com.karl.base.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.karl.base.annotation.ExcelCol;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * view基类
 *
 * @author karl
 * @date 2020/5/25
 */
@Data
public class BaseView extends Model<BaseView> {

    @TableId(value = "A.OBJECT_ID")
    @ExcelCol("主键")
    private String objectId;

    @TableField(value = "A.CREATE_TIME")
    @ExcelCol(value = "创建时间", order = Integer.MAX_VALUE - 3)
    private LocalDateTime createTime;

    @TableField(value = "A.CREATE_BY")
    @ExcelCol(value = "创建人", order = Integer.MAX_VALUE - 2)
    private String createBy;

    @TableField(value = "A.UPDATE_TIME")
    @ExcelCol(value = "更新日期", order = Integer.MAX_VALUE - 1)
    private LocalDateTime updateTime;

    @TableField(value = "A.UPDATE_BY")
    @ExcelCol(value = "更新人", order = Integer.MAX_VALUE)
    private String updateBy;

}
