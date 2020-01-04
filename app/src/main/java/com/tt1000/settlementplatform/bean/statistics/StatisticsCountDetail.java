package com.tt1000.settlementplatform.bean.statistics;

public class StatisticsCountDetail {
    private float priceWechat;
    private int numWechat;
    private float priceAlipay;
    private int numAlipay;
    private float priceMember;
    private int numMember;
    private float priceCash;
    private String mealTimes;
    private String mealTimesName;

    public String getMealTimesName() {
        return mealTimesName;
    }

    public void setMealTimesName(String mealTimesName) {
        this.mealTimesName = mealTimesName;
    }

    public String getMealTimes() {
        return mealTimes;
    }

    public void setMealTimes(String mealTimes) {
        this.mealTimes = mealTimes;
    }

    public float getPriceWechat() {
        return priceWechat;
    }

    public void setPriceWechat(float priceWechat) {
        this.priceWechat = priceWechat;
    }

    public int getNumWechat() {
        return numWechat;
    }

    public void setNumWechat(int numWechat) {
        this.numWechat = numWechat;
    }

    public float getPriceAlipay() {
        return priceAlipay;
    }

    public void setPriceAlipay(float priceAlipay) {
        this.priceAlipay = priceAlipay;
    }

    public int getNumAlipay() {
        return numAlipay;
    }

    public void setNumAlipay(int numAlipay) {
        this.numAlipay = numAlipay;
    }

    public float getPriceMember() {
        return priceMember;
    }

    public void setPriceMember(float priceMember) {
        this.priceMember = priceMember;
    }

    public int getNumMember() {
        return numMember;
    }

    public void setNumMember(int numMember) {
        this.numMember = numMember;
    }

    public float getPriceCash() {
        return priceCash;
    }

    public void setPriceCash(float priceCash) {
        this.priceCash = priceCash;
    }

    public int getNumCash() {
        return numCash;
    }

    public void setNumCash(int numCash) {
        this.numCash = numCash;
    }

    private int numCash;
}
