package com.karl.base.util.excel;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2018/08/09
 */
public class PageReadExcelHandle {

    private PageReadExcel callBack;
    private List<Map<String, String>> mapList;
    private int pageCount;

    public PageReadExcelHandle(PageReadExcel callBack, int pageCount) {
        this.callBack = callBack;
        this.pageCount = pageCount;
        mapList = new ArrayList<>(pageCount);
    }

    public void handler(Map<String, String> o) {
        mapList.add(o);
        if (mapList.size() == pageCount) {
            doCallBack();
        }
    }

    public void doCallBack() {
        if (CollectionUtils.isNotEmpty(mapList)) {
            List<Map<String, String>> copyList = new ArrayList<>(mapList);
            mapList.clear();
            callBack.pageCallback(copyList);
        }
    }

    public PageReadExcel getCallBack() {
        return callBack;
    }
}
