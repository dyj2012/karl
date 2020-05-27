package com.karl.core.auth.api.exception;

/**
 * Token异常
 *
 * @author Li Jinhui
 * @since 2018/12/7
 */
public class TokenException extends RuntimeException {
    public TokenException() {
        super("False token");
    }
}
