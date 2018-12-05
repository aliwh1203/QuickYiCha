package com.aliwh.android.quickyicha.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.bean.BoxOfficeWeek;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 周票房信息适配器
 *
 * @author liwh
 */
public class BoxOfficeWeekAdapter extends BaseAdapter {

    private Context context;
    private List<BoxOfficeWeek.ResultBean> list;

    public BoxOfficeWeekAdapter(Context context, List<BoxOfficeWeek.ResultBean> list) {
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
            convertView = View.inflate(context, R.layout.box_office_week_item, null);
            // 初始化View
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_item_name.setText("电影名称：" + list.get(arg0).getName());
        holder.tv_item_days.setText("已上映天数：" + list.get(arg0).getDays());
        holder.tv_item_weeksum.setText("周票房(内地)：" + list.get(arg0).getWeekSum() + "万元");
        holder.tv_item_sum.setText("当前总票房(内地)：" + list.get(arg0).getSum() + "万元");
        holder.tv_item_weekperiod.setText("周票房的周期：" + list.get(arg0).getWeekPeriod());
        return convertView;
    }

    static class ViewHolder {
        @ViewInject(R.id.item_name)
        private TextView tv_item_name;
        @ViewInject(R.id.item_days)
        private TextView tv_item_days;
        @ViewInject(R.id.item_weeksum)
        private TextView tv_item_weeksum;
        @ViewInject(R.id.item_sum)
        private TextView tv_item_sum;
        @ViewInject(R.id.item_weekperiod)
        private TextView tv_item_weekperiod;
    }
}
