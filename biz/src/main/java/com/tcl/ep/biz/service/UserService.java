package com.tcl.ep.biz.service;

import com.tcl.ep.persistence.entity.UserInfo;

/**
 * Created by panmin on 16-11-26.
 */
public interface UserService {
    /**
     * 通过用户名查询用户信息
     * @param userName
     * @return
     */
    UserInfo selectByUserName(String userName);
}
