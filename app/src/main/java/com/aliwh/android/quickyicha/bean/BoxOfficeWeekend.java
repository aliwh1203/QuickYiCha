package com.aliwh.android.quickyicha.bean;

import java.util.List;

/**
 * @author liwh
 * @date 2018/4/26
 * 周末票房实体类
 */

public class BoxOfficeWeekend {

    /**
     * msg : success
     * result : [{"days":7,"name":"脱单告急","sum":3136.88,"weekendPeriod":"2018-04-20至2018-04-22","weekendSum":2289},{"days":56,"name":"厉害了，我的国","sum":47400.83,"weekendPeriod":"2018-04-20至2018-04-22","weekendSum":213},{"days":22,"name":"冰雪女王3：火与冰","sum":6066.1,"weekendPeriod":"2018-04-20至2018-04-22","weekendSum":375},{"days":7,"name":"犬之岛","sum":3281.65,"weekendPeriod":"2018-04-20至2018-04-22","weekendSum":2594},{"days":28,"name":"头号玩家","sum":132615.72,"weekendPeriod":"2018-04-20至2018-04-22","weekendSum":5145},{"days":14,"name":"湮灭","sum":6298.6,"weekendPeriod":"2018-04-20至2018-04-22","weekendSum":656},{"days":7,"name":"21克拉","sum":9347.74,"weekendPeriod":"2018-04-20至2018-04-22","weekendSum":6288},{"days":70,"name":"红海行动","sum":364469.86,"weekendPeriod":"2018-04-20至2018-04-22","weekendSum":259},{"days":14,"name":"狂暴巨兽","sum":74294.53,"weekendPeriod":"2018-04-20至2018-04-22","weekendSum":17073},{"days":23,"name":"起跑线","sum":20648.69,"weekendPeriod":"2018-04-20至2018-04-22","weekendSum":750}]
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
         * days : 7
         * name : 脱单告急
         * sum : 3136.88
         * weekendPeriod : 2018-04-20至2018-04-22
         * weekendSum : 2289
         */

        private int days;
        private String name;
        private double sum;
        private String weekendPeriod;
        private int weekendSum;

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getSum() {
            return sum;
        }

        public void setSum(double sum) {
            this.sum = sum;
        }

        public String getWeekendPeriod() {
            return weekendPeriod;
        }

        public void setWeekendPeriod(String weekendPeriod) {
            this.weekendPeriod = weekendPeriod;
        }

        public int getWeekendSum() {
            return weekendSum;
        }

        public void setWeekendSum(int weekendSum) {
            this.weekendSum = weekendSum;
        }
    }
}
