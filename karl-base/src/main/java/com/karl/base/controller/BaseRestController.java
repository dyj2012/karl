package com.karl.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.karl.base.model.BaseEntity;
import com.karl.base.service.BaseService;
import com.karl.base.util.L;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Rest 风格controller基础类
 *
 * @author 杜永军
 * @date 2020/05/24
 */
@Slf4j
public abstract class BaseRestController<Entity extends BaseEntity, Service extends IService<Entity>> {

    @Autowired
    protected Service service;

    @Autowired
    protected BaseService baseService;

    /**
     * 新增一个对象
     *
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询条件, 如: query=name:like:2,code:like:2", dataType = "String", paramType = "query", example = "name:like:2,code:like:2"),
            @ApiImplicitParam(name = "field", value = "查询列, 如: field=name,email", dataType = "String", paramType = "query", example = "name,email"),
            @ApiImplicitParam(name = "page", value = "查询条件, 如: page=total:true,current:2,size:2 或者 page=all", dataType = "String", paramType = "query", example = "all"),
            @ApiImplicitParam(name = "orderBy", value = "查询列, 如: orderBy=name,code:asc", dataType = "String", paramType = "query", example = "name,code:asc")
    })
    @ApiOperation(value = "查询接口", notes = "可以通过参数{query,field,page,orderBy}进行条件查询")
    @GetMapping
    public R<Page<Entity>> select(HttpServletRequest request) {
        return L.l(log, "select", () -> {
            String query = request.getParameter("query");
            String field = request.getParameter("field");
            String pageStr = request.getParameter("page");
            String orderBy = request.getParameter("orderBy");

            QueryWrapper<Entity> w = new QueryWrapper<>();
            Page<Entity> page = new Page<>();
            baseService.dealQueryWrapper(w, query, field);
            baseService.dealPageAndOrder(page, pageStr, orderBy);
            return R.ok(service.page(page, w));
        });
    }

    /**
     * 新增一个对象
     *
     * @param entity
     * @return
     */
    @ApiOperation(value = "新增接口", notes = "新增一个entity")
    @PostMapping
    public R<Boolean> add(@RequestBody Entity entity) {
        return L.l(log, "add", () -> {
            this.modifyEntity(entity);
            return R.ok(service.save(entity));
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
    public R<Boolean> batch(@RequestBody List<Entity> list) {
        return L.l(log, "batch", () -> {
            if (CollectionUtils.isNotEmpty(list)) {
                for (Entity entity : list) {
                    this.modifyEntity(entity);
                }
                return R.ok(service.saveBatch(list));
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
    @ApiOperation(value = "删除接口", notes = "根据Id删除一个entity")
    @DeleteMapping(value = "/{id}")
    public R<Boolean> delete(@PathVariable("id") String id) {
        return L.l(log, "delete", () -> R.ok(service.removeById(id)));

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
    @ApiOperation(value = "查询接口", notes = "根据Id查询一个entity")
    @GetMapping(value = "/{id}")
    public R<Entity> get(@PathVariable("id") String id) {
        return L.l(log, "get", () -> R.ok(service.getById(id)));

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
    @ApiOperation(value = "更新接口", notes = "根据Id更新一个entity")
    @PatchMapping(value = "/{id}")
    public R<Boolean> patch(@RequestBody Entity entity, @PathVariable("id") String id) {
        return L.l(log, "patch", () -> {
            entity.setObjectId(id);
            modifyEntity(entity);
            return R.ok(service.updateById(entity));
        });

    }

    protected void modifyEntity(Entity entity) {

    }
}
