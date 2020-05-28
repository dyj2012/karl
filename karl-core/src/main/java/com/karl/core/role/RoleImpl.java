package com.karl.core.role;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karl.core.role.api.RoleService;
import com.karl.core.role.api.entity.RoleEntity;
import com.karl.core.role.mapper.RoleMapper;

/**
 * 角色 服务实现类
 *
 * @author 杜永军
 * @date 2020/5/25
 */
public class RoleImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {
}