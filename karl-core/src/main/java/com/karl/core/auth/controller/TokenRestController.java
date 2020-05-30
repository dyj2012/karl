package com.karl.core.auth.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.karl.core.auth.api.AuthService;
import com.karl.core.auth.api.vo.SignInData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Think
 */
@RestController
@RequestMapping("/auth")
public class TokenRestController {

    @Autowired
    AuthService authService;


    @PostMapping(value = "/login")
    public R<String> login(SignInData sign) {
        return R.ok(authService.login(sign));
    }


}