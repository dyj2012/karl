package com.karl.module.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.karl.base.model.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 记录
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
@TableName("T_CO_RECORD")
@Table(name = "T_CO_RECORD")
public class RecordEntity extends BaseEntity {

    @TableField("WRISTBAND_ID")
    @Column(name = "WRISTBAND_ID", comment = "手环ID", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String wristbandId;

    /**
     * 记录时间
     */
    @TableField("RECORD_TIME")
    @Column(name = "RECORD_TIME", comment = "记录时间", type = MySqlTypeConstant.DATETIME)
    private LocalDateTime recordTime;

    /**
     * 体温
     */
    @TableField("TEMPERATURE")
    @Column(name = "TEMPERATURE", comment = "体温", type = MySqlTypeConstant.DECIMAL, length = 18, decimalLength = 2)
    private BigDecimal temperature;
}
