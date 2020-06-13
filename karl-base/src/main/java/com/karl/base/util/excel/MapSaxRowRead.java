package com.karl.base.util.excel;

import com.karl.base.util.excel.vo.ExportParam;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * MapSax读取
 *
 * @author karl
 * @date 2018/08/17
 */
public class MapSaxRowRead {

    /**
     * 表头key
     */
    private List<String> mapKey;
    /**
     * 必填列
     */
    private List<Integer> requiredList;
    /**
     * 分解读取处理器
     */
    private PageReadExcelHandle pageReadExcelHandle;
    /**
     * 导入参数
     */
    private ExportParam exportParam;
    /**
     * 是否处理导入参数
     */
    private boolean dealExcelParam;

    /**
     * 处理表头行次数
     */
    private int dealHeaderRowCount = 0;

    /**
     * 处理表头行次数
     */
    private int dealExcelParamRowCount = 0;
    /**
     * 解析表头结束
     */
    private boolean parseHeaderEnd;
    private static final String NULL_TITLE = "***";
    /**
     * sheet解析处理器
     */
    private MySheetHandler sheetHandler;

    /**
     * 需要处理的表头层数,默认为0,表示自动解析,大于0,按该层数处理
     */
    int headLevelNum = 0;

    /**
     * 需要处理的表头层数,
     * -2,表示未初始化
     * -1,表示自动解析,
     * 大于等于0,按该层数处理
     */
    int excelParamNum = NOT_INIT;

    /**
     * 未初始化
     */
    private static final int NOT_INIT = -2;
    /**
     * 自动处理
     */
    private static final int AUTO_DEAL = -1;

    public MapSaxRowRead(PageReadExcelHandle pageReadExcelHandle) {
        this.pageReadExcelHandle = pageReadExcelHandle;
    }

    public void reset() {
        headLevelNum = 0;
        dealHeaderRowCount = 0;
        excelParamNum = NOT_INIT;
        dealExcelParamRowCount = 0;
        dealExcelParam = false;
        parseHeaderEnd = false;
        mapKey = null;
        requiredList = null;
        exportParam = null;
        if (sheetHandler != null) {
            sheetHandler.reset();
        }

    }

    /**
     * @param index
     * @param dataList
     */
    public void parse(int index, List<String> dataList) {
        if (!parseHeaderEnd) {
            dataList = removeNullCell(dataList);
        }
        if (!dealExcelParam && parseExcelParam(index, dataList)) {
            return;
        }
        //表头行
        if (!parseHeaderEnd) {
            parseHeader(dataList);
            return;
        }
        Map<String, String> rowMap = new LinkedHashMap<>(mapKey.size());
        for (int i = 0; i < mapKey.size(); i++) {
            if (dataList.size() > i) {
                rowMap.put(mapKey.get(i), dataList.get(i));
            } else {
                rowMap.put(mapKey.get(i), "");
            }
        }
        pageReadExcelHandle.handler(rowMap);
    }

    /**
     * 解析导入参数
     *
     * @param index
     * @param dataList
     * @return true则解析成功, false表示未数据行
     */
    private boolean parseExcelParam(int index, List<String> dataList) {
        if (excelParamNum == NOT_INIT) {
            excelParamNum = pageReadExcelHandle.getCallBack().getExcelParamNum();
        }

        if (exportParam == null) {
            exportParam = new ExportParam();
            exportParam.setParamMap(new HashMap<>(10));
        }

        if (index == 0 && pageReadExcelHandle.getCallBack().hasStatement()) {
            exportParam.setStatement(dataList.get(0));
            return true;
        }
        dealExcelParamRowCount++;
        // 判断是否是参数行
        boolean isParamRow = excelParamNum == AUTO_DEAL && dataList.size() <= 2
                || excelParamNum >= dealExcelParamRowCount;
        if (isParamRow) {
            return parseParam(dataList);
        }
        dealExcelParam = true;
        pageReadExcelHandle.getCallBack().dealExcelParam(exportParam);
        return false;
    }

    private boolean parseParam(List<String> dataList) {
        if (dataList.size() == 0) {
            return true;
        }
        String key = dataList.get(0).trim().replace(":", "").replace("：", "");
        if (dataList.size() == 1) {
            exportParam.getParamMap().put(key, null);
        } else {
            exportParam.getParamMap().put(key, dataList.get(1));
        }
        return true;
    }

    /**
     * 去除空白cell
     *
     * @param dataList
     * @return
     */
    private List<String> removeNullCell(List<String> dataList) {
        // 在表头解析之前去除所有空白cell
        List<String> tmp = new ArrayList<>(dataList.size());
        for (String s : dataList) {
            if (StringUtils.isNotBlank(s)) {
                tmp.add(s);
            }
        }
        if (tmp.size() <= 2) {
            dataList = tmp;
        } else {
            //去掉末尾空白cell
            for (int i = dataList.size() - 1; i > 0; i--) {
                if (StringUtils.isBlank(dataList.get(i))) {
                    dataList.remove(i);
                } else {
                    break;
                }
            }
        }
        return dataList;
    }

    private void parseHeader(List<String> dataList) {
        parseHeaderEnd = true;
        dealHeaderRowCount++;
        if (headLevelNum == 0) {
            headLevelNum = pageReadExcelHandle.getCallBack().getHeadLevelNum();
        }
        if (dealHeaderRowCount == 1) {
            mapKey = new ArrayList<>(dataList.size());
            requiredList = new ArrayList<>(dataList.size());
        }
        for (int i = 0; i < dataList.size(); i++) {
            if (dealHeaderRowCount == 1) {
                //解析主表头
                dealHeaderTitle(i, dataList.get(i));
            } else {
                //解析子表头
                if (mapKey.size() - 1 >= i && mapKey.get(i).equals(NULL_TITLE)) {
                    mapKey.remove(i);
                    dealHeaderTitle(i, dataList.get(i));
                }
            }
        }
        if (parseHeaderEnd) {
            mapKey = pageReadExcelHandle.getCallBack().dealHeader(mapKey, requiredList);
        }
    }

    private void dealHeaderTitle(int i, String headerTitle) {
        if (StringUtils.isBlank(headerTitle)) {
            if (dealHeaderRowCount == headLevelNum) {
                mapKey.add(i, "");
                return;
            }
            if (dealHeaderRowCount == 1) {
                mapKey.remove(i - 1);
                mapKey.add(NULL_TITLE);
                mapKey.add(NULL_TITLE);
            } else {
                mapKey.add(i, NULL_TITLE);
            }
            parseHeaderEnd = false;
        } else if (headerTitle.contains("*")) {
            mapKey.add(i, headerTitle.replace("*", ""));
            requiredList.add(i);
        } else {
            mapKey.add(i, headerTitle);
        }
    }

    public MySheetHandler getSheetHandler() {
        return sheetHandler;
    }

    public void setSheetHandler(MySheetHandler sheetHandler) {
        this.sheetHandler = sheetHandler;
    }
}
