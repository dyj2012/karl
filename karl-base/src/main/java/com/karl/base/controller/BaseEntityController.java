package com.karl.base.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.karl.base.model.BaseEntity;
import com.karl.base.util.Log;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ApiOperation(value = "新增接口", notes = "新增一个entity")
    @PostMapping
    public R<Boolean> add(@RequestBody Entity entity) {
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
    public R<Boolean> batch(@RequestBody List<Entity> list) {
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
    @ApiOperation(value = "删除接口", notes = "根据Id删除一个entity")
    @DeleteMapping(value = "/{id}")
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
    @ApiOperation(value = "更新接口", notes = "根据Id更新一个entity")
    @PatchMapping(value = "/{id}")
    public R<Boolean> patch(@RequestBody Entity entity, @PathVariable("id") String id) {
        return Log.p(log, "patch", () -> {
            ((BaseEntity) entity).setObjectId(id);
            modifyEntity(entity);
            return R.ok(super.updateById(entity));
        });

    }

    protected void modifyEntity(Entity entity) {

    }
}
