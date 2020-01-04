package com.tt1000.settlementplatform.bean.pay;

import java.util.List;

/**
 * 上传给服务器，本地的订单信息
 */
public class InlineUpdateInfo {

    private List<OrderBean> order;
    private List<DetailsBean> details;
    private List<CardBean> card;

    public List<OrderBean> getOrder() {
        return order;
    }

    public void setOrder(List<OrderBean> order) {
        this.order = order;
    }

    public List<CardBean> getCard() {
        return card;
    }

    public void setCard(List<CardBean> card) {
        this.card = card;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }



    public static class OrderBean {
        /**
         * COR_ID : 153300086517210614490
         * COR_MONERY : 10.00
         * COR_AMOUNT : 10.00
         * MACHINE_NO : cloud
         * ISM_STATUS : 0
         * STORE_CODE : 0000
         * CREATETIME : 2018-07-31 09:34:25
         * COR_TYPE : 1
         * ADDR_ID : 49
         * CLIENT_CODE : 153267599744410
         * UPDATETIME : 2018-07-31 09:34:25
         */

        private String COR_ID;
        private String COR_MONERY;
        private String COR_AMOUNT;
        private String MACHINE_NO;
        private String ISM_STATUS;
        private String STORE_CODE;
        private String CREATETIME;
        private String COR_TYPE;
        private String ADDR_ID;
        private String CLIENT_CODE;
        private String UPDATETIME;

        public String getCOR_ID() {
            return COR_ID;
        }

        public void setCOR_ID(String COR_ID) {
            this.COR_ID = COR_ID;
        }

        public String getCOR_MONERY() {
            return COR_MONERY;
        }

        public void setCOR_MONERY(String COR_MONERY) {
            this.COR_MONERY = COR_MONERY;
        }

        public String getCOR_AMOUNT() {
            return COR_AMOUNT;
        }

        public void setCOR_AMOUNT(String COR_AMOUNT) {
            this.COR_AMOUNT = COR_AMOUNT;
        }

        public String getMACHINE_NO() {
            return MACHINE_NO;
        }

        public void setMACHINE_NO(String MACHINE_NO) {
            this.MACHINE_NO = MACHINE_NO;
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

        public String getCREATETIME() {
            return CREATETIME;
        }

        public void setCREATETIME(String CREATETIME) {
            this.CREATETIME = CREATETIME;
        }

        public String getCOR_TYPE() {
            return COR_TYPE;
        }

        public void setCOR_TYPE(String COR_TYPE) {
            this.COR_TYPE = COR_TYPE;
        }

        public String getADDR_ID() {
            return ADDR_ID;
        }

        public void setADDR_ID(String ADDR_ID) {
            this.ADDR_ID = ADDR_ID;
        }

        public String getCLIENT_CODE() {
            return CLIENT_CODE;
        }

        public void setCLIENT_CODE(String CLIENT_CODE) {
            this.CLIENT_CODE = CLIENT_CODE;
        }

        public String getUPDATETIME() {
            return UPDATETIME;
        }

        public void setUPDATETIME(String UPDATETIME) {
            this.UPDATETIME = UPDATETIME;
        }
    }

    public static class DetailsBean {
        /**
         * COR_ID : 153300086517210614490
         * CDR_UNIT_PRICE : 10
         * CDR_MONEY : 10.0
         * CDR_NO : 900881
         * CDR_TYPE : 2
         * ISM_STATUS : 0
         * STORE_CODE : 0000
         * CREATETIME : 2018-07-31 09:34:25
         * CDR_NUMBER : 1
         * CLIENT_CODE : 153267599744410
         * CDR_ID : 15330008651721501262
         */

        private String COR_ID;
        private String CDR_UNIT_PRICE;
        private String CDR_MONEY;
        private String CDR_NO;
        private String CDR_TYPE;
        private String ISM_STATUS;
        private String STORE_CODE;
        private String CREATETIME;
        private String CDR_NUMBER;
        private String CLIENT_CODE;
        private String CDR_ID;

        public String getCOR_ID() {
            return COR_ID;
        }

        public void setCOR_ID(String COR_ID) {
            this.COR_ID = COR_ID;
        }

        public String getCDR_UNIT_PRICE() {
            return CDR_UNIT_PRICE;
        }

        public void setCDR_UNIT_PRICE(String CDR_UNIT_PRICE) {
            this.CDR_UNIT_PRICE = CDR_UNIT_PRICE;
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

        public String getCREATETIME() {
            return CREATETIME;
        }

        public void setCREATETIME(String CREATETIME) {
            this.CREATETIME = CREATETIME;
        }

        public String getCDR_NUMBER() {
            return CDR_NUMBER;
        }

        public void setCDR_NUMBER(String CDR_NUMBER) {
            this.CDR_NUMBER = CDR_NUMBER;
        }

        public String getCLIENT_CODE() {
            return CLIENT_CODE;
        }

        public void setCLIENT_CODE(String CLIENT_CODE) {
            this.CLIENT_CODE = CLIENT_CODE;
        }

        public String getCDR_ID() {
            return CDR_ID;
        }

        public void setCDR_ID(String CDR_ID) {
            this.CDR_ID = CDR_ID;
        }
    }

    public static class CardBean {
        /**
         * COR_ID : 153300086517210614490
         * CCR_MONEY : 10.00
         * IC_ID : 0030004487
         * MI_ID : 2
         * MACHINE_NO : cloud
         * CCR_ID : 15330008651741331999
         * ISM_STATUS : 0
         * STORE_CODE : 0000
         * CREATETIME : 2018-07-31 09:34:25
         * CLIENT_CODE : 153267599744410
         * CCR_PAY_TYPE : 1
         * CCR_STATUS : 1
         */

        private String COR_ID;
        private String CCR_MONEY;
        private String IC_ID;
        private String MI_ID;
        private String MACHINE_NO;
        private String CCR_ID;
        private String ISM_STATUS;
        private String STORE_CODE;
        private String CREATETIME;
        private String CLIENT_CODE;
        private String CCR_PAY_TYPE;
        private String CCR_STATUS;
        private String CCR_ORIGINALAMOUNT;
        private String U_ID;
        private String CCR_UPLOAD_TIME;

        public String getMT_ID() {
            return MT_ID;
        }

        public void setMT_ID(String MT_ID) {
            this.MT_ID = MT_ID;
        }

        private String MT_ID;

        public String getCOR_ID() {
            return COR_ID;
        }

        public void setCOR_ID(String COR_ID) {
            this.COR_ID = COR_ID;
        }

        public String getCCR_MONEY() {
            return CCR_MONEY;
        }

        public void setCCR_MONEY(String CCR_MONEY) {
            this.CCR_MONEY = CCR_MONEY;
        }

        public String getIC_ID() {
            return IC_ID;
        }

        public void setIC_ID(String IC_ID) {
            this.IC_ID = IC_ID;
        }

        public String getMI_ID() {
            return MI_ID;
        }

        public void setMI_ID(String MI_ID) {
            this.MI_ID = MI_ID;
        }

        public String getMACHINE_NO() {
            return MACHINE_NO;
        }

        public void setMACHINE_NO(String MACHINE_NO) {
            this.MACHINE_NO = MACHINE_NO;
        }

        public String getCCR_ID() {
            return CCR_ID;
        }

        public void setCCR_ID(String CCR_ID) {
            this.CCR_ID = CCR_ID;
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

        public String getCREATETIME() {
            return CREATETIME;
        }

        public void setCREATETIME(String CREATETIME) {
            this.CREATETIME = CREATETIME;
        }

        public String getCLIENT_CODE() {
            return CLIENT_CODE;
        }

        public void setCLIENT_CODE(String CLIENT_CODE) {
            this.CLIENT_CODE = CLIENT_CODE;
        }

        public String getCCR_PAY_TYPE() {
            return CCR_PAY_TYPE;
        }

        public void setCCR_PAY_TYPE(String CCR_PAY_TYPE) {
            this.CCR_PAY_TYPE = CCR_PAY_TYPE;
        }

        public String getCCR_STATUS() {
            return CCR_STATUS;
        }

        public void setCCR_STATUS(String CCR_STATUS) {
            this.CCR_STATUS = CCR_STATUS;
        }

        public String getCCR_ORIGINALAMOUNT() {
            return CCR_ORIGINALAMOUNT;
        }

        public void setCCR_ORIGINALAMOUNT(String CCR_ORIGINALAMOUNT) {
            this.CCR_ORIGINALAMOUNT = CCR_ORIGINALAMOUNT;
        }

        public String getU_ID() {
            return U_ID;
        }

        public void setU_ID(String u_ID) {
            U_ID = u_ID;
        }

        public String getCCR_UPLOAD_TIME() {
            return CCR_UPLOAD_TIME;
        }

        public void setCCR_UPLOAD_TIME(String CCR_UPLOAD_TIME) {
            this.CCR_UPLOAD_TIME = CCR_UPLOAD_TIME;
        }
    }
}
