package com.karl.core.auth.api.exception;

import com.karl.base.exception.BaseException;

/**
 * 未认证异常
 *
 * @author Li Jinhui
 * @since 2018/12/7
 */
public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
        super("Unauthorized", 401);
    }
}
