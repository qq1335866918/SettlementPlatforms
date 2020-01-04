package com.tt1000.settlementplatform.bean.statistics;

public class StatisticsCommodity {
    private String commdityNmae;
    private int totalSalesVolume;
    private float unitPrice;
    private float totalSales;
    private String commodityNo;

    public String getCommodityNo() {
        return commodityNo;
    }

    public void setCommodityNo(String commodityNo) {
        this.commodityNo = commodityNo;
    }

    public String getCommdityNmae() {
        return commdityNmae;
    }

    public void setCommdityNmae(String commdityNmae) {
        this.commdityNmae = commdityNmae;
    }

    public int getTotalSalesVolume() {
        return totalSalesVolume;
    }

    public void setTotalSalesVolume(int totalSalesVolume) {
        this.totalSalesVolume = totalSalesVolume;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(float totalSales) {
        this.totalSales = totalSales;
    }
}
