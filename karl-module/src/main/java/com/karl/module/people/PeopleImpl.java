package com.karl.module.people;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karl.module.people.api.PeopleService;
import com.karl.module.people.api.entity.PeopleEntity;
import com.karl.module.people.mapper.PeopleMapper;
import org.springframework.stereotype.Service;

/**
 * demo 服务实现类
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Service
public class PeopleImpl extends ServiceImpl<PeopleMapper, PeopleEntity> implements PeopleService {
}
