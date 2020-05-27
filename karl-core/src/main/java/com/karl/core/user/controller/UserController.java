package com.karl.core.user.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.karl.base.controller.RestBaseController;
import com.karl.core.user.api.UserService;
import com.karl.core.user.api.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Think
 */
@RestController
@RequestMapping("/users")
public class UserController extends RestBaseController<UserEntity> {
    @Autowired
    private UserService userService;

    @Override
    protected IService<UserEntity> getService() {
        return userService;
    }


    /**
     * 根据ID获取对象
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/aa/{id}", method = RequestMethod.GET)
    public Object get22(@PathVariable("id") String id) {
        return userService.getById(id);
    }


}