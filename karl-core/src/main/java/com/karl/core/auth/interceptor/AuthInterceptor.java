package com.karl.core.auth.interceptor;

import com.karl.base.util.CurrentUser;
import com.karl.base.util.CurrentUserUtils;
import com.karl.core.auth.api.constants.AuthConstants;
import com.karl.core.auth.api.enums.TokenSubject;
import com.karl.core.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * auth 验证拦截器
 *
 * @author 杜永军
 * @date 2020/05/30
 */
public class AuthInterceptor implements HandlerInterceptor {

    private static final String LOGIN_TOKEN = "LOGIN_TOKEN";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String loginToken = request.getHeader(LOGIN_TOKEN);
        if (StringUtils.isBlank(loginToken)) {
            loginToken = request.getParameter(LOGIN_TOKEN);
        }
        if(StringUtils.isBlank(loginToken)){
            return false;
        }
        Jws<Claims> parse = JwtUtils.parse(TokenSubject.ACCESS.toString(), loginToken);
        CurrentUserUtils.putUser(new CurrentUser() {
            @Override
            public String getName() {
                return (String) parse.getBody().get(AuthConstants.NAME);
            }

            @Override
            public String getId() {
                return (String) parse.getBody().get(AuthConstants.ID);
            }
        });
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        CurrentUserUtils.remove();
    }
}
