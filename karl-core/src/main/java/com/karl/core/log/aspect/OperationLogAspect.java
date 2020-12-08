/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.karl.core.log.aspect;

import com.karl.base.annotation.LogModule;
import com.karl.base.annotation.OperationLog;
import com.karl.base.util.HttpContextUtils;
import com.karl.base.util.IPUtils;
import com.karl.core.entity.OperationLogEntity;
import com.karl.core.log.mapper.OperationLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 * @author karl
 */
@Aspect
@Component
@Slf4j
public class OperationLogAspect {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Pointcut("@annotation(com.karl.base.annotation.OperationLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveLog(point, time);

        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        OperationLogEntity logEntity = new OperationLogEntity();
        OperationLog operationLog = method.getAnnotation(OperationLog.class);
        if (operationLog != null) {
            //注解上的描述
            logEntity.setOperation(operationLog.value());
        }

        Class<?> controllerClass = joinPoint.getTarget().getClass();
        //请求的方法名
        String className = controllerClass.getName();
        String methodName = signature.getName();
        logEntity.setMethod(className + "." + methodName + "()");
        LogModule logModule = controllerClass.getAnnotation(LogModule.class);

        if (logModule != null) {
            logEntity.setModule(logModule.value());
        } else {
            log.warn("class:{},未增加LogModule注解,日志记录时无法记录操作模块!", className);
        }
//        //请求的参数
//        Object[] args = joinPoint.getArgs();
//        try {
//            if (args.length > 0) {
//                List<Object> argList = Arrays.asList(args);
//                for (int i = argList.size() - 1; i > 0; i--) {
//                    Object arg = argList.get(i);
//                    if (!(arg instanceof String)) {
//                        argList.remove(arg);
//                    }
//                }
//                if (argList.size() > 0) {
//                    String params = new Gson().toJson(argList);
//                    logEntity.setParams(params);
//                }
//            }
//        } catch (Exception ignored) {
//
//        }

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        logEntity.setClientIp(IPUtils.getIpAddr(request));
        logEntity.setExecuteTime(time);

        //保存系统日志
        operationLogMapper.insert(logEntity);
    }
}
