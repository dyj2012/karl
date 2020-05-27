package com.karl.core.user.exception;

import com.karl.base.exception.BaseException;

public class PasswordErrorException extends BaseException {
    public PasswordErrorException() {
        super(10400, "密码错误");
    }
}