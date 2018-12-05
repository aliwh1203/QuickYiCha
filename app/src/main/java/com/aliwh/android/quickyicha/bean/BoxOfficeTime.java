package com.aliwh.android.quickyicha.bean;

import java.util.List;

/**
 * @author liwh
 * @date 2018/4/26
 * 实时票房实体类
 */

public class BoxOfficeTime {

    /**
     * msg : success
     * result : [{"cur":97.27,"days":7,"name":"脱单告急","sum":3120.14},{"cur":6.98,"days":23,"name":"暴裂无声","sum":5359.65},{"cur":89.25,"days":56,"name":"厉害了，我的国","sum":47396.33},{"cur":2.95,"days":22,"name":"冰雪女王3：火与冰","sum":6066.1},{"cur":95.14,"days":7,"name":"犬之岛","sum":3267.19},{"cur":266.57,"days":28,"name":"头号玩家","sum":132571.17},{"cur":42.53,"days":14,"name":"湮灭","sum":6290.81},{"cur":286.59,"days":7,"name":"21克拉","sum":9289.92},{"cur":23.83,"days":70,"name":"红海行动","sum":364465.77},{"cur":715.73,"days":14,"name":"狂暴巨兽","sum":74163.68},{"cur":42.53,"days":23,"name":"起跑线","sum":20640.66},{"cur":2.64,"days":35,"name":"出山记","sum":38.54}]
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
         * cur : 97.27
         * days : 7
         * name : 脱单告急
         * sum : 3120.14
         */

        private double cur;
        private int days;
        private String name;
        private double sum;

        public double getCur() {
            return cur;
        }

        public void setCur(double cur) {
            this.cur = cur;
        }

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
    }
}
