package com.tt1000.settlementplatform.bean.member;

import com.tt1000.settlementplatform.utils.MyUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;

/**
 * 商品类型
 */
@Entity(nameInDb = "commodity_type_record")
public class CommodityTypeRecord {

    /**
     * SEQNO : 15326760613221711957
     * TYPE_NAME : 全部分类
     * CREATETIME : 1532676061000
     * UPDATETIME : 1532676061000
     * P_ID : 0
     * CLIENT_CODE : 153267599744410
     * STORE_CODE : 0000
     * STATUS : 0
     * SORT : 0
     * T_ID :
     */
    @Id
    @Property(nameInDb = "SEQNO")
    private String SEQNO;
    @Property(nameInDb = "TYPE_NAME")
    private String TYPE_NAME;
    private long CREATETIME;
    @Property(nameInDb = "UPDATETIME")
    private long UPDATETIME;
    @Property(nameInDb = "P_ID")
    private String P_ID;
    @Property(nameInDb = "CLIENT_CODE")
    private String CLIENT_CODE;
    @Property(nameInDb = "STORE_CODE")
    private String STORE_CODE;
    private String STATUS;
    private String SORT;
    @Property(nameInDb = "T_ID")
    private String T_ID;

//    @Generated(hash = 628937342)
    @Keep
    public CommodityTypeRecord(String SEQNO, String TYPE_NAME, long CREATETIME,
            long UPDATETIME, String P_ID, String CLIENT_CODE, String STORE_CODE,
            String STATUS, String SORT, String T_ID) {
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
    }

//    @Generated(hash = 1064799380)
    @Keep
    public CommodityTypeRecord() {
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
        this.CREATETIME = CREATETIME;
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
}
