package com.aliwh.android.quickyicha.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.bean.CarDetail;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 车辆详情参数显示适配器
 *
 * @author liwh
 */
public class CarParamsAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T> list;
    private int PARAM_TYPE = 0;

    public CarParamsAdapter(Context context, List<T> list, int PARAM_TYPE) {
        this.list = list;
        this.context = context;
        this.PARAM_TYPE = PARAM_TYPE;
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
            convertView = View.inflate(context, R.layout.car_params_item, null);
            // 初始化View
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (PARAM_TYPE == 1) {
            CarDetail.ResultBean.AirConfigBean airConfigBean = (CarDetail.ResultBean.AirConfigBean) list.get(arg0);
            holder.carParamsText.setText(airConfigBean.getName() + "：" + airConfigBean.getValue());
        } else if (PARAM_TYPE == 2) {
            CarDetail.ResultBean.BaseInfoBean beseInfoBean = (CarDetail.ResultBean.BaseInfoBean) list.get(arg0);
            holder.carParamsText.setText(beseInfoBean.getName() + "：" + beseInfoBean.getValue());
        } else if (PARAM_TYPE == 3) {
            CarDetail.ResultBean.CarbodyBean carbodyBean = (CarDetail.ResultBean.CarbodyBean) list.get(arg0);
            holder.carParamsText.setText(carbodyBean.getName() + "：" + carbodyBean.getValue());
        } else if (PARAM_TYPE == 4) {
            CarDetail.ResultBean.ChassisBean chassisBean = (CarDetail.ResultBean.ChassisBean) list.get(arg0);
            holder.carParamsText.setText(chassisBean.getName() + "：" + chassisBean.getValue());
        } else if (PARAM_TYPE == 5) {
            CarDetail.ResultBean.ControlConfigBean controlConfigBean = (CarDetail.ResultBean.ControlConfigBean) list.get(arg0);
            holder.carParamsText.setText(controlConfigBean.getName() + "：" + controlConfigBean.getValue());
        } else if (PARAM_TYPE == 6) {
            CarDetail.ResultBean.EngineBean engineBean = (CarDetail.ResultBean.EngineBean) list.get(arg0);
            holder.carParamsText.setText(engineBean.getName() + "：" + engineBean.getValue());
        } else if (PARAM_TYPE == 7) {
            CarDetail.ResultBean.ExterConfigBean exterConfigBean = (CarDetail.ResultBean.ExterConfigBean) list.get(arg0);
            holder.carParamsText.setText(exterConfigBean.getName() + "：" + exterConfigBean.getValue());
        } else if (PARAM_TYPE == 8) {
            CarDetail.ResultBean.GlassConfigBean glassConfigBean = (CarDetail.ResultBean.GlassConfigBean) list.get(arg0);
            holder.carParamsText.setText(glassConfigBean.getName() + "：" + glassConfigBean.getValue());
        } else if (PARAM_TYPE == 9) {
            CarDetail.ResultBean.InterConfigBean interConfigBean = (CarDetail.ResultBean.InterConfigBean) list.get(arg0);
            holder.carParamsText.setText(interConfigBean.getName() + "：" + interConfigBean.getValue());
        } else if (PARAM_TYPE == 10) {
            CarDetail.ResultBean.LightConfigBean lightConfigBean = (CarDetail.ResultBean.LightConfigBean) list.get(arg0);
            holder.carParamsText.setText(lightConfigBean.getName() + "：" + lightConfigBean.getValue());
        } else if (PARAM_TYPE == 11) {
            CarDetail.ResultBean.MediaConfigBean mediaConfigBean = (CarDetail.ResultBean.MediaConfigBean) list.get(arg0);
            holder.carParamsText.setText(mediaConfigBean.getName() + "：" + mediaConfigBean.getValue());
        } else if (PARAM_TYPE == 12) {
            CarDetail.ResultBean.SafetyDeviceBean safetyDeviceBean = (CarDetail.ResultBean.SafetyDeviceBean) list.get(arg0);
            holder.carParamsText.setText(safetyDeviceBean.getName() + "：" + safetyDeviceBean.getValue());
        } else if (PARAM_TYPE == 13) {
            CarDetail.ResultBean.SeatConfigBean seatConfigBean = (CarDetail.ResultBean.SeatConfigBean) list.get(arg0);
            holder.carParamsText.setText(seatConfigBean.getName() + "：" + seatConfigBean.getValue());
        } else if (PARAM_TYPE == 14) {
            CarDetail.ResultBean.TechConfigBean techConfigBean = (CarDetail.ResultBean.TechConfigBean) list.get(arg0);
            holder.carParamsText.setText(techConfigBean.getName() + "：" + techConfigBean.getValue());
        } else if (PARAM_TYPE == 15) {
            CarDetail.ResultBean.TransmissionBean transmissionBean = (CarDetail.ResultBean.TransmissionBean) list.get(arg0);
            holder.carParamsText.setText(transmissionBean.getName() + "：" + transmissionBean.getValue());
        } else if (PARAM_TYPE == 16) {
            CarDetail.ResultBean.WheelInfoBean wheelInfoBean = (CarDetail.ResultBean.WheelInfoBean) list.get(arg0);
            holder.carParamsText.setText(wheelInfoBean.getName() + "：" + wheelInfoBean.getValue());
        }


        return convertView;
    }

    static class ViewHolder {
        @ViewInject(R.id.car_params_text)
        private TextView carParamsText;

    }
}
