package com.tt1000.settlementplatform.bean.member;

import com.tt1000.settlementplatform.utils.MyUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * 会员类型
 */
@Entity
public class MemberTypeRecord {

    /**
     * SEQNO : 15326760612841601502
     * TYPE_NAME : 全部类型
     * CREATETIME : 1532676061000
     * UPDATETIME : 1532676061000
     * P_ID : -1
     * CLIENT_CODE : 153267599744410
     * STORE_CODE : 0000
     * STATUS : 0
     * SORT : 0
     * T_ID :
     * DISCOUNT_RATE : 1
     * CODE : 601502
     */
    @Id
    private String SEQNO;
    @Property(nameInDb = "TYPE_NAME")
    private String TYPE_NAME;
    private long CREATETIME;
    private long UPDATETIME;
    @Property(nameInDb = "P_ID")
    private String P_ID;
    @Property(nameInDb = "CLIENT_CODE")
    private String CLIENT_CODE;
    @Property(nameInDb = "STORE_CODE")
    private String STORE_CODE;
    //0:正常   1：禁用
    private String STATUS;
    private String SORT;
    @Property(nameInDb = "T_ID")
    private String T_ID;
    @Property(nameInDb = "DISCOUNT_RATE")
    private String DISCOUNT_RATE;
    // extra
    private String CODE;

    @Generated(hash = 628889761)
    public MemberTypeRecord(String SEQNO, String TYPE_NAME, long CREATETIME,
            long UPDATETIME, String P_ID, String CLIENT_CODE, String STORE_CODE,
            String STATUS, String SORT, String T_ID, String DISCOUNT_RATE,
            String CODE) {
        this.SEQNO = SEQNO;
        this.TYPE_NAME = TYPE_NAME;
        this.CREATETIME = CREATETIME;
        this.UPDATETIME = UPDATETIME;
        this.P_ID = P_ID;
        this.CLIENT_CODE = CLIENT_CODE;
        this.STORE_CODE = STORE_CODE;
        this.STATUS = STATUS;
        this.SORT = SORT;
        this.T_ID = T_ID;
        this.DISCOUNT_RATE = DISCOUNT_RATE;
        this.CODE = CODE;
    }

    @Generated(hash = 288369156)
    public MemberTypeRecord() {
    }

    public String getSEQNO() {
        return SEQNO;
    }

    public void setSEQNO(String SEQNO) {
        this.SEQNO = SEQNO;
    }

    public String getTYPE_NAME() {
        return TYPE_NAME;
    }

    public void setTYPE_NAME(String TYPE_NAME) {
        this.TYPE_NAME = TYPE_NAME;
    }

    public long getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(long CREATETIME) {
        this.CREATETIME =CREATETIME;
    }

    public long getUPDATETIME() {
        return UPDATETIME;
    }

    public void setUPDATETIME(long UPDATETIME) {
        this.UPDATETIME = UPDATETIME;
    }

    public String getP_ID() {
        return P_ID;
    }

    public void setP_ID(String P_ID) {
        this.P_ID = P_ID;
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

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getSORT() {
        return SORT;
    }

    public void setSORT(String SORT) {
        this.SORT = SORT;
    }

    public String getT_ID() {
        return T_ID;
    }

    public void setT_ID(String T_ID) {
        this.T_ID = T_ID;
    }

    public String getDISCOUNT_RATE() {
        return DISCOUNT_RATE;
    }

    public void setDISCOUNT_RATE(String DISCOUNT_RATE) {
        this.DISCOUNT_RATE = DISCOUNT_RATE;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }
}
