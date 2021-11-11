package com.karl.base.service;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.karl.base.actable.annotation.Column;
import com.karl.base.annotation.ExcelCol;
import com.karl.base.annotation.ExcelSheet;
import com.karl.base.constants.ErrorCodeConstants;
import com.karl.base.exception.BaseException;
import com.karl.base.service.spi.BaseService;
import com.karl.base.service.vo.ExcelTitleVo;
import com.karl.base.util.Log;
import com.karl.base.util.excel.vo.ExcelKeyTitle;
import com.karl.base.util.excel.vo.ExcelWriteParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * 基础服务
 *
 * @author karl
 * @date 2020/6/3
 */
@Component
@Slf4j
public class BaseServiceImpl implements BaseService {

    /**
     * column属性分隔符
     */
    private static final String COLUMN_SPLIT = ",";
    /**
     * key value 分隔符
     */
    private static final String KEY_VALUE_SPLIT = ":";
    /**
     * 默认忽略的导入模板的列
     */
    public static final String TEMPLATE_IGNORE_COLUMN = ",CREATE_TIME,CREATE_BY,UPDATE_TIME,UPDATE_BY,";

    /**
     * 构建导出excel表头
     *
     * @param entityClass
     * @param ignoreColumn
     * @return
     */
    @Override
    public ExcelWriteParam buildExcelWriteParam(Class<?> entityClass, Function<String, Boolean> ignoreColumn) {
        return Log.p(log, "buildExcelWriteParam", () -> {
            TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
            List<TableFieldInfo> fieldList = tableInfo.getFieldList();
            List<ExcelTitleVo> titleList = new ArrayList<>(fieldList.size());
            titleList.add(getPrimaryExcelTitleVo());
            for (TableFieldInfo tableFieldInfo : fieldList) {
                ExcelCol excelCol = tableFieldInfo.getField().getAnnotation(ExcelCol.class);

                if (excelCol != null) {
                    ExcelTitleVo excelTitleVo = new ExcelTitleVo();
                    excelTitleVo.setKey(tableFieldInfo.getProperty());
                    if (StringUtils.isEmpty(excelCol.value())) {
                        Column column = tableFieldInfo.getField().getAnnotation(Column.class);
                        if (column != null && StringUtils.isNotEmpty(column.comment())) {
                            excelTitleVo.setTitle(column.comment());
                        } else {
                            excelTitleVo.setTitle(tableFieldInfo.getColumn());
                        }
                    } else {
                        excelTitleVo.setTitle(excelCol.value());
                    }

                    excelTitleVo.setOrder(excelCol.order());
                    excelTitleVo.setExcelCol(excelCol);
                    titleList.add(excelTitleVo);
                }
            }

            titleList.sort(Comparator.comparing(ExcelTitleVo::getOrder));
            ExcelWriteParam param = new ExcelWriteParam();
            List<ExcelKeyTitle> keyTitleList = new ArrayList<>(titleList.size());
            for (ExcelTitleVo vo : titleList) {
                String key = vo.getKey();
                if (Boolean.TRUE.equals(ignoreColumn.apply(key))) {
                    continue;
                }
                ExcelKeyTitle excelKeyTitle = new ExcelKeyTitle(key, vo.getTitle());
                if (vo.getExcelCol() != null) {
                    excelKeyTitle.setRequired(vo.getExcelCol().required());
                    excelKeyTitle.setComment(vo.getExcelCol().cellComment());
                }
                keyTitleList.add(excelKeyTitle);
            }
            TableName tableName = entityClass.getAnnotation(TableName.class);
            ExcelSheet excelSheet = entityClass.getAnnotation(ExcelSheet.class);
            if (excelSheet != null) {
                param.setSheetName(excelSheet.value());
            } else {
                param.setSheetName(tableName.value().toLowerCase());
            }
            param.setKeyTitleList(keyTitleList);
            log.debug("return buildExcelWriteParam");
            return param;
        });

    }

    private ExcelTitleVo getPrimaryExcelTitleVo() {
        ExcelTitleVo primaryKeyTitle = new ExcelTitleVo();
        primaryKeyTitle.setKey("objectId");
        primaryKeyTitle.setTitle("主键");
        primaryKeyTitle.setOrder(0);
        return primaryKeyTitle;
    }

    @Override
    public void parsePageAndOrder(Class<?> cls, Page<?> page, String pageStr, String orderBy) {
        Log.p(log, "dealPageAndOrder", () -> {
            this.dealOrder(cls, page, orderBy);
            this.dealPage(page, pageStr);
        });
    }

    @Override
    public void parseQueryWrapper(Class<?> cls, QueryWrapper<?> w, String query, String field) {
        Log.p(log, "dealPageAndOrder", () -> {
            this.dealQuery(cls, w, query);
            this.dealSelectField(cls, w, field);
        });
    }

    private static final String ORDER_ASC = "asc";
    private static final String ORDER_DESC = "desc";

    private String findColumnName(Class<?> cls, String fieldName) {
        Assert.notNull(fieldName, "fieldName must not be null");
        TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
        if (fieldName.equals(tableInfo.getKeyProperty())) {
            return tableInfo.getKeyColumn();
        }
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        for (TableFieldInfo tableFieldInfo : fieldList) {
            if (tableFieldInfo.getProperty().equalsIgnoreCase(fieldName)) {
                return tableFieldInfo.getColumn();
            }

        }
        throw new BaseException(1000, String.format("[%s]未定义的列", fieldName));
    }


    private void dealOrder(Class<?> cls, Page<?> page, String orderBy) {
        Log.p(log, "dealOrder", () -> {
            List<OrderItem> orders = null;
            if (!org.springframework.util.StringUtils.isEmpty(orderBy)) {
                String[] orderColumns = orderBy.split(COLUMN_SPLIT);
                orders = new ArrayList<>(orderColumns.length);
                for (String orderColumn : orderColumns) {
                    String[] split = orderColumn.split(KEY_VALUE_SPLIT);
                    String columnName = this.findColumnName(cls, split[0]);
                    if (split.length == 1) {
                        orders.add(OrderItem.desc(columnName));
                        continue;
                    }
                    switch (split[1]) {
                        case ORDER_ASC:
                            orders.add(OrderItem.asc(columnName));
                            break;
                        case ORDER_DESC:
                            orders.add(OrderItem.desc(columnName));
                            break;
                        default:
                            throw new BaseException(ErrorCodeConstants.ORDER_ARG, "无效的排序参数");
                    }
                }
            }
            page.setOrders(orders);
        });
    }

    private static final String PAGE_ALL = "all";
    private static final String PAGE_TOTAL = "total";
    private static final String PAGE_CURRENT = "current";
    private static final String PAGE_SIZE = "size";

    private void dealPage(Page<?> page, String pageStr) {
        Log.p(log, "dealPage", () -> {
            boolean isSearchCount = false;
            long current = 0;
            long size = 50;

            if (!org.springframework.util.StringUtils.isEmpty(pageStr)) {
                if (PAGE_ALL.equalsIgnoreCase(pageStr)) {
                    size = Long.MAX_VALUE;
                } else {
                    String[] pageInfo = pageStr.split(COLUMN_SPLIT);
                    for (String info : pageInfo) {
                        String[] split = info.split(KEY_VALUE_SPLIT);
                        switch (split[0]) {
                            case PAGE_TOTAL:
                                isSearchCount = Boolean.parseBoolean(split[1]);
                                break;
                            case PAGE_CURRENT:
                                current = Long.parseLong(split[1]);
                                break;
                            case PAGE_SIZE:
                                size = Long.parseLong(split[1]);
                                break;
                            default:
                                throw new BaseException(1001, "无效的分页参数");
                        }
                    }
                }
            }
            page.setCurrent(current);
            page.setSize(size);
            page.setSearchCount(isSearchCount);
        });
    }

    private void dealSelectField(Class<?> cls, QueryWrapper<?> w, String fieldStr) {
        Log.p(log, "dealSelectField", () -> {
            if (!StringUtils.isEmpty(fieldStr)) {
                String[] fields = fieldStr.split(COLUMN_SPLIT);
                StringBuilder sb = null;
                for (String field : fields) {
                    String columnName = this.findColumnName(cls, field);
                    if (sb == null) {
                        sb = new StringBuilder(columnName);
                    } else {
                        sb.append(String.format(",%s", columnName));
                    }
                }
                w.select(sb.toString());
            }
        });
    }

    private void dealQuery(Class<?> cls, QueryWrapper<?> w, String query) {
        Log.p(log, "dealQuery", () -> {
            if (!StringUtils.isEmpty(query)) {
                String[] condition = query.split(COLUMN_SPLIT);
                try {
                    for (String conditionColumn : condition) {
                        String[] split = conditionColumn.split(KEY_VALUE_SPLIT);
                        if (split.length == 1) {
                            if ("or".equalsIgnoreCase(split[0])) {
                                w.or();
                                continue;
                            }
                            throw new BaseException(1004, "未定义的单值查询条件!");
                        }
                        String columnName = this.findColumnName(cls, split[0]);
                        switch (split[1].toLowerCase()) {
                            //>=
                            case "ge":
                                w.ge(columnName, split[2]);
                                break;
                            //>
                            case "gt":
                                w.gt(columnName, split[2]);
                                break;
                            //<=
                            case "le":
                                w.le(columnName, split[2]);
                                break;
                            //<
                            case "lt":
                                w.lt(columnName, split[2]);
                                break;
                            //=
                            case "eq":
                                w.eq(columnName, split[2]);
                                break;
                            //<>
                            case "ne":
                                w.ne(columnName, split[2]);
                                break;
                            case "isNotNull":
                                w.isNotNull(columnName);
                                break;
                            case "isNull":
                                w.isNull(columnName);
                                break;
                            case "like":
                                w.like(columnName, split[2]);
                                break;
                            case "likeLeft":
                                w.likeLeft(columnName, split[2]);
                                break;
                            case "likeRight":
                                w.likeRight(columnName, split[2]);
                                break;
                            case "between":
                                w.between(columnName, split[2], split[3]);
                                break;
                            case "notBetween":
                                w.notBetween(columnName, split[2], split[3]);
                                break;
                            case "in":
                                w.in(columnName, (Object) Arrays.copyOfRange(split, 2, split.length));
                                break;
                            case "notIn":
                                w.notIn(columnName, (Object) Arrays.copyOfRange(split, 2, split.length));
                                break;
                            default:
                                throw new BaseException(ErrorCodeConstants.ORDER_ARG, "无效的查询条件参数");
                        }
                    }
                } catch (BaseException e) {
                    throw e;
                } catch (Exception e) {
                    log.error("解析查询条件参数异常", e);
                    throw new BaseException(1002, "解析查询条件参数异常");
                }
            }
        });
    }
}
