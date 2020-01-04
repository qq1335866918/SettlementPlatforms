package com.tt1000.settlementplatform.bean.member;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;

/**
 * 餐次
 */
@Entity(nameInDb = "tf_mealtimes")
public class TfMealTimes {

    /**
     * MT_ID : 1
     * MT_NAME : 早餐
     * STARTTIME : 05:45:00
     * ENDTIME : 09:00:00
     * MT_STATUS : 0
     * UPDATETIME : 1532676061000
     * CLIENT_CODE : 153267599744410
     * STORE_CODE : 0000
     */
    @Id
    @Property(nameInDb = "MT_ID")
    private String MT_ID;
    @Property(nameInDb = "MT_NAME")
    private String MT_NAME;
    private String STARTTIME;
    private String ENDTIME;
    @Property(nameInDb = "MT_STATUS")
    private String MT_STATUS;
    @Property(nameInDb = "UPDATETIME")
    private long UPDATETIME;
    @Property(nameInDb = "CLIENT_CODE")
    private String CLIENT_CODE;
    @Property(nameInDb = "STORE_CODE")
    private String STORE_CODE;

    @Keep
//    @Generated(hash = 606456715)
    public TfMealTimes(String MT_ID, String MT_NAME, String STARTTIME, String ENDTIME,
                       String MT_STATUS, long UPDATETIME, String CLIENT_CODE,
                       String STORE_CODE) {
        this.MT_ID = MT_ID;
        this.MT_NAME = MT_NAME;
        this.STARTTIME = STARTTIME;
        this.ENDTIME = ENDTIME;
        this.MT_STATUS = MT_STATUS;
        this.UPDATETIME = UPDATETIME;
        this.CLIENT_CODE = CLIENT_CODE;
        this.STORE_CODE = STORE_CODE;
    }

    @Keep
//    @Generated(hash = 141675136)
    public TfMealTimes() {
    }

    public String getMT_ID() {
        return MT_ID;
    }

    public void setMT_ID(String MT_ID) {
        this.MT_ID = MT_ID;
    }

    public String getMT_NAME() {
        return MT_NAME;
    }

    public void setMT_NAME(String MT_NAME) {
        this.MT_NAME = MT_NAME;
    }

    public String getSTARTTIME() {
        return STARTTIME;
    }

    public void setSTARTTIME(String STARTTIME) {
        this.STARTTIME = STARTTIME;
    }

    public String getENDTIME() {
        return ENDTIME;
    }

    public void setENDTIME(String ENDTIME) {
        this.ENDTIME = ENDTIME;
    }

    public String getMT_STATUS() {
        return MT_STATUS;
    }

    public void setMT_STATUS(String MT_STATUS) {
        this.MT_STATUS = MT_STATUS;
    }

    public long getUPDATETIME() {
        return UPDATETIME;
    }

    public void setUPDATETIME(long UPDATETIME) {
        this.UPDATETIME = UPDATETIME;
    }

    public String getCLIENT_CODE() {
        return CLIENT_CODE;
    }

    public void setCLIENT_CODE(String CLIENT_CODE) {
        this.CLIENT_CODE = CLIENT_CODE;
    }

    public String getSTORE_CODE() {
        return STORE_CODE;
    }

    public void setSTORE_CODE(String STORE_CODE) {
        this.STORE_CODE = STORE_CODE;
    }
}
