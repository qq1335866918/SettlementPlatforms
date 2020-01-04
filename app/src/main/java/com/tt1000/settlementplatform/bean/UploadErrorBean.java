package com.tt1000.settlementplatform.bean;

public class UploadErrorBean {

    /**
     * msg : 请求成功
     * code : 0000
     * success : true
     */

    private String msg;
    private String code;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
