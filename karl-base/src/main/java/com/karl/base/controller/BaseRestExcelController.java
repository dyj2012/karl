package com.karl.base.controller;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.karl.base.annotation.ExcelName;
import com.karl.base.mapper.MetadataMapper;
import com.karl.base.mapper.vo.TableColumn;
import com.karl.base.model.BaseEntity;
import com.karl.base.util.CamelUtils;
import com.karl.base.util.ListToMapUtils;
import com.karl.base.util.MapEntityUtils;
import com.karl.base.util.excel.ExcelReadUtils;
import com.karl.base.util.excel.ExcelWriteUtils;
import com.karl.base.util.excel.PageReadExcel;
import com.karl.base.util.excel.vo.ExcelKeyTitle;
import com.karl.base.util.excel.vo.ExcelWriteParam;
import com.karl.base.util.excel.vo.ExportParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * excel 导入导出controller基础类
 *
 * @author 杜永军
 * @date 2020/05/24
 */
@Slf4j
public abstract class BaseRestExcelController<Entity extends BaseEntity, Service extends IService<Entity>> extends BaseRestController<Entity, Service> {

    @Autowired
    MetadataMapper metadataMapper;

    protected Class<Entity> getEntityClass() {
        return (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 导入excel
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/imports", method = RequestMethod.POST)
    public R<Boolean> imports(@RequestParam("file") MultipartFile file) {
        ExcelWriteParam excelWriteParam = buildExcelWriteParam();
        try (InputStream inputStream = file.getInputStream()) {
            ExcelReadUtils.readExcelByMap(inputStream, 200, new PageReadExcel() {
                @Override
                public boolean nextSheet(int index, String sheetName) {
                    return index == 0;
                }

                @Override
                public void dealExcelParam(ExportParam exportParam) {

                }

                @Override
                public List<String> dealHeader(List<String> headerList, List<Integer> requiredList) {
                    List<String> keyList = new ArrayList<>(headerList.size());
                    List<ExcelKeyTitle> keyTitleList = excelWriteParam.getKeyTitleList();
                    Map<String, ExcelKeyTitle> excelKeyTitleMap = ListToMapUtils.toMap(keyTitleList, ExcelKeyTitle::getTitle);
                    for (String title : headerList) {
                        ExcelKeyTitle excelKeyTitle = excelKeyTitleMap.get(title);
                        if (excelKeyTitle == null) {
                            throw new RuntimeException(String.format("%s,未定义的title", title));
                        }
                        keyList.add(CamelUtils.toCamel(excelKeyTitle.getKey()));
                    }
                    return keyList;
                }

                @Override
                public void pageCallback(List<Map<String, String>> mapList) {
                    List<Entity> list = new ArrayList<>(mapList.size());
                    for (Map<String, String> map : mapList) {
                        Entity t = MapEntityUtils.convert(map, getEntityClass(), null);
                        if (log.isDebugEnabled()) {
                            log.debug("解析entity的{}", t);
                        }
                        list.add(t);
                    }
                    service.saveBatch(list);
                }

                @Override
                public boolean hasStatement() {
                    return false;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.ok(Boolean.TRUE);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
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
                    MapEntityUtils.entityToMap(record, map, CamelUtils::toUnderline, (key, value) -> String.valueOf(value));
                    mapList.add(map);
                }
                return mapList;
            }, (cell, text, rowData, entity) -> {
            }, excelWriteParam, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("导出记录IO错误", e);
            throw new RuntimeException("导出记录异常");
        }
    }

    /**
     * 导出excel模板
     *
     * @param response
     */
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
            log.error("导出模板IO错误", e);
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
