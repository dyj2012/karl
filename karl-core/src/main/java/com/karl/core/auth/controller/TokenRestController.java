package com.karl.core.auth.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.karl.core.auth.api.AuthService;
import com.karl.core.auth.api.vo.SignInData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Think
 */
@RestController
@RequestMapping("/auth")
@Api(tags = "权限接口")
public class TokenRestController {

    @Autowired
    AuthService authService;


    /**
     * 登录
     *
     * @param sign
     * @return
     */
    @ApiOperation(value = "登录接口", notes = "系统登录,返回token")
    @GetMapping
    @PostMapping(value = "/login")
    public R<String> login(@RequestBody SignInData sign) {
        return R.ok(authService.login(sign));
    }


}