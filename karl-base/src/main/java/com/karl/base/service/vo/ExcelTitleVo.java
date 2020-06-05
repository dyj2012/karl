package com.karl.base.service.vo;

import lombok.Data;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2020/6/5
 */
@Data
public class ExcelTitleVo implements Comparable<ExcelTitleVo> {

    private String key;
    private String title;
    private int order;

    @Override
    public int compareTo(ExcelTitleVo o) {
        return Integer.compare(order, o.order);
    }
}
