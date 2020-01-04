package com.tt1000.settlementplatform.bean.statistics;

public class StatisticsMeal {

    private String meal;
    private float total;
    private int orders;
    private float average;
    private int headcount;
    private float cash;
    private int cashTime;
    private float swipingCard;
    private int swipingCardTime;

    public float getAverage() {

        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public int getHeadcount() {
        return headcount;
    }

    public void setHeadcount(int headcount) {
        this.headcount = headcount;
    }


    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }

    public int getCashTime() {
        return cashTime;
    }

    public void setCashTime(int cashTime) {
        this.cashTime = cashTime;
    }

    public float getSwipingCard() {
        return swipingCard;
    }

    public void setSwipingCard(float swipingCard) {
        this.swipingCard = swipingCard;
    }

    public int getSwipingCardTime() {
        return swipingCardTime;
    }

    public void setSwipingCardTime(int swipingCardTime) {
        this.swipingCardTime = swipingCardTime;
    }
}
