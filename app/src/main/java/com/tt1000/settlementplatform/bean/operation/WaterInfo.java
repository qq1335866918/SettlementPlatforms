package com.tt1000.settlementplatform.bean.operation;

/**
 * 流水
 */
public class WaterInfo {
    String personCount;
    String totalPrice;

    public WaterInfo() {

    }

    public WaterInfo(String personCount, String totalPrice) {
        this.personCount = personCount;
        this.totalPrice = totalPrice;
    }

    public String getPersonCount() {
        return personCount;
    }

    public void setPersonCount(String personCount) {
        this.personCount = personCount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
