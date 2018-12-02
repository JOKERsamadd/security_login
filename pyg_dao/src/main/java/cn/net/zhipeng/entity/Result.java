package cn.net.zhipeng.entity;

import java.io.Serializable;

public class Result implements Serializable {

    private static final long serialVersionUID = 3547879651719074510L;
    private boolean success;  //前端根据success做判断
    private String message;   //错误信息

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
