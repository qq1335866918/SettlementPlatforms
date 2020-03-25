package com.tt1000.settlementplatform.bean;

public class CommodityBean {
    public String commodity_name;
    public String commodity_price;

    public CommodityBean(String commodity_name, String commodity_price) {
        super();
        this.commodity_name = commodity_name;
        this.commodity_price = commodity_price;
    }

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    public String getCommodity_price() {
        return commodity_price;
    }

    public void setCommodity_price(String commodity_price) {
        this.commodity_price = commodity_price;
    }
}
