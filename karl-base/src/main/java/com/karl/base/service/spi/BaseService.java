package com.karl.base.service.spi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.karl.base.util.excel.vo.ExcelWriteParam;

import java.util.function.Function;

/**
 * <description>
 *
 * @author karl
 * @date 2020/6/8
 */
public interface BaseService {
    /**
     * 获取excel表头参数
     *
     * @param entityClass
     * @param ignoreColumn
     * @return
     */
    ExcelWriteParam buildExcelWriteParam(Class<?> entityClass, Function<String, Boolean> ignoreColumn);

    /**
     * 解析分页排序信息
     *
     * @param cls
     * @param page
     * @param pageStr
     * @param orderBy
     */
    void parsePageAndOrder(Class<?> cls, Page<?> page, String pageStr, String orderBy);

    /**
     * 解析查询条件
     *
     * @param cls
     * @param w
     * @param query
     * @param field
     */
    void parseQueryWrapper(Class<?> cls, QueryWrapper<?> w, String query, String field);
}
