package com.tcl.ep.biz.service.impl;

import com.tcl.ep.biz.service.UserService;
import com.tcl.ep.persistence.dao.UserDao;
import com.tcl.ep.persistence.entity.UserInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by panmin on 16-11-26.
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public UserInfo selectByUserName(String userName) {
        return userDao.selectByUserName(userName);
    }
}
