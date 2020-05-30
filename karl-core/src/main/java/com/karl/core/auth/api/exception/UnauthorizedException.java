package com.karl.core.auth.api.exception;

import com.karl.base.exception.BaseException;

/**
 * 未认证异常
 *
 * @author 杜永军
 */
public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
        super(401, "Unauthorized");
    }
}
