package com.aliwh.android.quickyicha.bean;

/**
 * @author liwh
 * @date 2018/4/25
 * 号码归属地实体类
 */

public class PhoneAddress {

    /**
     * msg : success
     * result : {"city":"深圳市","cityCode":"0755","mobileNumber":"1318976","operator":"联通如意通卡","province":"广东","zipCode":"518000"}
     * retCode : 200
     */

    private String msg;
    private ResultBean result;
    private String retCode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public static class ResultBean {
        /**
         * city : 深圳市
         * cityCode : 0755
         * mobileNumber : 1318976
         * operator : 联通如意通卡
         * province : 广东
         * zipCode : 518000
         */

        private String city;
        private String cityCode;
        private String mobileNumber;
        private String operator;
        private String province;
        private String zipCode;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }
}
