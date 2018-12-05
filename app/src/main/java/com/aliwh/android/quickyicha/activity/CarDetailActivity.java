package com.aliwh.android.quickyicha.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliwh.android.quickyicha.BuildConfig;
import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.MyApplication;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.XutilsHttp;
import com.aliwh.android.quickyicha.adapter.CarParamsAdapter;
import com.aliwh.android.quickyicha.bean.CarDetail;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.aliwh.android.quickyicha.view.MyListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

/**
 * 汽车详情页面
 */
@ContentView(R.layout.activity_car_detail)
public class CarDetailActivity extends BaseActivity {

    @ViewInject(R.id.car_image)
    private ImageView carImageView;
    @ViewInject(R.id.car_brand)
    private TextView carBrand;
    @ViewInject(R.id.car_brand_name)
    private TextView carBrandName;

    @ViewInject(R.id.airConfig_listview)
    private MyListView airConfigListView;

    @ViewInject(R.id.baseInfo_listview)
    private MyListView baseInfoListView;

    @ViewInject(R.id.carbody_listview)
    private MyListView carbodyListView;

    @ViewInject(R.id.chassis_listview)
    private MyListView chassislistView;

    @ViewInject(R.id.controlConfig_listview)
    private MyListView controlConfigListView;

    @ViewInject(R.id.engine_listview)
    private MyListView engineListView;

    @ViewInject(R.id.exterConfig_listview)
    private MyListView exterConfigListView;

    @ViewInject(R.id.glassConfig_listview)
    private MyListView glassConfigListLiew;

    @ViewInject(R.id.interConfig_listview)
    private MyListView interConfigListView;

    @ViewInject(R.id.lightConfig_listview)
    private MyListView lightConfigListView;

    @ViewInject(R.id.mediaConfig_listview)
    private MyListView mediaConfigListView;

    @ViewInject(R.id.safetyDevice_listview)
    private MyListView safetyDeviceListView;

    @ViewInject(R.id.seatConfig_listview)
    private MyListView seatConfigListView;

    @ViewInject(R.id.techConfig_listview)
    private MyListView techConfigListView;

    @ViewInject(R.id.transmission_listview)
    private MyListView transmissionListView;

    @ViewInject(R.id.wheelInfo_listview)
    private MyListView wheelInfoListView;

    private String carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carId = getIntent().getStringExtra("cid");
        //ListView顶部或者底部也显示分割线
        airConfigListView.addHeaderView(new ViewStub(this));
        airConfigListView.addFooterView(new ViewStub(this));
        baseInfoListView.addHeaderView(new ViewStub(this));
        baseInfoListView.addFooterView(new ViewStub(this));
        carbodyListView.addHeaderView(new ViewStub(this));
        carbodyListView.addFooterView(new ViewStub(this));
        chassislistView.addHeaderView(new ViewStub(this));
        chassislistView.addFooterView(new ViewStub(this));
        controlConfigListView.addHeaderView(new ViewStub(this));
        controlConfigListView.addFooterView(new ViewStub(this));
        engineListView.addHeaderView(new ViewStub(this));
        engineListView.addFooterView(new ViewStub(this));
        exterConfigListView.addHeaderView(new ViewStub(this));
        exterConfigListView.addFooterView(new ViewStub(this));
        glassConfigListLiew.addHeaderView(new ViewStub(this));
        glassConfigListLiew.addFooterView(new ViewStub(this));
        interConfigListView.addHeaderView(new ViewStub(this));
        interConfigListView.addFooterView(new ViewStub(this));
        lightConfigListView.addHeaderView(new ViewStub(this));
        lightConfigListView.addFooterView(new ViewStub(this));
        mediaConfigListView.addHeaderView(new ViewStub(this));
        mediaConfigListView.addFooterView(new ViewStub(this));
        safetyDeviceListView.addHeaderView(new ViewStub(this));
        safetyDeviceListView.addFooterView(new ViewStub(this));
        seatConfigListView.addHeaderView(new ViewStub(this));
        seatConfigListView.addFooterView(new ViewStub(this));
        techConfigListView.addHeaderView(new ViewStub(this));
        techConfigListView.addFooterView(new ViewStub(this));
        transmissionListView.addHeaderView(new ViewStub(this));
        transmissionListView.addFooterView(new ViewStub(this));
        wheelInfoListView.addHeaderView(new ViewStub(this));
        wheelInfoListView.addFooterView(new ViewStub(this));

        loadCarDetailData();
    }

    private void loadCarDetailData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.MOB_API_KEY);
        map.put("cid", carId);
        DialogUtil.showRequestDialog(CarDetailActivity.this, "加载中...");
        XutilsHttp.getInstance().get(Constants.CAR_DETAIL_QUERY, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("allwhere", "onResponse: " + BuildConfig.DEBUG);
                Log.e("allwhere", "onResponse: " + result.toString());
                if (result != null) {
                    Gson gson = new Gson();
                    CarDetail carDetail = gson.fromJson(result, CarDetail.class);
                    if (carDetail.getRetCode().equals(Constants.REQUEST_SUCCESS)) {
                        CarDetail.ResultBean resultBean = carDetail.getResult().get(0);
                        x.image().bind(carImageView, resultBean.getCarImage(), MyApplication.builder.build());
                        carBrand.setText(resultBean.getBrand());
                        carBrandName.setText(resultBean.getBrandName());
                        airConfigListView.setAdapter(new CarParamsAdapter(CarDetailActivity.this, resultBean.getAirConfig(), 1));
                        baseInfoListView.setAdapter(new CarParamsAdapter(CarDetailActivity.this, resultBean.getBaseInfo(), 2));
                        carbodyListView.setAdapter(new CarParamsAdapter(CarDetailActivity.this, resultBean.getCarbody(), 3));
                        chassislistView.setAdapter(new CarParamsAdapter(CarDetailActivity.this, resultBean.getChassis(), 4));
                        controlConfigListView.setAdapter(new CarParamsAdapter(CarDetailActivity.this, resultBean.getControlConfig(), 5));
                        engineListView.setAdapter(new CarParamsAdapter(CarDetailActivity.this, resultBean.getEngine(), 6));
                        exterConfigListView.setAdapter(new CarParamsAdapter(CarDetailActivity.this, resultBean.getExterConfig(), 7));
                        glassConfigListLiew.setAdapter(new CarParamsAdapter(CarDetailActivity.this, resultBean.getGlassConfig(), 8));
                        interConfigListView.setAdapter(new CarParamsAdapter(CarDetailActivity.this, resultBean.getInterConfig(), 9));
                        lightConfigListView.setAdapter(new CarParamsAdapter(CarDetailActivity.this, resultBean.getLightConfig(), 10));
                        mediaConfigListView.setAdapter(new CarParamsAdapter(CarDetailActivity.this, resultBean.getMediaConfig(), 11));
                        safetyDeviceListView.setAdapter(new CarParamsAdapter(CarDetailActivity.this, resultBean.getSafetyDevice(), 12));
                        seatConfigListView.setAdapter(new CarParamsAdapter(CarDetailActivity.this, resultBean.getSeatConfig(), 13));
                        techConfigListView.setAdapter(new CarParamsAdapter(CarDetailActivity.this, resultBean.getTechConfig(), 14));
                        transmissionListView.setAdapter(new CarParamsAdapter(CarDetailActivity.this, resultBean.getTransmission(), 15));
                        wheelInfoListView.setAdapter(new CarParamsAdapter(CarDetailActivity.this, resultBean.getWheelInfo(), 16));
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(CarDetailActivity.this, "获取数据成功");
                    } else {
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(CarDetailActivity.this, "服务器数据异常，请稍后再试！");
                    }
                } else {
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(CarDetailActivity.this, "服务器数据异常，请稍后再试！");
                }

            }

            @Override
            public void onFail(String result) {
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(CarDetailActivity.this, "信息查询失败" + result);
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
