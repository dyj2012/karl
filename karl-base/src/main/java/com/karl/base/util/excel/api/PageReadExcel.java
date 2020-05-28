package com.karl.base.util.excel.api;


import com.karl.base.util.excel.api.vo.ExportParam;

import java.util.List;
import java.util.Map;

/**
 * 分页回调函数
 *
 * @author 杜永军
 * @date 2018/08/09
 */
public interface PageReadExcel {

    /**
     * 第一步 sheet处理
     *
     * @param index     第几个sheet
     * @param sheetName sheet名称
     * @return 返回true则解析该sheet, 返回false则跳过该sheet
     */
    boolean nextSheet(int index, String sheetName);

    /**
     * 是否有说明行
     *
     * @return
     */
    default boolean hasStatement() {
        return true;
    }

    /**
     * 第二步 处理导入参数ExcelParam
     *
     * @param exportParam 导入参数
     */
    void dealExcelParam(ExportParam exportParam);

    /**
     * 获取需要解析表头层数
     *
     * @return
     */
    default int getHeadLevelNum() {
        return 0;
    }

    /**
     * 获取需要导入参数层数
     * -1表示自动处理,大于等于0,则按照此参数进行解析
     *
     * @return
     */
    default int getExcelParamNum() {
        return -1;
    }

    /**
     * 第三步,处理表头信息
     *
     * @param headerList   表头
     * @param requiredList 必填列
     * @return 返回表头对应的Key
     */
    List<String> dealHeader(List<String> headerList, List<Integer> requiredList);

    /**
     * 第四步 分页处理行数据
     *
     * @param mapList map中的key使用的是第三步返回的List的内容
     */
    void pageCallback(List<Map<String, String>> mapList);

}
