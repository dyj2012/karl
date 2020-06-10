package com.karl.base.util;

import org.slf4j.Logger;

import java.util.function.Supplier;

/**
 * 打印方法进出日志包装工具类
 *
 * @author 杜永军
 * @date 2020年5月8日 下午2:04:33
 */
public final class Log {

    /**
     * 进入某个方法
     */
    public static final String LOG_ENTER_METHOD = "ENTER [{}] METHOD";

    /**
     * 离开某个方法
     */
    public static final String LOG_LEAVE_METHOD = "LEAVE [{}] METHOD, EXECUTE [{}] MILLISECONDS";

    public static <T> T p(Logger logger, String m, Supplier<T> c) {
        logger.debug(LOG_ENTER_METHOD, m);
        long s = System.currentTimeMillis();
        try {
            return c.get();
        } finally {
            long e = System.currentTimeMillis() - s;
            logger.debug(LOG_LEAVE_METHOD, m, e);
        }
    }

    public static void p(Logger logger, String m, Runnable c) {
        logger.debug(LOG_ENTER_METHOD, m);
        long s = System.currentTimeMillis();
        try {
            c.run();
        } finally {
            long e = System.currentTimeMillis() - s;
            logger.debug(LOG_LEAVE_METHOD, m, e);
        }
    }

    public void test() {
//        return L.p(log,"method",()->{
//
//        });
    }
}
