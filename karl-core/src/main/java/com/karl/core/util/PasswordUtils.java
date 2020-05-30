package com.karl.core.util;

import com.karl.base.util.MD5Utils;

/**
 * 密码工具类
 *
 * @author 杜永军
 * @date 2020/05/30
 */
public class PasswordUtils {

    public static String encryption(String password) {
        return MD5Utils.encodeByMd5(password);
    }
}
