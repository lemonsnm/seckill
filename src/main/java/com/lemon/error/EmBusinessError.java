package com.lemon.error;

/**
 * @author lemonsun
 */
public enum EmBusinessError implements  CommonError {
    //通用错误类型00001 (邮箱，验证码等等错误)
    PARAMETER_VALIDATION_ERROR(00001,"参数不合法"),
    //10000开头为用户信息相关错误定义
    USER_NOT_EXIST(10001,"用户不存在")
    ;

    private EmBusinessError(int errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;
     
    @Override
    public int getCode() {
        return errCode;
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }

    //定制化的方式改动errMsg信息
    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
