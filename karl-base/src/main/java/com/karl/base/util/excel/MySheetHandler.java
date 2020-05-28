package com.karl.base.util.excel;

import com.karl.base.util.excel.api.vo.CellValueTypeEnum;
import com.karl.base.util.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <description>
 *
 * @author 杜永军
 */
public class MySheetHandler extends DefaultHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MySheetHandler.class);
    private SharedStringsTable sst;
    private String lastContents = "";

    /**
     * 当前行
     */
    private int curRow = 0;
    /**
     * 当前列
     */
    private int curCol = 0;

    /**
     * 单元格类型
     */
    private CellValueTypeEnum type;

    private MapSaxRowRead read;

    private StylesTable stylesTable;

    /**
     * 存储行记录的容器
     */
    private List<String> rowlist = new ArrayList<>();

    public MySheetHandler(SharedStringsTable sst, MapSaxRowRead rowRead, StylesTable stylesTable) {
        this.sst = sst;
        this.read = rowRead;
        this.stylesTable = stylesTable;
    }

    public void reset() {
        lastContents = "";
        curRow = 0;
        curCol = 0;
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        if (LOGGER.isDebugEnabled() && name.equals("c") || name.equals("v")) {
            LOGGER.debug("startElement name " + name + " t:" + attributes.getValue("t") + " s:" + attributes.getValue("s"));
        }

        // c => 单元格
        if ("c".equals(name)) {
            lastContents = "";
            String cellCode = attributes.getValue("r");
            int colIndex = this.getColIndex(cellCode);
            // 增加poi解析时跳过的空cell
            while (colIndex > curCol) {
                endElement(null, "c", "c");
            }
            String cellType = attributes.getValue("t");
            String cellStyleStr = attributes.getValue("s");
            if ("s".equals(cellType)) {
                // 如果下一个元素是 SST 的索引
                type = CellValueTypeEnum.STRING;
            } else if ("b".equals(cellType)) {
                type = CellValueTypeEnum.BOOLEAN;
            } else if (cellStyleStr != null) {
                XSSFCellStyle style = stylesTable.getStyleAt(Integer.parseInt(cellStyleStr));
                String formatString = style.getDataFormatString();
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("startElement formatString: " + formatString);
                }
                //                Utility to identify builtin formats.
//                  Now can handle user defined data formats also.
//                      The following is a list of the formats as returned by this class.
//                0, "General"
//                1, "0"
//                2, "0.00"
//                3, "#,##0"
//                4, "#,##0.00"
//                5, "($#,##0_);($#,##0)"
//                6, "($#,##0_);[Red]($#,##0)"
//                7, "($#,##0.00);($#,##0.00)"
//                8, "($#,##0.00_);[Red]($#,##0.00)"
//                9, "0%"
//                0xa, "0.00%"
//                0xb, "0.00E+00"
//                0xc, "# ?/?"
//                0xd, "# ??/??"
//                0xe, "m/d/yy"
//                0xf, "d-mmm-yy"
//                0x10, "d-mmm"
//                0x11, "mmm-yy"
//                0x12, "h:mm AM/PM"
//                0x13, "h:mm:ss AM/PM"
//                0x14, "h:mm"
//                0x15, "h:mm:ss"
//                0x16, "m/d/yy h:mm"
////              0x17 - 0x24 reserved for international and undocumented 0x25, "(#,##0_);(#,##0)"
//                0x26, "(#,##0_);[Red](#,##0)"
//                0x27, "(#,##0.00_);(#,##0.00)"
//                0x28, "(#,##0.00_);[Red](#,##0.00)"
//                0x29, "_(*#,##0_);_(*(#,##0);_(* \"-\"_);_(@_)"
//                0x2a, "_($*#,##0_);_($*(#,##0);_($* \"-\"_);_(@_)"
//                0x2b, "_(*#,##0.00_);_(*(#,##0.00);_(*\"-\"??_);_(@_)"
//                0x2c, "_($*#,##0.00_);_($*(#,##0.00);_($*\"-\"??_);_(@_)"
//                0x2d, "mm:ss"
//                0x2e, "[h]:mm:ss"
//                0x2f, "mm:ss.0"
//                0x30, "##0.0E+0"
//                0x31, "@" - This is text format.
//                0x31 "text" - Alias for "@"
                //General 一般是空的或者数字的单元格
                if ("mm:ss.0".equals(formatString)) {
                    type = CellValueTypeEnum.DATE;
                } else if ("General".equals(formatString)
                        || "text".equals(formatString)
                        || "@".equals(formatString)
                        || formatString.contains("0")) {
                    type = CellValueTypeEnum.NONE;
                } else {
                    type = CellValueTypeEnum.DATE;
                }
            } else {
                type = CellValueTypeEnum.NONE;
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        if ("row".equals(name)) {
            read.parse(curRow, rowlist);
            rowlist.clear();
            curRow++;
            curCol = 0;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("endElement row" + curRow + "======================================");
            }
            return;
        }
        if ("c".equals(name)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("endElement content:" + lastContents.replace("\n", "") + " colIndex:" + curCol);
                LOGGER.debug("endElement colIndex:" + curCol);
            }
            rowlist.add(curCol, lastContents);
            curCol++;
            return;
        }
        if ("v".equals(name)) {
            if (CellValueTypeEnum.STRING == type) {
                try {
                    int idx = Integer.parseInt(lastContents);
                    lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                } catch (Exception e) {
                }
            } else if (CellValueTypeEnum.DATE == type) {
                Date date = HSSFDateUtil.getJavaDate(Double.parseDouble(lastContents));
                lastContents = DateUtils.dateToString(date);
            } else if (CellValueTypeEnum.BOOLEAN == type) {
                if (lastContents.trim().equals("1")) {
                    lastContents = "TRUE";
                } else {
                    lastContents = "FALSE";
                }
            } else {
                lastContents = lastContents.trim();
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //得到单元格内容的值
        lastContents += new String(ch, start, length);
    }

    private int getColIndex(String cellCode) {
        int firstDigit = -1;
        for (int c = 0; c < cellCode.length(); ++c) {
            if (Character.isDigit(cellCode.charAt(c))) {
                firstDigit = c;
                break;
            }
        }
        return nameToColumn(cellCode.substring(0, firstDigit));
    }

    /**
     * Converts an Excel column name like "C" to a zero-based index.
     *
     * @param name
     */
    private int nameToColumn(String name) {
        int column = -1;
        for (int i = 0; i < name.length(); ++i) {
            int c = name.charAt(i);
            column = (column + 1) * 26 + c - 'A';
        }
        return column;
    }
}
