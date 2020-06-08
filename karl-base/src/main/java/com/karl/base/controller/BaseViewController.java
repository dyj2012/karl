package com.karl.base.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.karl.base.annotation.SelectSwagger;
import com.karl.base.model.BaseView;
import com.karl.base.util.CamelUtils;
import com.karl.base.util.Log;
import com.karl.base.util.MapEntityUtils;
import com.karl.base.util.excel.ExcelWriteUtils;
import com.karl.base.util.excel.vo.ExcelWriteParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
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
@Slf4j
public abstract class BaseViewController<Mapper extends BaseMapper<Entity>, Entity extends BaseView> extends BaseQueryController<Mapper, Entity> {

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
        Log.p(log, "export", () -> {
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
        });
    }

    protected boolean exportIgnoreColumn(String columnName) {
        return false;
    }
}
