package com.tt1000.settlementplatform.bean;

import java.util.List;

public class BaseBean<T> {

    /**
     * code : 0000
     * data : []
     * msg :
     * result : false
     * status : 200
     * type : 0
     */

    private String code;
    private String msg;
    private boolean result;
    private int status;
    private int type;
    private List<T> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
