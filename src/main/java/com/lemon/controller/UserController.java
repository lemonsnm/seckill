package com.lemon.controller;

import com.lemon.controller.viewobject.UserVO;
import com.lemon.error.BusinessException;
import com.lemon.error.EmBusinessError;
import com.lemon.response.CommonReturnType;
import com.lemon.service.UserService;
import com.lemon.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lemonsun
 */
@Controller("user")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id")Integer id) throws BusinessException {
        //调取service服务获取对应id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);
        //若获取的对应用户信息不存在
        if(userModel == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        //将核心领域模型用户对象转换为可供UI使用的viewobject
        UserVO userVO = convertFromModel(userModel);
        //返回通用对象
        return CommonReturnType.create(userVO);
    }

    //将UserModel转换为UserDO  保证信息安全
    private UserVO convertFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }

    //定义exceptionhandler解决 未被controller层吸收的exception
    @ExceptionHandler(Exception.class)  //定义收到什么样的exception之后，才会进入它的处理环节
    @ResponseStatus(HttpStatus.OK)  // 返回http status 200
    public Object handlerException(HttpServletRequest request,Exception ex){
        /*handlerException仅仅只能返回页面的路径 无法返回业务处理类返回的ResponseBody这种形式 还需要解决一层问题*/
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setStatus("fail");
        commonReturnType.setData(ex);
        return commonReturnType;
    }
}
