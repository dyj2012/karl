package com.karl.base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MybatisPlus自动写入参数配置
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 自动插入创建时间到create_time字段
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createBy", "System", metaObject);
    }

    /**
     * 自动插入更新时间到update_time字段
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

}