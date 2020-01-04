package com.tt1000.settlementplatform.bean.member;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 商店表
 */
@Entity(nameInDb = "tf_store_record")
public class TfStoreRecord {
    @Id
    @Property(nameInDb = "STORE_ID")
    private String STORE_ID;
    private String STORE_NAME;
    private String STORE_ADDR;
    private String STORE_STATUS;
    private String STORE_CODE;
    private long CREATETIME;
    private long UPDATETIME;
    private String CLIENT_CODE;
    private String STORE_DUTY_PERSON;
    private String STORE_DUTY_PHONE;
    private String STORE_IMG;
    private String SYN_STATUS;
    private String VAILD_TIME;
    private String APPID;
    private String SECRET;
    private String SMS;

    @Keep
//    @Generated(hash = 351087925)
    public TfStoreRecord(String STORE_ID, String STORE_NAME, String STORE_ADDR, String STORE_STATUS,
                         String STORE_CODE, String UPAPERWORK_NO, String USEX, long CREATETIME,
                         long UPDATETIME, String CLIENT_CODE, String STORE_DUTY_PERSON, String STORE_DUTY_PHONE,
                         String STORE_IMG, String SYN_STATUS, String VAILD_TIME,String APPID,String SECRET,String SMS) {
        this.STORE_ID = STORE_ID;
        this.STORE_NAME = STORE_NAME;
        this.STORE_ADDR = STORE_ADDR;
        this.STORE_STATUS = STORE_STATUS;
        this.STORE_CODE = STORE_CODE;
        this.CREATETIME = CREATETIME;
        this.UPDATETIME = UPDATETIME;
        this.CLIENT_CODE = CLIENT_CODE;
        this.STORE_DUTY_PERSON = STORE_DUTY_PERSON;
        this.STORE_DUTY_PHONE = STORE_DUTY_PHONE;
        this.STORE_IMG = STORE_IMG;
        this.SYN_STATUS = SYN_STATUS;
        this.VAILD_TIME = VAILD_TIME;
        this.APPID = APPID;
        this.SECRET = SECRET;
        this.SMS = SMS;
    }
    @Keep
//    @Generated(hash = 1279772520)
    public TfStoreRecord() {
    }
    @Generated(hash = 1039203793)
    public TfStoreRecord(String STORE_ID, String STORE_NAME, String STORE_ADDR, String STORE_STATUS, String STORE_CODE,
            long CREATETIME, long UPDATETIME, String CLIENT_CODE, String STORE_DUTY_PERSON, String STORE_DUTY_PHONE,
            String STORE_IMG, String SYN_STATUS, String VAILD_TIME, String APPID, String SECRET, String SMS) {
        this.STORE_ID = STORE_ID;
        this.STORE_NAME = STORE_NAME;
        this.STORE_ADDR = STORE_ADDR;
        this.STORE_STATUS = STORE_STATUS;
        this.STORE_CODE = STORE_CODE;
        this.CREATETIME = CREATETIME;
        this.UPDATETIME = UPDATETIME;
        this.CLIENT_CODE = CLIENT_CODE;
        this.STORE_DUTY_PERSON = STORE_DUTY_PERSON;
        this.STORE_DUTY_PHONE = STORE_DUTY_PHONE;
        this.STORE_IMG = STORE_IMG;
        this.SYN_STATUS = SYN_STATUS;
        this.VAILD_TIME = VAILD_TIME;
        this.APPID = APPID;
        this.SECRET = SECRET;
        this.SMS = SMS;
    }

    public String getSTORE_ID() {
        return STORE_ID;
    }

    public void setSTORE_ID(String STORE_ID) {
        this.STORE_ID = STORE_ID;
    }

    public String getSTORE_NAME() {
        return STORE_NAME;
    }

    public void setSTORE_NAME(String STORE_NAME) {
        this.STORE_NAME = STORE_NAME;
    }

    public String getSTORE_ADDR() {
        return STORE_ADDR;
    }

    public void setSTORE_ADDR(String STORE_ADDR) {
        this.STORE_ADDR = STORE_ADDR;
    }

    public String getSTORE_STATUS() {
        return STORE_STATUS;
    }

    public void setSTORE_STATUS(String STORE_STATUS) {
        this.STORE_STATUS = STORE_STATUS;
    }

    public String getSTORE_CODE() {
        return STORE_CODE;
    }

    public void setSTORE_CODE(String STORE_CODE) {
        this.STORE_CODE = STORE_CODE;
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

    public String getCLIENT_CODE() {
        return CLIENT_CODE;
    }

    public void setCLIENT_CODE(String CLIENT_CODE) {
        this.CLIENT_CODE = CLIENT_CODE;
    }

    public String getSTORE_DUTY_PERSON() {
        return STORE_DUTY_PERSON;
    }

    public void setSTORE_DUTY_PERSON(String STORE_DUTY_PERSON) {
        this.STORE_DUTY_PERSON = STORE_DUTY_PERSON;
    }

    public String getSTORE_DUTY_PHONE() {
        return STORE_DUTY_PHONE;
    }

    public void setSTORE_DUTY_PHONE(String STORE_DUTY_PHONE) {
        this.STORE_DUTY_PHONE = STORE_DUTY_PHONE;
    }

    public String getSTORE_IMG() {
        return STORE_IMG;
    }

    public void setSTORE_IMG(String STORE_IMG) {
        this.STORE_IMG = STORE_IMG;
    }

    public String getSYN_STATUS() {
        return SYN_STATUS;
    }

    public void setSYN_STATUS(String SYN_STATUS) {
        this.SYN_STATUS = SYN_STATUS;
    }

    public String getVAILD_TIME() {
        return VAILD_TIME;
    }

    public void setVAILD_TIME(String VAILD_TIME) {
        this.VAILD_TIME = VAILD_TIME;
    }

    public String getAPPID() {
        return APPID;
    }

    public void setAPPID(String APPID) {
        this.APPID = APPID;
    }

    public String getSECRET() {
        return SECRET;
    }

    public void setSECRET(String SECRET) {
        this.SECRET = SECRET;
    }

    public String getSMS() {
        return SMS;
    }

    public void setSMS(String SMS) {
        this.SMS = SMS;
    }
}
