package com.karl.base.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.karl.base.annotation.OperationLog;
import com.karl.base.model.BaseEntity;
import com.karl.base.service.BaseServiceImpl;
import com.karl.base.util.ListToMapUtils;
import com.karl.base.util.Log;
import com.karl.base.util.MapEntityUtils;
import com.karl.base.util.excel.ExcelReadUtils;
import com.karl.base.util.excel.ExcelWriteUtils;
import com.karl.base.util.excel.PageReadExcel;
import com.karl.base.util.excel.vo.ExcelKeyTitle;
import com.karl.base.util.excel.vo.ExcelWriteParam;
import com.karl.base.util.excel.vo.ExportParam;
import com.karl.base.validator.group.AddGroup;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Rest 风格controller基础类
 *
 * @author 杜永军
 * @date 2020/05/24
 */
@Slf4j
public abstract class BaseEntityController<Mapper extends BaseMapper<Entity>, Entity extends BaseEntity> extends BaseQueryController<Mapper, Entity> {

    /**
     * 新增一个对象
     *
     * @param entity
     * @return
     */
    @ApiOperation(value = "对象新增接口", notes = "新增一个entity")
    @PostMapping
    @OperationLog("对象新增")
    public R<Boolean> add(@RequestBody @Validated(AddGroup.class) Entity entity) {
        return Log.p(log, "add", () -> {
            this.modifyEntity(entity);
            return R.ok(super.save(entity));
        });

    }

    /**
     * 新增一个对象
     *
     * @param list
     * @return
     */

    @ApiOperation(value = "批量新增接口", notes = "以数组的形式增加多个entity,[entity,entity,entity]")
    @PostMapping("/batch")
    @OperationLog("批量新增")
    public R<Boolean> batch(@RequestBody @Validated(AddGroup.class) List<Entity> list) {
        return Log.p(log, "batch", () -> {
            if (CollectionUtils.isNotEmpty(list)) {
                for (Entity entity : list) {
                    this.modifyEntity(entity);
                }
                return R.ok(super.saveBatch(list));
            } else {
                return R.failed("传输对象为空");
            }
        });

    }

    /**
     * 根据ID删除一个对象
     *
     * @param id
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "实体ID", required = true, dataType = "String", paramType = "path", example = "123abc")
    })
    @ApiOperation(value = "对象删除接口", notes = "根据Id删除一个entity")
    @DeleteMapping(value = "/{id}")
    @OperationLog("对象删除")
    public R<Boolean> delete(@PathVariable("id") String id) {
        return Log.p(log, "delete", () -> R.ok(super.removeById(id)));
    }


    /**
     * 更新一个对象
     *
     * @param entity
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "实体ID", required = true, dataType = "String", paramType = "path", example = "123abc")
    })
    @ApiOperation(value = "对象更新接口", notes = "根据Id更新一个entity")
    @PatchMapping(value = "/{id}")
    @OperationLog("对象更新")
    public R<Boolean> patch(@RequestBody Entity entity, @PathVariable("id") String id) {
        return Log.p(log, "patch", () -> {
            entity.setObjectId(id);
            modifyEntity(entity);
            return R.ok(super.updateById(entity));
        });
    }

    /**
     * 导入excel
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "excel导入接口", notes = "excel导入entity")
    @PostMapping(value = "/imports")
    @OperationLog("excel导入")
    public R<Boolean> imports(@RequestParam("file") MultipartFile file) {
        return Log.p(log, "imports", () -> {
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
     * 导出excel模板
     *
     * @param response
     */
    @ApiOperation(value = "excel模板接口", notes = "导出entity导入模板")
    @GetMapping(value = "/exportTemplate", produces = "application/octet-stream")
    @OperationLog("excel模板")
    public void exportTemplate(HttpServletResponse response) {
        Log.p(log, "exportTemplate", () -> {
            ExcelWriteParam excelWriteParam = baseService.buildExcelWriteParam(entityClass, this::exportTemplateIgnoreColumn);
            try (OutputStream outputStream = response.getOutputStream()) {
                String fileName = String.format("导入模板-%s.xlsx", excelWriteParam.getSheetName());
                response.setContentType("application/vnd.ms-excel;charset=UTF-8;");
                response.addHeader("Content-Disposition", "attachment;FileName=" + URLEncoder.encode(fileName, "utf-8"));
                ExcelWriteUtils.pageWriteExcel(() -> null, null, excelWriteParam, outputStream);
                outputStream.flush();
            } catch (IOException e) {
                log.error("导出模板IO错误", e);
                throw new RuntimeException("导出模板异常");
            }
        });
    }

    protected boolean exportTemplateIgnoreColumn(String columnName) {
        return BaseServiceImpl.TEMPLATE_IGNORE_COLUMN.contains(String.format(",%s,", columnName));
    }

    protected void modifyEntity(Entity entity) {

    }
}
