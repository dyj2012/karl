package com.karl.core.user.service;

import com.karl.core.entity.RoleEntity;
import com.karl.core.entity.UserEntity;
import com.karl.core.entity.UserRoleEntity;
import com.karl.core.role.RoleServer;
import com.karl.core.user.UserRoleServer;
import com.karl.core.user.UserServer;
import com.karl.core.user.constants.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户认证服务
 *
 * @author karl
 * @date 2020/06/13
 */
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserServer userServer;
    @Autowired
    private UserRoleServer userRoleServer;
    @Autowired
    private RoleServer roleServer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.notNull(username, "登录名不可为空!");
        if (UserConstants.USER_ADMIN_NAME.equals(username)) {
            return new User(UserConstants.USER_ADMIN_NAME, UserConstants.USER_ADMIN_PASSWORD, Collections.singletonList(UserConstants.USER));
        }
        UserEntity user = userServer.lambdaQuery()
                .eq(UserEntity::getLoginName, username).oneOpt()
                .orElseThrow(() -> new UsernameNotFoundException(String.format("未找到%s用户信息", username)));
        List<UserRoleEntity> userRoleList = userRoleServer.lambdaQuery()
                .eq(UserRoleEntity::getUserId, user.getObjectId()).list();
        if (CollectionUtils.isEmpty(userRoleList)) {
            return new User(username, user.getPassword(), Collections.singletonList(UserConstants.USER));
        }
        List<String> roleIdList = userRoleList.stream().map(UserRoleEntity::getRoleId).collect(Collectors.toList());
        List<RoleEntity> roleList = roleServer.lambdaQuery().in(RoleEntity::getObjectId, roleIdList).list();
        List<String> roleCodeList = roleList.stream().map(RoleEntity::getCode).collect(Collectors.toList());
        List<GrantedAuthority> authorityList = roleCodeList.stream().map(code -> {
            switch (code) {
                case "ADMIN":
                    return UserConstants.ADMIN;
                default:
                    return UserConstants.USER;
            }
        }).collect(Collectors.toList());
        return new User(username, user.getPassword(), authorityList);
    }
}
