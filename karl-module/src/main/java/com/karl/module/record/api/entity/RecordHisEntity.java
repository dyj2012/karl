package com.karl.module.record.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.model.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 历史记录
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
@TableName("T_CO_RECORD_HIS")
public class RecordHisEntity extends BaseEntity {

    /**
     * 手环ID
     */
    @TableField("WRISTBAND_ID")
    private String wristbandId;

    /**
     * 记录时间
     */
    @TableField("RECORD_TIME")
    private LocalDateTime recordTime;

    /**
     * 体温
     */
    @TableField("TEMPERATURE")
    private BigDecimal temperature;
}
