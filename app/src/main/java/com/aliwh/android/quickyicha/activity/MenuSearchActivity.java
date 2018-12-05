package com.aliwh.android.quickyicha.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.aliwh.android.quickyicha.BuildConfig;
import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.XutilsHttp;
import com.aliwh.android.quickyicha.adapter.MenuThreeAdapter;
import com.aliwh.android.quickyicha.bean.MenuBean;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.DoubleClickUtil;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.aliwh.android.quickyicha.view.ClearEditText;
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
 * 菜谱查找
 */
@ContentView(R.layout.activity_menu_search)
public class MenuSearchActivity extends BaseActivity {

    private List<MenuBean.ResultBean.ListBean> lists = new ArrayList<>();

    @ViewInject(R.id.menu_search_edittext)
    private ClearEditText menuSearchEditText;

    @ViewInject(R.id.search_gridView)
    private GridView searchGridView;

    private MenuThreeAdapter homeItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeItemAdapter = new MenuThreeAdapter(MenuSearchActivity.this, lists);
        searchGridView.setAdapter(homeItemAdapter);
    }


    private void searchMenuData(String searchText) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.MOB_API_KEY);
        map.put("name", searchText);
        DialogUtil.showRequestDialog(MenuSearchActivity.this, "加载中...");
        XutilsHttp.getInstance().get(Constants.Query_MENU_BY_TAG, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("allwhere", "onResponse: " + BuildConfig.DEBUG);
                Log.e("allwhere", "onResponse: " + result.toString());
                Gson gson = new Gson();
                MenuBean categoryBean = gson.fromJson(result, MenuBean.class);
                if (categoryBean.getRetCode().equals(Constants.REQUEST_SUCCESS)) {
                    if (categoryBean.getResult().getList() != null && categoryBean.getResult().getList().size() > 0) {
                        lists.clear();
                        lists.addAll(categoryBean.getResult().getList());
                        homeItemAdapter.notifyDataSetChanged();
                    }
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(MenuSearchActivity.this, "查询成功");
                } else {
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(MenuSearchActivity.this, "查询失败！");
                }

            }

            @Override
            public void onFail(String result) {
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(MenuSearchActivity.this, "查询失败" + result);
            }

            @Override
            public void onCancel(Callback.CancelledException cex) {
                DialogUtil.closeRequestDialog();
            }
        });


    }

    @Event(value = {R.id.search_gridView}, type = GridView.OnItemClickListener.class)
    private void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            Intent intent = new Intent(MenuSearchActivity.this, MenuDetailActivity.class);
            intent.putExtra("bean", (Serializable) lists.get(position));
            startActivity(intent);
        }

    }

    /**
     * 搜索查找菜品
     *
     * @param
     */
    @Event(value = {R.id.menu_search_btn}) //type默认为OnClickListener
    private void menuSearch(View v) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            String searchText = menuSearchEditText.getText().toString().trim();
            if (!TextUtils.isEmpty(searchText)) {
                searchMenuData(searchText);
            }
        }
    }


    // 返回
    @Event(R.id.back_btn)
    private void onBack(View v) {
        closeKeyboard();
        finish();
    }

}
