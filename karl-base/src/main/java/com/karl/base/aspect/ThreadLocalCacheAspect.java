package com.karl.base.aspect;

import com.karl.base.model.CacheNull;
import com.karl.base.util.ThreadLocalUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author karl
 * @date 2018年12月29日 上午11:07:28
 */
@Aspect
@Component
public class ThreadLocalCacheAspect {

    @Pointcut("@annotation(com.karl.base.annotation.ThreadLocalCache)")
    public void threadLocalCache() {
    }

    private static final String INIT = "init";

    public static void init() {
        ThreadLocalUtils.set(ThreadLocalCacheAspect.class, INIT, Boolean.TRUE);
        ;
    }

    private static boolean isInit() {
        return ThreadLocalUtils.get(ThreadLocalCacheAspect.class, INIT) == Boolean.TRUE;
    }

    @Around("threadLocalCache()")
    public Object aroundThreadLocalCache(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!isInit()) {
            return joinPoint.proceed();
        }
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;
        Object target = joinPoint.getTarget();
        Class<?> targetClass = target.getClass();
        Object[] args = joinPoint.getArgs();
        // key为方法名_参数1_参数2
        StringBuilder keyValueSb = new StringBuilder(methodSignature.getName());
        if (args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof Collection) {
                    for (Object o : (Collection) arg) {
                        keyValueSb.append("_").append(o);
                    }
                } else {
                    keyValueSb.append("_").append(arg);
                }
            }
        }
        String keyValue = keyValueSb.toString();
        Object value = ThreadLocalUtils.get(targetClass, keyValue);
        if (value == null) {
            value = joinPoint.proceed();
            if (value == null) {
                ThreadLocalUtils.set(targetClass, keyValue, CacheNull.INSTANCE);
            } else {
                ThreadLocalUtils.set(targetClass, keyValue, value);
            }
        } else if (value == CacheNull.INSTANCE) {
            return null;
        }
        return value;
    }
}
