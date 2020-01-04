package com.tt1000.settlementplatform.bean.member;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;

/**
 * 账户表
 */
@Entity
public class TfMemberAccountRecord {

    /**
     * ACCOUNT_ID : 15326778037941681950
     * BALANCE : 0
     * TOTAL_RECHARGE_MONEY : 0
     * ACCOUNT_STATUS : 0
     * REFUND_STATUS : 0
     * CREATETIME : 1532677803000
     * UPDATETIME : 1532677803000
     * MI_ID : 15326778037861041328
     * ACCOUNT_TYPE : 1
     * STORE_CODE : 0000
     * CLIENT_CODE : 153267599744410
     * ISM_STATUS : 2
     */
    @Id
    @Property(nameInDb = "ACCOUNT_ID")
    private String ACCOUNT_ID;
    private String BALANCE;
    @Property(nameInDb = "TOTAL_RECHARGE_MONEY")
    private String TOTAL_RECHARGE_MONEY;
    @Property(nameInDb = "ACCOUNT_STATUS")
    private String ACCOUNT_STATUS;
    @Property(nameInDb = "REFUND_STATUS")
    private String REFUND_STATUS;
    private long CREATETIME;
    private long UPDATETIME;
    @Property(nameInDb = "U_ID")
    private String U_ID;
    @Property(nameInDb = "MI_ID")
    private String MI_ID;
    @Property(nameInDb = "ACCOUNT_TYPE")
    private String ACCOUNT_TYPE;
    @Property(nameInDb = "STORE_CODE")
    private String STORE_CODE;
    @Property(nameInDb = "CLIENT_CODE")
    private String CLIENT_CODE;
    @Property(nameInDb = "ISM_STATUS")
    private String ISM_STATUS;

    @Keep
//    @Generated(hash = 604897990)
    public TfMemberAccountRecord(String ACCOUNT_ID, String BALANCE,
                                 String TOTAL_RECHARGE_MONEY, String ACCOUNT_STATUS,
                                 String REFUND_STATUS, long CREATETIME, long UPDATETIME, String U_ID,
                                 String MI_ID, String ACCOUNT_TYPE, String STORE_CODE,
                                 String CLIENT_CODE, String ISM_STATUS) {
        this.ACCOUNT_ID = ACCOUNT_ID;
        this.BALANCE = BALANCE;
        this.TOTAL_RECHARGE_MONEY = TOTAL_RECHARGE_MONEY;
        this.ACCOUNT_STATUS = ACCOUNT_STATUS;
        this.REFUND_STATUS = REFUND_STATUS;
        this.CREATETIME = CREATETIME;
        this.UPDATETIME = UPDATETIME;
        this.U_ID = U_ID;
        this.MI_ID = MI_ID;
        this.ACCOUNT_TYPE = ACCOUNT_TYPE;
        this.STORE_CODE = STORE_CODE;
        this.CLIENT_CODE = CLIENT_CODE;
        this.ISM_STATUS = ISM_STATUS;
    }

    @Keep
//    @Generated(hash = 739995645)
    public TfMemberAccountRecord() {
    }

    public String getU_ID() {
        return U_ID;
    }

    public void setU_ID(String u_ID) {
        U_ID = u_ID;
    }
    public String getACCOUNT_ID() {
        return ACCOUNT_ID;
    }

    public void setACCOUNT_ID(String ACCOUNT_ID) {
        this.ACCOUNT_ID = ACCOUNT_ID;
    }

    public String getBALANCE() {
        return BALANCE;
    }

    public void setBALANCE(String BALANCE) {
        this.BALANCE = BALANCE;
    }

    public String getTOTAL_RECHARGE_MONEY() {
        return TOTAL_RECHARGE_MONEY;
    }

    public void setTOTAL_RECHARGE_MONEY(String TOTAL_RECHARGE_MONEY) {
        this.TOTAL_RECHARGE_MONEY = TOTAL_RECHARGE_MONEY;
    }

    public String getACCOUNT_STATUS() {
        return ACCOUNT_STATUS;
    }

    public void setACCOUNT_STATUS(String ACCOUNT_STATUS) {
        this.ACCOUNT_STATUS = ACCOUNT_STATUS;
    }

    public String getREFUND_STATUS() {
        return REFUND_STATUS;
    }

    public void setREFUND_STATUS(String REFUND_STATUS) {
        this.REFUND_STATUS = REFUND_STATUS;
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

    public String getMI_ID() {
        return MI_ID;
    }

    public void setMI_ID(String MI_ID) {
        this.MI_ID = MI_ID;
    }

    public String getACCOUNT_TYPE() {
        return ACCOUNT_TYPE;
    }

    public void setACCOUNT_TYPE(String ACCOUNT_TYPE) {
        this.ACCOUNT_TYPE = ACCOUNT_TYPE;
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

    public String getISM_STATUS() {
        return ISM_STATUS;
    }

    public void setISM_STATUS(String ISM_STATUS) {
        this.ISM_STATUS = ISM_STATUS;
    }
}
