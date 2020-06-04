package com.karl.base.util.excel;

import com.karl.base.util.excel.vo.ExcelCell;
import com.karl.base.util.excel.vo.ExcelKeyTitle;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 提供POI基础操作服务
 *
 * @author 杜永军
 */
public abstract class AbstractExportService {
    /**
     * 创建 最主要的 Cells
     */
    public static void createCells(int index, Map<String, String> objectMap, List<ExcelKeyTitle> excelParams,
                                   Sheet sheet, int cellNum, Map<String, Object> commonMap, CellCallback cellCallback) {
        Row row = sheet.getRow(index) == null ? sheet.createRow(index) : sheet.getRow(index);
        for (int i = 0; i < excelParams.size(); i++) {
            ExcelKeyTitle excelKeyTitle = excelParams.get(i);
            String text = "";
            if (null != objectMap.get(excelKeyTitle.getKey())) {
                text = String.valueOf(objectMap.get(excelKeyTitle.getKey()));
            }
            Cell cell = createStringCell(row, cellNum++, text, null);
            if (cellCallback != null) {
                cellCallback.callBack(new ExcelCell(cell, commonMap), text, objectMap, excelKeyTitle);
            }
        }
    }

    protected static Cell createStringCell(Row row, int index, String value, CellStyle style) {
        Cell cell;
        if (value != null) {
            cell = row.createCell(index);
            try {
                cell.setCellValue(value);
            } catch (IllegalArgumentException e) {
                forceSetStringValue(cell, value);
            }

        } else {
            cell = row.createCell(index);
        }
        if (style != null) {
            cell.setCellStyle(style);
        }
        return cell;
    }

    /**
     * 强制设置字符串值
     *
     * @param cell
     * @param str
     */
    public static void forceSetStringValue(Cell cell, String str) {
        try {
            Field valueField = cell.getClass().getDeclaredField("_value");
            valueField.setAccessible(true);
            Object value = valueField.get(cell);
            Field valueValueField = value.getClass().getDeclaredField("_value");
            valueValueField.setAccessible(true);
            valueValueField.set(value, str);
        } catch (Exception e) {
            throw new RuntimeException("字段超长,强制设置字符串值失败!", e);
        }
    }

    public static void setCellWith(List<ExcelKeyTitle> excelParams, Sheet sheet) {
        int index = 0;
        for (int i = 0; i < excelParams.size(); i++) {
            ExcelKeyTitle keyTitle = excelParams.get(i);
            if (keyTitle.getSubTitleList() != null) {
                List<ExcelKeyTitle> list = excelParams.get(i).getSubTitleList();
                for (int j = 0; j < list.size(); j++) {
                    sheet.setColumnWidth(index, (256 * (keyTitle.getTitle().getBytes().length + 10)));
                    index++;
                }
            } else {
                sheet.setColumnWidth(index, (256 * (keyTitle.getTitle().getBytes().length + 10)));
                index++;
            }
        }
    }

    /**
     * 获取导出报表的字段总长度
     */
    public static int getFieldLength(List<ExcelKeyTitle> excelParams) {
        // 从0开始计算单元格的
        int length = 0;
        for (ExcelKeyTitle entity : excelParams) {
            if (entity.getSubTitleList() != null) {
                length += getFieldLength(entity.getSubTitleList());
            } else {
                length++;
            }
        }
        return length;
    }

    public static int getFieldDeep(List<ExcelKeyTitle> excelParams) {
        // 从0开始计算单元格的
        int deep = 0;
        List<ExcelKeyTitle> deepList = new ArrayList<>(excelParams);
        while (CollectionUtils.isNotEmpty(deepList)) {
            deep++;
            for (int i = deepList.size() - 1; i >= 0; i--) {
                ExcelKeyTitle excelKeyTitle = deepList.remove(i);
                if (CollectionUtils.isNotEmpty(excelKeyTitle.getSubTitleList())) {
                    deepList.addAll(excelKeyTitle.getSubTitleList());
                }
            }
        }
        return deep;
    }
}
