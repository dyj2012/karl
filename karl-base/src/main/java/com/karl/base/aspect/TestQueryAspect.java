/**
 * 元年软件
 */
package com.karl.base.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 多语言按名称模糊查询
 */
@Aspect
@Component
public class TestQueryAspect {
    /**
     * 初始化标识
     */
    private static final String INIT = "init";
    @Autowired
    private PlatformTransactionManager transaction;

    /**
     * 监控所有DAO层的get和select方法
     * 定义切面点
     */
    @Pointcut("execution(* com.karl..*Mapper.get*(..))||execution(* com.karl..*Mapper.select*(..))")
    public void i18nStringCorrect() {
    }


    /**
     * @param joinPoint 切面点-JoinPoint
     */
    @Around("i18nStringCorrect()")
    public Object aroundDaoQuery(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(11111);
        return joinPoint.proceed();

    }
}
