package com.tt1000.settlementplatform.bean.result_info;

public class VerifyMachineResultInfo {

    /**
     "SEQNO": "153187691948518000015318774835761451",--主键
     "MACHINE_NO": "153187742016013",--机器号
     "MACHINE_NAME": "8888",--机器名称
     "REGISTER_IP": "192.168.1.100",--机器的注册IP
     "REMARKS": "",--备注
     "USE_STATUS": "0",--状态（0：正常使用；1：停用）
     "U_ID": "0",--操作员Id
     "CREATETIME": 1531877483000,--创建时间
     "UPDATETIME": 1531877483000,--更新时间
     "CLIENT_CODE": "153187691948518",--客户代码
     "STORE_CODE": "0000",--店铺代码
     "VAILD_TIME": 1531843200000,--机器有效期
     "ONLINE": "0",--是否在线（0：在线；1：离线）
     "SYN_STATUS": "0",--同步状态
     "MAC": "68-ED-A4-0C-22-F2"--机器Mac码
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
    private String MAC;

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

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }
}
