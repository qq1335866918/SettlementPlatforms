package com.tt1000.settlementplatform.bean;

public class OverView {

    private String date;
    private float cash;
    private float aliPay;
    private float weChat;
    private float memberCard;
    private float bank;
    private float free;
    private String subTotal;

    public float getBank() {
        return bank;
    }

    public void setBank(float bank) {
        this.bank = bank;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }

    public float getAliPay() {
        return aliPay;
    }

    public void setAliPay(float aliPay) {
        this.aliPay = aliPay;
    }

    public float getWeChat() {
        return weChat;
    }

    public void setWeChat(float weChat) {
        this.weChat = weChat;
    }

    public float getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(float memberCard) {
        this.memberCard = memberCard;
    }

    public float getFree() {
        return free;
    }

    public void setFree(float free) {
        this.free = free;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }
}
