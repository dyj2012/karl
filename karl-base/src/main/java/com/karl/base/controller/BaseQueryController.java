package com.karl.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karl.base.annotation.OperationLog;
import com.karl.base.annotation.SelectSwagger;
import com.karl.base.service.spi.BaseService;
import com.karl.base.util.Log;
import com.karl.base.util.MapEntityUtils;
import com.karl.base.util.excel.ExcelWriteUtils;
import com.karl.base.util.excel.vo.ExcelWriteParam;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Rest 风格controller基础类
 *
 * @author karl
 * @date 2020/05/24
 */
@Slf4j
public abstract class BaseQueryController<Mapper extends BaseMapper<Entity>, Entity> extends ServiceImpl<Mapper, Entity> {

    @Autowired
    protected BaseService baseService;

    /**
     * 新增一个对象
     *
     * @return
     */
    @ApiOperation(value = "列表查询接口", notes = "可以通过参数{query,field,page,orderBy}进行条件查询")
    @SelectSwagger
    @GetMapping
    @OperationLog("列表查询")
    public R<Page<Entity>> select(HttpServletRequest request) {
        return Log.p(log, "select", () -> {
            String query = request.getParameter("query");
            String field = request.getParameter("field");
            String pageStr = request.getParameter("page");
            String orderBy = request.getParameter("orderBy");
            QueryWrapper<Entity> w = new QueryWrapper<>();
            Page<Entity> page = new Page<>();
            baseService.parseQueryWrapper(entityClass, w, query, field);
            baseService.parsePageAndOrder(entityClass, page, pageStr, orderBy);
            return R.ok(super.page(page, w));
        });
    }

    /**
     * 根据ID获取对象
     *
     * @param id
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "实体ID", required = true, dataType = "String", paramType = "path", example = "123abc")
    })
    @ApiOperation(value = "对象查询接口", notes = "根据Id查询一个entity")
    @GetMapping("/{id}")
    @OperationLog("对象查询")
    public R<Entity> get(@PathVariable("id") String id) {
        return Log.p(log, "get", () -> R.ok(super.getById(id)));
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @SelectSwagger
    @ApiOperation(value = "excel导出接口", notes = "将entity导出到excel,可以通过参数{query,field,page,orderBy}进行条件查询")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    @OperationLog("excel导出")
    public void export(HttpServletRequest request, HttpServletResponse response) {
        Log.p(log, "export", () -> {
            R<Page<Entity>> selectR = this.select(request);
            ExcelWriteParam excelWriteParam = baseService.buildExcelWriteParam(entityClass, this::exportIgnoreColumn);
            try (OutputStream outputStream = response.getOutputStream()) {
                String fileName = String.format("导出数据-%s.xlsx", excelWriteParam.getSheetName());
                response.setContentType("application/vnd.ms-excel;charset=UTF-8;");
                response.addHeader("Content-Disposition", "attachment;FileName=" + URLEncoder.encode(fileName, "utf-8"));
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


    protected boolean exportIgnoreColumn(String columnName) {
        return false;
    }
}
