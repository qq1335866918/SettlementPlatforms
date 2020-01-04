package com.tt1000.settlementplatform.bean.statistics;

public class StatisticsLeakage {
    private String date;
    private String nonPayment;
    private float amount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNonPayment() {
        return nonPayment;
    }

    public void setNonPayment(String nonPayment) {
        this.nonPayment = nonPayment;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
