package com.tt1000.settlementplatform.bean;

public class ThirdPartyPayInfo {


    /**
     * clientCode : 153267599744410
     * storeCode : 0000
     * authCode : 282954151282015252
     * requestNum : 15330167759091361506
     * transamt : 0.10
     */

    private String clientCode;
    private String storeCode;
    private String authCode;
    private String requestNum;
    private String transamt;

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getRequestNum() {
        return requestNum;
    }

    public void setRequestNum(String requestNum) {
        this.requestNum = requestNum;
    }

    public String getTransamt() {
        return transamt;
    }

    public void setTransamt(String transamt) {
        this.transamt = transamt;
    }
}
