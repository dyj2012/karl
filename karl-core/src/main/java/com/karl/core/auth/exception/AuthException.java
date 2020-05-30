package com.karl.core.auth.exception;

import com.karl.base.exception.BaseException;

/**
 * token 异常
 *
 * @author 杜永军
 * @date 2020/05/30
 */
public class AuthException extends BaseException {

    public AuthException() {
        super(2002, "账号或者密码错误");
    }

}
