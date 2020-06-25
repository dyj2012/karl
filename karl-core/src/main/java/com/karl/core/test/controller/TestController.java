package com.karl.core.test.controller;

import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONUtil;
import com.karl.base.annotation.LogModule;
import com.karl.base.annotation.OperationLog;
import com.karl.base.util.Log;
import com.karl.core.entity.TestEntity;
import com.karl.core.entity.TestJsonEntity;
import com.karl.core.entity.TestZipEntity;
import com.karl.core.test.mapper.TestJsonMapper;
import com.karl.core.test.mapper.TestMapper;
import com.karl.core.test.mapper.TestZipMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author karl
 */
@RestController
@RequestMapping("/test")
@Api(tags = "test接口")
@LogModule("test")
@Slf4j
public class TestController {

    @Autowired
    private TestJsonMapper testJsonMapper;
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private TestZipMapper testZipMapper;

    /**
     * 新增一个对象
     *
     * @return
     */
    @ApiOperation(value = "列表查询接口", notes = "可以通过参数{query,field,page,orderBy}进行条件查询")
    @GetMapping("/test1")
    @OperationLog("列表查询")
    @SneakyThrows
    public String test1() {
        for (int i = 0; i < 2; i++) {
            List<TestEntity> testEntities = buildList();
            Log.p(log, "testMapper", () -> {
                testMapper.mysqlBatchInsert(testEntities);
            });
            List<TestJsonEntity> jsonList = testEntities.stream().map(a -> {
                TestJsonEntity e = new TestJsonEntity();
                e.setJson(JSONUtil.toJsonStr(a));
                return e;
            }).collect(Collectors.toList());
            Log.p(log, "testJsonMapper", () -> {
                testJsonMapper.mysqlBatchInsert(jsonList);
            });

            List<TestZipEntity> gzipList = testEntities.stream().map(a -> {
                TestZipEntity e = new TestZipEntity();
                e.setZip(ZipUtil.gzip(JSONUtil.toJsonStr(a), "UTF-8"));
                return e;
            }).collect(Collectors.toList());
            Log.p(log, "testZipMapper", () -> {
                testZipMapper.mysqlBatchInsert(gzipList);
            });
        }
        return "test";
    }

    private List<TestEntity> buildList() {
        ArrayList<TestEntity> testEntities = new ArrayList<>(10000);
        for (int i = 0; i < 10000; i++) {
            TestEntity testEntity = new TestEntity();
            testEntity.setName1(UUID.randomUUID().toString());
            testEntity.setName2(UUID.randomUUID().toString());
            testEntity.setName3(UUID.randomUUID().toString());
            testEntity.setName4(UUID.randomUUID().toString());
            testEntity.setName5(UUID.randomUUID().toString());
            testEntity.setName6(UUID.randomUUID().toString());
            testEntity.setName6(UUID.randomUUID().toString());
            testEntity.setName7(UUID.randomUUID().toString());
            testEntity.setName8(UUID.randomUUID().toString());
            testEntity.setName9(UUID.randomUUID().toString());
            testEntity.setName10(UUID.randomUUID().toString());
            testEntity.setName11(UUID.randomUUID().toString());
            testEntity.setName12(UUID.randomUUID().toString());
            testEntity.setName13(UUID.randomUUID().toString());
            testEntity.setName14(UUID.randomUUID().toString());
            testEntity.setName15(UUID.randomUUID().toString());
            testEntity.setName16(UUID.randomUUID().toString());
            testEntity.setName17(UUID.randomUUID().toString());
            testEntity.setName18(UUID.randomUUID().toString());
            testEntity.setName19(UUID.randomUUID().toString());
            testEntity.setName20(UUID.randomUUID().toString());
            testEntity.setName21(UUID.randomUUID().toString());
            testEntities.add(testEntity);
        }
        return testEntities;
    }
}