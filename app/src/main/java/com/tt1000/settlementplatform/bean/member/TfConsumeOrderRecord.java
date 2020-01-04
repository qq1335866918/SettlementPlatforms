package com.tt1000.settlementplatform.bean.member;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 消费订单表
 */

@Entity
public class TfConsumeOrderRecord {
    @Id
    @Property(nameInDb = "COR_ID")
    private String COR_ID;
    private String CREATETIME;
    @Property(nameInDb = "COR_AMOUNT")
    private String COR_AMOUNT;
    @Property(nameInDb = "MACHINE_NO")
    private String MACHINE_NO;
    @Property(nameInDb = "COR_MONERY")
    private String COR_MONERY;
    @Property(nameInDb = "U_ID")
    private String U_ID;
    @Property(nameInDb = "ISM_STATUS")
    private String ISM_STATUS;
    @Property(nameInDb = "STORE_CODE")
    private String STORE_CODE;
    @Property(nameInDb = "CLIENT_CODE")
    private String CLIENT_CODE;
    @Property(nameInDb = "COR_TYPE")
    private String COR_TYPE;
    @Property(nameInDb = "COR_STATUS")
    private String COR_STATUS;
    private String UPDATETIME;
    @Property(nameInDb = "ADDR_ID")
    private long ADDR_ID;
//    private String EATTIME;


    public String getCOR_ID() {
        return COR_ID;
    }

    public void setCOR_ID(String COR_ID) {
        this.COR_ID = COR_ID;
    }

    public String getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(String CREATETIME) {
        this.CREATETIME = CREATETIME;
    }

    public String getCOR_AMOUNT() {
        return COR_AMOUNT;
    }

    public void setCOR_AMOUNT(String COR_AMOUNT) {
        this.COR_AMOUNT = COR_AMOUNT;
    }

    public String getMACHINE_NO() {
        return MACHINE_NO;
    }

    public void setMACHINE_NO(String MACHINE_NO) {
        this.MACHINE_NO = MACHINE_NO;
    }

    public String getCOR_MONERY() {
        return COR_MONERY;
    }

    public void setCOR_MONERY(String COR_MONERY) {
        this.COR_MONERY = COR_MONERY;
    }

    public String getU_ID() {
        return U_ID;
    }

    public void setU_ID(String u_ID) {
        U_ID = u_ID;
    }

    public String getISM_STATUS() {
        return ISM_STATUS;
    }

    public void setISM_STATUS(String ISM_STATUS) {
        this.ISM_STATUS = ISM_STATUS;
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

    public String getCOR_TYPE() {
        return COR_TYPE;
    }

    public void setCOR_TYPE(String COR_TYPE) {
        this.COR_TYPE = COR_TYPE;
    }

    public String getCOR_STATUS() {
        return COR_STATUS;
    }

    public void setCOR_STATUS(String COR_STATUS) {
        this.COR_STATUS = COR_STATUS;
    }

    public String getUPDATETIME() {
        return UPDATETIME;
    }

    public void setUPDATETIME(String UPDATETIME) {
        this.UPDATETIME = UPDATETIME;
    }

    public long getADDR_ID() {
        return ADDR_ID;
    }

    public void setADDR_ID(long ADDR_ID) {
        this.ADDR_ID = ADDR_ID;
    }


    @Keep
//    @Generated(hash = 2072088058)
    public TfConsumeOrderRecord(String COR_ID, String CREATETIME, String COR_AMOUNT,
                                String MACHINE_NO, String COR_MONERY, String U_ID, String ISM_STATUS,
                                String STORE_CODE, String CLIENT_CODE, String COR_TYPE,
                                String COR_STATUS, String UPDATETIME, long ADDR_ID) {
        this.COR_ID = COR_ID;
        this.CREATETIME = CREATETIME;
        this.COR_AMOUNT = COR_AMOUNT;
        this.MACHINE_NO = MACHINE_NO;
        this.COR_MONERY = COR_MONERY;
        this.U_ID = U_ID;
        this.ISM_STATUS = ISM_STATUS;
        this.STORE_CODE = STORE_CODE;
        this.CLIENT_CODE = CLIENT_CODE;
        this.COR_TYPE = COR_TYPE;
        this.COR_STATUS = COR_STATUS;
        this.UPDATETIME = UPDATETIME;
        this.ADDR_ID = ADDR_ID;
    }

    @Keep
//    @Generated(hash = 1330550712)
    public TfConsumeOrderRecord() {
    }
}
