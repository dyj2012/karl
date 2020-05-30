package com.karl.base.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.karl.base.constants.ErrorCodeConstants;
import com.karl.base.exception.BaseException;
import com.karl.base.model.BaseEntity;
import com.karl.base.util.CamelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    /**
     * 新增一个对象
     *
     * @return
     */
    @GetMapping
    public R<Page<Entity>> select(HttpServletRequest request) {
        String query = request.getParameter("query");
        String field = request.getParameter("field");
        String pageStr = request.getParameter("page");
        String orderBy = request.getParameter("orderBy");

        QueryChainWrapper<Entity> w = new QueryChainWrapper<Entity>(service.getBaseMapper());
        dealQuery(query, w);
        dealSelectField(w, field);
        List<OrderItem> orders = dealOrder(orderBy);
        Page<Entity> page = dealPage(pageStr, orders);
        return R.ok(w.page(page));
    }


    /**
     * 新增一个对象
     *
     * @param entity
     * @return
     */
    @PostMapping
    public R<Boolean> add(Entity entity) {
        this.modifyEntity(entity);
        return R.ok(service.save(entity));
    }


    /**
     * 根据ID删除一个对象
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public R<Boolean> delete(@PathVariable("id") String id) {
        return R.ok(service.removeById(id));
    }

    /**
     * 根据ID获取对象
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public R<Entity> get(@PathVariable("id") String id) {
        return R.ok(service.getById(id));
    }

    /**
     * 更新一个对象
     *
     * @param entity
     * @return
     */
    @PutMapping(value = "/{id}")
    public R<Boolean> update(Entity entity, @PathVariable("id") String id) {
        entity.setObjectId(id);
        modifyEntity(entity);
        return R.ok(service.updateById(entity));
    }

    protected void modifyEntity(Entity entity) {

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
        long size = 50;

        if (!StringUtils.isEmpty(pageStr)) {
            if ("all".equalsIgnoreCase(pageStr)) {
                size = Long.MAX_VALUE;
            } else {
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
}
