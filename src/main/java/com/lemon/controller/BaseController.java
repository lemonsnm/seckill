package com.lemon.controller;

import com.lemon.error.BusinessException;
import com.lemon.error.EmBusinessError;
import com.lemon.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lemonsun
 */
public class BaseController {

    //定义exceptionhandler解决 未被controller层吸收的exception
    @ExceptionHandler(Exception.class)  //定义收到什么样的exception之后，才会进入它的处理环节
    @ResponseStatus(HttpStatus.OK)  // 返回http status 200
    @ResponseBody
    /* 寻找本地路径下的页面文件*/
    public Object handlerException(HttpServletRequest request, Exception ex){
        /*handlerException仅仅只能返回页面的路径 无法返回业务处理类返回的ResponseBody这种形式 还需要解决一层问题*/
        Map<String,Object> responseData = new HashMap<>();
        if(ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex;
            responseData.put("errCode",businessException.getCode());
            responseData.put("errMsg",businessException.getErrMsg());
        }else{
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getCode());
            responseData.put("errMsg",EmBusinessError.UNKNOWN_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseData,"fail");

    }
}
