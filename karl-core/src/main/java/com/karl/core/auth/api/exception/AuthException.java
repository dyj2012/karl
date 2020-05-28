package com.karl.core.auth.api.exception;

import com.karl.base.exception.BaseException;

/**
 * 认证异常
 *
 * @author Li Jinhui
 * @since 2018/12/7
 */
public class AuthException extends BaseException {
    public AuthException() {
        super(403, "Authentication failed");
    }
}
