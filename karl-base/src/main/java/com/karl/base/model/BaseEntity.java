package com.karl.base.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
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
    @Column(name = "OBJECT_ID", comment = "{order:0,title:主键ID}", type = MySqlTypeConstant.VARCHAR, isKey = true, length = 64)
    private String objectId;

    @TableField(value = "CREATE_TIME", updateStrategy = FieldStrategy.NEVER)
    @Column(name = "CREATE_TIME", comment = "{order:1,title:创建日期}", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime createTime;

    @TableField(value = "CREATE_BY", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    @Column(name = "CREATE_BY", comment = "{order:2,title:创建者}", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String createBy;

    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    @Column(name = "UPDATE_TIME", comment = "{order:3,title:更新日期}", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime updateTime;

    @TableField(value = "UPDATE_BY", fill = FieldFill.INSERT_UPDATE)
    @Column(name = "UPDATE_BY", comment = "{order:4,title:更新者}", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String updateBy;

}
