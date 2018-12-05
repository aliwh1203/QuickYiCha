package com.aliwh.android.quickyicha.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.aliwh.android.quickyicha.BuildConfig;
import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.XutilsHttp;
import com.aliwh.android.quickyicha.adapter.CarSeriesAdapter;
import com.aliwh.android.quickyicha.bean.CarSeries;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 车型类别页面
 */
@ContentView(R.layout.activity_car_series)
public class CarSeriesActivity extends BaseActivity {

    @ViewInject(R.id.car_series_listview)
    private ListView carSeriesListView;

    private String carType;
    private List<CarSeries.ResultBean> resultBeen = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carType = getIntent().getStringExtra("type");
        loadCarSeriesData();
    }

    private void loadCarSeriesData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.MOB_API_KEY);
        map.put("name", carType);
        DialogUtil.showRequestDialog(CarSeriesActivity.this, "加载中...");
        XutilsHttp.getInstance().get(Constants.CAR_TYPE_QUERY, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("allwhere", "onResponse: " + BuildConfig.DEBUG);
                Log.e("allwhere", "onResponse: " + result.toString());
                if (result != null) {
                    Gson gson = new Gson();
                    CarSeries carBean = gson.fromJson(result, CarSeries.class);
                    if (carBean.getRetCode().equals(Constants.REQUEST_SUCCESS)) {
                        resultBeen = carBean.getResult();
                        CarSeriesAdapter carSeriesAdapter = new CarSeriesAdapter(CarSeriesActivity.this, resultBeen);
                        carSeriesListView.setAdapter(carSeriesAdapter);
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(CarSeriesActivity.this, "获取数据成功");
                    } else {
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(CarSeriesActivity.this, "服务器数据异常，请稍后再试！");
                    }
                } else {
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(CarSeriesActivity.this, "服务器数据异常，请稍后再试！");
                }

            }

            @Override
            public void onFail(String result) {
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(CarSeriesActivity.this, "信息查询失败" + result);
            }

            @Override
            public void onCancel(Callback.CancelledException cex) {
                DialogUtil.closeRequestDialog();
            }
        });

    }


    /**
     * 返回按钮
     */
    @Event(R.id.back_btn)
    private void onBack(View view) {
        this.finish();
    }
}
