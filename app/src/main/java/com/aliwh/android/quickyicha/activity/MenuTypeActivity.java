package com.aliwh.android.quickyicha.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.aliwh.android.quickyicha.BuildConfig;
import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.XutilsHttp;
import com.aliwh.android.quickyicha.adapter.MenuThreeAdapter;
import com.aliwh.android.quickyicha.bean.MenuBean;
import com.aliwh.android.quickyicha.utils.DoubleClickUtil;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜谱查询
 */
@ContentView(R.layout.activity_menu_type)
public class MenuTypeActivity extends BaseActivity {

    private List<MenuBean.ResultBean.ListBean> lists = new ArrayList<>();

    private int PAGE = 1;

    private String cid;
    private String name;

    @ViewInject(R.id.gridView)
    private GridView gridView;

    @ViewInject(R.id.menu_type)
    private TextView menuTypeTextView;

    @ViewInject(R.id.refreshLayout)
    private RefreshLayout refreshLayout;

    private MenuThreeAdapter homeItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cid = getIntent().getStringExtra("cid");
        name = getIntent().getStringExtra("name");
        menuTypeTextView.setText(name);
        homeItemAdapter = new MenuThreeAdapter(MenuTypeActivity.this, lists);
        gridView.setAdapter(homeItemAdapter);
        loadData(String.valueOf(PAGE), true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                PAGE = 1;
                loadData(String.valueOf(PAGE), true);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                PAGE += 1;
                loadData(String.valueOf(PAGE), false);
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
        //设置 Header 为 Material样式
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
    }


    private void loadData(String page, final boolean isPullDown) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.MOB_API_KEY);
        map.put("cid", cid);
        map.put("page", page);
        map.put("size", "21");
        XutilsHttp.getInstance().get(Constants.Query_MENU_BY_TAG, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("allwhere", "onResponse: " + BuildConfig.DEBUG);
                Log.e("allwhere", "onResponse: " + result.toString());
                Gson gson = new Gson();
                MenuBean categoryBean = gson.fromJson(result, MenuBean.class);
                if (isPullDown) {
                    lists.clear();
                }
                lists.addAll(categoryBean.getResult().getList());
                homeItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String result) {

            }

            @Override
            public void onCancel(Callback.CancelledException cex) {

            }
        });


    }

    @Event(value = {R.id.gridView}, type = GridView.OnItemClickListener.class)
    private void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            Intent intent = new Intent(MenuTypeActivity.this, MenuDetailActivity.class);
            intent.putExtra("bean", (Serializable) lists.get(position));
            startActivity(intent);
        }

    }


    // 返回
    @Event(R.id.back_btn)
    private void onBack(View v) {
        finish();
    }
}
