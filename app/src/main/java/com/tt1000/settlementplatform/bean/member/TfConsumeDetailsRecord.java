package com.tt1000.settlementplatform.bean.member;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 消费详情记录表
 * 主键id
 * 同步状态
 * 店铺代码
 * 客户代码
 */
@Entity
public class TfConsumeDetailsRecord {
    @Id
    @Unique
    @Property(nameInDb = "CDR_ID")
    private String CDR_ID;
    @Property(nameInDb = "COR_ID")
    private String COR_ID;
    private String CREATETIME;
    @Property(nameInDb = "CDR_UNIT_PRICE")
    private String CDR_UNIT_PRICE;
    @Property(nameInDb = "CDR_NUMBER")
    private String CDR_NUMBER;
    @Property(nameInDb = "CDR_MONEY")
    private String CDR_MONEY;
    @Property(nameInDb = "CDR_NO")
    private String CDR_NO;
    @Property(nameInDb = "CDR_TYPE")
    private String CDR_TYPE;
    @Property(nameInDb = "ISM_STATUS")
    private String ISM_STATUS;
    @Property(nameInDb = "STORE_CODE")
    private String STORE_CODE;
    @Property(nameInDb = "CLIENT_CODE")
    private String CLIENT_CODE;
    private String REMARKS;
    @Property(nameInDb = "DROP_STATUS")
    private String DROP_STATUS;
    @Property(nameInDb = "CHIP_ID")
    private String CHIP_ID;

    @Generated(hash = 212111381)
    public TfConsumeDetailsRecord() {
    }

    @Generated(hash = 191288044)
    public TfConsumeDetailsRecord(String CDR_ID, String COR_ID, String CREATETIME,
            String CDR_UNIT_PRICE, String CDR_NUMBER, String CDR_MONEY, String CDR_NO, String CDR_TYPE,
            String ISM_STATUS, String STORE_CODE, String CLIENT_CODE, String REMARKS,
            String DROP_STATUS, String CHIP_ID) {
        this.CDR_ID = CDR_ID;
        this.COR_ID = COR_ID;
        this.CREATETIME = CREATETIME;
        this.CDR_UNIT_PRICE = CDR_UNIT_PRICE;
        this.CDR_NUMBER = CDR_NUMBER;
        this.CDR_MONEY = CDR_MONEY;
        this.CDR_NO = CDR_NO;
        this.CDR_TYPE = CDR_TYPE;
        this.ISM_STATUS = ISM_STATUS;
        this.STORE_CODE = STORE_CODE;
        this.CLIENT_CODE = CLIENT_CODE;
        this.REMARKS = REMARKS;
        this.DROP_STATUS = DROP_STATUS;
        this.CHIP_ID = CHIP_ID;
    }

    public String getCDR_ID() {
        return CDR_ID;
    }

    public void setCDR_ID(String CDR_ID) {
        this.CDR_ID = CDR_ID;
    }

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

    public String getCDR_UNIT_PRICE() {
        return CDR_UNIT_PRICE;
    }

    public void setCDR_UNIT_PRICE(String CDR_UNIT_PRICE) {
        this.CDR_UNIT_PRICE = CDR_UNIT_PRICE;
    }

    public String getCDR_NUMBER() {
        return CDR_NUMBER;
    }

    public void setCDR_NUMBER(String CDR_NUMBER) {
        this.CDR_NUMBER = CDR_NUMBER;
    }

    public String getCDR_MONEY() {
        return CDR_MONEY;
    }

    public void setCDR_MONEY(String CDR_MONEY) {
        this.CDR_MONEY = CDR_MONEY;
    }

    public String getCDR_NO() {
        return CDR_NO;
    }

    public void setCDR_NO(String CDR_NO) {
        this.CDR_NO = CDR_NO;
    }

    public String getCDR_TYPE() {
        return CDR_TYPE;
    }

    public void setCDR_TYPE(String CDR_TYPE) {
        this.CDR_TYPE = CDR_TYPE;
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

    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }

    public String getDROP_STATUS() {
        return this.DROP_STATUS;
    }

    public void setDROP_STATUS(String DROP_STATUS) {
        this.DROP_STATUS = DROP_STATUS;
    }

    public String getCHIP_ID() {
        return this.CHIP_ID;
    }

    public void setCHIP_ID(String CHIP_ID) {
        this.CHIP_ID = CHIP_ID;
    }
}
