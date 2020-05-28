package com.karl.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 服务异常类
 *
 * @author karl
 * @update 2018/12/28 10:45
 * @since 2018/12/6 10:00
 */
@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private int code;
    private String message;
    private Object data;

    public BaseException(int code, String message) {
        this.code = code;
        this.message = message;
    }

}