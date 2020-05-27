package com.karl.core.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.karl.core.user.api.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Think
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}