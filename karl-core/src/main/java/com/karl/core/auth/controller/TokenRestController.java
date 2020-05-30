package com.karl.core.auth.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Think
 */
@RestController
@RequestMapping("/tokens")
public class TokenRestController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public R<String> login(HttpServletRequest request) {

        return R.ok("123");
    }


}