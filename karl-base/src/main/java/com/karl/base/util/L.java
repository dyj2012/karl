/**
 * 元年软件
 *
 * @author xiaohuzi
 * @date 2018年4月17日 下午3:02:44
 * @version V1.0
 */
package com.karl.base.util;

import org.slf4j.Logger;

import java.util.function.Supplier;

/**
 * 打印方法进出日志包装工具类
 *
 * @author 杜永军
 * @date 2020年5月8日 下午2:04:33
 */
public final class L {

    /**
     * 进入某个方法
     */
    public static final String LOG_ENTER_METHOD = "ENTER [{}] METHOD";

    /**
     * 离开某个方法
     */
    public static final String LOG_LEAVE_METHOD = "LEAVE [{}] METHOD";

    public static <T> T l(Logger l, String m, Supplier<T> c) {
        l.debug(LOG_ENTER_METHOD, m);
        try {
            return c.get();
        } finally {
            l.debug(LOG_LEAVE_METHOD, m);
        }
    }

    public static void l(Logger l, String m, Runnable c) {
        l.debug(LOG_ENTER_METHOD, m);
        try {
            c.run();
        } finally {
            l.debug(LOG_LEAVE_METHOD, m);
        }
    }

    public void test() {
//        return L.l(log,"method",()->{
//
//        });
    }
}
