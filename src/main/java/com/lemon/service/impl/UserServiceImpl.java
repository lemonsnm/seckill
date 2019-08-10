package com.lemon.service.impl;

import com.lemon.dao.UserDOMapper;
import com.lemon.dao.UserPasswordDOMapper;
import com.lemon.dataobject.UserDO;
import com.lemon.dataobject.UserPasswordDO;
import com.lemon.error.BusinessException;
import com.lemon.error.EmBusinessError;
import com.lemon.service.UserService;
import com.lemon.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;

/**
 * @author lemonsun
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;


    @Override
    public UserModel getUserById(Integer id) {
        //调用userDOMapper获取到对应的用户dataobject
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if(userDO == null){
            return null;
        }
        //通过用户id获取对应的用户加密密码信息
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());

        return convertFromDataObject(userDO,userPasswordDO);
    }

    //用户注册
    @Override
    @Transactional  //事务提交 多个sql为一个事务
    public void register(UserModel userModel) throws BusinessException{
        if(userModel == null){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if(StringUtils.isEmpty(userModel.getName())
                || userModel.getGender() == null
                || userModel.getAge() == null
                || StringUtils.isEmpty(userModel.getTelphone())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR); 
        }

        //实现model转成dataobject方法
        UserDO userDO = convertFromModel(userModel);
        userDOMapper.insertSelective(userDO);
        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
    }

    //UserPasswordDO转UserDO
    private UserPasswordDO convertPasswordFromModel(UserModel userModel){
        if(userModel == null){
            return  null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userPasswordDO.getEncrptPassword());
        userPasswordDO.setId(userModel.getId());
        
        return userPasswordDO;
    }

    //UserModel 转 UserDO
    private UserDO convertFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }


    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){

        if(userDO == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);

        if(userPasswordDO != null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        
        return userModel;
    }
    
}
