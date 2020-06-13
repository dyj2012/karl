package com.karl.core.user.service;

import com.google.common.collect.Lists;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 用户认证服务
 *
 * @author karl
 * @date 2020/06/13
 */
@Component
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private static final GrantedAuthority ADMIN = new SimpleGrantedAuthority("ADMIN");
    private static final GrantedAuthority USER = new SimpleGrantedAuthority("USER");

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> list = Lists.newArrayList();
        String password = new BCryptPasswordEncoder().encode("123");
        return new User(username, password, Arrays.asList(ADMIN, USER));
    }
}
