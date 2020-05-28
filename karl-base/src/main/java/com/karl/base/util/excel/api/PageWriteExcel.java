package com.karl.base.util.excel.api;

import java.util.List;
import java.util.Map;

/**
 * 分页回调函数
 *
 * @author 杜永军
 * @date 2018/08/09
 */
@FunctionalInterface
public interface PageWriteExcel {

    /**
     * 分页查询
     *
     * @return
     */
    List<Map<String, String>> nextPage();
}
