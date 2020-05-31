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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @PostMapping(value = "/imports")
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
                        modifyEntity(t);
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
    @GetMapping(value = "/export")
    public void export(HttpServletRequest request, HttpServletResponse response) {
        R<Page<Entity>> selectR = this.select(request);
        ExcelWriteParam excelWriteParam = buildExcelWriteParam();
        try (OutputStream outputStream = response.getOutputStream()) {
            String fileName = String.format("导出数据-%s.xlsx", excelWriteParam.getSheetName());
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));
            response.setContentType("multipart/form-data");
            Page<Entity> data = selectR.getData();
            List<Entity> records = data.getRecords();
            ExcelWriteUtils.pageWriteExcel(() -> {
                if (records.size() == 0) {
                    return null;
                }
                List<Map<String, String>> mapList = new ArrayList<>(records.size());
                for (Entity record : records) {
                    Map<String, String> map = new LinkedHashMap<>();
                    MapEntityUtils.entityToMap(record, map, key -> CamelUtils.toUnderline(key).toUpperCase(), (key, value) -> String.valueOf(value));
                    mapList.add(map);
                }
                records.clear();
                return mapList;
            }, null, excelWriteParam, outputStream);
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
    @GetMapping(value = "/exportTemplate")
    public void exportTemplate(HttpServletResponse response) {
        ExcelWriteParam excelWriteParam = buildExcelWriteParam();
        try (OutputStream outputStream = response.getOutputStream()) {
            String fileName = String.format("导入模板-%s.xlsx", excelWriteParam.getSheetName());
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));
            response.setContentType("multipart/form-data");
            ExcelWriteUtils.pageWriteExcel(() -> null, null, excelWriteParam, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("导出模板IO错误", e);
            throw new RuntimeException("导出模板异常");
        }
    }

    /**
     * 编译正则表达式
     */
    private static final Pattern patten = Pattern.compile("\\{.*\\}");

    /**
     * @param str
     * @return
     */
    private String getMatchStr(String str) {
        Matcher matcher = patten.matcher(str);
        //此处find（）每次被调用后，
        while (matcher.find()) {
            return matcher.group().replace("{", "").replace("}", "");
        }
        return null;
    }

    protected ExcelWriteParam buildExcelWriteParam() {
        Class<Entity> entityClass = this.getEntityClass();
        TableName tableName = entityClass.getAnnotation(TableName.class);
        List<TableColumn> columns = metadataMapper.getColumns(tableName.value());
        parsePropertiesByComment(columns);
        Collections.sort(columns);
        ExcelWriteParam param = new ExcelWriteParam();
        List<ExcelKeyTitle> keyTitleList = new ArrayList<>(columns.size());
        for (TableColumn column : columns) {
            String columnName = column.getColumnName();
            if (ignoreColumn(columnName)) {
                continue;
            }
            keyTitleList.add(new ExcelKeyTitle(columnName, column.getTitle()));
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

    private void parsePropertiesByComment(List<TableColumn> columns) {
        for (TableColumn column : columns) {
            //{order:0,title:主键ID}
            String comment = column.getColumnComment();
            if (StringUtils.isNotBlank(comment)) {
                if (comment.contains("title") || comment.contains("order")) {
                    String matchStr = getMatchStr(comment);
                    String[] properties = matchStr.split(",");
                    for (String property : properties) {
                        String[] keyValue = property.split(":");
                        switch (keyValue[0]) {
                            case "order":
                                column.setPosition(Integer.parseInt(keyValue[1]));
                                break;
                            case "title":
                                column.setTitle(keyValue[1]);
                                break;
                            default:
                                break;
                        }
                    }
                }
                if (StringUtils.isEmpty(column.getTitle())) {
                    column.setTitle(comment);
                }
            }
            if (StringUtils.isEmpty(column.getTitle())) {
                column.setTitle(column.getColumnName());
            }
        }
    }

    protected String getIgnoreColumn() {
        return ",CREATE_TIME,CREATE_BY,UPDATE_TIME,UPDATE_BY,";
    }

    protected boolean ignoreColumn(String columnName) {
        return getIgnoreColumn().contains(String.format(",%s,", columnName));
    }
}
