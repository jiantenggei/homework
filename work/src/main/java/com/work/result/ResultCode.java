package com.work.result;

public enum ResultCode { //枚举请求结果类型
    SUCCESS(200),//网页请求代码200表示成功
    FAIL(400),//失败
    UNAUTHORIZED(401),//未授权
    NOT_FOUNT(404),
    INTERNAL_SERVER_ERROR(500);
    public int code;
    ResultCode(int code){
        this.code =code;
    }

}
