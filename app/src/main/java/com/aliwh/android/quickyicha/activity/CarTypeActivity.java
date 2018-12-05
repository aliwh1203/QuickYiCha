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
import com.aliwh.android.quickyicha.adapter.CarTypeAdapter;
import com.aliwh.android.quickyicha.bean.CarBrand;
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
@ContentView(R.layout.activity_car_search_type)
public class CarTypeActivity extends BaseActivity {

    @ViewInject(R.id.car_type_listview)
    private ListView carTypeListView;

    private CarBrand.ResultBean resultBean = new CarBrand.ResultBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultBean = (CarBrand.ResultBean) getIntent().getSerializableExtra("carBrandResult");
        CarTypeAdapter carTypeAdapter = new CarTypeAdapter(CarTypeActivity.this, resultBean.getSon());
        carTypeListView.setAdapter(carTypeAdapter);
    }


    /**
     * 返回按钮
     */
    @Event(R.id.back_btn)
    private void onBack(View view) {
        this.finish();
    }
}
