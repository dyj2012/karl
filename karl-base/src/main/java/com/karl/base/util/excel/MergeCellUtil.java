package com.karl.base.util.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2019/01/14
 */
public class MergeCellUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MergeCellUtil.class);

    private MergeCellUtil() {
    }

    /**
     * 获取一个单元格的值,确保这个单元格必须有值,不然向上查询
     *
     * @param cell
     * @param index
     * @param rowNum
     * @return
     */
    private static String getCellNotNullText(Cell cell, int index, int rowNum) {
        if (cell == null || cell.getRow() == null) {
            return null;
        }
        if (cell.getRow().getCell(index) != null
                && StringUtils.isNotEmpty(cell.getRow().getCell(index).getStringCellValue())) {
            return cell.getRow().getCell(index).getStringCellValue();
        }
        return getCellNotNullText(cell.getRow().getSheet().getRow(--rowNum).getCell(index), index, rowNum);
    }

    public static void addMergedRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        try {
            sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
        } catch (Exception e) {
            LOGGER.error("发生了一次合并单元格错误,{},{},{},{}", new Integer[]{firstRow, lastRow, firstCol, lastCol});
            // 忽略掉合并的错误,不打印异常
            LOGGER.debug(e.getMessage(), e);
        }
    }

}
