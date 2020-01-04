package com.tt1000.settlementplatform.bean.result_info;

import java.util.List;

public class UpdateResultInfo {

    /**
     * code : 0000
     * data : {"account":[{"ACCOUNT_ID":"15389945506191271886","ACCOUNT_STATUS":"0","ACCOUNT_TYPE":"0","BALANCE":0,"BRANCH_ID":"0","CLIENT_CODE":"153898504344114","COMPANY_ID":"0","CREATETIME":1538994550000,"ISM_STATUS":"2","MEMBER_IMG":"0","MI_ADDR":"","MI_AGE":"0","MI_BIRTH":1538928000000,"MI_EMAIL":"","MI_ID":"15389945506111771034","MI_NAME":"郭先生","MI_NO":"","MI_PAPERWORK_NO":"","MI_PHONE":"13800010001","MI_SEX":"0","MI_STATUS":"0","MI_TYPE":"15389851450061541196","REFUND_STATUS":"0","STORE_CODE":"0000","TOTAL_RECHARGE_MONEY":"200.0","UPDATETIME":1539767793000},{"ACCOUNT_ID":"15389945506221181344","ACCOUNT_STATUS":"0","ACCOUNT_TYPE":"1","BALANCE":"0","BRANCH_ID":"0","CLIENT_CODE":"153898504344114","COMPANY_ID":"0","CREATETIME":1538994550000,"ISM_STATUS":"2","MEMBER_IMG":"0","MI_ADDR":"","MI_AGE":"0","MI_BIRTH":1538928000000,"MI_EMAIL":"","MI_ID":"15389945506111771034","MI_NAME":"郭先生","MI_NO":"","MI_PAPERWORK_NO":"","MI_PHONE":"13800010001","MI_SEX":"0","MI_STATUS":"0","MI_TYPE":"15389851450061541196","REFUND_STATUS":"0","STORE_CODE":"0000","TOTAL_RECHARGE_MONEY":"0","UPDATETIME":1538994550000}]}
     * msg : 支付成功
     * result : true
     * status : 200
     * type : 0
     */

    private String code;
    private DataBean data;
    private String msg;
    private boolean result;
    private int status;
    private int type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class DataBean {
        private List<AccountBean> account;

        public List<AccountBean> getAccount() {
            return account;
        }

        public void setAccount(List<AccountBean> account) {
            this.account = account;
        }

        public static class AccountBean {
            /**
             * ACCOUNT_ID : 15389945506191271886
             * ACCOUNT_STATUS : 0
             * ACCOUNT_TYPE : 0
             * BALANCE : 0
             * BRANCH_ID : 0
             * CLIENT_CODE : 153898504344114
             * COMPANY_ID : 0
             * CREATETIME : 1538994550000
             * ISM_STATUS : 2
             * MEMBER_IMG : 0
             * MI_ADDR :
             * MI_AGE : 0
             * MI_BIRTH : 1538928000000
             * MI_EMAIL :
             * MI_ID : 15389945506111771034
             * MI_NAME : 郭先生
             * MI_NO :
             * MI_PAPERWORK_NO :
             * MI_PHONE : 13800010001
             * MI_SEX : 0
             * MI_STATUS : 0
             * MI_TYPE : 15389851450061541196
             * REFUND_STATUS : 0
             * STORE_CODE : 0000
             * TOTAL_RECHARGE_MONEY : 200.0
             * UPDATETIME : 1539767793000
             */

            private String ACCOUNT_ID;
            private String ACCOUNT_STATUS;
            private String ACCOUNT_TYPE;
            private float BALANCE;
            private String BRANCH_ID;
            private String CLIENT_CODE;
            private String COMPANY_ID;
            private long CREATETIME;
            private String ISM_STATUS;
            private String MEMBER_IMG;
            private String MI_ADDR;
            private String MI_AGE;
            private long MI_BIRTH;
            private String MI_EMAIL;
            private String MI_ID;
            private String MI_NAME;
            private String MI_NO;
            private String MI_PAPERWORK_NO;
            private String MI_PHONE;
            private String MI_SEX;
            private String MI_STATUS;
            private String MI_TYPE;
            private String REFUND_STATUS;
            private String STORE_CODE;
            private String TOTAL_RECHARGE_MONEY;
            private long UPDATETIME;

            public String getACCOUNT_ID() {
                return ACCOUNT_ID;
            }

            public void setACCOUNT_ID(String ACCOUNT_ID) {
                this.ACCOUNT_ID = ACCOUNT_ID;
            }

            public String getACCOUNT_STATUS() {
                return ACCOUNT_STATUS;
            }

            public void setACCOUNT_STATUS(String ACCOUNT_STATUS) {
                this.ACCOUNT_STATUS = ACCOUNT_STATUS;
            }

            public String getACCOUNT_TYPE() {
                return ACCOUNT_TYPE;
            }

            public void setACCOUNT_TYPE(String ACCOUNT_TYPE) {
                this.ACCOUNT_TYPE = ACCOUNT_TYPE;
            }

            public float getBALANCE() {
                return BALANCE;
            }

            public void setBALANCE(float BALANCE) {
                this.BALANCE = BALANCE;
            }

            public String getBRANCH_ID() {
                return BRANCH_ID;
            }

            public void setBRANCH_ID(String BRANCH_ID) {
                this.BRANCH_ID = BRANCH_ID;
            }

            public String getCLIENT_CODE() {
                return CLIENT_CODE;
            }

            public void setCLIENT_CODE(String CLIENT_CODE) {
                this.CLIENT_CODE = CLIENT_CODE;
            }

            public String getCOMPANY_ID() {
                return COMPANY_ID;
            }

            public void setCOMPANY_ID(String COMPANY_ID) {
                this.COMPANY_ID = COMPANY_ID;
            }

            public long getCREATETIME() {
                return CREATETIME;
            }

            public void setCREATETIME(long CREATETIME) {
                this.CREATETIME = CREATETIME;
            }

            public String getISM_STATUS() {
                return ISM_STATUS;
            }

            public void setISM_STATUS(String ISM_STATUS) {
                this.ISM_STATUS = ISM_STATUS;
            }

            public String getMEMBER_IMG() {
                return MEMBER_IMG;
            }

            public void setMEMBER_IMG(String MEMBER_IMG) {
                this.MEMBER_IMG = MEMBER_IMG;
            }

            public String getMI_ADDR() {
                return MI_ADDR;
            }

            public void setMI_ADDR(String MI_ADDR) {
                this.MI_ADDR = MI_ADDR;
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

            public String getMI_EMAIL() {
                return MI_EMAIL;
            }

            public void setMI_EMAIL(String MI_EMAIL) {
                this.MI_EMAIL = MI_EMAIL;
            }

            public String getMI_ID() {
                return MI_ID;
            }

            public void setMI_ID(String MI_ID) {
                this.MI_ID = MI_ID;
            }

            public String getMI_NAME() {
                return MI_NAME;
            }

            public void setMI_NAME(String MI_NAME) {
                this.MI_NAME = MI_NAME;
            }

            public String getMI_NO() {
                return MI_NO;
            }

            public void setMI_NO(String MI_NO) {
                this.MI_NO = MI_NO;
            }

            public String getMI_PAPERWORK_NO() {
                return MI_PAPERWORK_NO;
            }

            public void setMI_PAPERWORK_NO(String MI_PAPERWORK_NO) {
                this.MI_PAPERWORK_NO = MI_PAPERWORK_NO;
            }

            public String getMI_PHONE() {
                return MI_PHONE;
            }

            public void setMI_PHONE(String MI_PHONE) {
                this.MI_PHONE = MI_PHONE;
            }

            public String getMI_SEX() {
                return MI_SEX;
            }

            public void setMI_SEX(String MI_SEX) {
                this.MI_SEX = MI_SEX;
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

            public String getREFUND_STATUS() {
                return REFUND_STATUS;
            }

            public void setREFUND_STATUS(String REFUND_STATUS) {
                this.REFUND_STATUS = REFUND_STATUS;
            }

            public String getSTORE_CODE() {
                return STORE_CODE;
            }

            public void setSTORE_CODE(String STORE_CODE) {
                this.STORE_CODE = STORE_CODE;
            }

            public String getTOTAL_RECHARGE_MONEY() {
                return TOTAL_RECHARGE_MONEY;
            }

            public void setTOTAL_RECHARGE_MONEY(String TOTAL_RECHARGE_MONEY) {
                this.TOTAL_RECHARGE_MONEY = TOTAL_RECHARGE_MONEY;
            }

            public long getUPDATETIME() {
                return UPDATETIME;
            }

            public void setUPDATETIME(long UPDATETIME) {
                this.UPDATETIME = UPDATETIME;
            }
        }
    }
}
