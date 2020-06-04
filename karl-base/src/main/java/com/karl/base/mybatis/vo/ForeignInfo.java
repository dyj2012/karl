package com.karl.base.mybatis.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2020/6/4
 */
@Data
public class ForeignInfo {

    /**
     * 关联字段 默认主键
     */
    private String conditionField;

    private String foreignTableName;

    private String foreignColumn;

    private String alias;

    List<RelationInfo> relationColumnList = new ArrayList<>();
}
