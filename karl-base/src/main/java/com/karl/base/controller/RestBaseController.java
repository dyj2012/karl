package com.karl.base.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.karl.base.constants.ErrorCodeConstants;
import com.karl.base.exception.BaseException;
import com.karl.base.model.BaseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Rest 风格controller基础类
 *
 * @author 杜永军
 * @date 2020/05/24
 */
public abstract class RestBaseController<Entity extends BaseEntity> {

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
                String columnName = split[0].replaceAll("[A-Z]", "_$0").toUpperCase();
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
                        throw new BaseException("无效的查询条件参数", ErrorCodeConstants.ORDER_ARG);
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
                String columnName = split[0].replaceAll("[A-Z]", "_$0").toUpperCase();
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
                        throw new BaseException("无效的排序参数", ErrorCodeConstants.ORDER_ARG);
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
                        throw new BaseException("无效的分页参数", 1001);
                }
            }

        }
        Page<Entity> page = new Page<>(current, size, isSearchCount);
        page.setOrders(orders);
        return page;
    }

    private void dealSelectField(QueryChainWrapper<Entity> w, String field) {
        if (!StringUtils.isEmpty(field)) {
            String[] fields = field.replaceAll("[A-Z]", "_$0").toUpperCase().split(",");
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
}
