package com.aliwh.android.quickyicha.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author liwh
 * @date 2018/7/17
 */
public class HealthBean {

    /**
     * msg : success
     * result : {"list":[{"content":"　　专家因此提醒市民，吃小龙虾一定要注意饮食卫生，如果烧煮时间短，细菌未被完全杀灭，极有可能造成食物中毒。   　　由于市场需求量大，目前的小龙虾基本都从外地购进，在夏季高温气候下再经过长途运输，死亡的小龙虾不在少数，个别饭店甚至将死龙虾用油炸过后出售。由于小龙虾体内含大量组氨酸，一旦小龙虾死亡，组氨酸会转化为有毒物质。虽然少量食用不会马上引起中毒，但日积月累会因为毒素积累引发疾病。   　　此外，小龙虾是肺吸虫的第二中间宿主，在烹饪时若未煮熟煮透，或采用烤、炒、腌、醉等形式烹饪，人食用后就有可能感染肺吸虫病。 　　专家还提醒，如果在食用小龙虾时，发现小龙虾的尾巴不蜷曲、肉质松散，有可能就是死龙虾，切勿食用。另外，一次食用小龙虾的数量也不宜过多。","id":7408,"title":"小龙虾烹饪时间过短易致中毒"}],"page":1,"total":1}
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
         * list : [{"content":"　　专家因此提醒市民，吃小龙虾一定要注意饮食卫生，如果烧煮时间短，细菌未被完全杀灭，极有可能造成食物中毒。   　　由于市场需求量大，目前的小龙虾基本都从外地购进，在夏季高温气候下再经过长途运输，死亡的小龙虾不在少数，个别饭店甚至将死龙虾用油炸过后出售。由于小龙虾体内含大量组氨酸，一旦小龙虾死亡，组氨酸会转化为有毒物质。虽然少量食用不会马上引起中毒，但日积月累会因为毒素积累引发疾病。   　　此外，小龙虾是肺吸虫的第二中间宿主，在烹饪时若未煮熟煮透，或采用烤、炒、腌、醉等形式烹饪，人食用后就有可能感染肺吸虫病。 　　专家还提醒，如果在食用小龙虾时，发现小龙虾的尾巴不蜷曲、肉质松散，有可能就是死龙虾，切勿食用。另外，一次食用小龙虾的数量也不宜过多。","id":7408,"title":"小龙虾烹饪时间过短易致中毒"}]
         * page : 1
         * total : 1
         */

        private int page;
        private int total;
        private List<ListBean> list;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            /**
             * content : 　　专家因此提醒市民，吃小龙虾一定要注意饮食卫生，如果烧煮时间短，细菌未被完全杀灭，极有可能造成食物中毒。   　　由于市场需求量大，目前的小龙虾基本都从外地购进，在夏季高温气候下再经过长途运输，死亡的小龙虾不在少数，个别饭店甚至将死龙虾用油炸过后出售。由于小龙虾体内含大量组氨酸，一旦小龙虾死亡，组氨酸会转化为有毒物质。虽然少量食用不会马上引起中毒，但日积月累会因为毒素积累引发疾病。   　　此外，小龙虾是肺吸虫的第二中间宿主，在烹饪时若未煮熟煮透，或采用烤、炒、腌、醉等形式烹饪，人食用后就有可能感染肺吸虫病。 　　专家还提醒，如果在食用小龙虾时，发现小龙虾的尾巴不蜷曲、肉质松散，有可能就是死龙虾，切勿食用。另外，一次食用小龙虾的数量也不宜过多。
             * id : 7408
             * title : 小龙虾烹饪时间过短易致中毒
             */

            private String content;
            private int id;
            private String title;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
