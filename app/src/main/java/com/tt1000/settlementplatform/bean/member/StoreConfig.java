package com.tt1000.settlementplatform.bean.member;

import com.tt1000.settlementplatform.utils.MyUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * 店铺配置
 */
@Entity
public class StoreConfig {

    /**
     * SEQNO : 15326760613381121109
     * STORE_CODE : 0000
     * CLIENT_CODE : 153267599744410
     * CLOUD : 1
     * STORE_TYPE : 0
     * CREATETIME : 1532676061000
     * UPDATETIME : 1532676061000
     * ROUND : 1
     * SET_MEAL : 1
     * DISCOUNT : 1
     * LOGOUT : 0
     * OD_MONEY : 0
     * ONLINE_DISCOUNT : 0
     * INLINE_DISCOUNT : 0
     * TAKE_OUT_CHECK : 0
     * IS_MEMBER_JOIN : 0
     * CARD_COST : 900852
     * SUBSIDY_JUDGE : 0
     * PRICING : 900853
     * IS_OPEN_SCAN_DESK : 0
     * IS_AUTO_PRINT : 1
     */

    @Id
    private String SEQNO;
    @Property(nameInDb = "STORE_CODE")
    private String STORE_CODE;
    @Property(nameInDb = "CLIENT_CODE")
    private String CLIENT_CODE;
    private String CLOUD;
    @Property(nameInDb = "STORE_TYPE")
    private String STORE_TYPE;
    private long CREATETIME;
    private long UPDATETIME;
    private String ROUND;
    @Property(nameInDb = "SET_MEAL")
    private String SET_MEAL;
    private String DISCOUNT;
    private String LOGOUT;
    @Property(nameInDb = "OD_MONEY")
    private String OD_MONEY;
    @Property(nameInDb = "ONLINE_DISCOUNT")
    private String ONLINE_DISCOUNT;
    @Property(nameInDb = "INLINE_DISCOUNT")
    private String INLINE_DISCOUNT;
    @Property(nameInDb = "TAKE_OUT_CHECK")
    private String TAKE_OUT_CHECK;
    @Property(nameInDb = "IS_MEMBER_JOIN")
    private String IS_MEMBER_JOIN;
    @Property(nameInDb = "CARD_COST")
    private String CARD_COST;
    // extra data
    @Property(nameInDb = "SUBSIDY_JUDGE")
    private String SUBSIDY_JUDGE;
    private String PRICING;
    @Property(nameInDb = "IS_OPEN_SCAN_DESK")
    private String IS_OPEN_SCAN_DESK;
    @Property(nameInDb = "IS_AUTO_PRINT")
    private String IS_AUTO_PRINT;

    @Generated(hash = 1075701735)
    public StoreConfig(String SEQNO, String STORE_CODE, String CLIENT_CODE,
            String CLOUD, String STORE_TYPE, long CREATETIME, long UPDATETIME,
            String ROUND, String SET_MEAL, String DISCOUNT, String LOGOUT,
            String OD_MONEY, String ONLINE_DISCOUNT, String INLINE_DISCOUNT,
            String TAKE_OUT_CHECK, String IS_MEMBER_JOIN, String CARD_COST,
            String SUBSIDY_JUDGE, String PRICING, String IS_OPEN_SCAN_DESK,
            String IS_AUTO_PRINT) {
        this.SEQNO = SEQNO;
        this.STORE_CODE = STORE_CODE;
        this.CLIENT_CODE = CLIENT_CODE;
        this.CLOUD = CLOUD;
        this.STORE_TYPE = STORE_TYPE;
        this.CREATETIME = CREATETIME;
        this.UPDATETIME = UPDATETIME;
        this.ROUND = ROUND;
        this.SET_MEAL = SET_MEAL;
        this.DISCOUNT = DISCOUNT;
        this.LOGOUT = LOGOUT;
        this.OD_MONEY = OD_MONEY;
        this.ONLINE_DISCOUNT = ONLINE_DISCOUNT;
        this.INLINE_DISCOUNT = INLINE_DISCOUNT;
        this.TAKE_OUT_CHECK = TAKE_OUT_CHECK;
        this.IS_MEMBER_JOIN = IS_MEMBER_JOIN;
        this.CARD_COST = CARD_COST;
        this.SUBSIDY_JUDGE = SUBSIDY_JUDGE;
        this.PRICING = PRICING;
        this.IS_OPEN_SCAN_DESK = IS_OPEN_SCAN_DESK;
        this.IS_AUTO_PRINT = IS_AUTO_PRINT;
    }

    @Generated(hash = 1052004871)
    public StoreConfig() {
    }

    public String getSEQNO() {
        return SEQNO;
    }

    public void setSEQNO(String SEQNO) {
        this.SEQNO = SEQNO;
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

    public String getCLOUD() {
        return CLOUD;
    }

    public void setCLOUD(String CLOUD) {
        this.CLOUD = CLOUD;
    }

    public String getSTORE_TYPE() {
        return STORE_TYPE;
    }

    public void setSTORE_TYPE(String STORE_TYPE) {
        this.STORE_TYPE = STORE_TYPE;
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

    public String getROUND() {
        return ROUND;
    }

    public void setROUND(String ROUND) {
        this.ROUND = ROUND;
    }

    public String getSET_MEAL() {
        return SET_MEAL;
    }

    public void setSET_MEAL(String SET_MEAL) {
        this.SET_MEAL = SET_MEAL;
    }

    public String getDISCOUNT() {
        return DISCOUNT;
    }

    public void setDISCOUNT(String DISCOUNT) {
        this.DISCOUNT = DISCOUNT;
    }

    public String getLOGOUT() {
        return LOGOUT;
    }

    public void setLOGOUT(String LOGOUT) {
        this.LOGOUT = LOGOUT;
    }

    public String getOD_MONEY() {
        return OD_MONEY;
    }

    public void setOD_MONEY(String OD_MONEY) {
        this.OD_MONEY = OD_MONEY;
    }

    public String getONLINE_DISCOUNT() {
        return ONLINE_DISCOUNT;
    }

    public void setONLINE_DISCOUNT(String ONLINE_DISCOUNT) {
        this.ONLINE_DISCOUNT = ONLINE_DISCOUNT;
    }

    public String getINLINE_DISCOUNT() {
        return INLINE_DISCOUNT;
    }

    public void setINLINE_DISCOUNT(String INLINE_DISCOUNT) {
        this.INLINE_DISCOUNT = INLINE_DISCOUNT;
    }

    public String getTAKE_OUT_CHECK() {
        return TAKE_OUT_CHECK;
    }

    public void setTAKE_OUT_CHECK(String TAKE_OUT_CHECK) {
        this.TAKE_OUT_CHECK = TAKE_OUT_CHECK;
    }

    public String getIS_MEMBER_JOIN() {
        return IS_MEMBER_JOIN;
    }

    public void setIS_MEMBER_JOIN(String IS_MEMBER_JOIN) {
        this.IS_MEMBER_JOIN = IS_MEMBER_JOIN;
    }

    public String getCARD_COST() {
        return CARD_COST;
    }

    public void setCARD_COST(String CARD_COST) {
        this.CARD_COST = CARD_COST;
    }

    public String getSUBSIDY_JUDGE() {
        return SUBSIDY_JUDGE;
    }

    public void setSUBSIDY_JUDGE(String SUBSIDY_JUDGE) {
        this.SUBSIDY_JUDGE = SUBSIDY_JUDGE;
    }

    public String getPRICING() {
        return PRICING;
    }

    public void setPRICING(String PRICING) {
        this.PRICING = PRICING;
    }

    public String getIS_OPEN_SCAN_DESK() {
        return IS_OPEN_SCAN_DESK;
    }

    public void setIS_OPEN_SCAN_DESK(String IS_OPEN_SCAN_DESK) {
        this.IS_OPEN_SCAN_DESK = IS_OPEN_SCAN_DESK;
    }

    public String getIS_AUTO_PRINT() {
        return IS_AUTO_PRINT;
    }

    public void setIS_AUTO_PRINT(String IS_AUTO_PRINT) {
        this.IS_AUTO_PRINT = IS_AUTO_PRINT;
    }
}
