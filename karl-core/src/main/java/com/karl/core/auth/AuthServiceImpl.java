package com.karl.core.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.karl.core.auth.api.AuthService;
import com.karl.core.auth.api.constants.AuthConstants;
import com.karl.core.auth.api.enums.TokenSubject;
import com.karl.core.auth.api.vo.SignInData;
import com.karl.core.auth.exception.AuthException;
import com.karl.core.entity.UserEntity;
import com.karl.core.user.controller.UserController;
import com.karl.core.util.JwtUtils;
import com.karl.core.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * auth 服务
 *
 * @author 杜永军
 **/
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserController userService;

    @Override
    public String login(SignInData sign) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PASSWORD", PasswordUtils.encryption(sign.getPassword()));
        queryWrapper.eq("LOGIN_NAME", sign.getLoginName());
        UserEntity user = userService.getOne(queryWrapper);
        if (user == null) {
            throw new AuthException();
        }
        Map<String, Object> payload = new HashMap<>(2);
        payload.put(AuthConstants.NAME, user.getName());
        payload.put(AuthConstants.ID, user.getObjectId());
        return JwtUtils.generate(TokenSubject.ACCESS.toString(), payload, 24);
    }



}