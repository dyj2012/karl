package com.karl.base.util.excel;

import com.karl.base.util.excel.vo.ExcelCell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

/**
 * @author karl
 */
public class ExcelStyleUtils {

    private final static String CLIENT_ANCHOR = "ClientAnchor";

    private ExcelStyleUtils() {
    }

    public static void setCellComment(ExcelCell cell, String val) {
        if (cell == null) {
            return;
        }
        Drawing draw = cell.getCell().getSheet().getDrawingPatriarch();
        if (draw == null) {
            draw = cell.getCell().getSheet().createDrawingPatriarch();
        }
        Comment comment = draw.createCellComment(getClientAnchor(cell));
        RichTextString textValue = cell.getCell().getSheet().getWorkbook().getCreationHelper()
                .createRichTextString(val);
        comment.setString(textValue);
        comment.setAddress(cell.getCell().getRowIndex(), cell.getCell().getColumnIndex());
        cell.getCell().setCellComment(comment);
    }


    private static ClientAnchor getClientAnchor(ExcelCell cell) {
        return (ClientAnchor) cell.getCommonMap().computeIfAbsent(CLIENT_ANCHOR, a -> {
            ClientAnchor anchor = cell.getCell().getRow().getSheet().getWorkbook().getCreationHelper()
                    .createClientAnchor();
            anchor.setDx1(0);
            anchor.setDy1(0);
            anchor.setDx2(0);
            anchor.setDy2(0);
            anchor.setCol1(4);
            anchor.setRow1(2);
            anchor.setCol2(6);
            anchor.setRow2(5);
            return anchor;
        });
    }

    /**
     * 设置单元格填充颜色
     *
     * @param cell  ExcelCell
     * @param color 颜色值
     */
    public static void setCellForegroundColor(ExcelCell cell, short color) {
        if (cell == null || cell.getCell() == null) {
            return;
        }
        Workbook wb = cell.getCell().getSheet().getWorkbook();
        if (wb == null) {
            return;
        }

        CellStyle cellStyle;
        String colorKey = "background_color_" + color;
        if (cell.getCommonMap().containsKey(colorKey)) {
            cellStyle = (CellStyle) cell.getCommonMap().get(colorKey);
        } else {
            cellStyle = wb.createCellStyle();
            cellStyle.cloneStyleFrom(cell.getCell().getCellStyle());
            cellStyle.setFillForegroundColor(color);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.getCommonMap().put(colorKey, cellStyle);
        }
        cell.getCell().setCellStyle(cellStyle);
    }

    /**
     * @Description: 设置导出格式为text
     * @Param:
     * @return:
     */
    public static void setCellStyleText(ExcelCell cell) {
        if (cell == null || cell.getCell() == null) {
            return;
        }
        String textKey = "font_text_common";
        CellStyle cellStyle = null;
        if (!cell.getCommonMap().containsKey(textKey)) {
            Workbook wb = cell.getCell().getSheet().getWorkbook();
            if (wb == null) {
                return;
            }
            cellStyle = wb.createCellStyle();
            DataFormat format = wb.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("@"));
            cell.getCommonMap().put(textKey, cellStyle);
        } else {
            cellStyle = (CellStyle) cell.getCommonMap().get(textKey);
        }
        cell.getCell().setCellStyle(cellStyle);

    }

    public static void setCellFontColor(ExcelCell cell, short color) {
        if (cell == null || cell.getCell() == null) {
            return;
        }
        Workbook wb = cell.getCell().getSheet().getWorkbook();
        if (wb == null) {
            return;
        }
        CellStyle cellStyle;
        String colorKey = "font_color_" + color;
        if (!cell.getCommonMap().containsKey(colorKey)) {
            // 设置字体
            CellStyle oldCellStyle = cell.getCell().getCellStyle();
            short oldFontIndex = oldCellStyle.getFontIndex();
            Font oldFont = wb.getFontAt(oldFontIndex);
            Font newFont = findFont(wb, oldFont.getBold(), color, oldFont.getFontHeight(), oldFont.getFontName(),
                    oldFont.getItalic(), oldFont.getStrikeout(), oldFont.getTypeOffset(), oldFont.getUnderline());
            if (oldFont.getIndex() == newFont.getIndex()) {
                return;
            }
            // 查找单元格样式
            oldCellStyle.setFont(newFont);
            cellStyle = wb.createCellStyle();
            cellStyle.cloneStyleFrom(oldCellStyle);
            // 恢复单元格字体样式
            oldCellStyle.setFont(oldFont);
            cell.getCommonMap().put(colorKey, cellStyle);
        } else {
            cellStyle = (CellStyle) cell.getCommonMap().get(colorKey);
        }
        // 设置单元格新样式
        cell.getCell().setCellStyle(cellStyle);
    }

    /**
     * 查找字体
     *
     * @param wb
     * @param bold
     * @param color
     * @param fontHeight
     * @param name
     * @param italic
     * @param strikeout
     * @param typeOffset
     * @param underline
     * @return
     */
    public static Font findFont(Workbook wb, boolean bold, short color, short fontHeight, String name, boolean italic,
                                boolean strikeout, short typeOffset, byte underline) {
        //        findFont(boolean bold, short color, short fontHeight, String name, boolean italic, boolean strikeout, short typeOffset, byte underline)
        Font f = wb.findFont(bold, color, fontHeight, name, italic, strikeout, typeOffset, underline);
        if (f == null) {
            f = wb.createFont();
            f.setBold(bold);
            f.setColor(color);
            f.setFontHeight(fontHeight);
            f.setFontName(name);
            f.setItalic(italic);
            f.setStrikeout(strikeout);
            f.setTypeOffset(typeOffset);
            f.setUnderline(underline);
        }
        return f;
    }

    private static CellStyle getForegroundStyle(Workbook workbook, short color) {
        CellStyle titleStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(color);
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

    /**
     * 添加约束
     *
     * @param excelCell
     * @date： 2019/3/12 18:31
     **/
    public static void addDataValidation(ExcelCell excelCell, List<String> datas) {
        DataValidation dataValidation = null;
        Sheet sheet = excelCell.getCell().getSheet();
        XSSFSheet curSheet = null;
        if (sheet instanceof SXSSFSheet) {
            SXSSFSheet s = (SXSSFSheet) sheet;
            SXSSFWorkbook workbook = s.getWorkbook();
            XSSFWorkbook xssfWorkbook = workbook.getXSSFWorkbook();
            curSheet = xssfWorkbook.getSheet(sheet.getSheetName());

        } else if (sheet instanceof XSSFSheet) {
            curSheet = (XSSFSheet) sheet;
        }
        if (curSheet != null) {
            XSSFDataValidationHelper helper = new XSSFDataValidationHelper(curSheet);
            XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) helper
                    .createExplicitListConstraint(datas.toArray(new String[datas.size()]));
            CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(excelCell.getCell().getRowIndex(),
                    excelCell.getCell().getRowIndex(), excelCell.getCell().getColumnIndex(),
                    excelCell.getCell().getColumnIndex());
            dataValidation = helper.createValidation(dvConstraint, cellRangeAddressList);
            excelCell.getCell().getSheet().addValidationData(dataValidation);
            dataValidation.getRegions().addCellRangeAddress(excelCell.getCell().getArrayFormulaRange());
        }
    }

    public static String getFormat(Boolean percentage, Integer decimal) {
        String format = null;
        if (percentage != null && percentage) {
            if (0 == decimal) {
                format = "0%";
            } else if (1 == decimal) {
                format = "0.0%";
            } else if (2 == decimal) {
                format = "0.00%";
            } else if (3 == decimal) {
                format = "0.000%";
            } else if (4 == decimal) {
                format = "0.0000%";
            } else if (5 == decimal) {
                format = "0.00000%";
            } else if (6 == decimal) {
                format = "0.000000%";
            }
        } else {
            if (0 == decimal) {
                format = "0";
            } else if (1 == decimal) {
                format = "0.0";
            } else if (2 == decimal) {
                format = "0.00";
            } else if (3 == decimal) {
                format = "0.000";
            } else if (4 == decimal) {
                format = "0.0000";
            } else if (5 == decimal) {
                format = "0.00000";
            } else if (6 == decimal) {
                format = "0.000000";
            }
        }
        return format;
    }
}
