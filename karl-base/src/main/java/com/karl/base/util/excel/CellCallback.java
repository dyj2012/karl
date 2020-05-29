package com.karl.base.util.excel;


import com.karl.base.util.excel.vo.ExcelCell;
import com.karl.base.util.excel.vo.ExcelKeyTitle;

import java.util.Map;

/**
 * 单元格回调
 *
 * @author 杜永军
 * @date 2018/12/19
 */
public interface CellCallback {
    /**
     * 单元回调方法
     *
     * @param cell    单元格
     * @param text    文本内容
     * @param rowData 行输数据
     * @param entity  表头信息
     */
    void callBack(ExcelCell cell, String text, Map<String, String> rowData, ExcelKeyTitle entity);
}
