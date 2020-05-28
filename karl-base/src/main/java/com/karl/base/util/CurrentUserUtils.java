/**
 * 元年软件
 *
 * @author 王亚平
 * @date 2019年2月25日 下午3:33:10
 * @version V1.0
 */
package com.karl.base.util;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 当前登录人信息工具类
 * @author Think
 */
public class CurrentUserUtils {

    private static ThreadLocal<Object> LOGIN_SESSION_KEY = new TransmittableThreadLocal<>();

    public static void putUser(Object user) {
        LOGIN_SESSION_KEY.set(user);
    }

    /**
     * 清除线程变量值
     */
    public static void remove() {
        LOGIN_SESSION_KEY.remove();
    }

    /**
     * 获取当前登陆人的授权主题对象
     *
     * @return
     */
    public static Object getUser() {
        return LOGIN_SESSION_KEY.get();
    }

}
