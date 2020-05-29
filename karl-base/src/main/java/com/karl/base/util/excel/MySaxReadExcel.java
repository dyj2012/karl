package com.karl.base.util.excel;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;

/**
 * @author 杜永军
 * @date 2019/01/07
 */
public class MySaxReadExcel {

    public void readExcel(InputStream inputstream, MapSaxRowRead rowRead, PageReadExcel callBack,
                          PageReadExcelHandle pageReadExcelHandle) {
        try {
            OPCPackage opcPackage = OPCPackage.open(inputstream);
            readExcel(opcPackage, rowRead, callBack, pageReadExcelHandle);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void readExcel(OPCPackage opcPackage, MapSaxRowRead rowRead, PageReadExcel callBack,
                           PageReadExcelHandle pageReadExcelHandle) {
        try {
            XSSFReader xssfReader = new XSSFReader(opcPackage);
            SharedStringsTable sst = xssfReader.getSharedStringsTable();
            XMLReader parser = fetchSheetParser(sst, rowRead, xssfReader.getStylesTable());
            XSSFReader.SheetIterator sheets = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
            int sheetIndex = 0;
            while (sheets.hasNext()) {
                rowRead.reset();
                InputStream sheet = sheets.next();
                InputSource sheetSource = new InputSource(sheet);
                String sheetName = sheets.getSheetName();
                if (callBack.nextSheet(sheetIndex, sheetName)) {
                    parser.parse(sheetSource);
                    pageReadExcelHandle.doCallBack();
                }
                sheet.close();
                sheetIndex++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private XMLReader fetchSheetParser(SharedStringsTable sst, MapSaxRowRead rowRead, StylesTable stylesTable)
            throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        MySheetHandler handler = new MySheetHandler(sst, rowRead, stylesTable);
        rowRead.setSheetHandler(handler);
        parser.setContentHandler(handler);
        return parser;
    }

}
