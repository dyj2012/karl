package com.karl.module.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.actable.annotation.Column;
import com.karl.base.actable.annotation.Index;
import com.karl.base.actable.constants.MySqlTypeConstant;
import com.karl.base.model.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 记录
 *
 * @author karl
 * @date 2020/5/25
 */
@Data
@TableName("T_CO_RECORD")
public class RecordEntity extends BaseEntity {

    @TableField("WRISTBAND_ID")
    @Column(comment = "手环ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    @Index(columns = "WRISTBAND_ID")
    private String wristbandId;

    /**
     * 记录时间
     */
    @TableField("RECORD_TIME")
    @Column(comment = "记录时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime recordTime;

    /**
     * 体温
     */
    @TableField("TEMPERATURE")
    @Column(type = MySqlTypeConstant.DECIMAL, length = 10)
    private BigDecimal temperature;
}
