package com.tt1000.settlementplatform.bean.member;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 会员卡消费记录
 */
@Entity
public class TfConsumeCardRecord {
    @Id
    @Property(nameInDb = "CCR_ID")
    private String CCR_ID;
    @Property(nameInDb = "IC_ID")
    private String IC_ID;
    @Property(nameInDb = "CCR_ORIGINALAMOUNT")
    private String CCR_ORIGINALAMOUNT;
    @Property(nameInDb = "CCR_MONEY")
    private float CCR_MONEY;
    @Property(nameInDb = "CREATETIME")
    private String CREATETIME;
    @Property(nameInDb = "U_ID")
    private String U_ID;
    @Property(nameInDb = "COR_ID")
    private String COR_ID;
    @Property(nameInDb = "MACHINE_NO")
    private String MACHINE_NO;
    @Property(nameInDb = "CCR_STATUS")
    private String CCR_STATUS;
    // syncType : 1 在线 2 离线 3 下次离线
    @Property(nameInDb = "CCR_UPLOAD_STATUS")
    private String CCR_UPLOAD_STATUS;
    @Property(nameInDb = "CCR_UPLOAD_TIME")
    private String CCR_UPLOAD_TIME;
    @Property(nameInDb = "CCR_DATABASE_STATUS")
    private String CCR_DATABASE_STATUS;
    @Property(nameInDb = "IC_SERIAL_NUMBER")
    private String IC_SERIAL_NUMBER;
    @Property(nameInDb = "MT_ID")
    private String MT_ID;
    @Property(nameInDb = "CCR_PAY_TYPE")
    private String CCR_PAY_TYPE;
    @Property(nameInDb = "MI_ID")
    private String MI_ID;
    @Property(nameInDb = "ACCOUNT_ID")
    private String ACCOUNT_ID;
    @Property(nameInDb = "ISM_STATUS")
    private String ISM_STATUS;
    @Property(nameInDb = "CCR_DATABASE_TIME")
    private String CCR_DATABASE_TIME;
    @Property(nameInDb = "STORE_CODE")
    private String STORE_CODE;
    @Property(nameInDb = "CLIENT_CODE")
    private String CLIENT_CODE;
    @Property(nameInDb = "DISCOUNT_MONEY")
    private String DISCOUNT_MONEY;
    @Property(nameInDb = "DISCOUNT_REMARK")
    private String DISCOUNT_REMARK;
    @Property(nameInDb = "PAY_REMARK")
    private String PAY_REMARK;


    public String getCCR_ID() {
        return CCR_ID;
    }

    public void setCCR_ID(String CCR_ID) {
        this.CCR_ID = CCR_ID;
    }

    public String getIC_ID() {
        return IC_ID;
    }

    public void setIC_ID(String IC_ID) {
        this.IC_ID = IC_ID;
    }

    public String getCCR_ORIGINALAMOUNT() {
        return CCR_ORIGINALAMOUNT;
    }

    public void setCCR_ORIGINALAMOUNT(String CCR_ORIGINALAMOUNT) {
        this.CCR_ORIGINALAMOUNT = CCR_ORIGINALAMOUNT;
    }

    public float getCCR_MONEY() {
        return CCR_MONEY;
    }

    public void setCCR_MONEY(float CCR_MONEY) {
        this.CCR_MONEY = CCR_MONEY;
    }

    public String getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(String CREATETIME) {
        this.CREATETIME = CREATETIME;
    }

    public String getU_ID() {
        return U_ID;
    }

    public void setU_ID(String u_ID) {
        U_ID = u_ID;
    }

    public String getCOR_ID() {
        return COR_ID;
    }

    public void setCOR_ID(String COR_ID) {
        this.COR_ID = COR_ID;
    }

    public String getMACHINE_NO() {
        return MACHINE_NO;
    }

    public void setMACHINE_NO(String MACHINE_NO) {
        this.MACHINE_NO = MACHINE_NO;
    }

    public String getCCR_STATUS() {
        return CCR_STATUS;
    }

    public void setCCR_STATUS(String CCR_STATUS) {
        this.CCR_STATUS = CCR_STATUS;
    }

    public String getCCR_UPLOAD_STATUS() {
        return CCR_UPLOAD_STATUS;
    }

    public void setCCR_UPLOAD_STATUS(String CCR_UPLOAD_STATUS) {
        this.CCR_UPLOAD_STATUS = CCR_UPLOAD_STATUS;
    }

    public String getCCR_UPLOAD_TIME() {
        return CCR_UPLOAD_TIME;
    }

    public void setCCR_UPLOAD_TIME(String CCR_UPLOAD_TIME) {
        this.CCR_UPLOAD_TIME = CCR_UPLOAD_TIME;
    }

    public String getCCR_DATABASE_STATUS() {
        return CCR_DATABASE_STATUS;
    }

    public void setCCR_DATABASE_STATUS(String CCR_DATABASE_STATUS) {
        this.CCR_DATABASE_STATUS = CCR_DATABASE_STATUS;
    }

    public String getIC_SERIAL_NUMBER() {
        return IC_SERIAL_NUMBER;
    }

    public void setIC_SERIAL_NUMBER(String IC_SERIAL_NUMBER) {
        this.IC_SERIAL_NUMBER = IC_SERIAL_NUMBER;
    }

    public String getMT_ID() {
        return MT_ID;
    }

    public void setMT_ID(String MT_ID) {
        this.MT_ID = MT_ID;
    }

    public String getCCR_PAY_TYPE() {
        return CCR_PAY_TYPE;
    }

    public void setCCR_PAY_TYPE(String CCR_PAY_TYPE) {
        this.CCR_PAY_TYPE = CCR_PAY_TYPE;
    }

    public String getMI_ID() {
        return MI_ID;
    }

    public void setMI_ID(String MI_ID) {
        this.MI_ID = MI_ID;
    }

    public String getACCOUNT_ID() {
        return ACCOUNT_ID;
    }

    public void setACCOUNT_ID(String ACCOUNT_ID) {
        this.ACCOUNT_ID = ACCOUNT_ID;
    }

    public String getISM_STATUS() {
        return ISM_STATUS;
    }

    public void setISM_STATUS(String ISM_STATUS) {
        this.ISM_STATUS = ISM_STATUS;
    }

    public String getCCR_DATABASE_TIME() {
        return CCR_DATABASE_TIME;
    }

    public void setCCR_DATABASE_TIME(String CCR_DATABASE_TIME) {
        this.CCR_DATABASE_TIME = CCR_DATABASE_TIME;
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

    public String getDISCOUNT_MONEY() {
        return DISCOUNT_MONEY;
    }

    public void setDISCOUNT_MONEY(String DISCOUNT_MONEY) {
        this.DISCOUNT_MONEY = DISCOUNT_MONEY;
    }

    public String getDISCOUNT_REMARK() {
        return DISCOUNT_REMARK;
    }

    public void setDISCOUNT_REMARK(String DISCOUNT_REMARK) {
        this.DISCOUNT_REMARK = DISCOUNT_REMARK;
    }

    public String getPAY_REMARK() {
        return PAY_REMARK;
    }

    public void setPAY_REMARK(String PAY_REMARK) {
        this.PAY_REMARK = PAY_REMARK;
    }

    public long getELAPSEDTIME() {
        return ELAPSEDTIME;
    }

    public void setELAPSEDTIME(long ELAPSEDTIME) {
        this.ELAPSEDTIME = ELAPSEDTIME;
    }

    private long ELAPSEDTIME;

    @Keep
//    @Generated(hash = 439364975)
    public TfConsumeCardRecord(String CCR_ID, String IC_ID,
            String CCR_ORIGINALAMOUNT, float CCR_MONEY, String CREATETIME,
            String U_ID, String COR_ID, String MACHINE_NO, String CCR_STATUS,
            String CCR_UPLOAD_STATUS, String CCR_UPLOAD_TIME,
            String CCR_DATABASE_STATUS, String IC_SERIAL_NUMBER, String MT_ID,
            String CCR_PAY_TYPE, String MI_ID, String ACCOUNT_ID, String ISM_STATUS,
            String CCR_DATABASE_TIME, String STORE_CODE, String CLIENT_CODE,
            String DISCOUNT_MONEY, String DISCOUNT_REMARK, String PAY_REMARK,
            long ELAPSEDTIME) {
        this.CCR_ID = CCR_ID;
        this.IC_ID = IC_ID;
        this.CCR_ORIGINALAMOUNT = CCR_ORIGINALAMOUNT;
        this.CCR_MONEY = CCR_MONEY;
        this.CREATETIME = CREATETIME;
        this.U_ID = U_ID;
        this.COR_ID = COR_ID;
        this.MACHINE_NO = MACHINE_NO;
        this.CCR_STATUS = CCR_STATUS;
        this.CCR_UPLOAD_STATUS = CCR_UPLOAD_STATUS;
        this.CCR_UPLOAD_TIME = CCR_UPLOAD_TIME;
        this.CCR_DATABASE_STATUS = CCR_DATABASE_STATUS;
        this.IC_SERIAL_NUMBER = IC_SERIAL_NUMBER;
        this.MT_ID = MT_ID;
        this.CCR_PAY_TYPE = CCR_PAY_TYPE;
        this.MI_ID = MI_ID;
        this.ACCOUNT_ID = ACCOUNT_ID;
        this.ISM_STATUS = ISM_STATUS;
        this.CCR_DATABASE_TIME = CCR_DATABASE_TIME;
        this.STORE_CODE = STORE_CODE;
        this.CLIENT_CODE = CLIENT_CODE;
        this.DISCOUNT_MONEY = DISCOUNT_MONEY;
        this.DISCOUNT_REMARK = DISCOUNT_REMARK;
        this.PAY_REMARK = PAY_REMARK;
        this.ELAPSEDTIME = ELAPSEDTIME;
    }

    @Keep
//    @Generated(hash = 2142402027)
    public TfConsumeCardRecord() {
    }

}
