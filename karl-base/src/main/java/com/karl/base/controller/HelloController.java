package com.karl.base.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * hello
 */
@Api(tags = "HELLO CONTROLLER")
@RestController
public class HelloController {


    @Value("${server.servlet.context-path:null}")
    private String contextPath;

    @RequestMapping(value = "/")
    public void homePage(HttpServletRequest request, HttpServletResponse response) {
        String contextPathStr = contextPath;
        if ("null".equals(contextPath)) {
            contextPathStr = "";
        }
        try {
            response.sendRedirect(String.format("%s/doc.html", contextPathStr));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
