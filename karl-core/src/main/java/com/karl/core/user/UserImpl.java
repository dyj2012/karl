package com.karl.core.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karl.core.user.api.UserService;
import com.karl.core.entity.UserEntity;
import com.karl.core.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author Think
 */
@Service
public class UserImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {


}
