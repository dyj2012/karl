package com.karl.core.auth.exception;

import com.karl.base.exception.BaseException;

/**
 * token 异常
 *
 * @author karl
 * @date 2020/05/30
 */
public class TokenException extends BaseException {

    public TokenException() {
        super(2001, "token解析失败");
    }

}
