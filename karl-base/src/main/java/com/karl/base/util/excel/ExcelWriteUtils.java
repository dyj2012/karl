package com.karl.base.util.excel;

import com.karl.base.util.excel.vo.ExcelKeyTitle;
import com.karl.base.util.excel.vo.ExcelWriteParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导出Excel工具类
 *
 * @author 杜永军
 * @date 2018/08/09
 */
public class ExcelWriteUtils {

    private ExcelWriteUtils() {
    }

    /**
     * 导出Excel
     *
     * @param page          分页回调
     * @param styleCallBack cell回调
     * @param param         导出excel参数
     * @param os            输出流
     */
    public static void pageWriteExcel(PageWriteExcel page, CellCallback styleCallBack, ExcelWriteParam param,
                                      OutputStream os) {
        Workbook workbook = pageWriteExcel(page, styleCallBack, param);
        if (workbook == null) {
            throw new RuntimeException("导出数据不能为空!");
        }
        try {
            workbook.write(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 导出Excel
     *
     * @param page         分页回调
     * @param cellCallback
     * @param param
     * @return Workbook
     */
    public static Workbook pageWriteExcel(PageWriteExcel page, CellCallback cellCallback,
                                          ExcelWriteParam param) {
        Assert.notNull(page, "分页回调不可为空");
        Assert.notNull(param, "param不可为空");
        Assert.notEmpty(param.getKeyTitleList(), "标题和key不可为空");
        Workbook workbook = ExcelWriteService.init(param);
        if (param.getCommonMap() == null) {
            param.setCommonMap(new HashMap<>(10));
        }
        if (param.getWriteKeyList() == null) {
            List<ExcelKeyTitle> keyTitleList = param.getKeyTitleList();
            param.setWriteKeyList(buildWriteKeyList(new ArrayList<>(keyTitleList.size()), keyTitleList));
        }
        List<Map<String, String>> nextPage = page.nextPage();
        while (CollectionUtils.isNotEmpty(nextPage)) {
            ExcelWriteService.appendData(workbook, param.getSheetName(), param.getWriteKeyList(), nextPage,
                    param.getCommonMap(), cellCallback);
            nextPage = page.nextPage();
        }
        return workbook;
    }

    private static List<ExcelKeyTitle> buildWriteKeyList(List<ExcelKeyTitle> writeKeyList,
                                                         List<ExcelKeyTitle> excelKeyTitleList) {
        for (ExcelKeyTitle excelKeyTitle : excelKeyTitleList) {
            if (CollectionUtils.isNotEmpty(excelKeyTitle.getSubTitleList())) {
                buildWriteKeyList(writeKeyList, excelKeyTitle.getSubTitleList());
            } else {
                writeKeyList.add(excelKeyTitle);
            }
        }
        return writeKeyList;
    }
}
