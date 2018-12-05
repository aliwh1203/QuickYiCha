package com.aliwh.android.quickyicha.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.XutilsHttp;
import com.aliwh.android.quickyicha.adapter.CarBrandAdapter;
import com.aliwh.android.quickyicha.bean.CarBrand;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.DoubleClickUtil;
import com.aliwh.android.quickyicha.utils.PingYinUtil;
import com.aliwh.android.quickyicha.utils.RegexUtils;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.aliwh.android.quickyicha.view.CarLetterListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_car_choice)
public class CarChoiceActivity extends BaseActivity {
    private CarBrandAdapter carBrandAdapter;
    private ResultListAdapter resultListAdapter;
    private TextView overlay; // 对话框首字母textview

    private Handler handler;
    private OverlayThread overlayThread; // 显示首字母对话框

    @ViewInject(R.id.car_brand_listview)
    private ExpandableListView carBrandListView;

    @ViewInject(R.id.search_car_result)
    private ListView searchResultListView;

    @ViewInject(R.id.search_car_view)
    private EditText searchEditText;

    @ViewInject(R.id.car_letter_listView)
    private CarLetterListView letterListView; // A-Z listview

    @ViewInject(R.id.tv_noresult)
    private TextView tv_noresult;

    private List<CarBrand.ResultBean> carBrandList;// 汽车品牌列表
    private List<CarBrand.ResultBean.SonBean> carBrandResult; //关键字搜索列表

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carBrandList = new ArrayList<>();
        carBrandResult = new ArrayList<>();
        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString() == null || "".equals(s.toString())) {
                    letterListView.setVisibility(View.VISIBLE);
                    carBrandListView.setVisibility(View.VISIBLE);
                    searchResultListView.setVisibility(View.GONE);
                    tv_noresult.setVisibility(View.GONE);
                } else {
                    carBrandResult.clear();
                    letterListView.setVisibility(View.GONE);
                    carBrandListView.setVisibility(View.GONE);
                    getResultCityList(s.toString());
                    if (carBrandResult.size() <= 0) {
                        tv_noresult.setVisibility(View.VISIBLE);
                        searchResultListView.setVisibility(View.GONE);
                    } else {
                        tv_noresult.setVisibility(View.GONE);
                        searchResultListView.setVisibility(View.VISIBLE);
                        resultListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler();
        overlayThread = new OverlayThread();

        resultListAdapter = new ResultListAdapter(carBrandResult);
        searchResultListView.setAdapter(resultListAdapter);

        loadCarBrandData();
        initOverlay();

    }


    @Event(value = R.id.car_letter_listView, type = CarLetterListView.OnTouchingLetterChangedListener.class)
    private void onTouchingLetterChangedListener(String s) {
        if (carBrandAdapter.getAlphaIndexer().get(s) != null) {
            int position = carBrandAdapter.getAlphaIndexer().get(s);
            carBrandListView.setSelection(position);
            overlay.setText(s);
            overlay.setVisibility(View.VISIBLE);
            handler.removeCallbacks(overlayThread);
            // 延迟一秒后执行，让overlay为不可见
            handler.postDelayed(overlayThread, 1000);
        }
    }

    @Event(value = {R.id.search_car_result}, type = OnItemClickListener.class)
    private void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            Intent intent = new Intent(CarChoiceActivity.this, CarSeriesActivity.class);
            intent.putExtra("type", carBrandResult.get(position).getType());
            startActivity(intent);
        }

    }


    private void getResultCityList(String keyword) {
        for (CarBrand.ResultBean resultBean : carBrandList) {
            for (CarBrand.ResultBean.SonBean sonBean : resultBean.getSon()) {
                //搜索条件大于6个字符将不按拼音首字母查询
                if (keyword.length() <= 6) {
                    //判断是否为汉字
                    if (RegexUtils.isChineseChar(keyword)) {
                        if (sonBean.getCar().contains(keyword)) {
                            carBrandResult.add(sonBean);
                        }
                    } else {
                        //根据首字母或者前几个字母进行模糊查询
                        if ((PingYinUtil.getPingYin(sonBean.getCar().toLowerCase()).contains(keyword.toLowerCase()))) {
                            carBrandResult.add(sonBean);
                        }
                    }
                } else {
                    //根据全拼查询
                    if (keyword.equalsIgnoreCase(PingYinUtil.getPingYin(sonBean.getCar()))) {
                        carBrandResult.add(sonBean);
                    }
                }
            }

        }
        Collections.sort(carBrandResult, comparator);
    }

    /**
     * a-z排序
     */
    @SuppressWarnings("rawtypes")
    Comparator comparator = new Comparator<CarBrand.ResultBean.SonBean>() {
        @Override
        public int compare(CarBrand.ResultBean.SonBean lhs, CarBrand.ResultBean.SonBean rhs) {
            String a = PingYinUtil.getPingYin(lhs.getCar()).substring(0, 1);
            String b = PingYinUtil.getPingYin(rhs.getCar()).substring(0, 1);
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };

    /**
     * 加载汽车品牌数据
     */
    private void loadCarBrandData() {
        DialogUtil.showRequestDialog(CarChoiceActivity.this, "加载中...");
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.MOB_API_KEY);
        XutilsHttp.getInstance().get(Constants.CAR_BRAND_QUERY, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("allwhere", "onResponse: " + result.toString());
                if (result != null) {
                    Gson gson = new Gson();
                    CarBrand carBrand = gson.fromJson(result, CarBrand.class);
                    if (carBrand.getRetCode().equals(Constants.REQUEST_SUCCESS)) {
                        carBrandList = carBrand.getResult();
//                        adapter = new CarBrandListAdapter(CarChoiceActivity.this, carBrandList);
//                        cityListView.setAdapter(adapter);
                        carBrandAdapter = new CarBrandAdapter(CarChoiceActivity.this, carBrandList);
                        carBrandListView.setAdapter(carBrandAdapter);
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(CarChoiceActivity.this, "获取数据成功");
                    } else {
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(CarChoiceActivity.this, "服务器数据异常，请稍后再试！");
                    }
                } else {
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(CarChoiceActivity.this, "服务器数据异常，请稍后再试！");
                }

            }

            @Override
            public void onFail(String result) {
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(CarChoiceActivity.this, "信息查询失败" + result);
            }

            @Override
            public void onCancel(Callback.CancelledException cex) {

            }
        });
    }

    private class ResultListAdapter extends BaseAdapter {
        private List<CarBrand.ResultBean.SonBean> results = new ArrayList<>();

        public ResultListAdapter(List<CarBrand.ResultBean.SonBean> results) {
            this.results = results;
        }

        @Override
        public int getCount() {
            return results.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(CarChoiceActivity.this).inflate(R.layout.list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(results.get(position).getCar() + results.get(position).getType());
            return convertView;
        }

        class ViewHolder {
            TextView name;
        }
    }


    // 初始化汉语拼音首字母弹出提示框
    private void initOverlay() {
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.show_overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    private class LetterListViewListener implements CarLetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            if (carBrandAdapter.getAlphaIndexer().get(s) != null) {
                int position = carBrandAdapter.getAlphaIndexer().get(s);
                carBrandListView.setSelection(position);
                overlay.setText(s);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // 延迟一秒后执行，让overlay为不可见
                handler.postDelayed(overlayThread, 1000);
            }
        }
    }

    // 设置overlay不可见
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    /**
     * 返回按钮
     */
    @Event(R.id.back_btn)
    private void onBack(View view) {
        this.finish();
    }

}