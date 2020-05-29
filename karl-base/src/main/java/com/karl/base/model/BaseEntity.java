package com.karl.base.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID;

/**
 * Entity基类
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
public class BaseEntity extends Model {

    @TableId(value = "OBJECT_ID", type = ASSIGN_UUID)
    private String objectId;
    @TableField(value = "CREATE_TIME", updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime createTime;
    @TableField(value = "CREATE_BY", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private String createBy;
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    @Version
    @TableField("DATA_VERSION")
    private Long dataVersion;
}
