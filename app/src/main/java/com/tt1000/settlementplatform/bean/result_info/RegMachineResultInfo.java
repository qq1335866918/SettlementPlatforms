package com.tt1000.settlementplatform.bean.result_info;

import java.util.List;

public class RegMachineResultInfo {


    /**
     * code : 00000
     * data :
     * machineInformation : [{"SEQNO":"153086231209116000015319674782001700","MACHINE_NO":"153196746289219","CREATETIME":1531967478000,"UPDATETIME":1531967478000,"REGISTER_IP":"127.0.0.1","REMARKS":"","USE_STATUS":"0","U_ID":"0","STORE_CODE":"0000","CLIENT_CODE":"153086231209116","VAILD_TIME":1534608000000,"MACHINE_NAME":"1号机器","SYN_STATUS":"0","ONLINE":"0"}]
     * msg : 机器号绑定成功
     * result : true
     * status : 200
     * storeInformation : [{"STORE_ID":"2018070615322515308623120911600001881","STORE_NAME":"总店","STORE_ADDR":"博智中心","STORE_STATUS":"0","STORE_CODE":"0000","CREATETIME":1530862345000,"UPDATETIME":1531100477000,"CLIENT_CODE":"153086231209116","STORE_DUTY_PERSON":"陈权","STORE_DUTY_PHONE":"17373950062","SYN_STATUS":"0","VAILD_TIME":1530806400000,"APPID":"wxc8c6f4b2e9e70486","SECRET":"f4d35086fc8a401992b9c5055b663866","SMS":"123115006"}]
     * type : 0
     */

    private String code;
    private String data;
    private String msg;
    private boolean result;
    private int status;
    private int type;
    private List<MachineInformationBean> machineInformation;
    private List<StoreInformationBean> storeInformation;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
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

    public List<MachineInformationBean> getMachineInformation() {
        return machineInformation;
    }

    public void setMachineInformation(List<MachineInformationBean> machineInformation) {
        this.machineInformation = machineInformation;
    }

    public List<StoreInformationBean> getStoreInformation() {
        return storeInformation;
    }

    public void setStoreInformation(List<StoreInformationBean> storeInformation) {
        this.storeInformation = storeInformation;
    }

    public static class MachineInformationBean {
        /**
         * SEQNO : 153086231209116000015319674782001700
         * MACHINE_NO : 153196746289219
         * CREATETIME : 1531967478000
         * UPDATETIME : 1531967478000
         * REGISTER_IP : 127.0.0.1
         * REMARKS :
         * USE_STATUS : 0
         * U_ID : 0
         * STORE_CODE : 0000
         * CLIENT_CODE : 153086231209116
         * VAILD_TIME : 1534608000000
         * MACHINE_NAME : 1号机器
         * SYN_STATUS : 0
         * ONLINE : 0
         */

        private String SEQNO;
        private String MACHINE_NO;
        private long CREATETIME;
        private long UPDATETIME;
        private String REGISTER_IP;
        private String REMARKS;
        private String USE_STATUS;
        private String U_ID;
        private String STORE_CODE;
        private String CLIENT_CODE;
        private long VAILD_TIME;
        private String MACHINE_NAME;
        private String SYN_STATUS;
        private String ONLINE;

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

        public long getVAILD_TIME() {
            return VAILD_TIME;
        }

        public void setVAILD_TIME(long VAILD_TIME) {
            this.VAILD_TIME = VAILD_TIME;
        }

        public String getMACHINE_NAME() {
            return MACHINE_NAME;
        }

        public void setMACHINE_NAME(String MACHINE_NAME) {
            this.MACHINE_NAME = MACHINE_NAME;
        }

        public String getSYN_STATUS() {
            return SYN_STATUS;
        }

        public void setSYN_STATUS(String SYN_STATUS) {
            this.SYN_STATUS = SYN_STATUS;
        }

        public String getONLINE() {
            return ONLINE;
        }

        public void setONLINE(String ONLINE) {
            this.ONLINE = ONLINE;
        }
    }

    public static class StoreInformationBean {
        /**
         * STORE_ID : 2018070615322515308623120911600001881
         * STORE_NAME : 总店
         * STORE_ADDR : 博智中心
         * STORE_STATUS : 0
         * STORE_CODE : 0000
         * CREATETIME : 1530862345000
         * UPDATETIME : 1531100477000
         * CLIENT_CODE : 153086231209116
         * STORE_DUTY_PERSON : 陈权
         * STORE_DUTY_PHONE : 17373950062
         * SYN_STATUS : 0
         * VAILD_TIME : 1530806400000
         * APPID : wxc8c6f4b2e9e70486
         * SECRET : f4d35086fc8a401992b9c5055b663866
         * SMS : 123115006
         */

        private String STORE_ID;
        private String STORE_NAME;
        private String STORE_ADDR;
        private String STORE_STATUS;
        private String STORE_CODE;
        private long CREATETIME;
        private long UPDATETIME;
        private String CLIENT_CODE;
        private String STORE_DUTY_PERSON;
        private String STORE_DUTY_PHONE;
        private String SYN_STATUS;
        private long VAILD_TIME;
        private String APPID;
        private String SECRET;
        private String SMS;

        public String getSTORE_ID() {
            return STORE_ID;
        }

        public void setSTORE_ID(String STORE_ID) {
            this.STORE_ID = STORE_ID;
        }

        public String getSTORE_NAME() {
            return STORE_NAME;
        }

        public void setSTORE_NAME(String STORE_NAME) {
            this.STORE_NAME = STORE_NAME;
        }

        public String getSTORE_ADDR() {
            return STORE_ADDR;
        }

        public void setSTORE_ADDR(String STORE_ADDR) {
            this.STORE_ADDR = STORE_ADDR;
        }

        public String getSTORE_STATUS() {
            return STORE_STATUS;
        }

        public void setSTORE_STATUS(String STORE_STATUS) {
            this.STORE_STATUS = STORE_STATUS;
        }

        public String getSTORE_CODE() {
            return STORE_CODE;
        }

        public void setSTORE_CODE(String STORE_CODE) {
            this.STORE_CODE = STORE_CODE;
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

        public String getSYN_STATUS() {
            return SYN_STATUS;
        }

        public void setSYN_STATUS(String SYN_STATUS) {
            this.SYN_STATUS = SYN_STATUS;
        }

        public long getVAILD_TIME() {
            return VAILD_TIME;
        }

        public void setVAILD_TIME(long VAILD_TIME) {
            this.VAILD_TIME = VAILD_TIME;
        }

        public String getAPPID() {
            return APPID;
        }

        public void setAPPID(String APPID) {
            this.APPID = APPID;
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
    }
}
