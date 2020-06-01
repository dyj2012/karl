package com.karl.base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.karl.base.util.CurrentUser;
import com.karl.base.util.CurrentUserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MybatisPlus自动写入参数配置
 */
@Component
public class BaseMetaObjectHandler implements MetaObjectHandler {

    /**
     * 自动插入创建时间到create_time字段
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        CurrentUser user = CurrentUserUtils.getUser();
        if (user == null || StringUtils.isEmpty(user.getName())) {
            this.setFieldValByName("createBy", "system", metaObject);
            this.setFieldValByName("updateBy", "system", metaObject);
        } else {
            this.setFieldValByName("createBy", user.getName(), metaObject);
            this.setFieldValByName("updateBy", user.getName(), metaObject);
        }
    }

    /**
     * 自动插入更新时间到update_time字段
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        CurrentUser user = CurrentUserUtils.getUser();
        if (user == null || StringUtils.isEmpty(user.getName())) {
            this.setFieldValByName("updateBy", "system", metaObject);
        } else {
            this.setFieldValByName("updateBy", user.getName(), metaObject);
        }
    }

}