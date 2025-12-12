package com.web.strictteach.exception;

public class selfDefineException extends RuntimeException {
    private String msg;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public selfDefineException(String msg) {
        this.msg = msg;
    }
}
