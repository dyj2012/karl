package com.karl.base.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.karl.base.annotation.SelectSwagger;
import com.karl.base.model.BaseEntity;
import com.karl.base.service.BaseService;
import com.karl.base.util.L;
import com.karl.base.util.ListToMapUtils;
import com.karl.base.util.MapEntityUtils;
import com.karl.base.util.excel.ExcelReadUtils;
import com.karl.base.util.excel.ExcelWriteUtils;
import com.karl.base.util.excel.PageReadExcel;
import com.karl.base.util.excel.vo.ExcelKeyTitle;
import com.karl.base.util.excel.vo.ExcelWriteParam;
import com.karl.base.util.excel.vo.ExportParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
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
public abstract class BaseExcelController<Mapper extends BaseMapper<Entity>, Entity extends BaseEntity> extends BaseEntityController<Mapper, Entity> {


    /**
     * 导入excel
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "excel导入接口", notes = "excel导入entity")
    @PostMapping(value = "/imports")
    public R<Boolean> imports(@RequestParam("file") MultipartFile file) {
        return L.l(log, "imports", () -> {
            ExcelWriteParam excelWriteParam = baseService.buildExcelWriteParam(entityClass, this::exportTemplateIgnoreColumn);
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
                            keyList.add(excelKeyTitle.getKey());
                        }
                        return keyList;
                    }

                    @Override
                    public void pageCallback(List<Map<String, String>> mapList) {
                        List<Entity> list = new ArrayList<>(mapList.size());
                        for (Map<String, String> map : mapList) {
                            Entity t = MapEntityUtils.convert(map, currentModelClass(), null);
                            if (log.isDebugEnabled()) {
                                log.debug("解析entity的{}", t);
                            }
                            modifyEntity(t);
                            list.add(t);
                        }
                        saveBatch(list);
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
        });
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @SelectSwagger
    @ApiOperation(value = "excel导出接口", notes = "将entity导出到excel,可以通过参数{query,field,page,orderBy}进行条件查询")
    @GetMapping(value = "/export")
    public void export(HttpServletRequest request, HttpServletResponse response) {
        L.l(log, "export", () -> {
            R<Page<Entity>> selectR = this.select(request);
            ExcelWriteParam excelWriteParam = baseService.buildExcelWriteParam(entityClass, this::exportIgnoreColumn);
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
                        MapEntityUtils.entityToMap(record, map, key -> key, (key, value) -> String.valueOf(value));
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
        });
    }

    /**
     * 导出excel模板
     *
     * @param response
     */
    @ApiOperation(value = "excel模板接口", notes = "导出entity导入模板")
    @GetMapping(value = "/exportTemplate")
    public void exportTemplate(HttpServletResponse response) {
        L.l(log, "exportTemplate", () -> {
            ExcelWriteParam excelWriteParam = baseService.buildExcelWriteParam(entityClass, this::exportTemplateIgnoreColumn);
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
        });
    }

    protected boolean exportTemplateIgnoreColumn(String columnName) {
        return BaseService.TEMPLATE_IGNORE_COLUMN.contains(String.format(",%s,", columnName));
    }

    protected boolean exportIgnoreColumn(String columnName) {
        return false;
    }
}
