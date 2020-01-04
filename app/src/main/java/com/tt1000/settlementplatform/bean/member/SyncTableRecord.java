package com.tt1000.settlementplatform.bean.member;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity
public class SyncTableRecord {
    @Id
    private String SEQNO;
    private String TABLENAME;
    private String PRIMARYKEY;
    private int QUESTTIME;
    @Property(nameInDb = "ISM_STATUS")
    private String ISM_STATUS;
    @Property(nameInDb = "INIT_STATUS")
    private String INIT_STATUS;
    @Property(nameInDb = "CLIENT_CODE")
    private String CLIENT_CODE;
    @Property(nameInDb = "STORE_CODE")
    private String STORE_CODE;
    private String CREATETIME;
    private String UPDATETIME;
    @Property(nameInDb = "ANOTHER_NAME")
    private String ANOTHER_NAME;

    @Generated(hash = 2049449936)
    public SyncTableRecord(String SEQNO, String TABLENAME, String PRIMARYKEY,
            int QUESTTIME, String ISM_STATUS, String INIT_STATUS,
            String CLIENT_CODE, String STORE_CODE, String CREATETIME,
            String UPDATETIME, String ANOTHER_NAME) {
        this.SEQNO = SEQNO;
        this.TABLENAME = TABLENAME;
        this.PRIMARYKEY = PRIMARYKEY;
        this.QUESTTIME = QUESTTIME;
        this.ISM_STATUS = ISM_STATUS;
        this.INIT_STATUS = INIT_STATUS;
        this.CLIENT_CODE = CLIENT_CODE;
        this.STORE_CODE = STORE_CODE;
        this.CREATETIME = CREATETIME;
        this.UPDATETIME = UPDATETIME;
        this.ANOTHER_NAME = ANOTHER_NAME;
    }

    @Generated(hash = 942887645)
    public SyncTableRecord() {
    }

    public String getSEQNO() {
        return SEQNO;
    }

    public void setSEQNO(String SEQNO) {
        this.SEQNO = SEQNO;
    }

    public String getTABLENAME() {
        return TABLENAME;
    }

    public void setTABLENAME(String TABLENAME) {
        this.TABLENAME = TABLENAME;
    }

    public String getPRIMARYKEY() {
        return PRIMARYKEY;
    }

    public void setPRIMARYKEY(String PRIMARYKEY) {
        this.PRIMARYKEY = PRIMARYKEY;
    }

    public int getQUESTTIME() {
        return QUESTTIME;
    }

    public void setQUESTTIME(int QUESTTIME) {
        this.QUESTTIME = QUESTTIME;
    }

    public String getISM_STATUS() {
        return ISM_STATUS;
    }

    public void setISM_STATUS(String ISM_STATUS) {
        this.ISM_STATUS = ISM_STATUS;
    }

    public String getINIT_STATUS() {
        return INIT_STATUS;
    }

    public void setINIT_STATUS(String INIT_STATUS) {
        this.INIT_STATUS = INIT_STATUS;
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

    public String getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(String CREATETIME) {
        this.CREATETIME = CREATETIME;
    }

    public String getUPDATETIME() {
        return UPDATETIME;
    }

    public void setUPDATETIME(String UPDATETIME) {
        this.UPDATETIME = UPDATETIME;
    }

    public String getANOTHER_NAME() {
        return ANOTHER_NAME;
    }

    public void setANOTHER_NAME(String ANOTHER_NAME) {
        this.ANOTHER_NAME = ANOTHER_NAME;
    }
}
