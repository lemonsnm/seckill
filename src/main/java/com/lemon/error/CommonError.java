package com.lemon.error;

/**
 * @author lemonsun
 */
public interface CommonError{

    int getCode();
    String getErrMsg();
    CommonError setErrMsg(String errMsg);
}
