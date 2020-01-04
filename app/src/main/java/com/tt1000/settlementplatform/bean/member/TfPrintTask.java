package com.tt1000.settlementplatform.bean.member;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;

/**
 * 打印任务
 */
@Entity
public class TfPrintTask {

    /**
     * SEQNO : 15332861530291541986
     * COR_ID : 15331795627501621980
     * PRINT_TYPE : 0
     * CREATETIME : 1533286153000
     * CLIENT_CODE : 153267599744410
     * STORE_CODE : 0000
     * PRINT_STATUS : 0
     * UPDATETIME : 1533286153000
     */
    @Id
    private String SEQNO;
    @Property(nameInDb = "COR_ID")
    private String COR_ID;
    @Property(nameInDb = "PRINT_TYPE")
    private String PRINT_TYPE;
    private long CREATETIME;
    @Property(nameInDb = "CLIENT_CODE")
    private String CLIENT_CODE;
    @Property(nameInDb = "STORE_CODE")
    private String STORE_CODE;
    @Property(nameInDb = "PRINTER_NAME")
    private String PRINTER_NAME;
    @Property(nameInDb = "PRINT_STATUS")
    private String PRINT_STATUS;
    private long UPDATETIME;

    @Keep
//    @Generated(hash = 1699380701)
    public TfPrintTask(String SEQNO, String COR_ID, String PRINT_TYPE,
                       long CREATETIME, String CLIENT_CODE, String STORE_CODE,
                       String PRINTER_NAME, String PRINT_STATUS, long UPDATETIME) {
        this.SEQNO = SEQNO;
        this.COR_ID = COR_ID;
        this.PRINT_TYPE = PRINT_TYPE;
        this.CREATETIME = CREATETIME;
        this.CLIENT_CODE = CLIENT_CODE;
        this.STORE_CODE = STORE_CODE;
        this.PRINTER_NAME = PRINTER_NAME;
        this.PRINT_STATUS = PRINT_STATUS;
        this.UPDATETIME = UPDATETIME;
    }

    @Keep
//    @Generated(hash = 1965228004)
    public TfPrintTask() {
    }

    public String getPRINTER_NAME() {
        return PRINTER_NAME;
    }

    public void setPRINTER_NAME(String PRINTER_NAME) {
        this.PRINTER_NAME = PRINTER_NAME;
    }

    public String getSEQNO() {
        return SEQNO;
    }

    public void setSEQNO(String SEQNO) {
        this.SEQNO = SEQNO;
    }

    public String getCOR_ID() {
        return COR_ID;
    }

    public void setCOR_ID(String COR_ID) {
        this.COR_ID = COR_ID;
    }

    public String getPRINT_TYPE() {
        return PRINT_TYPE;
    }

    public void setPRINT_TYPE(String PRINT_TYPE) {
        this.PRINT_TYPE = PRINT_TYPE;
    }

    public long getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(long CREATETIME) {
        this.CREATETIME = CREATETIME;
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

    public String getPRINT_STATUS() {
        return PRINT_STATUS;
    }

    public void setPRINT_STATUS(String PRINT_STATUS) {
        this.PRINT_STATUS = PRINT_STATUS;
    }

    public long getUPDATETIME() {
        return UPDATETIME;
    }

    public void setUPDATETIME(long UPDATETIME) {
        this.UPDATETIME = UPDATETIME;
    }
}
