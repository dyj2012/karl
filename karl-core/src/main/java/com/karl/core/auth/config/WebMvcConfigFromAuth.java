package com.karl.core.auth.config;

import com.karl.core.auth.api.annotation.resolver.AuthMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WebMvc配置添加Auth注解解析器
 **/
@Configuration
public class WebMvcConfigFromAuth implements WebMvcConfigurer {

    /**
     * 添加认证注解参数解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authMethodArgumentResolver());
    }

    /**
     * 注册认证注解参数解析器
     */
    @Bean
    public AuthMethodArgumentResolver authMethodArgumentResolver() {
        return new AuthMethodArgumentResolver();
    }

}