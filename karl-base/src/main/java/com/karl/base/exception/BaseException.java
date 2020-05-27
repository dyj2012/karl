package com.karl.base.exception;

import lombok.Getter;

/**
 * 服务异常类
 *
 * @author karl
 * @update 2018/12/28 10:45
 * @since 2018/12/6 10:00
 */
@Getter
public class BaseException extends RuntimeException {

    private int code;
    private String message;
    private Object data;

    public BaseException(Object data, String message) {
        this.code = 400;
        this.message = message;
        this.data = data;
    }

    public BaseException(Object data, String message, int code) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseException(String message) {
        this.code = 400;
        this.message = message;
    }

    public BaseException(String message, int code) {
        this.code = code;
        this.message = message;
    }

}