package com.tt1000.settlementplatform.bean.member;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 会员卡信息
 */
@Entity(nameInDb = "tf_cardinfo")
public class TfCardInfo {

    /**
     * IC_ID : 3386616374
     * CREATETIME : 1536055648000
     * UPDATETIME : 1536055648000
     * IC_TYPE : 0
     * MI_ID : 15357698832101321536
     * IC_SERIAL_NUMBER : 3386616374
     * U_ID : 0
     * IC_STATUS : 0
     * STORE_CODE : 0000
     * CLIENT_CODE : 153301827628914
     */

    @Id
    @Property(nameInDb = "IC_ID")
    private String IC_ID;
    private long CREATETIME;
    private long UPDATETIME;
    @Property(nameInDb = "IC_TYPE")
    private String IC_TYPE;
    @Property(nameInDb = "MI_ID")
    private String MI_ID;
    @Property(nameInDb = "IC_SERIAL_NUMBER")
    private String IC_SERIAL_NUMBER;
    @Property(nameInDb = "U_ID")
    private String U_ID;
    @Property(nameInDb = "IC_STATUS")
    private String IC_STATUS;
    @Property(nameInDb = "STORE_CODE")
    private String STORE_CODE;
    @Property(nameInDb = "CLIENT_CODE")
    private String CLIENT_CODE;

    @Keep
//    @Generated(hash = 1669996199)
    public TfCardInfo(String IC_ID, long CREATETIME, long UPDATETIME,
            String IC_TYPE, String MI_ID, String IC_SERIAL_NUMBER, String U_ID,
            String IC_STATUS, String STORE_CODE, String CLIENT_CODE) {
        this.IC_ID = IC_ID;
        this.CREATETIME = CREATETIME;
        this.UPDATETIME = UPDATETIME;
        this.IC_TYPE = IC_TYPE;
        this.MI_ID = MI_ID;
        this.IC_SERIAL_NUMBER = IC_SERIAL_NUMBER;
        this.U_ID = U_ID;
        this.IC_STATUS = IC_STATUS;
        this.STORE_CODE = STORE_CODE;
        this.CLIENT_CODE = CLIENT_CODE;
    }

    @Keep
    //    @Generated(hash = 882718375)
    public TfCardInfo() {
    }

    public String getIC_ID() {
        return IC_ID;
    }

    public void setIC_ID(String IC_ID) {
        this.IC_ID = IC_ID;
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

    public String getIC_TYPE() {
        return IC_TYPE;
    }

    public void setIC_TYPE(String IC_TYPE) {
        this.IC_TYPE = IC_TYPE;
    }

    public String getMI_ID() {
        return MI_ID;
    }

    public void setMI_ID(String MI_ID) {
        this.MI_ID = MI_ID;
    }

    public String getIC_SERIAL_NUMBER() {
        return IC_SERIAL_NUMBER;
    }

    public void setIC_SERIAL_NUMBER(String IC_SERIAL_NUMBER) {
        this.IC_SERIAL_NUMBER = IC_SERIAL_NUMBER;
    }

    public String getU_ID() {
        return U_ID;
    }

    public void setU_ID(String U_ID) {
        this.U_ID = U_ID;
    }

    public String getIC_STATUS() {
        return IC_STATUS;
    }

    public void setIC_STATUS(String IC_STATUS) {
        this.IC_STATUS = IC_STATUS;
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

    public void setCLIENT_CODE(String CLINET_CODE) {
        this.CLIENT_CODE = CLINET_CODE;
    }
}
