package com.example.springboot.shiro.vo;

public class BaseResponse<T> {
    private int code = 200;
    private String message = "SUCCESS";
    protected T result;

    public BaseResponse() {
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(int code, String message, T result) {
        this(code, message);
        this.result = result;
    }

    public BaseResponse(T result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
