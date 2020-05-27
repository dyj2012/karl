package com.karl.core.user.exception;

import com.karl.base.exception.BaseException;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(10404, "用户不存在");
    }
}