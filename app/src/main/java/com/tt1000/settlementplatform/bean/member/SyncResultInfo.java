package com.tt1000.settlementplatform.bean.member;

import java.util.List;

public class SyncResultInfo<T> {

    /**
     * result : true
     * status : 200
     * type : 0
     * msg : 获取系统时间成功
     * data :
     */

    private boolean result;
    private int status;
    private int type;
    private String msg;
    private List<T> data;

    public boolean getResult() {
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
