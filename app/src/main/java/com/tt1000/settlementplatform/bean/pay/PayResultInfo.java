package com.tt1000.settlementplatform.bean.pay;

public class PayResultInfo {


    /**
     * code : 0000
     * data : {"openId":"o6PxrwuVj7JqEeqyu23a3LAvW53Q","outOrderNo":"15740464426911281437","payTime":"2019-11-18 11:07:29","productId":"0106","referNo":"10021015740464469601933","signature":"HgUhU90E16L7//s1RQ1KPQrUc5O41pUt3H8FaX5QdoQsjfDUA1SFKKflhnq/Dv5IAXEoOVqu2xKnxozPKi6lhbtlHXtYwK58S1MnVvmLBRrk1URIEYheT9OEP8Qu2MIHq06HkM1hM+xI35IC+IZgy12P0RVGvgDBq/2OKe56E7U=","status":1,"tramsAmt":"10","transactionId":"10021015740464469601933"}
     * dataJson : {"authCode":"134606039992107533","clientCode":"155774791138413","clientType":"1","code":"0000","missTime":3461,"outOrderNo":"15740464426911281437","payType":"2","productId":"0106","requestNum":"15740464426911281437","startTime":"1574046446653","storeCode":"0000","transAmt":"0.1","transamt":"0.1"}
     * msg : 交易成功
     * result : true
     * status : 200
     * type : 0
     */

    private String code;
    private DataBean data;
    private DataJsonBean dataJson;
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

    public DataJsonBean getDataJson() {
        return dataJson;
    }

    public void setDataJson(DataJsonBean dataJson) {
        this.dataJson = dataJson;
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
         * openId : o6PxrwuVj7JqEeqyu23a3LAvW53Q
         * outOrderNo : 15740464426911281437
         * payTime : 2019-11-18 11:07:29
         * productId : 0106
         * referNo : 10021015740464469601933
         * signature : HgUhU90E16L7//s1RQ1KPQrUc5O41pUt3H8FaX5QdoQsjfDUA1SFKKflhnq/Dv5IAXEoOVqu2xKnxozPKi6lhbtlHXtYwK58S1MnVvmLBRrk1URIEYheT9OEP8Qu2MIHq06HkM1hM+xI35IC+IZgy12P0RVGvgDBq/2OKe56E7U=
         * status : 1
         * tramsAmt : 10
         * transactionId : 10021015740464469601933
         */

        private String openId;
        private String outOrderNo;
        private String payTime;
        private String productId;
        private String referNo;
        private String signature;
        private int status;
        private String tramsAmt;
        private String transactionId;

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getOutOrderNo() {
            return outOrderNo;
        }

        public void setOutOrderNo(String outOrderNo) {
            this.outOrderNo = outOrderNo;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getReferNo() {
            return referNo;
        }

        public void setReferNo(String referNo) {
            this.referNo = referNo;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTramsAmt() {
            return tramsAmt;
        }

        public void setTramsAmt(String tramsAmt) {
            this.tramsAmt = tramsAmt;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }
    }

    public static class DataJsonBean {
        /**
         * authCode : 134606039992107533
         * clientCode : 155774791138413
         * clientType : 1
         * code : 0000
         * missTime : 3461
         * outOrderNo : 15740464426911281437
         * payType : 2
         * productId : 0106
         * requestNum : 15740464426911281437
         * startTime : 1574046446653
         * storeCode : 0000
         * transAmt : 0.1
         * transamt : 0.1
         */

        private String authCode;
        private String clientCode;
        private String clientType;
        private String code;
        private int missTime;
        private String outOrderNo;
        private String payType;
        private String productId;
        private String requestNum;
        private String startTime;
        private String storeCode;
        private String transAmt;
        private String transamt;

        public String getAuthCode() {
            return authCode;
        }

        public void setAuthCode(String authCode) {
            this.authCode = authCode;
        }

        public String getClientCode() {
            return clientCode;
        }

        public void setClientCode(String clientCode) {
            this.clientCode = clientCode;
        }

        public String getClientType() {
            return clientType;
        }

        public void setClientType(String clientType) {
            this.clientType = clientType;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getMissTime() {
            return missTime;
        }

        public void setMissTime(int missTime) {
            this.missTime = missTime;
        }

        public String getOutOrderNo() {
            return outOrderNo;
        }

        public void setOutOrderNo(String outOrderNo) {
            this.outOrderNo = outOrderNo;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getRequestNum() {
            return requestNum;
        }

        public void setRequestNum(String requestNum) {
            this.requestNum = requestNum;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public String getTransAmt() {
            return transAmt;
        }

        public void setTransAmt(String transAmt) {
            this.transAmt = transAmt;
        }

        public String getTransamt() {
            return transamt;
        }

        public void setTransamt(String transamt) {
            this.transamt = transamt;
        }
    }
}
