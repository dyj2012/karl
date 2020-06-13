package com.karl.core.user.constants;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 用户常理
 *
 * @author 杜永军
 * @date 2020/06/13
 */
public class UserConstants {

    public static final PasswordEncoder PASSWORD = new BCryptPasswordEncoder();
    public static final String USER_ADMIN_NAME = "admin";

    public static final String USER_ADMIN_PASSWORD = PASSWORD.encode("123");

    public static final GrantedAuthority ADMIN = new SimpleGrantedAuthority("ADMIN");

    public static final GrantedAuthority USER = new SimpleGrantedAuthority("USER");
}
