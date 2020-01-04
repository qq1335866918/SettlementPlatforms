package com.tt1000.settlementplatform.bean;

import java.util.List;

public class AutoUpdateBean {

    /**
     * code : 0000
     * data : [{"DOWNLOAD_URL":"http://47.107.130.237/k-occ/version/双屏安卓机/1.3/SettlementPlatform.apk","VERSION_NO":"1.3"}]
     * msg :
     * result : true
     * status : 200
     * type : 0
     */

    private String code;
    private String msg;
    private boolean result;
    private int status;
    private int type;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * DOWNLOAD_URL : http://47.107.130.237/k-occ/version/双屏安卓机/1.3/SettlementPlatform.apk
         * VERSION_NO : 1.3
         */

        private String DOWNLOAD_URL;
        private String VERSION_NO;

        public String getDOWNLOAD_URL() {
            return DOWNLOAD_URL;
        }

        public void setDOWNLOAD_URL(String DOWNLOAD_URL) {
            this.DOWNLOAD_URL = DOWNLOAD_URL;
        }

        public String getVERSION_NO() {
            return VERSION_NO;
        }

        public void setVERSION_NO(String VERSION_NO) {
            this.VERSION_NO = VERSION_NO;
        }
    }
}
