package com.aliwh.android.quickyicha.bean;

import java.util.List;

/**
 * @author liwh
 * @date 2018/4/26
 */

public class LotteryResult {


    /**
     * msg : success
     * result : {"awardDateTime":"2018-04-25 20:30","lotteryDetails":[{"awardNumber":3,"awardPrice":1.0E7,"awards":"一等奖"},{"awardNumber":1,"awardPrice":6000000,"awards":"一等奖","type":"追加"},{"awardNumber":62,"awardPrice":183363,"awards":"二等奖"},{"awardNumber":30,"awardPrice":110017,"awards":"二等奖","type":"追加"},{"awardNumber":814,"awardPrice":5182,"awards":"三等奖"},{"awardNumber":478,"awardPrice":3109,"awards":"三等奖","type":"追加"},{"awardNumber":31064,"awardPrice":200,"awards":"四等奖"},{"awardNumber":19008,"awardPrice":100,"awards":"四等奖","type":"追加"},{"awardNumber":588244,"awardPrice":10,"awards":"五等奖"},{"awardNumber":354994,"awardPrice":5,"awards":"五等奖","type":"追加"},{"awardNumber":5869867,"awardPrice":5,"awards":"六等奖"}],"lotteryNumber":["03","08","10","14","20","04","08"],"name":"大乐透","period":"18047","pool":5.29349148724E9,"sales":2.58398718E8}
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
         * awardDateTime : 2018-04-25 20:30
         * lotteryDetails : [{"awardNumber":3,"awardPrice":1.0E7,"awards":"一等奖"},{"awardNumber":1,"awardPrice":6000000,"awards":"一等奖","type":"追加"},{"awardNumber":62,"awardPrice":183363,"awards":"二等奖"},{"awardNumber":30,"awardPrice":110017,"awards":"二等奖","type":"追加"},{"awardNumber":814,"awardPrice":5182,"awards":"三等奖"},{"awardNumber":478,"awardPrice":3109,"awards":"三等奖","type":"追加"},{"awardNumber":31064,"awardPrice":200,"awards":"四等奖"},{"awardNumber":19008,"awardPrice":100,"awards":"四等奖","type":"追加"},{"awardNumber":588244,"awardPrice":10,"awards":"五等奖"},{"awardNumber":354994,"awardPrice":5,"awards":"五等奖","type":"追加"},{"awardNumber":5869867,"awardPrice":5,"awards":"六等奖"}]
         * lotteryNumber : ["03","08","10","14","20","04","08"]
         * name : 大乐透
         * period : 18047
         * pool : 5.29349148724E9
         * sales : 2.58398718E8
         */

        private String awardDateTime;
        private String name;
        private String period;
        private double pool;
        private double sales;
        private List<LotteryDetailsBean> lotteryDetails;
        private List<String> lotteryNumber;

        public String getAwardDateTime() {
            return awardDateTime;
        }

        public void setAwardDateTime(String awardDateTime) {
            this.awardDateTime = awardDateTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public double getPool() {
            return pool;
        }

        public void setPool(double pool) {
            this.pool = pool;
        }

        public double getSales() {
            return sales;
        }

        public void setSales(double sales) {
            this.sales = sales;
        }

        public List<LotteryDetailsBean> getLotteryDetails() {
            return lotteryDetails;
        }

        public void setLotteryDetails(List<LotteryDetailsBean> lotteryDetails) {
            this.lotteryDetails = lotteryDetails;
        }

        public List<String> getLotteryNumber() {
            return lotteryNumber;
        }

        public void setLotteryNumber(List<String> lotteryNumber) {
            this.lotteryNumber = lotteryNumber;
        }

        public static class LotteryDetailsBean {
            /**
             * awardNumber : 3
             * awardPrice : 1.0E7
             * awards : 一等奖
             * type : 追加
             */

            private int awardNumber;
            private double awardPrice;
            private String awards;
            private String type;

            public int getAwardNumber() {
                return awardNumber;
            }

            public void setAwardNumber(int awardNumber) {
                this.awardNumber = awardNumber;
            }

            public double getAwardPrice() {
                return awardPrice;
            }

            public void setAwardPrice(double awardPrice) {
                this.awardPrice = awardPrice;
            }

            public String getAwards() {
                return awards;
            }

            public void setAwards(String awards) {
                this.awards = awards;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
