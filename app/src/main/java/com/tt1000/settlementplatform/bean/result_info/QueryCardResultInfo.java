package com.tt1000.settlementplatform.bean.result_info;

import java.util.List;

public class QueryCardResultInfo {

    /**
     * code : 0000
     * data : {"account":[{"ACCOUNT_ID":"2315560010514811","BALANCE":"100","TOTAL_RECHARGE_MONEY":"0","ACCOUNT_STATUS":"0","REFUND_STATUS":"0","CREATETIME":1575542462000,"UPDATETIME":1575542462000,"U_ID":"","MI_ID":"2315560010514492","ACCOUNT_TYPE":"0","STORE_CODE":"0000","CLIENT_CODE":"157285526959516","ISM_STATUS":"2"},{"ACCOUNT_ID":"2315560010515067","BALANCE":"100","TOTAL_RECHARGE_MONEY":"0","ACCOUNT_STATUS":"0","REFUND_STATUS":"0","CREATETIME":1575542462000,"UPDATETIME":1575542462000,"U_ID":"","MI_ID":"2315560010514492","ACCOUNT_TYPE":"1","STORE_CODE":"0000","CLIENT_CODE":"157285526959516","ISM_STATUS":"2"}],"card":[{"IC_ID":"182315560010515460","CREATETIME":1575542462000,"UPDATETIME":1575544718000,"IC_TYPE":"1","MI_ID":"2315560010514492","IC_SERIAL_NUMBER":"182315560010515460","U_ID":"15755285481231271894","IC_STATUS":"2","IC_PASSWORD":"666666","STORE_CODE":"0000","CLIENT_CODE":"157285526959516"},{"IC_ID":"2978525458","CREATETIME":1575544718000,"UPDATETIME":1575544718000,"IC_TYPE":"0","MI_ID":"2315560010514492","IC_SERIAL_NUMBER":"2978525458","U_ID":"15755285481231271894","IC_STATUS":"0","STORE_CODE":"0000","CLIENT_CODE":"157285526959516","IC_SN":""}],"member":{"MI_ID":"2315560010514492","MI_NO":"96325375","MI_NAME":"黄涛","MI_PAPERWORK_NO":"44522132659857375","MI_STATUS":"0","MI_TYPE":"0","COMPANY_ID":"20191127123456","BRANCH_ID":"20191127123457","ISM_STATUS":"0","CREATETIME":1575542462000,"UPDATETIME":1575533193000,"STORE_CODE":"0000","CLIENT_CODE":"157285526959516","MI_PASSWORD":"666666","MEMBER_IMG":"0","TEMP_1":"","TEMP_2":"","TEMP_3":"","TEMP_4":"","TEMP_5":"","ENGLISH_NAME":"Weichaiceshi375","company":"潍柴分公司1","branch":"正式员工"}}
     * msg :
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
        /**
         * account : [{"ACCOUNT_ID":"2315560010514811","BALANCE":"100","TOTAL_RECHARGE_MONEY":"0","ACCOUNT_STATUS":"0","REFUND_STATUS":"0","CREATETIME":1575542462000,"UPDATETIME":1575542462000,"U_ID":"","MI_ID":"2315560010514492","ACCOUNT_TYPE":"0","STORE_CODE":"0000","CLIENT_CODE":"157285526959516","ISM_STATUS":"2"},{"ACCOUNT_ID":"2315560010515067","BALANCE":"100","TOTAL_RECHARGE_MONEY":"0","ACCOUNT_STATUS":"0","REFUND_STATUS":"0","CREATETIME":1575542462000,"UPDATETIME":1575542462000,"U_ID":"","MI_ID":"2315560010514492","ACCOUNT_TYPE":"1","STORE_CODE":"0000","CLIENT_CODE":"157285526959516","ISM_STATUS":"2"}]
         * card : [{"IC_ID":"182315560010515460","CREATETIME":1575542462000,"UPDATETIME":1575544718000,"IC_TYPE":"1","MI_ID":"2315560010514492","IC_SERIAL_NUMBER":"182315560010515460","U_ID":"15755285481231271894","IC_STATUS":"2","IC_PASSWORD":"666666","STORE_CODE":"0000","CLIENT_CODE":"157285526959516"},{"IC_ID":"2978525458","CREATETIME":1575544718000,"UPDATETIME":1575544718000,"IC_TYPE":"0","MI_ID":"2315560010514492","IC_SERIAL_NUMBER":"2978525458","U_ID":"15755285481231271894","IC_STATUS":"0","STORE_CODE":"0000","CLIENT_CODE":"157285526959516","IC_SN":""}]
         * member : {"MI_ID":"2315560010514492","MI_NO":"96325375","MI_NAME":"黄涛","MI_PAPERWORK_NO":"44522132659857375","MI_STATUS":"0","MI_TYPE":"0","COMPANY_ID":"20191127123456","BRANCH_ID":"20191127123457","ISM_STATUS":"0","CREATETIME":1575542462000,"UPDATETIME":1575533193000,"STORE_CODE":"0000","CLIENT_CODE":"157285526959516","MI_PASSWORD":"666666","MEMBER_IMG":"0","TEMP_1":"","TEMP_2":"","TEMP_3":"","TEMP_4":"","TEMP_5":"","ENGLISH_NAME":"Weichaiceshi375","company":"潍柴分公司1","branch":"正式员工"}
         */

        private MemberBean member;
        private List<AccountBean> account;
        private List<CardBean> card;

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public List<AccountBean> getAccount() {
            return account;
        }

        public void setAccount(List<AccountBean> account) {
            this.account = account;
        }

        public List<CardBean> getCard() {
            return card;
        }

        public void setCard(List<CardBean> card) {
            this.card = card;
        }

        public static class MemberBean {
            /**
             * MI_ID : 2315560010514492
             * MI_NO : 96325375
             * MI_NAME : 黄涛
             * MI_PAPERWORK_NO : 44522132659857375
             * MI_STATUS : 0
             * MI_TYPE : 0
             * COMPANY_ID : 20191127123456
             * BRANCH_ID : 20191127123457
             * ISM_STATUS : 0
             * CREATETIME : 1575542462000
             * UPDATETIME : 1575533193000
             * STORE_CODE : 0000
             * CLIENT_CODE : 157285526959516
             * MI_PASSWORD : 666666
             * MEMBER_IMG : 0
             * TEMP_1 :
             * TEMP_2 :
             * TEMP_3 :
             * TEMP_4 :
             * TEMP_5 :
             * ENGLISH_NAME : Weichaiceshi375
             * company : 潍柴分公司1
             * branch : 正式员工
             */

            private String MI_ID;
            private String MI_NO;
            private String MI_NAME;
            private String MI_PHONE;

            public String getMI_PHONE() {
                return MI_PHONE;
            }

            public void setMI_PHONE(String MI_PHONE) {
                this.MI_PHONE = MI_PHONE;
            }

            private String MI_PAPERWORK_NO;
            private String MI_STATUS;
            private String MI_TYPE;
            private String COMPANY_ID;
            private String BRANCH_ID;
            private String ISM_STATUS;
            private long CREATETIME;
            private long UPDATETIME;
            private String STORE_CODE;
            private String CLIENT_CODE;
            private String MI_PASSWORD;
            private String MEMBER_IMG;
            private String TEMP_1;
            private String TEMP_2;
            private String TEMP_3;
            private String TEMP_4;
            private String TEMP_5;
            private String ENGLISH_NAME;
            private String company;
            private String branch;

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

            public String getMI_PAPERWORK_NO() {
                return MI_PAPERWORK_NO;
            }

            public void setMI_PAPERWORK_NO(String MI_PAPERWORK_NO) {
                this.MI_PAPERWORK_NO = MI_PAPERWORK_NO;
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

            public String getMI_PASSWORD() {
                return MI_PASSWORD;
            }

            public void setMI_PASSWORD(String MI_PASSWORD) {
                this.MI_PASSWORD = MI_PASSWORD;
            }

            public String getMEMBER_IMG() {
                return MEMBER_IMG;
            }

            public void setMEMBER_IMG(String MEMBER_IMG) {
                this.MEMBER_IMG = MEMBER_IMG;
            }

            public String getTEMP_1() {
                return TEMP_1;
            }

            public void setTEMP_1(String TEMP_1) {
                this.TEMP_1 = TEMP_1;
            }

            public String getTEMP_2() {
                return TEMP_2;
            }

            public void setTEMP_2(String TEMP_2) {
                this.TEMP_2 = TEMP_2;
            }

            public String getTEMP_3() {
                return TEMP_3;
            }

            public void setTEMP_3(String TEMP_3) {
                this.TEMP_3 = TEMP_3;
            }

            public String getTEMP_4() {
                return TEMP_4;
            }

            public void setTEMP_4(String TEMP_4) {
                this.TEMP_4 = TEMP_4;
            }

            public String getTEMP_5() {
                return TEMP_5;
            }

            public void setTEMP_5(String TEMP_5) {
                this.TEMP_5 = TEMP_5;
            }

            public String getENGLISH_NAME() {
                return ENGLISH_NAME;
            }

            public void setENGLISH_NAME(String ENGLISH_NAME) {
                this.ENGLISH_NAME = ENGLISH_NAME;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getBranch() {
                return branch;
            }

            public void setBranch(String branch) {
                this.branch = branch;
            }
        }

        public static class AccountBean {
            /**
             * ACCOUNT_ID : 2315560010514811
             * BALANCE : 100
             * TOTAL_RECHARGE_MONEY : 0
             * ACCOUNT_STATUS : 0
             * REFUND_STATUS : 0
             * CREATETIME : 1575542462000
             * UPDATETIME : 1575542462000
             * U_ID :
             * MI_ID : 2315560010514492
             * ACCOUNT_TYPE : 0
             * STORE_CODE : 0000
             * CLIENT_CODE : 157285526959516
             * ISM_STATUS : 2
             */

            private String ACCOUNT_ID;
            private String BALANCE;
            private String TOTAL_RECHARGE_MONEY;
            private String ACCOUNT_STATUS;
            private String REFUND_STATUS;
            private long CREATETIME;
            private long UPDATETIME;
            private String U_ID;
            private String MI_ID;
            private String ACCOUNT_TYPE;
            private String STORE_CODE;
            private String CLIENT_CODE;
            private String ISM_STATUS;

            public String getACCOUNT_ID() {
                return ACCOUNT_ID;
            }

            public void setACCOUNT_ID(String ACCOUNT_ID) {
                this.ACCOUNT_ID = ACCOUNT_ID;
            }

            public String getBALANCE() {
                return BALANCE;
            }

            public void setBALANCE(String BALANCE) {
                this.BALANCE = BALANCE;
            }

            public String getTOTAL_RECHARGE_MONEY() {
                return TOTAL_RECHARGE_MONEY;
            }

            public void setTOTAL_RECHARGE_MONEY(String TOTAL_RECHARGE_MONEY) {
                this.TOTAL_RECHARGE_MONEY = TOTAL_RECHARGE_MONEY;
            }

            public String getACCOUNT_STATUS() {
                return ACCOUNT_STATUS;
            }

            public void setACCOUNT_STATUS(String ACCOUNT_STATUS) {
                this.ACCOUNT_STATUS = ACCOUNT_STATUS;
            }

            public String getREFUND_STATUS() {
                return REFUND_STATUS;
            }

            public void setREFUND_STATUS(String REFUND_STATUS) {
                this.REFUND_STATUS = REFUND_STATUS;
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

            public String getU_ID() {
                return U_ID;
            }

            public void setU_ID(String U_ID) {
                this.U_ID = U_ID;
            }

            public String getMI_ID() {
                return MI_ID;
            }

            public void setMI_ID(String MI_ID) {
                this.MI_ID = MI_ID;
            }

            public String getACCOUNT_TYPE() {
                return ACCOUNT_TYPE;
            }

            public void setACCOUNT_TYPE(String ACCOUNT_TYPE) {
                this.ACCOUNT_TYPE = ACCOUNT_TYPE;
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

            public String getISM_STATUS() {
                return ISM_STATUS;
            }

            public void setISM_STATUS(String ISM_STATUS) {
                this.ISM_STATUS = ISM_STATUS;
            }
        }

        public static class CardBean {
            /**
             * IC_ID : 182315560010515460
             * CREATETIME : 1575542462000
             * UPDATETIME : 1575544718000
             * IC_TYPE : 1
             * MI_ID : 2315560010514492
             * IC_SERIAL_NUMBER : 182315560010515460
             * U_ID : 15755285481231271894
             * IC_STATUS : 2
             * IC_PASSWORD : 666666
             * STORE_CODE : 0000
             * CLIENT_CODE : 157285526959516
             * IC_SN :
             */

            private String IC_ID;
            private long CREATETIME;
            private long UPDATETIME;
            private String IC_TYPE;
            private String MI_ID;
            private String IC_SERIAL_NUMBER;
            private String U_ID;
            private String IC_STATUS;
            private String IC_PASSWORD;
            private String STORE_CODE;
            private String CLIENT_CODE;
            private String IC_SN;

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

            public String getIC_PASSWORD() {
                return IC_PASSWORD;
            }

            public void setIC_PASSWORD(String IC_PASSWORD) {
                this.IC_PASSWORD = IC_PASSWORD;
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

            public String getIC_SN() {
                return IC_SN;
            }

            public void setIC_SN(String IC_SN) {
                this.IC_SN = IC_SN;
            }
        }
    }
}
