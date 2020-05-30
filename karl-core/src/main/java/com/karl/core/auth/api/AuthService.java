package com.karl.core.auth.api;

import com.karl.core.auth.api.vo.SignInData;

/**
 * Auth 服务
 *
 * @author: 杜永军
 **/
public interface AuthService {

    /**
     * 登录
     **/
    String login(SignInData signInData);
}