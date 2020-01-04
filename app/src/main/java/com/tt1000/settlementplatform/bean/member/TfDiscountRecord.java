package com.tt1000.settlementplatform.bean.member;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;

/**
 * 折扣表
 */
@Entity
public class TfDiscountRecord {

    /**
     * SEQNO : 15326760613131861289
     * DISCOUNT_TYPE : 0
     * CREATETIME : 1532676061000
     * UPDATETIME : 1532676061000
     * STARTTIME :
     * ENDTIME :
     * STORE_CODE : 0000
     * CLIENT_CODE : 153267599744410
     * DISCOUNT_STATUS : 1
     * DISCOUNT_RATE_C1 : 100
     * DISCOUNT_RATE_C2 : 100
     * DISCOUNT_RATE_C3 : 100
     * DISCOUNT_RATE_C4 : 100
     * DISCOUNT_RATE_C5 : 100
     * DISCOUNT_RATE_C6 : 100
     * DISCOUNT_RATE_C7 : 100
     * DISCOUNT_RATE_C8 : 100
     * DISCOUNT_RATE_C9 : 100
     * DISCOUNT_RATE_C10 : 100
     */
    @Id
    private String SEQNO;
    //0：全员打折    1：会员打折     2：自定义打折
    @Property(nameInDb = "DISCOUNT_TYPE")
    private String DISCOUNT_TYPE;
    private long CREATETIME;
    private long UPDATETIME;
    @Property(nameInDb = "STORE_CODE")
    private String STORE_CODE;
    @Property(nameInDb = "CLIENT_CODE")
    private String CLIENT_CODE;
    private String STARTTIME;
    private String ENDTIME;
    //0：关闭   1：开启
    @Property(nameInDb = "DISCOUNT_STATUS")
    private String DISCOUNT_STATUS;
    @Property(nameInDb = "DISCOUNT_RATE_C1")
    private String DISCOUNT_RATE_C1;
    @Property(nameInDb = "DISCOUNT_RATE_C2")
    private String DISCOUNT_RATE_C2;
    @Property(nameInDb = "DISCOUNT_RATE_C3")
    private String DISCOUNT_RATE_C3;
    @Property(nameInDb = "DISCOUNT_RATE_C4")
    private String DISCOUNT_RATE_C4;
    @Property(nameInDb = "DISCOUNT_RATE_C5")
    private String DISCOUNT_RATE_C5;
    @Property(nameInDb = "DISCOUNT_RATE_C6")
    private String DISCOUNT_RATE_C6;
    @Property(nameInDb = "DISCOUNT_RATE_C7")
    private String DISCOUNT_RATE_C7;
    @Property(nameInDb = "DISCOUNT_RATE_C8")
    private String DISCOUNT_RATE_C8;
    @Property(nameInDb = "DISCOUNT_RATE_C9")
    private String DISCOUNT_RATE_C9;
    @Property(nameInDb = "DISCOUNT_RATE_C10")
    private String DISCOUNT_RATE_C10;

    @Keep()
//    @Generated(hash = 1305631696)
    public TfDiscountRecord() {
    }

    @Keep
//    @Generated(hash = 1412695179)
    public TfDiscountRecord(String SEQNO, String DISCOUNT_TYPE,
                            String DISCOUNT_RATE_C1, String DISCOUNT_RATE_C2,
                            String DISCOUNT_RATE_C3, String DISCOUNT_RATE_C4,
                            String DISCOUNT_RATE_C5, String DISCOUNT_RATE_C6,
                            String DISCOUNT_RATE_C7, String DISCOUNT_RATE_C8,
                            String DISCOUNT_RATE_C9, String DISCOUNT_RATE_C10, long CREATETIME,
                            long UPDATETIME, String STORE_CODE, String CLIENT_CODE,
                            String STARTTIME, String ENDTIME, String DISCOUNT_STATUS) {
        this.SEQNO = SEQNO;
        this.DISCOUNT_TYPE = DISCOUNT_TYPE;
        this.DISCOUNT_RATE_C1 = DISCOUNT_RATE_C1;
        this.DISCOUNT_RATE_C2 = DISCOUNT_RATE_C2;
        this.DISCOUNT_RATE_C3 = DISCOUNT_RATE_C3;
        this.DISCOUNT_RATE_C4 = DISCOUNT_RATE_C4;
        this.DISCOUNT_RATE_C5 = DISCOUNT_RATE_C5;
        this.DISCOUNT_RATE_C6 = DISCOUNT_RATE_C6;
        this.DISCOUNT_RATE_C7 = DISCOUNT_RATE_C7;
        this.DISCOUNT_RATE_C8 = DISCOUNT_RATE_C8;
        this.DISCOUNT_RATE_C9 = DISCOUNT_RATE_C9;
        this.DISCOUNT_RATE_C10 = DISCOUNT_RATE_C10;
        this.CREATETIME = CREATETIME;
        this.UPDATETIME = UPDATETIME;
        this.STORE_CODE = STORE_CODE;
        this.CLIENT_CODE = CLIENT_CODE;
        this.STARTTIME = STARTTIME;
        this.ENDTIME = ENDTIME;
        this.DISCOUNT_STATUS = DISCOUNT_STATUS;
    }

    public String getSEQNO() {
        return SEQNO;
    }

    public void setSEQNO(String SEQNO) {
        this.SEQNO = SEQNO;
    }

    public String getDISCOUNT_TYPE() {
        return DISCOUNT_TYPE;
    }

    public void setDISCOUNT_TYPE(String DISCOUNT_TYPE) {
        this.DISCOUNT_TYPE = DISCOUNT_TYPE;
    }

    public long getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(long CREATETIME) {
        this.CREATETIME = CREATETIME;
    }

    public long getUPDATETIME() {
        return UPDATETIME;
    }

    public void setUPDATETIME(long UPDATETIME) {
        this.UPDATETIME = UPDATETIME;
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

    public String getSTORE_CODE() {
        return STORE_CODE;
    }

    public void setSTORE_CODE(String STORE_CODE) {
        this.STORE_CODE = STORE_CODE;
    }

    public String getCLIENT_CODE() {
        return CLIENT_CODE;
    }

    public void setCLIENT_CODE(String CLIENT_CODE) {
        this.CLIENT_CODE = CLIENT_CODE;
    }

    public String getDISCOUNT_STATUS() {
        return DISCOUNT_STATUS;
    }

    public void setDISCOUNT_STATUS(String DISCOUNT_STATUS) {
        this.DISCOUNT_STATUS = DISCOUNT_STATUS;
    }

    public String getDISCOUNT_RATE_C1() {
        return DISCOUNT_RATE_C1;
    }

    public void setDISCOUNT_RATE_C1(String DISCOUNT_RATE_C1) {
        this.DISCOUNT_RATE_C1 = DISCOUNT_RATE_C1;
    }

    public String getDISCOUNT_RATE_C2() {
        return DISCOUNT_RATE_C2;
    }

    public void setDISCOUNT_RATE_C2(String DISCOUNT_RATE_C2) {
        this.DISCOUNT_RATE_C2 = DISCOUNT_RATE_C2;
    }

    public String getDISCOUNT_RATE_C3() {
        return DISCOUNT_RATE_C3;
    }

    public void setDISCOUNT_RATE_C3(String DISCOUNT_RATE_C3) {
        this.DISCOUNT_RATE_C3 = DISCOUNT_RATE_C3;
    }

    public String getDISCOUNT_RATE_C4() {
        return DISCOUNT_RATE_C4;
    }

    public void setDISCOUNT_RATE_C4(String DISCOUNT_RATE_C4) {
        this.DISCOUNT_RATE_C4 = DISCOUNT_RATE_C4;
    }

    public String getDISCOUNT_RATE_C5() {
        return DISCOUNT_RATE_C5;
    }

    public void setDISCOUNT_RATE_C5(String DISCOUNT_RATE_C5) {
        this.DISCOUNT_RATE_C5 = DISCOUNT_RATE_C5;
    }

    public String getDISCOUNT_RATE_C6() {
        return DISCOUNT_RATE_C6;
    }

    public void setDISCOUNT_RATE_C6(String DISCOUNT_RATE_C6) {
        this.DISCOUNT_RATE_C6 = DISCOUNT_RATE_C6;
    }

    public String getDISCOUNT_RATE_C7() {
        return DISCOUNT_RATE_C7;
    }

    public void setDISCOUNT_RATE_C7(String DISCOUNT_RATE_C7) {
        this.DISCOUNT_RATE_C7 = DISCOUNT_RATE_C7;
    }

    public String getDISCOUNT_RATE_C8() {
        return DISCOUNT_RATE_C8;
    }

    public void setDISCOUNT_RATE_C8(String DISCOUNT_RATE_C8) {
        this.DISCOUNT_RATE_C8 = DISCOUNT_RATE_C8;
    }

    public String getDISCOUNT_RATE_C9() {
        return DISCOUNT_RATE_C9;
    }

    public void setDISCOUNT_RATE_C9(String DISCOUNT_RATE_C9) {
        this.DISCOUNT_RATE_C9 = DISCOUNT_RATE_C9;
    }

    public String getDISCOUNT_RATE_C10() {
        return DISCOUNT_RATE_C10;
    }

    public void setDISCOUNT_RATE_C10(String DISCOUNT_RATE_C10) {
        this.DISCOUNT_RATE_C10 = DISCOUNT_RATE_C10;
    }
}
