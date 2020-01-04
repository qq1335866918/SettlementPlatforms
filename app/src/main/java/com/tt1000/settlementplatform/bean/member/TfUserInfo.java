package com.tt1000.settlementplatform.bean.member;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;

/**
 * 用户表
 */
@Entity(nameInDb = "tf_userinfo")
public class TfUserInfo {

    /**
     * U_ID : 15326760611921091505
     * UNO : 001
     * UNAME : 杨在
     * UPHONE : 13600148600
     * UADDR :
     * USEX : 0
     * CREATETIME : 1532676061000
     * UPDATETIME : 1532676061000
     * USTATUS : 0
     * ULOGINNO : 13600148600
     * UPASSWORD : BBBBBB
     * UTYPE : 3
     * STORE_CODE : 0000
     * CLIENT_CODE : 153267599744410
     */
    @Id
    @Property(nameInDb = "U_ID")
    private String U_ID;
    private String UNO;
    private String UNAME;
    private String UPHONE;
    private String UADDR;
    @Property(nameInDb = "UPAPERWORK_NO")
    private String UPAPERWORK_NO;
    private String USEX;
    private long CREATETIME;
    private long UPDATETIME;
    private String USTATUS;
    private String ULOGINNO;
    private String UPASSWORD;
    private String UTYPE;
    @Property(nameInDb = "STORE_CODE")
    private String STORE_CODE;
    @Property(nameInDb = "CLIENT_CODE")
    private String CLIENT_CODE;

    @Keep
//    @Generated(hash = 351087925)
    public TfUserInfo(String U_ID, String UNO, String UNAME, String UPHONE,
                      String UADDR, String UPAPERWORK_NO, String USEX, long CREATETIME,
                      long UPDATETIME, String USTATUS, String ULOGINNO, String UPASSWORD,
                      String UTYPE, String STORE_CODE, String CLIENT_CODE) {
        this.U_ID = U_ID;
        this.UNO = UNO;
        this.UNAME = UNAME;
        this.UPHONE = UPHONE;
        this.UADDR = UADDR;
        this.UPAPERWORK_NO = UPAPERWORK_NO;
        this.USEX = USEX;
        this.CREATETIME = CREATETIME;
        this.UPDATETIME = UPDATETIME;
        this.USTATUS = USTATUS;
        this.ULOGINNO = ULOGINNO;
        this.UPASSWORD = UPASSWORD;
        this.UTYPE = UTYPE;
        this.STORE_CODE = STORE_CODE;
        this.CLIENT_CODE = CLIENT_CODE;
    }
    @Keep
//    @Generated(hash = 1279772520)
    public TfUserInfo() {
    }

    public String getUPAPERWORK_NO() {
        return UPAPERWORK_NO;
    }

    public void setUPAPERWORK_NO(String UPAPERWORK_NO) {
        this.UPAPERWORK_NO = UPAPERWORK_NO;
    }

    public String getU_ID() {
        return U_ID;
    }

    public void setU_ID(String U_ID) {
        this.U_ID = U_ID;
    }

    public String getUNO() {
        return UNO;
    }

    public void setUNO(String UNO) {
        this.UNO = UNO;
    }

    public String getUNAME() {
        return UNAME;
    }

    public void setUNAME(String UNAME) {
        this.UNAME = UNAME;
    }

    public String getUPHONE() {
        return UPHONE;
    }

    public void setUPHONE(String UPHONE) {
        this.UPHONE = UPHONE;
    }

    public String getUADDR() {
        return UADDR;
    }

    public void setUADDR(String UADDR) {
        this.UADDR = UADDR;
    }

    public String getUSEX() {
        return USEX;
    }

    public void setUSEX(String USEX) {
        this.USEX = USEX;
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

    public String getUSTATUS() {
        return USTATUS;
    }

    public void setUSTATUS(String USTATUS) {
        this.USTATUS = USTATUS;
    }

    public String getULOGINNO() {
        return ULOGINNO;
    }

    public void setULOGINNO(String ULOGINNO) {
        this.ULOGINNO = ULOGINNO;
    }

    public String getUPASSWORD() {
        return UPASSWORD;
    }

    public void setUPASSWORD(String UPASSWORD) {
        this.UPASSWORD = UPASSWORD;
    }

    public String getUTYPE() {
        return UTYPE;
    }

    public void setUTYPE(String UTYPE) {
        this.UTYPE = UTYPE;
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
}
