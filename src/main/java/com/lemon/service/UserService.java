package com.lemon.service;

import com.lemon.dataobject.UserDO;
import com.lemon.error.BusinessException;
import com.lemon.service.model.UserModel;

/**
 * @author lemonsun
 */
public interface UserService {

    //通过用户ID 获取用户对象的方法
    UserModel getUserById(Integer id);

    //用户注册
    void register(UserModel userModel) throws BusinessException;
}
