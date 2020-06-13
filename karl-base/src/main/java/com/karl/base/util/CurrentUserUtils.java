package com.karl.base.util;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 当前登录人信息工具类
 *
 * @author karl
 */
public class CurrentUserUtils {

    private static ThreadLocal<CurrentUser> LOGIN_SESSION_KEY = new TransmittableThreadLocal<>();

    public static void putUser(CurrentUser user) {
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
    public static CurrentUser getUser() {
        return LOGIN_SESSION_KEY.get();
    }

}
