package com.aliwh.android.quickyicha.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.activity.CarDetailActivity;
import com.aliwh.android.quickyicha.activity.CarSeriesActivity;
import com.aliwh.android.quickyicha.bean.CarBrand;
import com.aliwh.android.quickyicha.bean.CarSeries;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 车型信息适配器
 *
 * @author liwh
 */
public class CarTypeAdapter extends BaseAdapter {

    private Context context;
    private List<CarBrand.ResultBean.SonBean> list;

    public CarTypeAdapter(Context context, List<CarBrand.ResultBean.SonBean> list) {
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
    public View getView(final int arg0, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.car_type_item, null);
            // 初始化View
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.carTypeName.setText(list.get(arg0).getCar() + list.get(arg0).getType());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CarSeriesActivity.class);
                intent.putExtra("type", list.get(arg0).getType());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @ViewInject(R.id.car_type_name)
        private TextView carTypeName;
    }
}
