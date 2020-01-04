package com.tt1000.settlementplatform.bean.operation;

public class OperationMenu {

    private int resId;
    // 菜名
    private String dishNmae;
    // 菜品编号
    private long id;
    // 数量
    private int count;
    // 总价
    private float totalPrice;
    // 单价
    private float unitPrice;
    //折扣后单价
    private float unitDisPrice;
    // 类型id
    private String ct_id;

    public String getCt_id() {
        return ct_id;
    }

    public void setCt_id(String ct_id) {
        this.ct_id = ct_id;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getUnitDisPrice() {
        return unitDisPrice;
    }

    public void setUnitDisPrice(float unitDisPrice) {
        this.unitDisPrice = unitDisPrice;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getDishNmae() {
        return dishNmae;
    }

    public void setDishNmae(String dishNmae) {
        this.dishNmae = dishNmae;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
