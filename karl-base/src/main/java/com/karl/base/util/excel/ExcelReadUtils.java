package com.karl.base.util.excel;

import org.springframework.util.Assert;

import java.io.InputStream;

/**
 * 读取Excel工具类
 *
 * @author karl
 * @date 2018/08/09
 */
public class ExcelReadUtils {

    private ExcelReadUtils() {
    }

    /**
     * 分页处理Excel,excel必须是07版本
     *
     * @param inputStream excel输入流
     * @param pageCount   分页处理每页数量
     * @param callBack    分页处理回调回调函数
     */
    public static void readExcelByMap(InputStream inputStream, int pageCount, PageReadExcel callBack) {
        Assert.isTrue(pageCount > 0, "pageCount 必须大于0");
        try {
            PageReadExcelHandle pageReadExcelHandle = new PageReadExcelHandle(callBack, pageCount);
            new MySaxReadExcel().readExcel(inputStream, new MapSaxRowRead(pageReadExcelHandle), callBack,
                    pageReadExcelHandle);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
