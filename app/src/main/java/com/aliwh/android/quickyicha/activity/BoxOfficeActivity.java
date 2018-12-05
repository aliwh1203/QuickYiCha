package com.aliwh.android.quickyicha.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.aliwh.android.quickyicha.BuildConfig;
import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.XutilsHttp;
import com.aliwh.android.quickyicha.adapter.BoxOfficeTimeAdapter;
import com.aliwh.android.quickyicha.adapter.BoxOfficeWeekAdapter;
import com.aliwh.android.quickyicha.adapter.BoxOfficeWeekendAdapter;
import com.aliwh.android.quickyicha.bean.BoxOfficeTime;
import com.aliwh.android.quickyicha.bean.BoxOfficeWeek;
import com.aliwh.android.quickyicha.bean.BoxOfficeWeekend;
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
import java.util.Map;

/**
 * 电影票房查询页面
 */
@ContentView(R.layout.activity_box_office)
public class BoxOfficeActivity extends BaseActivity {

    @ViewInject(R.id.box_office_listview)
    private ListView boxOfficeListview;

    private List<BoxOfficeTime.ResultBean> boxOfficeTimeList = new ArrayList<>();
    private List<BoxOfficeWeek.ResultBean> boxOfficeWeekList = new ArrayList<>();
    private List<BoxOfficeWeekend.ResultBean> boxOfficeWeekendList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadBoxOfficeTimeData();
    }


    @Event(value = R.id.box_office_radio_group, type = RadioGroup.OnCheckedChangeListener.class)
    private void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (checkedId == R.id.box_office_time_rb) {
            loadBoxOfficeTimeData();
        } else if (checkedId == R.id.box_office_week_rb) {
            loadBoxOfficeWeekData();
        } else if (checkedId == R.id.box_office_weekend_rb) {
            loadBoxOfficeWeekendData();
        }
    }

    /**
     * 加载实时票房数据
     */
    private void loadBoxOfficeTimeData() {
        DialogUtil.showRequestDialog(BoxOfficeActivity.this, "加载中...");
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.MOB_API_KEY);
        map.put("area", "CN");
        XutilsHttp.getInstance().get(Constants.BOX_OFFICE_TIME_QUERY, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("allwhere", "onResponse: " + BuildConfig.DEBUG);
                Log.e("allwhere", "onResponse: " + result.toString());
                if (result != null) {
                    Gson gson = new Gson();
                    BoxOfficeTime boxOfficeTime = gson.fromJson(result, BoxOfficeTime.class);
                    if (boxOfficeTime.getRetCode().equals(Constants.REQUEST_SUCCESS)) {
                        boxOfficeTimeList = boxOfficeTime.getResult();
                        BoxOfficeTimeAdapter boxOfficeTimeAdapter = new BoxOfficeTimeAdapter(BoxOfficeActivity.this, boxOfficeTimeList);
                        boxOfficeListview.setAdapter(boxOfficeTimeAdapter);
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(BoxOfficeActivity.this, "获取数据成功");
                    } else {
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(BoxOfficeActivity.this, "服务器数据异常，请稍后再试！");
                    }
                } else {
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(BoxOfficeActivity.this, "服务器数据异常，请稍后再试！");
                }

            }

            @Override
            public void onFail(String result) {
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(BoxOfficeActivity.this, "信息查询失败" + result);
            }

            @Override
            public void onCancel(Callback.CancelledException cex) {

            }
        });
    }

    /**
     * 加载周票房数据
     */
    private void loadBoxOfficeWeekData() {
        DialogUtil.showRequestDialog(BoxOfficeActivity.this, "加载中...");
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.MOB_API_KEY);
        map.put("area", "CN");
        XutilsHttp.getInstance().get(Constants.BOX_OFFICE_WEEK_QUERY, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("allwhere", "onResponse: " + BuildConfig.DEBUG);
                Log.e("allwhere", "onResponse: " + result.toString());
                if (result != null) {
                    Gson gson = new Gson();
                    BoxOfficeWeek boxOfficeWeek = gson.fromJson(result, BoxOfficeWeek.class);
                    if (boxOfficeWeek.getRetCode().equals(Constants.REQUEST_SUCCESS)) {
                        boxOfficeWeekList = boxOfficeWeek.getResult();
                        BoxOfficeWeekAdapter boxOfficeWeekAdapter = new BoxOfficeWeekAdapter(BoxOfficeActivity.this, boxOfficeWeekList);
                        boxOfficeListview.setAdapter(boxOfficeWeekAdapter);
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(BoxOfficeActivity.this, "获取数据成功");
                    } else {
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(BoxOfficeActivity.this, "服务器数据异常，请稍后再试！");
                    }
                } else {
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(BoxOfficeActivity.this, "服务器数据异常，请稍后再试！");
                }

            }

            @Override
            public void onFail(String result) {
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(BoxOfficeActivity.this, "信息查询失败" + result);
            }

            @Override
            public void onCancel(Callback.CancelledException cex) {

            }
        });
    }

    /**
     * 加载周末票房数据
     */
    private void loadBoxOfficeWeekendData() {
        DialogUtil.showRequestDialog(BoxOfficeActivity.this, "加载中...");
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.MOB_API_KEY);
        map.put("area", "CN");
        XutilsHttp.getInstance().get(Constants.BOX_OFFICE_WEEKEND_QUERY, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("allwhere", "onResponse: " + BuildConfig.DEBUG);
                Log.e("allwhere", "onResponse: " + result.toString());
                if (result != null) {
                    Gson gson = new Gson();
                    BoxOfficeWeekend boxOfficeWeekend = gson.fromJson(result, BoxOfficeWeekend.class);
                    if (boxOfficeWeekend.getRetCode().equals(Constants.REQUEST_SUCCESS)) {
                        boxOfficeWeekendList = boxOfficeWeekend.getResult();
                        BoxOfficeWeekendAdapter boxOfficeWeekendAdapter = new BoxOfficeWeekendAdapter(BoxOfficeActivity.this, boxOfficeWeekendList);
                        boxOfficeListview.setAdapter(boxOfficeWeekendAdapter);
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(BoxOfficeActivity.this, "获取数据成功");
                    } else {
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(BoxOfficeActivity.this, "服务器数据异常，请稍后再试！");
                    }
                } else {
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(BoxOfficeActivity.this, "服务器数据异常，请稍后再试！");
                }

            }

            @Override
            public void onFail(String result) {
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(BoxOfficeActivity.this, "信息查询失败" + result);
            }

            @Override
            public void onCancel(Callback.CancelledException cex) {

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
