package com.karl.module.demo.exception;

import com.karl.base.exception.BaseException;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2020/5/25
 */
public class DemoException extends BaseException {
    public DemoException(Object data, String message) {
        super(data, message);
    }

    public DemoException(Object data, String message, int code) {
        super(data, message, code);
    }

    public DemoException(String message) {
        super(message);
    }

    public DemoException(String message, int code) {
        super(message, code);
    }
}
