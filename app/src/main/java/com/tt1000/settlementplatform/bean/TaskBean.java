package com.tt1000.settlementplatform.bean;

public class TaskBean {
    private String tableName;
    private long updatetime;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getUpdatetime()
    {
        return updatetime;
    }

    public void setUpdatetime(long updatetime)
    {
        this.updatetime = updatetime;
    }

    private int type;
    private long ms;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getMs() {
        return ms;
    }

    public void setMs(long ms) {
        this.ms = ms;
    }

    @Override
    public String toString() {
        return "TaskBean{" +
                "tableName='" + tableName + '\'' +
                ", updatetime=" + updatetime +
                ", type=" + type +
                ", ms=" + ms +
                '}';
    }
}
