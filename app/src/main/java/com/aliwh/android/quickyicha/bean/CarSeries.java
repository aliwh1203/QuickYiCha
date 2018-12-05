package com.aliwh.android.quickyicha.bean;

import java.util.List;

/**
 * @author liwh
 * @date 2018/4/27
 * 汽车车型信息实体类
 */

public class CarSeries {

    /**
     * msg : success
     * result : [{"brandName":"奥迪Q5","carId":"1060133","guidePrice":"38.34万","seriesName":"奥迪Q5 2016款 40 TFSI 进取型"},{"brandName":"奥迪Q5","carId":"1060134","guidePrice":"42.76万","seriesName":"奥迪Q5 2016款 40 TFSI 技术型"},{"brandName":"奥迪Q5","carId":"1060135","guidePrice":"47.90万","seriesName":"奥迪Q5 2016款 40 TFSI 舒适型"},{"brandName":"奥迪Q5","carId":"1060136","guidePrice":"50.90万","seriesName":"奥迪Q5 2016款 40 TFSI 动感型plus"},{"brandName":"奥迪Q5","carId":"1060137","guidePrice":"53.40万","seriesName":"奥迪Q5 2016款 40 TFSI 豪华型plus"}]
     * retCode : 200
     */

    private String msg;
    private String retCode;
    private List<ResultBean> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * brandName : 奥迪Q5
         * carId : 1060133
         * guidePrice : 38.34万
         * seriesName : 奥迪Q5 2016款 40 TFSI 进取型
         */

        private String brandName;
        private String carId;
        private String guidePrice;
        private String seriesName;

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public String getGuidePrice() {
            return guidePrice;
        }

        public void setGuidePrice(String guidePrice) {
            this.guidePrice = guidePrice;
        }

        public String getSeriesName() {
            return seriesName;
        }

        public void setSeriesName(String seriesName) {
            this.seriesName = seriesName;
        }
    }
}
