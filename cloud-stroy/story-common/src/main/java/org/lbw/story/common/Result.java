package org.lbw.story.common;

public class Result <T>{

    public static final int CODE_OK = 200;//成功
    public static final int CODE_ERR_BUSINESS = 500;//业务失败
    public static final int CODE_ERR_UNLOGIN = 520;//未登录
    public static final int CODE_ERR_SYS = 530;//系统错误

    public static <T> Result<T> OK(){
        return new Result<>(true,CODE_OK,null,null);
    }
    public static <T> Result<T> OK(String message){
        return new Result<>(true,CODE_OK,message,null);
    }
    public static <T> Result<T> OK(String message,T data){
        return new Result<>(true,CODE_OK,message,data);
    }
    public static <T> Result<T> OK(T data){
        return new Result<>(true,CODE_OK,null,data);
    }

    public static <T> Result<T> err(int errCode,String message){
        return new Result<>(false,errCode,message,null);
    }
     public static <T> Result<T> err(int errCode,String message,T data){
        return new Result<>(false,errCode,message,data);
    }

    private boolean success;//操作是否成功
    private int code;//操作状态码
    private String message;//概要信息
    private T data;//服务端向前端传送信息的额外数据

    private Result(boolean success, int code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = (T) data;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
