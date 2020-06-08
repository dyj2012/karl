package com.karl.base.util.excel;

import com.karl.base.util.excel.vo.ExcelCell;
import com.karl.base.util.excel.vo.ExcelKeyTitle;
import com.karl.base.util.excel.vo.ExcelWriteParam;
import com.karl.base.util.excel.vo.ExportParam;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * excel导出
 *
 * @author 杜永军
 */
public class ExcelWriteService extends AbstractExportService {

    private static void createHeaderAndStatement(ExcelWriteParam param, Sheet sheet) {
        ExportParam exportParam = param.getExportParam();
        List<ExcelKeyTitle> excelParams = param.getKeyTitleList();
        int rowIndex = 0, fieldLength = getFieldLength(excelParams);
        if (exportParam != null) {
            rowIndex += createStatementRow(exportParam, sheet, fieldLength);
        }
        createHeaderRow(sheet, rowIndex, param);
        setCellWith(excelParams, sheet);
        setRegionBorder(sheet);
    }

    /**
     * 创建表头
     */
    private static void createHeaderRow(Sheet sheet, int index, ExcelWriteParam param) {
        List<ExcelKeyTitle> excelParams = param.getKeyTitleList();
        int fieldDeep = getFieldDeep(excelParams);
        Row[] rows = new Row[fieldDeep];
        for (int i = 0; i < fieldDeep; i++) {
            rows[i] = createRow(sheet, index + i);
        }
        createHeaderCell(0, 0, rows, excelParams, index, param);
    }

    public static void setRegionBorder(Sheet sheet) {
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        for (CellRangeAddress cra : mergedRegions) {
            if (cra.getFirstRow() == 0) {
                continue;
            }
            RegionUtil.setBorderBottom(BorderStyle.THIN, cra, sheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN, cra, sheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, cra, sheet);
            RegionUtil.setBorderTop(BorderStyle.THIN, cra, sheet);
        }

    }

    private static void createHeaderCell(int colIndex, int level, Row[] rows, List<ExcelKeyTitle> excelParams,
                                         int rowIndex, ExcelWriteParam param) {
        Row curRow = rows[level];
        for (ExcelKeyTitle excelParam : excelParams) {
            CellStyle headerStyle = getHeaderStyle(curRow, excelParam.isRequired(), excelParam.getColor());
            if (CollectionUtils.isEmpty(excelParam.getSubTitleList())) {
                createHeaderStringCell(curRow, colIndex, headerStyle, excelParam, param);
                if (level < rows.length - 1) {
                    MergeCellUtil.addMergedRegion(curRow.getSheet(), rowIndex + level, rowIndex + rows.length - 1,
                            colIndex, colIndex);
                }
                colIndex++;
            } else {
                createHeaderStringCell(curRow, colIndex, headerStyle, excelParam, param);
                int fieldLength = getFieldLength(excelParam.getSubTitleList());
                MergeCellUtil.addMergedRegion(curRow.getSheet(), rowIndex + level, rowIndex + level, colIndex,
                        colIndex + fieldLength - 1);
                createHeaderCell(colIndex, level + 1, rows, excelParam.getSubTitleList(), rowIndex, param);
                colIndex += fieldLength;
            }
        }
    }

    private static void createHeaderStringCell(Row curRow, int colIndex,
                                               CellStyle headerStyle, ExcelKeyTitle excelParam, ExcelWriteParam param) {
        Cell cell = null;
        if (excelParam.isRequired()) {
            cell = createStringCell(curRow, colIndex, "*" + excelParam.getTitle(), headerStyle);
        } else {
            cell = createStringCell(curRow, colIndex, excelParam.getTitle(), headerStyle);
        }

        if (cell != null && StringUtils.isNotBlank(excelParam.getComment())) {
            ExcelStyleUtils.setCellComment(new ExcelCell(cell, param.getCommonMap()), excelParam.getComment());
        }
    }

    private static CellStyle getHeaderStyle(Row row, boolean required, short color) {
        CellStyle titleStyle = row.getSheet().getWorkbook().createCellStyle();
        Font font = row.getSheet().getWorkbook().createFont();
        font.setBold(true);
        if (required) {
            font.setColor(Font.COLOR_RED);
        }
        titleStyle.setFont(font);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setFillForegroundColor(color);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return titleStyle;
    }

    private static Row createRow(Sheet sheet, int index) {
        Row row = sheet.createRow(index);
        short titleHeight = 500;
        row.setHeight(titleHeight);
        return row;
    }

    /**
     * 创建 表头改变
     */
    private static int createStatementRow(ExportParam param, Sheet sheet, int fieldWidth) {
        int rowIndex = 0;
        if (StringUtils.isNotBlank(param.getStatement())) {
            Row row = sheet.createRow(rowIndex++);
            row.setHeight((short) (300 * getEnterCount(param.getStatement())));
            Cell cell = row.createCell(0);
            cell.setCellValue(param.getStatement());
            cell.setCellStyle(getStatementStyle(sheet));
            MergeCellUtil.addMergedRegion(sheet, 0, 0, 0, fieldWidth - 1);
        }

        if (MapUtils.isNotEmpty(param.getParamMap())) {
            for (Map.Entry<String, String> entry : param.getParamMap().entrySet()) {
                Row paramRow = sheet.createRow(rowIndex++);
                paramRow.createCell(0).setCellValue(entry.getKey() + ":");
                paramRow.createCell(1).setCellValue(entry.getValue());
            }
        }
        return rowIndex;
    }

    private static int getEnterCount(String str) {
        return str.split("\n").length;
    }

    private static CellStyle getStatementStyle(Sheet sheet) {
        CellStyle titleStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        titleStyle.setFont(font);
        titleStyle.setWrapText(true);
        titleStyle.setAlignment(HorizontalAlignment.LEFT);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return titleStyle;
    }

    public static Workbook init(ExcelWriteParam param) {
        Workbook workbook = param.getWorkbook();
        if (workbook == null) {
            workbook = new SXSSFWorkbook();
            param.setWorkbook(workbook);
        }
        boolean newSheet = workbook.getSheet(param.getSheetName()) == null;
        Sheet sheet = getSheetByName(param.getSheetName(), workbook);
        if (newSheet) {
            createHeaderAndStatement(param, sheet);
            String sheetName = sheet.getSheetName();
            param.setSheetName(sheetName);
        }
        return workbook;
    }

    public static void over(Workbook workbook) {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void appendData(Workbook workbook, Collection<Map<String, String>> dataSet,
                                  CellCallback cellCallback, ExcelWriteParam param) {
        Sheet sheet = getSheetByName(param.getSheetName(), workbook);
        int lastRowNum = sheet.getLastRowNum() + 1;
        for (Map<String, String> map : dataSet) {
            createCells(lastRowNum++, map, param.getWriteKeyList(), sheet, 0, param.getCommonMap(), cellCallback);
        }
    }

    private static Sheet getSheetByName(String sheetName, Workbook workbook) {
        if (StringUtils.isNotBlank(sheetName)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
            }
            return sheet;
        } else {
            return workbook.createSheet();
        }
    }

}
