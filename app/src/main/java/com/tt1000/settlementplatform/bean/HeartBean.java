package com.tt1000.settlementplatform.bean;

public class HeartBean {


    /**
     * code : 0000
     * data : {"cmd":"0000","systemTime":"2019-12-07 13:45:36"}
     * msg : 获取系统时间成功
     * result : true
     */

    private String code;
    private DataBean data;
    private String msg;
    private boolean result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * cmd : 0000
         * systemTime : 2019-12-07 13:45:36
         */

        private String cmd;
        private String systemTime;

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public String getSystemTime() {
            return systemTime;
        }

        public void setSystemTime(String systemTime) {
            this.systemTime = systemTime;
        }
    }
}
