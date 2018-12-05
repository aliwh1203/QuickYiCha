package com.aliwh.android.quickyicha.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.bean.LotteryResult;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.math.BigDecimal;
import java.util.List;

/**
 * 彩票中奖信息适配器
 *
 * @author liwh
 */
public class LotteryResultAdapter extends BaseAdapter {

    private Context context;
    private List<LotteryResult.ResultBean.LotteryDetailsBean> list;

    public LotteryResultAdapter(Context context, List<LotteryResult.ResultBean.LotteryDetailsBean> list) {
        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.lottery_result_item, null);
            // 初始化View
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (TextUtils.isEmpty(list.get(arg0).getType())) {
            holder.tv_item_awards.setText("奖项：" + list.get(arg0).getAwards());
        } else {
            holder.tv_item_awards.setText("奖项：" + list.get(arg0).getAwards() + "（" + list.get(arg0).getType() + "）");
        }
        holder.tv_item_awards.setText("奖项：" + list.get(arg0).getAwards());
        holder.tv_item_awardprice.setText("中奖金额：" + new BigDecimal(list.get(arg0).getAwardPrice()).longValue() + "元");
        holder.tv_item_awardmumber.setText("中奖注数：" + list.get(arg0).getAwardNumber());
        return convertView;
    }

    static class ViewHolder {
        @ViewInject(R.id.item_awards)
        private TextView tv_item_awards;
        @ViewInject(R.id.item_awardprice)
        private TextView tv_item_awardprice;
        @ViewInject(R.id.item_awardmumber)
        private TextView tv_item_awardmumber;
    }
}
