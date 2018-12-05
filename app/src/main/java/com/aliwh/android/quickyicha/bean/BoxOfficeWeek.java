package com.aliwh.android.quickyicha.bean;

import java.util.List;

/**
 * @author liwh
 * @date 2018/4/26
 * 周票房实体类
 */

public class BoxOfficeWeek {

    /**
     * msg : success
     * result : [{"days":7,"name":"脱单告急","sum":3134.12,"weekPeriod":"2018-04-16至2018-04-22","weekSum":2291},{"days":56,"name":"厉害了，我的国","sum":47400.74,"weekPeriod":"2018-04-16至2018-04-22","weekSum":582},{"days":22,"name":"冰雪女王3：火与冰","sum":6066.1,"weekPeriod":"2018-04-16至2018-04-22","weekSum":467},{"days":7,"name":"犬之岛","sum":3278.82,"weekPeriod":"2018-04-16至2018-04-22","weekSum":2607},{"days":28,"name":"头号玩家","sum":132606.52,"weekPeriod":"2018-04-16至2018-04-22","weekSum":9204},{"days":14,"name":"湮灭","sum":6297.14,"weekPeriod":"2018-04-16至2018-04-22","weekSum":2108},{"days":7,"name":"21克拉","sum":9337.94,"weekPeriod":"2018-04-16至2018-04-22","weekSum":6301},{"days":70,"name":"红海行动","sum":364469.14,"weekPeriod":"2018-04-16至2018-04-22","weekSum":553},{"days":14,"name":"狂暴巨兽","sum":74272.03,"weekPeriod":"2018-04-16至2018-04-22","weekSum":32305},{"days":23,"name":"起跑线","sum":20647.46,"weekPeriod":"2018-04-16至2018-04-22","weekSum":1798}]
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
         * sum : 3134.12
         * weekPeriod : 2018-04-16至2018-04-22
         * weekSum : 2291
         */

        private int days;
        private String name;
        private double sum;
        private String weekPeriod;
        private int weekSum;

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

        public String getWeekPeriod() {
            return weekPeriod;
        }

        public void setWeekPeriod(String weekPeriod) {
            this.weekPeriod = weekPeriod;
        }

        public int getWeekSum() {
            return weekSum;
        }

        public void setWeekSum(int weekSum) {
            this.weekSum = weekSum;
        }
    }
}
