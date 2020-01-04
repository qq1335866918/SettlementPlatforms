package com.tt1000.settlementplatform.bean.member;

import com.tt1000.settlementplatform.utils.MyUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

/**
 * 商品表
 */
@Entity
public class CommodityRecord {

    /**
     * CI_ID : 900855
     * CI_NO : 0000153267599744410150
     * CI_NAME : 麻婆豆腐
     * CI_NUMBER :
     * CI_PRICE : 5
     * CI_NUM : 0
     * CI_IMPORTPRICE : 5
     * CI_SALEPRICE : 0
     * CI_MEMBERPRICE : 5
     * CI_UNIT :
     * CI_DISCOUNT :
     * CI_REMARKS :
     * CREATETIME : 1532676735000
     * UPDATETIME : 1532676735000
     * USE_STATUS : 0
     * CT_ID : 15326766192211411232
     * CI_SPELL :
     * CLIENT_CODE : 153267599744410
     * STORE_CODE : 0000
     * CI_IMPPRICE1 : 0
     * CI_IMPPRICE2 : 0
     * CI_IMPPRICE3 : 0
     * CI_MINPRICE : 0
     * CI_CONENT :
     * UU_ID : 15326767353231231321
     */
    @Property(nameInDb = "CI_ID")
    @Id
    private long CI_ID;
    @Property(nameInDb = "CI_NO")
    private String CI_NO;
    @Property(nameInDb = "CI_NAME")
    private String CI_NAME;
    @Property(nameInDb = "CI_NUMBER")
    private String CI_NUMBER;
    @Property(nameInDb = "CI_PRICE")
    private String CI_PRICE;
    @Property(nameInDb = "CI_NUM")
    private String CI_NUM;
    @Property(nameInDb = "CI_IMPORTPRICE")
    private String CI_IMPORTPRICE;
    @Property(nameInDb = "CI_SALEPRICE")
    private String CI_SALEPRICE;
    @Property(nameInDb = "CI_MEMBERPRICE")
    private String CI_MEMBERPRICE;
    @Property(nameInDb = "CI_UNIT")
    private String CI_UNIT;
    @Property(nameInDb = "CI_VOLUM")
    private String CI_VOLUM;
    @Property(nameInDb = "CI_DISCOUNT")
    private String CI_DISCOUNT;
    @Property(nameInDb = "CI_REMARKS")
    private String CI_REMARKS;
    private long CREATETIME;
    private long UPDATETIME;
    @Property(nameInDb = "U_ID")
    private String U_ID;
    @Property(nameInDb = "USE_STATUS")
    private String USE_STATUS;
    @Property(nameInDb = "CT_ID")
    private String CT_ID;
    @Property(nameInDb = "CI_SPELL")
    private String CI_SPELL;
    @Property(nameInDb = "CLIENT_CODE")
    private String CLIENT_CODE;
    @Property(nameInDb = "STORE_CODE")
    private String STORE_CODE;
    @Property(nameInDb = "CI_IMG")
    private String CI_IMG;
    @Property(nameInDb = "CI_IMPPRICE1")
    private String CI_IMPPRICE1;
    @Property(nameInDb = "CI_IMPPRICE2")
    private String CI_IMPPRICE2;
    @Property(nameInDb = "CI_IMPPRICE3")
    private String CI_IMPPRICE3;
    @Property(nameInDb = "CI_MINPRICE")
    private String CI_MINPRICE;
    @Property(nameInDb = "CI_CONENT")
    private String CI_CONENT;
    @Property(nameInDb = "UU_ID")
    private String UU_ID;

//    @Generated(hash = 1510249199)
    @Keep
    public CommodityRecord(int CI_ID, String CI_NO, String CI_NAME,
            String CI_NUMBER, String CI_PRICE, String CI_NUM, String CI_IMPORTPRICE,
            String CI_SALEPRICE, String CI_MEMBERPRICE, String CI_UNIT,
            String CI_VOLUM, String CI_DISCOUNT, String CI_REMARKS, long CREATETIME,
            long UPDATETIME, String U_ID, String USE_STATUS, String CT_ID,
            String CI_SPELL, String CLIENT_CODE, String STORE_CODE, String CI_IMG,
            String CI_IMPPRICE1, String CI_IMPPRICE2, String CI_IMPPRICE3,
            String CI_MINPRICE, String CI_CONENT, String UU_ID) {
        this.CI_ID = CI_ID;
        this.CI_NO = CI_NO;
        this.CI_NAME = CI_NAME;
        this.CI_NUMBER = CI_NUMBER;
        this.CI_PRICE = CI_PRICE;
        this.CI_NUM = CI_NUM;
        this.CI_IMPORTPRICE = CI_IMPORTPRICE;
        this.CI_SALEPRICE = CI_SALEPRICE;
        this.CI_MEMBERPRICE = CI_MEMBERPRICE;
        this.CI_UNIT = CI_UNIT;
        this.CI_VOLUM = CI_VOLUM;
        this.CI_DISCOUNT = CI_DISCOUNT;
        this.CI_REMARKS = CI_REMARKS;
        this.CREATETIME = CREATETIME;
        this.UPDATETIME = UPDATETIME;
        this.U_ID = U_ID;
        this.USE_STATUS = USE_STATUS;
        this.CT_ID = CT_ID;
        this.CI_SPELL = CI_SPELL;
        this.CLIENT_CODE = CLIENT_CODE;
        this.STORE_CODE = STORE_CODE;
        this.CI_IMG = CI_IMG;
        this.CI_IMPPRICE1 = CI_IMPPRICE1;
        this.CI_IMPPRICE2 = CI_IMPPRICE2;
        this.CI_IMPPRICE3 = CI_IMPPRICE3;
        this.CI_MINPRICE = CI_MINPRICE;
        this.CI_CONENT = CI_CONENT;
        this.UU_ID = UU_ID;
    }

//    @Generated(hash = 226298517)
    @Keep
    public CommodityRecord() {
    }

    @Generated(hash = 1695737015)
    public CommodityRecord(long CI_ID, String CI_NO, String CI_NAME,
            String CI_NUMBER, String CI_PRICE, String CI_NUM, String CI_IMPORTPRICE,
            String CI_SALEPRICE, String CI_MEMBERPRICE, String CI_UNIT,
            String CI_VOLUM, String CI_DISCOUNT, String CI_REMARKS, long CREATETIME,
            long UPDATETIME, String U_ID, String USE_STATUS, String CT_ID,
            String CI_SPELL, String CLIENT_CODE, String STORE_CODE, String CI_IMG,
            String CI_IMPPRICE1, String CI_IMPPRICE2, String CI_IMPPRICE3,
            String CI_MINPRICE, String CI_CONENT, String UU_ID) {
        this.CI_ID = CI_ID;
        this.CI_NO = CI_NO;
        this.CI_NAME = CI_NAME;
        this.CI_NUMBER = CI_NUMBER;
        this.CI_PRICE = CI_PRICE;
        this.CI_NUM = CI_NUM;
        this.CI_IMPORTPRICE = CI_IMPORTPRICE;
        this.CI_SALEPRICE = CI_SALEPRICE;
        this.CI_MEMBERPRICE = CI_MEMBERPRICE;
        this.CI_UNIT = CI_UNIT;
        this.CI_VOLUM = CI_VOLUM;
        this.CI_DISCOUNT = CI_DISCOUNT;
        this.CI_REMARKS = CI_REMARKS;
        this.CREATETIME = CREATETIME;
        this.UPDATETIME = UPDATETIME;
        this.U_ID = U_ID;
        this.USE_STATUS = USE_STATUS;
        this.CT_ID = CT_ID;
        this.CI_SPELL = CI_SPELL;
        this.CLIENT_CODE = CLIENT_CODE;
        this.STORE_CODE = STORE_CODE;
        this.CI_IMG = CI_IMG;
        this.CI_IMPPRICE1 = CI_IMPPRICE1;
        this.CI_IMPPRICE2 = CI_IMPPRICE2;
        this.CI_IMPPRICE3 = CI_IMPPRICE3;
        this.CI_MINPRICE = CI_MINPRICE;
        this.CI_CONENT = CI_CONENT;
        this.UU_ID = UU_ID;
    }

    public String getCI_VOLUM() {
        return CI_VOLUM;
    }

    public void setCI_VOLUM(String CI_VOLUM) {
        this.CI_VOLUM = CI_VOLUM;
    }

    public String getU_ID() {
        return U_ID;
    }

    public void setU_ID(String u_ID) {
        U_ID = u_ID;
    }

    public String getCI_IMG() {
        return CI_IMG;
    }

    public void setCI_IMG(String CI_IMG) {
        this.CI_IMG = CI_IMG;
    }

    public long getCI_ID() {
        return CI_ID;
    }

    public void setCI_ID(long CI_ID) {
        this.CI_ID = CI_ID;
    }

    public String getCI_NO() {
        return CI_NO;
    }

    public void setCI_NO(String CI_NO) {
        this.CI_NO = CI_NO;
    }

    public String getCI_NAME() {
        return CI_NAME;
    }

    public void setCI_NAME(String CI_NAME) {
        this.CI_NAME = CI_NAME;
    }

    public String getCI_NUMBER() {
        return CI_NUMBER;
    }

    public void setCI_NUMBER(String CI_NUMBER) {
        this.CI_NUMBER = CI_NUMBER;
    }

    public String getCI_PRICE() {
        return CI_PRICE;
    }

    public void setCI_PRICE(String CI_PRICE) {
        this.CI_PRICE = CI_PRICE;
    }

    public String getCI_NUM() {
        return CI_NUM;
    }

    public void setCI_NUM(String CI_NUM) {
        this.CI_NUM = CI_NUM;
    }

    public String getCI_IMPORTPRICE() {
        return CI_IMPORTPRICE;
    }

    public void setCI_IMPORTPRICE(String CI_IMPORTPRICE) {
        this.CI_IMPORTPRICE = CI_IMPORTPRICE;
    }

    public String getCI_SALEPRICE() {
        return CI_SALEPRICE;
    }

    public void setCI_SALEPRICE(String CI_SALEPRICE) {
        this.CI_SALEPRICE = CI_SALEPRICE;
    }

    public String getCI_MEMBERPRICE() {
        return CI_MEMBERPRICE;
    }

    public void setCI_MEMBERPRICE(String CI_MEMBERPRICE) {
        this.CI_MEMBERPRICE = CI_MEMBERPRICE;
    }

    public String getCI_UNIT() {
        return CI_UNIT;
    }

    public void setCI_UNIT(String CI_UNIT) {
        this.CI_UNIT = CI_UNIT;
    }

    public String getCI_DISCOUNT() {
        return CI_DISCOUNT;
    }

    public void setCI_DISCOUNT(String CI_DISCOUNT) {
        this.CI_DISCOUNT = CI_DISCOUNT;
    }

    public String getCI_REMARKS() {
        return CI_REMARKS;
    }

    public void setCI_REMARKS(String CI_REMARKS) {
        this.CI_REMARKS = CI_REMARKS;
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

    public String getUSE_STATUS() {
        return USE_STATUS;
    }

    public void setUSE_STATUS(String USE_STATUS) {
        this.USE_STATUS = USE_STATUS;
    }

    public String getCT_ID() {
        return CT_ID;
    }

    public void setCT_ID(String CT_ID) {
        this.CT_ID = CT_ID;
    }

    public String getCI_SPELL() {
        return CI_SPELL;
    }

    public void setCI_SPELL(String CI_SPELL) {
        this.CI_SPELL = CI_SPELL;
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

    public String getCI_IMPPRICE1() {
        return CI_IMPPRICE1;
    }

    public void setCI_IMPPRICE1(String CI_IMPPRICE1) {
        this.CI_IMPPRICE1 = CI_IMPPRICE1;
    }

    public String getCI_IMPPRICE2() {
        return CI_IMPPRICE2;
    }

    public void setCI_IMPPRICE2(String CI_IMPPRICE2) {
        this.CI_IMPPRICE2 = CI_IMPPRICE2;
    }

    public String getCI_IMPPRICE3() {
        return CI_IMPPRICE3;
    }

    public void setCI_IMPPRICE3(String CI_IMPPRICE3) {
        this.CI_IMPPRICE3 = CI_IMPPRICE3;
    }

    public String getCI_MINPRICE() {
        return CI_MINPRICE;
    }

    public void setCI_MINPRICE(String CI_MINPRICE) {
        this.CI_MINPRICE = CI_MINPRICE;
    }

    public String getCI_CONENT() {
        return CI_CONENT;
    }

    public void setCI_CONENT(String CI_CONENT) {
        this.CI_CONENT = CI_CONENT;
    }

    public String getUU_ID() {
        return UU_ID;
    }

    public void setUU_ID(String UU_ID) {
        this.UU_ID = UU_ID;
    }
}
