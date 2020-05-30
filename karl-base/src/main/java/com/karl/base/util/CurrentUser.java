package com.karl.base.util;

/**
 * 当前用户信息
 *
 * @author 杜永军
 * @date 2020/05/30
 */
public interface CurrentUser {

    /**
     * 获取用户名称
     *
     * @return
     */
    String getName();

    /**
     * 获取当前用户ID
     *
     * @return
     */
    String getId();
}
