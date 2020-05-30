package com.karl.core.auth.api.exception;

/**
 * Token异常
 *
 * @author 杜永军
 * @since 2018/12/7
 */
public class TokenException extends RuntimeException {
    public TokenException() {
        super("False token");
    }
}
