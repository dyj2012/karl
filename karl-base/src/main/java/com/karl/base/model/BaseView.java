package com.karl.base.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * view基类
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
public class BaseView extends Model<BaseView> {

    @TableId(value = "A.OBJECT_ID")
    private String objectId;

    @TableField(value = "A.CREATE_TIME")
    private LocalDateTime createTime;

    @TableField(value = "A.CREATE_BY")
    private String createBy;

    @TableField(value = "A.UPDATE_TIME")
    private LocalDateTime updateTime;

    @TableField(value = "A.UPDATE_BY")
    private String updateBy;

}
