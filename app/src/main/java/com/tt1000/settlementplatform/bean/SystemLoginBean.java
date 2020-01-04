package com.tt1000.settlementplatform.bean;


import java.util.List;
import java.util.Map;

public class SystemLoginBean {

    /**
     * code : 0000
     * data : {"ADDR":"","AREA_ID":110101,"CLIENT_CODE":"155774791138413","CLIENT_ID":"2019051319462515577479113841300001092","CLIENT_NAME":"新版tf测试","CLIENT_PERSON":"h","CLIENT_PHONE":"18512345678","CREATETIME":1557747985000,"SERVICE_IP":"xb.rfidstar.cn","SYN_STATUS":"1","UPDATETIME":1557747985000,"USE_STATUS":"0","storeInfo":{"0000":{"APPID":"wx14939a8f3659b974","CLIENT_CODE":"155774791138413","CREATETIME":1557747985000,"SECRET":"dd75b6b7a5cd292f9413aef812501fb6","SMS":"123115006","STORE_ADDR":"博智中心905","STORE_CODE":"0000","STORE_DUTY_PERSON":"h","STORE_DUTY_PHONE":"18520840802","STORE_ID":"2019051319462515577479113841300001772","STORE_IMG":"images/store/60M9HDLL00AQ0001.JPG","STORE_NAME":"总店","STORE_STATUS":"0","SYN_STATUS":"0","UPDATETIME":1573637467000,"VAILD_TIME":1593446400000,"meterInfo":[{"SEQNO":"155774791138413000015609070134151733","MACHINE_NO":"156090699273816","MACHINE_NAME":"安卓001","REGISTER_IP":"1","REMARKS":"1","USE_STATUS":"0","U_ID":"0","CREATETIME":1560907013000,"UPDATETIME":1567662245000,"CLIENT_CODE":"155774791138413","STORE_CODE":"0000","VAILD_TIME":1593446400000,"ONLINE":"0","SYN_STATUS":"0"},{"SEQNO":"155774791138413000015690504438021393","MACHINE_NO":"156905043418016","MACHINE_NAME":"手持机","REGISTER_IP":"1","REMARKS":"1","USE_STATUS":"0","U_ID":"0","CREATETIME":1569050443000,"UPDATETIME":1569475986000,"CLIENT_CODE":"155774791138413","STORE_CODE":"0000","VAILD_TIME":1601395200000,"ONLINE":"0","SYN_STATUS":"0"}]},"0001":{"APPID":"wx14939a8f3659b974","CLIENT_CODE":"155774791138413","CREATETIME":1558676554000,"SECRET":"dd75b6b7a5cd292f9413aef812501fb6","SMS":"123115006","STORE_ADDR":"深圳宝安区","STORE_CODE":"0001","STORE_DUTY_PERSON":"李丹","STORE_DUTY_PHONE":"18520840802","STORE_ID":"2019052413423415577479113841300011272","STORE_NAME":"分店","STORE_STATUS":"0","SYN_STATUS":"0","UPDATETIME":1558676554000,"VAILD_TIME":1558627200000,"meterInfo":[{"SEQNO":"155774791138413000115586765929491378","MACHINE_NO":"155867658345911","MACHINE_NAME":"分店1","REGISTER_IP":"1","REMARKS":"1","USE_STATUS":"0","U_ID":"0","CREATETIME":1558676592000,"UPDATETIME":1559636145000,"CLIENT_CODE":"155774791138413","STORE_CODE":"0001","VAILD_TIME":1560355200000,"ONLINE":"0","SYN_STATUS":"0"}]},"0002":{"APPID":"","CLIENT_CODE":"155774791138413","CREATETIME":1566202445000,"SECRET":"","SMS":"","STORE_ADDR":"店铺一","STORE_CODE":"0002","STORE_DUTY_PERSON":"店铺一","STORE_DUTY_PHONE":"17373957395","STORE_ID":"2019081916140515577479113841300021952","STORE_NAME":"店铺一","STORE_STATUS":"0","SYN_STATUS":"0","UPDATETIME":1566202445000,"VAILD_TIME":1566144000000,"meterInfo":[]}}}
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
         * ADDR :
         * AREA_ID : 110101
         * CLIENT_CODE : 155774791138413
         * CLIENT_ID : 2019051319462515577479113841300001092
         * CLIENT_NAME : 新版tf测试
         * CLIENT_PERSON : h
         * CLIENT_PHONE : 18512345678
         * CREATETIME : 1557747985000
         * SERVICE_IP : xb.rfidstar.cn
         * SYN_STATUS : 1
         * UPDATETIME : 1557747985000
         * USE_STATUS : 0
         * storeInfo : {"0000":{"APPID":"wx14939a8f3659b974","CLIENT_CODE":"155774791138413","CREATETIME":1557747985000,"SECRET":"dd75b6b7a5cd292f9413aef812501fb6","SMS":"123115006","STORE_ADDR":"博智中心905","STORE_CODE":"0000","STORE_DUTY_PERSON":"h","STORE_DUTY_PHONE":"18520840802","STORE_ID":"2019051319462515577479113841300001772","STORE_IMG":"images/store/60M9HDLL00AQ0001.JPG","STORE_NAME":"总店","STORE_STATUS":"0","SYN_STATUS":"0","UPDATETIME":1573637467000,"VAILD_TIME":1593446400000,"meterInfo":[{"SEQNO":"155774791138413000015609070134151733","MACHINE_NO":"156090699273816","MACHINE_NAME":"安卓001","REGISTER_IP":"1","REMARKS":"1","USE_STATUS":"0","U_ID":"0","CREATETIME":1560907013000,"UPDATETIME":1567662245000,"CLIENT_CODE":"155774791138413","STORE_CODE":"0000","VAILD_TIME":1593446400000,"ONLINE":"0","SYN_STATUS":"0"},{"SEQNO":"155774791138413000015690504438021393","MACHINE_NO":"156905043418016","MACHINE_NAME":"手持机","REGISTER_IP":"1","REMARKS":"1","USE_STATUS":"0","U_ID":"0","CREATETIME":1569050443000,"UPDATETIME":1569475986000,"CLIENT_CODE":"155774791138413","STORE_CODE":"0000","VAILD_TIME":1601395200000,"ONLINE":"0","SYN_STATUS":"0"}]},"0001":{"APPID":"wx14939a8f3659b974","CLIENT_CODE":"155774791138413","CREATETIME":1558676554000,"SECRET":"dd75b6b7a5cd292f9413aef812501fb6","SMS":"123115006","STORE_ADDR":"深圳宝安区","STORE_CODE":"0001","STORE_DUTY_PERSON":"李丹","STORE_DUTY_PHONE":"18520840802","STORE_ID":"2019052413423415577479113841300011272","STORE_NAME":"分店","STORE_STATUS":"0","SYN_STATUS":"0","UPDATETIME":1558676554000,"VAILD_TIME":1558627200000,"meterInfo":[{"SEQNO":"155774791138413000115586765929491378","MACHINE_NO":"155867658345911","MACHINE_NAME":"分店1","REGISTER_IP":"1","REMARKS":"1","USE_STATUS":"0","U_ID":"0","CREATETIME":1558676592000,"UPDATETIME":1559636145000,"CLIENT_CODE":"155774791138413","STORE_CODE":"0001","VAILD_TIME":1560355200000,"ONLINE":"0","SYN_STATUS":"0"}]},"0002":{"APPID":"","CLIENT_CODE":"155774791138413","CREATETIME":1566202445000,"SECRET":"","SMS":"","STORE_ADDR":"店铺一","STORE_CODE":"0002","STORE_DUTY_PERSON":"店铺一","STORE_DUTY_PHONE":"17373957395","STORE_ID":"2019081916140515577479113841300021952","STORE_NAME":"店铺一","STORE_STATUS":"0","SYN_STATUS":"0","UPDATETIME":1566202445000,"VAILD_TIME":1566144000000,"meterInfo":[]}}
         */

        private String ADDR;
        private int AREA_ID;
        private String CLIENT_CODE;
        private String CLIENT_ID;
        private String CLIENT_NAME;
        private String CLIENT_PERSON;
        private String CLIENT_PHONE;
        private long CREATETIME;
        private String SERVICE_IP;
        private String SYN_STATUS;
        private long UPDATETIME;
        private String USE_STATUS;
        private Map<String, StoreInfoBean> storeInfo;

        public String getADDR() {
            return ADDR;
        }

        public void setADDR(String ADDR) {
            this.ADDR = ADDR;
        }

        public int getAREA_ID() {
            return AREA_ID;
        }

        public void setAREA_ID(int AREA_ID) {
            this.AREA_ID = AREA_ID;
        }

        public String getCLIENT_CODE() {
            return CLIENT_CODE;
        }

        public void setCLIENT_CODE(String CLIENT_CODE) {
            this.CLIENT_CODE = CLIENT_CODE;
        }

        public String getCLIENT_ID() {
            return CLIENT_ID;
        }

        public void setCLIENT_ID(String CLIENT_ID) {
            this.CLIENT_ID = CLIENT_ID;
        }

        public String getCLIENT_NAME() {
            return CLIENT_NAME;
        }

        public void setCLIENT_NAME(String CLIENT_NAME) {
            this.CLIENT_NAME = CLIENT_NAME;
        }

        public String getCLIENT_PERSON() {
            return CLIENT_PERSON;
        }

        public void setCLIENT_PERSON(String CLIENT_PERSON) {
            this.CLIENT_PERSON = CLIENT_PERSON;
        }

        public String getCLIENT_PHONE() {
            return CLIENT_PHONE;
        }

        public void setCLIENT_PHONE(String CLIENT_PHONE) {
            this.CLIENT_PHONE = CLIENT_PHONE;
        }

        public long getCREATETIME() {
            return CREATETIME;
        }

        public void setCREATETIME(long CREATETIME) {
            this.CREATETIME = CREATETIME;
        }

        public String getSERVICE_IP() {
            return SERVICE_IP;
        }

        public void setSERVICE_IP(String SERVICE_IP) {
            this.SERVICE_IP = SERVICE_IP;
        }

        public String getSYN_STATUS() {
            return SYN_STATUS;
        }

        public void setSYN_STATUS(String SYN_STATUS) {
            this.SYN_STATUS = SYN_STATUS;
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

        public Map<String, StoreInfoBean> getStoreInfo() {
            return storeInfo;
        }

        public void setStoreInfo(Map<String, StoreInfoBean> storeInfo) {
            this.storeInfo = storeInfo;
        }

        public static class StoreInfoBean {
            /**
             * APPID : wx14939a8f3659b974
             * CLIENT_CODE : 155774791138413
             * CREATETIME : 1557747985000
             * SECRET : dd75b6b7a5cd292f9413aef812501fb6
             * SMS : 123115006
             * STORE_ADDR : 博智中心905
             * STORE_CODE : 0000
             * STORE_DUTY_PERSON : h
             * STORE_DUTY_PHONE : 18520840802
             * STORE_ID : 2019051319462515577479113841300001772
             * STORE_IMG : images/store/60M9HDLL00AQ0001.JPG
             * STORE_NAME : 总店
             * STORE_STATUS : 0
             * SYN_STATUS : 0
             * UPDATETIME : 1573637467000
             * VAILD_TIME : 1593446400000
             * meterInfo : [{"SEQNO":"155774791138413000015609070134151733","MACHINE_NO":"156090699273816","MACHINE_NAME":"安卓001","REGISTER_IP":"1","REMARKS":"1","USE_STATUS":"0","U_ID":"0","CREATETIME":1560907013000,"UPDATETIME":1567662245000,"CLIENT_CODE":"155774791138413","STORE_CODE":"0000","VAILD_TIME":1593446400000,"ONLINE":"0","SYN_STATUS":"0"},{"SEQNO":"155774791138413000015690504438021393","MACHINE_NO":"156905043418016","MACHINE_NAME":"手持机","REGISTER_IP":"1","REMARKS":"1","USE_STATUS":"0","U_ID":"0","CREATETIME":1569050443000,"UPDATETIME":1569475986000,"CLIENT_CODE":"155774791138413","STORE_CODE":"0000","VAILD_TIME":1601395200000,"ONLINE":"0","SYN_STATUS":"0"}]
             */

            private String APPID;
            private String CLIENT_CODE;
            private long CREATETIME;
            private String SECRET;
            private String SMS;
            private String STORE_ADDR;
            private String STORE_CODE;
            private String STORE_DUTY_PERSON;
            private String STORE_DUTY_PHONE;
            private String STORE_ID;
            private String STORE_IMG;
            private String STORE_NAME;
            private String STORE_STATUS;
            private String SYN_STATUS;
            private long UPDATETIME;
            private long VAILD_TIME;
            private List<MeterInfoBean> meterInfo;

            public String getAPPID() {
                return APPID;
            }

            public void setAPPID(String APPID) {
                this.APPID = APPID;
            }

            public String getCLIENT_CODE() {
                return CLIENT_CODE;
            }

            public void setCLIENT_CODE(String CLIENT_CODE) {
                this.CLIENT_CODE = CLIENT_CODE;
            }

            public long getCREATETIME() {
                return CREATETIME;
            }

            public void setCREATETIME(long CREATETIME) {
                this.CREATETIME = CREATETIME;
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

            public String getSTORE_ADDR() {
                return STORE_ADDR;
            }

            public void setSTORE_ADDR(String STORE_ADDR) {
                this.STORE_ADDR = STORE_ADDR;
            }

            public String getSTORE_CODE() {
                return STORE_CODE;
            }

            public void setSTORE_CODE(String STORE_CODE) {
                this.STORE_CODE = STORE_CODE;
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

            public String getSTORE_ID() {
                return STORE_ID;
            }

            public void setSTORE_ID(String STORE_ID) {
                this.STORE_ID = STORE_ID;
            }

            public String getSTORE_IMG() {
                return STORE_IMG;
            }

            public void setSTORE_IMG(String STORE_IMG) {
                this.STORE_IMG = STORE_IMG;
            }

            public String getSTORE_NAME() {
                return STORE_NAME;
            }

            public void setSTORE_NAME(String STORE_NAME) {
                this.STORE_NAME = STORE_NAME;
            }

            public String getSTORE_STATUS() {
                return STORE_STATUS;
            }

            public void setSTORE_STATUS(String STORE_STATUS) {
                this.STORE_STATUS = STORE_STATUS;
            }

            public String getSYN_STATUS() {
                return SYN_STATUS;
            }

            public void setSYN_STATUS(String SYN_STATUS) {
                this.SYN_STATUS = SYN_STATUS;
            }

            public long getUPDATETIME() {
                return UPDATETIME;
            }

            public void setUPDATETIME(long UPDATETIME) {
                this.UPDATETIME = UPDATETIME;
            }

            public long getVAILD_TIME() {
                return VAILD_TIME;
            }

            public void setVAILD_TIME(long VAILD_TIME) {
                this.VAILD_TIME = VAILD_TIME;
            }

            public List<MeterInfoBean> getMeterInfo() {
                return meterInfo;
            }

            public void setMeterInfo(List<MeterInfoBean> meterInfo) {
                this.meterInfo = meterInfo;
            }

            public static class MeterInfoBean {
                /**
                 * SEQNO : 155774791138413000015609070134151733
                 * MACHINE_NO : 156090699273816
                 * MACHINE_NAME : 安卓001
                 * REGISTER_IP : 1
                 * REMARKS : 1
                 * USE_STATUS : 0
                 * U_ID : 0
                 * CREATETIME : 1560907013000
                 * UPDATETIME : 1567662245000
                 * CLIENT_CODE : 155774791138413
                 * STORE_CODE : 0000
                 * VAILD_TIME : 1593446400000
                 * ONLINE : 0
                 * SYN_STATUS : 0
                 */

                private String SEQNO;
                private String MACHINE_NO;
                private String MACHINE_NAME;
                private String REGISTER_IP;
                private String REMARKS;
                private String USE_STATUS;
                private String U_ID;
                private long CREATETIME;
                private long UPDATETIME;
                private String CLIENT_CODE;
                private String STORE_CODE;
                private long VAILD_TIME;
                private String ONLINE;
                private String SYN_STATUS;

                public String getSEQNO() {
                    return SEQNO;
                }

                public void setSEQNO(String SEQNO) {
                    this.SEQNO = SEQNO;
                }

                public String getMACHINE_NO() {
                    return MACHINE_NO;
                }

                public void setMACHINE_NO(String MACHINE_NO) {
                    this.MACHINE_NO = MACHINE_NO;
                }

                public String getMACHINE_NAME() {
                    return MACHINE_NAME;
                }

                public void setMACHINE_NAME(String MACHINE_NAME) {
                    this.MACHINE_NAME = MACHINE_NAME;
                }

                public String getREGISTER_IP() {
                    return REGISTER_IP;
                }

                public void setREGISTER_IP(String REGISTER_IP) {
                    this.REGISTER_IP = REGISTER_IP;
                }

                public String getREMARKS() {
                    return REMARKS;
                }

                public void setREMARKS(String REMARKS) {
                    this.REMARKS = REMARKS;
                }

                public String getUSE_STATUS() {
                    return USE_STATUS;
                }

                public void setUSE_STATUS(String USE_STATUS) {
                    this.USE_STATUS = USE_STATUS;
                }

                public String getU_ID() {
                    return U_ID;
                }

                public void setU_ID(String U_ID) {
                    this.U_ID = U_ID;
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

                public String getSTORE_CODE() {
                    return STORE_CODE;
                }

                public void setSTORE_CODE(String STORE_CODE) {
                    this.STORE_CODE = STORE_CODE;
                }

                public long getVAILD_TIME() {
                    return VAILD_TIME;
                }

                public void setVAILD_TIME(long VAILD_TIME) {
                    this.VAILD_TIME = VAILD_TIME;
                }

                public String getONLINE() {
                    return ONLINE;
                }

                public void setONLINE(String ONLINE) {
                    this.ONLINE = ONLINE;
                }

                public String getSYN_STATUS() {
                    return SYN_STATUS;
                }

                public void setSYN_STATUS(String SYN_STATUS) {
                    this.SYN_STATUS = SYN_STATUS;
                }
            }
        }
    }
}