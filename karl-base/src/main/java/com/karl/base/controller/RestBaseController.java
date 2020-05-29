package com.karl.base.controller;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.karl.base.annotation.ExcelName;
import com.karl.base.constants.ErrorCodeConstants;
import com.karl.base.exception.BaseException;
import com.karl.base.mapper.MetadataMapper;
import com.karl.base.mapper.vo.TableColumn;
import com.karl.base.model.BaseEntity;
import com.karl.base.util.CamelUtils;
import com.karl.base.util.MapUtils;
import com.karl.base.util.excel.ExcelWriteUtils;
import com.karl.base.util.excel.vo.ExcelKeyTitle;
import com.karl.base.util.excel.vo.ExcelWriteParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Rest 风格controller基础类
 *
 * @author 杜永军
 * @date 2020/05/24
 */
public abstract class RestBaseController<Entity extends BaseEntity> {
    private final Logger logger = LoggerFactory.getLogger(RestBaseController.class);

    @Autowired
    MetadataMapper metadataMapper;

    protected Class<Entity> getEntityClass() {
        return (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 获取 Server
     *
     * @return IService<T>
     */
    protected abstract IService<Entity> getService();

    /**
     * 新增一个对象
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public R<Page<Entity>> select(HttpServletRequest request) {
        String query = request.getParameter("query");
        String field = request.getParameter("field");
        String pageStr = request.getParameter("page");
        String orderBy = request.getParameter("orderBy");

        QueryChainWrapper<Entity> w = new QueryChainWrapper<Entity>(getService().getBaseMapper());
        dealQuery(query, w);
        dealSelectField(w, field);
        List<OrderItem> orders = dealOrder(orderBy);
        Page<Entity> page = dealPage(pageStr, orders);
        return R.ok(w.page(page));
    }

    private void dealQuery(String query, QueryChainWrapper<Entity> w) {
        if (!StringUtils.isEmpty(query)) {
            String[] condition = query.split(",");
            for (String conditionColumn : condition) {
                String[] split = conditionColumn.split(":");
                String columnName = CamelUtils.toUnderline(split[0]).toUpperCase();
                switch (split[1]) {
                    case ">":
                        w.ge(columnName, split[2]);
                        break;
                    case "=":
                        w.eq(columnName, split[2]);
                        break;
                    case "like":
                        w.like(columnName, split[2]);
                        break;
                    default:
                        throw new BaseException(ErrorCodeConstants.ORDER_ARG, "无效的查询条件参数");
                }
            }
        }
    }

    private List<OrderItem> dealOrder(String orderBy) {
        List<OrderItem> orders = null;
        if (!StringUtils.isEmpty(orderBy)) {
            String[] orderColumns = orderBy.split(",");
            orders = new ArrayList<>(orderColumns.length);
            for (String orderColumn : orderColumns) {
                String[] split = orderColumn.split(":");
                String columnName = CamelUtils.toUnderline(split[0]).toUpperCase();
                if (split.length == 1) {
                    orders.add(OrderItem.desc(columnName));
                    continue;
                }
                switch (split[1]) {
                    case "asc":
                        orders.add(OrderItem.asc(columnName));
                        break;
                    case "desc":
                        orders.add(OrderItem.desc(columnName));
                        break;
                    default:
                        throw new BaseException(ErrorCodeConstants.ORDER_ARG, "无效的排序参数");
                }
            }
        }
        return orders;
    }

    private Page<Entity> dealPage(String pageStr, List<OrderItem> orders) {
        boolean isSearchCount = false;
        long current = 0;
        long size = 10;
        if (!StringUtils.isEmpty(pageStr)) {
            String[] pageInfo = pageStr.split(",");
            for (String info : pageInfo) {
                String[] split = info.split(":");
                switch (split[0]) {
                    case "total":
                        isSearchCount = Boolean.parseBoolean(split[1]);
                        break;
                    case "current":
                        current = Long.parseLong(split[1]);
                        break;
                    case "size":
                        size = Long.parseLong(split[1]);
                        break;
                    default:
                        throw new BaseException(1001, "无效的分页参数");
                }
            }

        }
        Page<Entity> page = new Page<>(current, size, isSearchCount);
        page.setOrders(orders);
        return page;
    }

    private void dealSelectField(QueryChainWrapper<Entity> w, String field) {
        if (!StringUtils.isEmpty(field)) {
            String[] fields = CamelUtils.toUnderline(field).toUpperCase().split(",");
            w.select(fields);
        }
    }


    /**
     * 新增一个对象
     *
     * @param entity
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public R<Boolean> add(Entity entity) {
        return R.ok(getService().save(entity));
    }

    /**
     * 根据ID删除一个对象
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public R<Boolean> delete(@PathVariable("id") String id) {
        return R.ok(getService().removeById(id));
    }

    /**
     * 根据ID获取对象
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R<Entity> get(@PathVariable("id") String id) {
        return R.ok(getService().getById(id));
    }

    /**
     * 更新一个对象
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public R<Boolean> update(Entity entity, @PathVariable("id") String id) {
        entity.setObjectId(id);
        return R.ok(getService().updateById(entity));
    }

    @RequestMapping(value = "/imports", method = RequestMethod.POST)
    public R<Boolean> imports(@RequestParam("file") MultipartFile file) {
        IService<Entity> service = getService();
        ExcelWriteParam excelWriteParam = buildExcelWriteParam();
        BaseMapper<Entity> baseMapper = service.getBaseMapper();
        return R.ok(Boolean.TRUE);
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response) {
        R<Page<Entity>> selectR = this.select(request);
        ExcelWriteParam excelWriteParam = buildExcelWriteParam();
        try (OutputStream outputStream = response.getOutputStream()) {
            String fileName = String.format("导入模板-%s.xlsx", excelWriteParam.getSheetName());
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
            response.setContentType("multipart/form-data");
            ExcelWriteUtils.pageWriteExcel(() -> {
                Page<Entity> data = selectR.getData();
                List<Entity> records = data.getRecords();
                List<Map<String, String>> mapList = new ArrayList<>(records.size());
                for (Entity record : records) {
                    Map<String, String> map = new LinkedHashMap<>();
                    MapUtils.entityToMap(record, map, CamelUtils::toUnderline, (key, value) -> String.valueOf(value));
                    mapList.add(map);
                }
                return mapList;
            }, (cell, text, rowData, entity) -> {
            }, excelWriteParam, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            logger.error("导出记录IO错误", e);
            throw new RuntimeException("导出记录异常");
        }
    }

    @RequestMapping(value = "/exportTemplate", method = RequestMethod.GET)
    public void exportTemplate(HttpServletResponse response) {
        ExcelWriteParam excelWriteParam = buildExcelWriteParam();
        try (OutputStream outputStream = response.getOutputStream()) {
            String fileName = String.format("导入模板-%s.xlsx", excelWriteParam.getSheetName());
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
            response.setContentType("multipart/form-data");
            ExcelWriteUtils.pageWriteExcel(() -> null, null, excelWriteParam, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            logger.error("导出模板IO错误", e);
            throw new RuntimeException("导出模板异常");
        }
    }

    protected ExcelWriteParam buildExcelWriteParam() {
        Class<Entity> entityClass = this.getEntityClass();
        TableName tableName = entityClass.getAnnotation(TableName.class);
        List<TableColumn> columns = metadataMapper.getColumns(tableName.value());
        ExcelWriteParam param = new ExcelWriteParam();
        List<ExcelKeyTitle> keyTitleList = new ArrayList<>(columns.size());
        for (TableColumn column : columns) {
            String title = column.getColumnComment();
            String columnName = column.getColumnName();
            if (ignoreColumn(columnName)) {
                continue;
            }
            keyTitleList.add(new ExcelKeyTitle(columnName, title));
        }
        ExcelName excelName = entityClass.getAnnotation(ExcelName.class);
        if (excelName != null) {
            param.setSheetName(excelName.value());
        } else {
            param.setSheetName(tableName.value().toLowerCase());
        }
        param.setKeyTitleList(keyTitleList);
        return param;
    }

    protected String getIgnoreColumn() {
        return ",CREATE_TIME,CREATE_BY,UPDATE_TIME,DATA_VERSION,";
    }

    protected boolean ignoreColumn(String columnName) {
        return getIgnoreColumn().contains(String.format(",%s,", columnName));
    }
}
