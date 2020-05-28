package com.karl.base.util.excel.api.vo;

import lombok.Data;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.List;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2018/08/25
 */
@Data
public class ExcelKeyTitle {
    /**
     * column
     */
    private final String key;
    /**
     * 展示标题
     */
    private final String title;
    /**
     * 是否必填
     */
    private boolean required;
    /**
     * 水平位置,默认居左
     */
    private HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
    /**
     * 表头颜色
     */
    private short color = HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex();
    /**
     * 子表头
     */
    private List<ExcelKeyTitle> subTitleList;

    public ExcelKeyTitle(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public ExcelKeyTitle(String key, String title, boolean required) {
        this.key = key;
        this.title = title;
        this.required = required;
    }

}
