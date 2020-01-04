package com.tt1000.settlementplatform.bean.member;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;

/**
 * 会员表
 */
@Entity(nameInDb = "tf_memberinfo")
public class TfMemberInfo {

    /**
     * MI_ID : 15327644364741281710
     * MI_NO :
     * MI_NAME : 李彬
     * MI_PHONE : 13678139111
     * MI_PAPERWORK_NO :
     * MI_ADDR :
     * MI_STATUS : 0
     * MI_TYPE : 15326772531251881199
     * COMPANY_ID : 0
     * BRANCH_ID : 0
     * ISM_STATUS : 2
     * CREATETIME : 1532764436000
     * UPDATETIME : 1532764436000
     * STORE_CODE : 0000
     * CLIENT_CODE : 153267599744410
     * MEMBER_IMG : 0
     * MI_SEX :
     * MI_EMAIL :
     * MI_AGE : 0
     * MI_BIRTH : 1532707200000
     */
    @Id
    @Property(nameInDb = "MI_ID")
    private String MI_ID;
    @Property(nameInDb = "OPEN_ID")
    private String OPEN_ID;
    @Property(nameInDb = "MI_NO")
    private String MI_NO;
    @Property(nameInDb = "MI_NAME")
    private String MI_NAME;
    @Property(nameInDb = "MI_PHONE")
    private String MI_PHONE;
    @Property(nameInDb = "MI_PAPERWORK_NO")
    private String MI_PAPERWORK_NO;
    @Property(nameInDb = "MI_ADDR")
    private String MI_ADDR;
    @Property(nameInDb = "MI_STATUS")
    private String MI_STATUS;
    @Property(nameInDb = "MI_TYPE")
    private String MI_TYPE;
    @Property(nameInDb = "MI_GRADE")
    private String MI_GRADE;
    @Property(nameInDb = "COMPANY_ID")
    private String COMPANY_ID;
    @Property(nameInDb = "BRANCH_ID")
    private String BRANCH_ID;
    @Property(nameInDb = "ISM_STATUS")
    private String ISM_STATUS;
    private long CREATETIME;
    private long UPDATETIME;
    @Property(nameInDb = "STORE_CODE")
    private String STORE_CODE;
    @Property(nameInDb = "CLIENT_CODE")
    private String CLIENT_CODE;
    @Property(nameInDb = "MI_PASSWORD")
    private String MI_PASSWORD;
    @Property(nameInDb = "MEMBER_IMG")
    private String MEMBER_IMG;
    private String STATE;
    @Property(nameInDb = "MI_SEX")
    private String MI_SEX;
    @Property(nameInDb = "MI_EMAIL")
    private String MI_EMAIL;
    @Property(nameInDb = "MI_AGE")
    private String MI_AGE;
    @Property(nameInDb = "MI_BIRTH")
    private long MI_BIRTH;

    @Keep
//    @Generated(hash = 1918002564)
    public TfMemberInfo(String MI_ID, String OPEN_ID, String MI_NO, String MI_NAME,
                        String MI_PHONE, String MI_PAPERWORK_NO, String MI_ADDR,
                        String MI_STATUS, String MI_TYPE, String MI_GRADE, String COMPANY_ID,
                        String BRANCH_ID, String ISM_STATUS, long CREATETIME, long UPDATETIME,
                        String STORE_CODE, String CLIENT_CODE, String MI_PASSWORD,
                        String MEMBER_IMG, String STATE, String MI_SEX, String MI_EMAIL,
                        String MI_AGE, long MI_BIRTH) {
        this.MI_ID = MI_ID;
        this.OPEN_ID = OPEN_ID;
        this.MI_NO = MI_NO;
        this.MI_NAME = MI_NAME;
        this.MI_PHONE = MI_PHONE;
        this.MI_PAPERWORK_NO = MI_PAPERWORK_NO;
        this.MI_ADDR = MI_ADDR;
        this.MI_STATUS = MI_STATUS;
        this.MI_TYPE = MI_TYPE;
        this.MI_GRADE = MI_GRADE;
        this.COMPANY_ID = COMPANY_ID;
        this.BRANCH_ID = BRANCH_ID;
        this.ISM_STATUS = ISM_STATUS;
        this.CREATETIME = CREATETIME;
        this.UPDATETIME = UPDATETIME;
        this.STORE_CODE = STORE_CODE;
        this.CLIENT_CODE = CLIENT_CODE;
        this.MI_PASSWORD = MI_PASSWORD;
        this.MEMBER_IMG = MEMBER_IMG;
        this.STATE = STATE;
        this.MI_SEX = MI_SEX;
        this.MI_EMAIL = MI_EMAIL;
        this.MI_AGE = MI_AGE;
        this.MI_BIRTH = MI_BIRTH;
    }

    @Keep
//    @Generated(hash = 175316736)
    public TfMemberInfo() {
    }

    public String getOPEN_ID() {
        return OPEN_ID;
    }

    public void setOPEN_ID(String OPEN_ID) {
        this.OPEN_ID = OPEN_ID;
    }

    public String getMI_GRADE() {
        return MI_GRADE;
    }

    public void setMI_GRADE(String MI_GRADE) {
        this.MI_GRADE = MI_GRADE;
    }

    public String getMI_PASSWORD() {
        return MI_PASSWORD;
    }

    public void setMI_PASSWORD(String MI_PASSWORD) {
        this.MI_PASSWORD = MI_PASSWORD;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getMI_ID() {
        return MI_ID;
    }

    public void setMI_ID(String MI_ID) {
        this.MI_ID = MI_ID;
    }

    public String getMI_NO() {
        return MI_NO;
    }

    public void setMI_NO(String MI_NO) {
        this.MI_NO = MI_NO;
    }

    public String getMI_NAME() {
        return MI_NAME;
    }

    public void setMI_NAME(String MI_NAME) {
        this.MI_NAME = MI_NAME;
    }

    public String getMI_PHONE() {
        return MI_PHONE;
    }

    public void setMI_PHONE(String MI_PHONE) {
        this.MI_PHONE = MI_PHONE;
    }

    public String getMI_PAPERWORK_NO() {
        return MI_PAPERWORK_NO;
    }

    public void setMI_PAPERWORK_NO(String MI_PAPERWORK_NO) {
        this.MI_PAPERWORK_NO = MI_PAPERWORK_NO;
    }

    public String getMI_ADDR() {
        return MI_ADDR;
    }

    public void setMI_ADDR(String MI_ADDR) {
        this.MI_ADDR = MI_ADDR;
    }

    public String getMI_STATUS() {
        return MI_STATUS;
    }

    public void setMI_STATUS(String MI_STATUS) {
        this.MI_STATUS = MI_STATUS;
    }

    public String getMI_TYPE() {
        return MI_TYPE;
    }

    public void setMI_TYPE(String MI_TYPE) {
        this.MI_TYPE = MI_TYPE;
    }

    public String getCOMPANY_ID() {
        return COMPANY_ID;
    }

    public void setCOMPANY_ID(String COMPANY_ID) {
        this.COMPANY_ID = COMPANY_ID;
    }

    public String getBRANCH_ID() {
        return BRANCH_ID;
    }

    public void setBRANCH_ID(String BRANCH_ID) {
        this.BRANCH_ID = BRANCH_ID;
    }

    public String getISM_STATUS() {
        return ISM_STATUS;
    }

    public void setISM_STATUS(String ISM_STATUS) {
        this.ISM_STATUS = ISM_STATUS;
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

    public String getMEMBER_IMG() {
        return MEMBER_IMG;
    }

    public void setMEMBER_IMG(String MEMBER_IMG) {
        this.MEMBER_IMG = MEMBER_IMG;
    }

    public String getMI_SEX() {
        return MI_SEX;
    }

    public void setMI_SEX(String MI_SEX) {
        this.MI_SEX = MI_SEX;
    }

    public String getMI_EMAIL() {
        return MI_EMAIL;
    }

    public void setMI_EMAIL(String MI_EMAIL) {
        this.MI_EMAIL = MI_EMAIL;
    }

    public String getMI_AGE() {
        return MI_AGE;
    }

    public void setMI_AGE(String MI_AGE) {
        this.MI_AGE = MI_AGE;
    }

    public long getMI_BIRTH() {
        return MI_BIRTH;
    }

    public void setMI_BIRTH(long MI_BIRTH) {
        this.MI_BIRTH = MI_BIRTH;
    }
}
