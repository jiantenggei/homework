package com.work.result;

/**
 * @author jian
 * @date 2020/8/3
 */
public class ResultFactory {
    //执行成功时返回的结果
    public static Result buildSuccessResult(Object data){
        return buildResult(ResultCode.SUCCESS,"成功",data);
    }
    //执行失败
    public static Result buildFailResult(String message){
        return  buildResult(ResultCode.FAIL,message,null);
    }
    //构建自定义返回内容
    public static Result bulildResult(ResultCode resultCode, String message, Object data){
        return buildResult(resultCode,message,data);
    }
    public  static Result buildResult(ResultCode resultCode, String message, Object data){
        return  new Result(resultCode,message,data);
    }
}
