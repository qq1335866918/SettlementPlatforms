package com.tt1000.settlementplatform.bean.operation;

public class WeiCaiWaterInfo {
    String payWay;
    String totalPrice;

    public WeiCaiWaterInfo() {

    }

    public WeiCaiWaterInfo(String payWay, String totalPrice) {
        this.payWay = payWay;
        this.totalPrice = totalPrice;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
