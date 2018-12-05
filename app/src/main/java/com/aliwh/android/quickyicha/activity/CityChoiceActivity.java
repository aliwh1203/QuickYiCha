package com.aliwh.android.quickyicha.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.bean.CityBean;
import com.aliwh.android.quickyicha.utils.JsonUtil;
import com.aliwh.android.quickyicha.utils.PermissionHelper;
import com.aliwh.android.quickyicha.utils.RegexUtils;
import com.aliwh.android.quickyicha.view.CityLetterListView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@ContentView(R.layout.activity_city_choice)
public class CityChoiceActivity extends BaseActivity {
    private BaseAdapter adapter;
    private ResultListAdapter resultListAdapter;
    private ListView cityListView;
    private ListView searchResultListView;
    private TextView overlay; // 对话框首字母textview
    private Button back_btn;//返回按钮
    private CityLetterListView letterListView; // A-Z listview
    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private Handler handler;
    private OverlayThread overlayThread; // 显示首字母对话框
    private List<CityBean> allCity_lists; // 所有城市列表
    private List<CityBean> city_lists;// 城市列表
    private List<CityBean> city_result; //关键字搜索列表
    private EditText searchEditText;
    private TextView tv_noresult;

    private LocationClient mLocationClient;
    private MyLocationListener mMyLocationListener;

    private String currentCity; // 用于保存定位到的城市
    private int locateProcess = 1; // 记录当前定位的状态 正在定位-定位成功-定位失败
    private boolean isNeedFresh;
    private PermissionHelper permissionHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionHelper = new PermissionHelper(this);
        cityListView = (ListView) findViewById(R.id.list_view);
        searchResultListView = (ListView) findViewById(R.id.search_result);
        searchEditText = (EditText) findViewById(R.id.search_view);
        tv_noresult = (TextView) findViewById(R.id.tv_noresult);
        back_btn = (Button) findViewById(R.id.back_btn);
        allCity_lists = new ArrayList<CityBean>();
        city_lists = new ArrayList<CityBean>();
        city_result = new ArrayList<CityBean>();
        cityInit();
        initOverlay();

        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                onClearOverlay();
                CityChoiceActivity.this.finish();
            }
        });
        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString() == null || "".equals(s.toString())) {
                    letterListView.setVisibility(View.VISIBLE);
                    cityListView.setVisibility(View.VISIBLE);
                    searchResultListView.setVisibility(View.GONE);
                    tv_noresult.setVisibility(View.GONE);
                } else {
                    city_result.clear();
                    letterListView.setVisibility(View.GONE);
                    cityListView.setVisibility(View.GONE);
                    getResultCityList(s.toString());
                    if (city_result.size() <= 0) {
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
        letterListView = (CityLetterListView) findViewById(R.id.MyLetterListView);
        letterListView
                .setOnTouchingLetterChangedListener(new LetterListViewListener());
        alphaIndexer = new HashMap<String, Integer>();
        handler = new Handler();
        overlayThread = new OverlayThread();
        isNeedFresh = true;
        locateProcess = 1;
        adapter = new ListAdapter(this, allCity_lists);
        cityListView.setAdapter(adapter);
        resultListAdapter = new ResultListAdapter(this, city_result);
        searchResultListView.setAdapter(resultListAdapter);
        searchResultListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //点击选择返回定位的城市数据
                Intent intent = new Intent();
                intent.putExtra("city", city_result.get(position).getName());
                setResult(RESULT_OK, intent);
                finish();//此处一定要调用finish()方法
            }
        });
        mMyLocationListener = new MyLocationListener();
        InitLocation();

    }

    /**
     * 从assert文件夹中读取json数据文件
     */
    private List<CityBean> getCityList() {
        List<CityBean> cityList = new ArrayList<CityBean>();
        BufferedReader reader;
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = getAssets().open("cities.json");
            BufferedInputStream in = new BufferedInputStream(is);
            reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String str = reader.readLine();
            while (str != null) {
                sb.append(str + "\r\n");
                str = reader.readLine();
            }
            is.close();
            reader.close();
            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray city_list = jsonObject
                    .getJSONArray("data");
            cityList = JsonUtil.toList(city_list,
                    CityBean.class);
            Collections.sort(cityList, comparator);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityList;
    }

    private void getResultCityList(String keyword) {
        for (CityBean city : city_lists) {
            //搜索条件大于6个字符将不按拼音首字母查询
            if (keyword.length() <= 6) {
                //判断是否为汉字
                if (RegexUtils.isChineseChar(keyword)) {
                    if (city.getName().contains(keyword)) {
                        city_result.add(city);
                    }
                } else {
                    //根据首字母或者前几个字母进行模糊查询
                    if ((city.getPinyin().contains(keyword.toLowerCase()))) {
                        city_result.add(city);
                    }
                }
            } else {
                //根据全拼或者拼音简写查询
                if (keyword.equalsIgnoreCase(city.getPinyin()) || keyword.equalsIgnoreCase(city.getAcronym())) {
                    city_result.add(city);
                }
            }
        }
        Collections.sort(city_result, comparator);
    }

    private void InitLocation() {
        permissionHelper.requestPermissions("需要定位权限", new PermissionHelper.PermissionListener() {
            @Override
            public void doAfterGrand(String... permissions) {
                if (mLocationClient == null) {
                    mLocationClient = new LocationClient(getApplicationContext());
                    mLocationClient.registerLocationListener(mMyLocationListener);
                    LocationClientOption options = new LocationClientOption();
                    options.setOpenGps(true);
                    options.setCoorType("bd09ll");
                    options.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
                    options.setScanSpan(15000);
                    options.disableCache(true);
                    //设置需要地址，不能的话onReceiveLocation中getCity返回null
                    options.setIsNeedAddress(true);
                    mLocationClient.setLocOption(options);
                    mLocationClient.start();
                } else {
                    mLocationClient.requestLocation();
                }
            }

            @Override
            public void doAfterDenied(String... permissions) {
//                permissionHelper.openPermissionsSetting("请到设置>权限中打开定位权限");
            }
        }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void cityInit() {
        CityBean city = new CityBean("dw", "定位", "0"); // 当前定位城市
        allCity_lists.add(city);
        city = new CityBean("qb", "全部", "1"); // 全部城市
        allCity_lists.add(city);
        city_lists = getCityList();
        allCity_lists.addAll(city_lists);
    }

    /**
     * a-z排序
     */
    @SuppressWarnings("rawtypes")
    Comparator comparator = new Comparator<CityBean>() {
        @Override
        public int compare(CityBean lhs, CityBean rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation arg0) {
            Log.e("info", "city = " + arg0.getCity());
            if (!isNeedFresh) {
                return;
            }
            isNeedFresh = false;
            if (arg0.getCity() == null) {
                locateProcess = 3; // 定位失败
                cityListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return;
            }
            currentCity = arg0.getCity().substring(0,
                    arg0.getCity().length() - 1);
            locateProcess = 2; // 定位成功
            cityListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }

    private class ResultListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<CityBean> results = new ArrayList<CityBean>();

        public ResultListAdapter(Context context, List<CityBean> results) {
            inflater = LayoutInflater.from(context);
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
                convertView = inflater.inflate(R.layout.list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView
                        .findViewById(R.id.name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(results.get(position).getName());
            return convertView;
        }

        class ViewHolder {
            TextView name;
        }
    }

    public class ListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<CityBean> list;
        final int VIEW_TYPE = 3;

        public ListAdapter(Context context, List<CityBean> list) {
            this.inflater = LayoutInflater.from(context);
            this.list = list;
            for (int i = 0; i < list.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = getAlpha(list.get(i).getPinyin());
                // 上一个汉语拼音首字母，如果不存在为" "
                String previewStr = (i - 1) >= 0 ? getAlpha(list.get(i - 1)
                        .getPinyin()) : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = getAlpha(list.get(i).getPinyin());
                    alphaIndexer.put(name, i);
                }
            }
        }

        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE;
        }

        @Override
        public int getItemViewType(int position) {
            return position < 2 ? position : 2;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        ViewHolder holder;

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final TextView city;
            int viewType = getItemViewType(position);
            if (viewType == 0) { // 定位
                convertView = inflater.inflate(R.layout.frist_list_item, null);
                TextView locateHint = (TextView) convertView
                        .findViewById(R.id.locateHint);
                city = (TextView) convertView.findViewById(R.id.lng_city);
                city.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (locateProcess == 2) {
                            //点击选择返回定位的城市数据
                            Intent intent = new Intent();
                            intent.putExtra("city", currentCity);
                            setResult(RESULT_OK, intent);
                            finish();//此处一定要调用finish()方法
                        } else if (locateProcess == 3) {
                            locateProcess = 1;
                            cityListView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            mLocationClient.stop();
                            isNeedFresh = true;
                            InitLocation();
                            currentCity = "";
                        }
                    }
                });
                ProgressBar pbLocate = (ProgressBar) convertView
                        .findViewById(R.id.pbLocate);
                if (locateProcess == 1) { // 正在定位
                    locateHint.setText("正在定位");
                    city.setVisibility(View.GONE);
                    pbLocate.setVisibility(View.VISIBLE);
                } else if (locateProcess == 2) { // 定位成功
                    locateHint.setText("当前定位城市");
                    city.setVisibility(View.VISIBLE);
                    city.setText(currentCity);
                    mLocationClient.stop();
                    pbLocate.setVisibility(View.GONE);
                } else if (locateProcess == 3) {
                    locateHint.setText("未定位到城市,请选择");
                    city.setVisibility(View.VISIBLE);
                    city.setText("重新选择");
                    pbLocate.setVisibility(View.GONE);
                }
            } else if (viewType == 1) {
                convertView = inflater.inflate(R.layout.total_item, null);
            } else {
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.list_item, null);
                    holder = new ViewHolder();
                    holder.alpha = (TextView) convertView
                            .findViewById(R.id.alpha);
                    holder.name = (TextView) convertView
                            .findViewById(R.id.name);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                if (position > 1) {
                    holder.name.setText(list.get(position).getName());
                    String currentStr = getAlpha(list.get(position).getPinyin());
                    String previewStr = (position - 1) >= 0 ? getAlpha(list
                            .get(position - 1).getPinyin()) : " ";
                    if (!previewStr.equals(currentStr)) {
                        holder.alpha.setVisibility(View.VISIBLE);
                        holder.alpha.setText(currentStr);
                    } else {
                        holder.alpha.setVisibility(View.GONE);
                    }
                    holder.name.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra("city", allCity_lists.get(position).getName());
                            setResult(RESULT_OK, intent);
//                            onClearOverlay();
                            CityChoiceActivity.this.finish();
                        }
                    });
                }
            }
            return convertView;
        }

        private class ViewHolder {
            TextView alpha; // 首字母标题
            TextView name; // 城市名字
        }
    }

    @Override
    protected void onStop() {
        mLocationClient.stop();
        super.onStop();
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

    private class LetterListViewListener implements
            CityLetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                cityListView.setSelection(position);
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


    private void onClearOverlay() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.removeView(overlay);

    }

    // 获得汉语拼音首字母
    private String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else if (str.equals("0")) {
            return "定位";
        } else if (str.equals("1")) {
            return "全部";
        } else {
            return "#";
        }
    }

    //将权限交给permissionhelper处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}