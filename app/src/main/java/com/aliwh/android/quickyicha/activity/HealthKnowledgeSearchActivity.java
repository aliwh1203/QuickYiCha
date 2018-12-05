package com.aliwh.android.quickyicha.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.aliwh.android.quickyicha.BuildConfig;
import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.XutilsHttp;
import com.aliwh.android.quickyicha.adapter.HealthKnowledgeAdapter;
import com.aliwh.android.quickyicha.adapter.MenuThreeAdapter;
import com.aliwh.android.quickyicha.bean.HealthBean;
import com.aliwh.android.quickyicha.bean.MenuBean;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.DoubleClickUtil;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.aliwh.android.quickyicha.view.ClearEditText;
import com.google.gson.Gson;

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
 * 健康知识查询
 */
@ContentView(R.layout.activity_health_search)
public class HealthKnowledgeSearchActivity extends BaseActivity {

    private List<HealthBean.ResultBean.ListBean> lists = new ArrayList<>();

    @ViewInject(R.id.health_search_edittext)
    private ClearEditText healthSearchEditText;

    @ViewInject(R.id.health_listView)
    private ListView healthListView;

    private HealthKnowledgeAdapter healthKnowledgeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        healthKnowledgeAdapter = new HealthKnowledgeAdapter(HealthKnowledgeSearchActivity.this, lists);
        healthListView.setAdapter(healthKnowledgeAdapter);
    }


    private void searchHealthKnowledgeData(String searchText) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.MOB_API_KEY);
        map.put("name", searchText);
        DialogUtil.showRequestDialog(HealthKnowledgeSearchActivity.this, "加载中...");
        XutilsHttp.getInstance().get(Constants.HEALTH_KNOWLEDGE_QUERY, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("allwhere", "onResponse: " + BuildConfig.DEBUG);
                Log.e("allwhere", "onResponse: " + result.toString());
                Gson gson = new Gson();
                HealthBean healthBean = gson.fromJson(result, HealthBean.class);
                if (healthBean.getRetCode().equals(Constants.REQUEST_SUCCESS)) {
                    if (healthBean.getResult().getList() != null && healthBean.getResult().getList().size() > 0) {
                        lists.clear();
                        lists.addAll(healthBean.getResult().getList());
                        healthKnowledgeAdapter.notifyDataSetChanged();
                    }
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(HealthKnowledgeSearchActivity.this, "查询成功");
                } else {
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(HealthKnowledgeSearchActivity.this, "查询失败！");
                }

            }

            @Override
            public void onFail(String result) {
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(HealthKnowledgeSearchActivity.this, "查询失败" + result);
            }

            @Override
            public void onCancel(Callback.CancelledException cex) {
                DialogUtil.closeRequestDialog();
            }
        });


    }

    @Event(value = {R.id.health_listView}, type = ListView.OnItemClickListener.class)
    private void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            Intent intent = new Intent(HealthKnowledgeSearchActivity.this, HealthKnowledgeDetailActivity.class);
            intent.putExtra("bean", (Serializable) lists.get(position));
            startActivity(intent);
        }

    }

    /**
     * 搜索
     *
     * @param
     */
    @Event(value = {R.id.health_search_btn}) //type默认为OnClickListener
    private void healthSearch(View v) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            String searchText = healthSearchEditText.getText().toString().trim();
            if (!TextUtils.isEmpty(searchText)) {
                searchHealthKnowledgeData(searchText);
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
