package com.aliwh.android.quickyicha.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.aliwh.android.quickyicha.BuildConfig;
import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.XutilsHttp;
import com.aliwh.android.quickyicha.adapter.MenuOneAdapter;
import com.aliwh.android.quickyicha.adapter.MenuTwoAdapter;
import com.aliwh.android.quickyicha.bean.MenuType;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.DoubleClickUtil;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.aliwh.android.quickyicha.view.ClearEditText;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜谱查询
 */
@ContentView(R.layout.activity_menu_query)
public class MenuQueryActivity extends BaseActivity {

    private List<MenuType.ResultBean.ChildsBeanX.CategoryInfoBeanX> menuList = new ArrayList<>();
    private List<MenuType.ResultBean.ChildsBeanX.ChildsBean> homeList = new ArrayList<>();
    private List<MenuType.ResultBean.ChildsBeanX.ChildsBean> choiceHomeList = new ArrayList<>();

    private List<Integer> showTitle = new ArrayList<>();

    private MenuOneAdapter menuAdapter;
    private MenuTwoAdapter homeAdapter;


    @ViewInject(R.id.lv_menu)
    private ListView lv_menu;

    @ViewInject(R.id.lv_home)
    private ListView lv_home;

    @ViewInject(R.id.tv_titile)
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuAdapter = new MenuOneAdapter(this, menuList);
        lv_menu.setAdapter(menuAdapter);
        homeAdapter = new MenuTwoAdapter(this, choiceHomeList);
        lv_home.setAdapter(homeAdapter);
        DialogUtil.showRequestDialog(MenuQueryActivity.this, "加载中...");
        loadData();
    }


    private void loadData() {
        DialogUtil.showRequestDialog(MenuQueryActivity.this, "加载中...");
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.MOB_API_KEY);
        XutilsHttp.getInstance().get(Constants.MENU_TYPE_TAG, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("allwhere", "onResponse: " + BuildConfig.DEBUG);
                Log.e("allwhere", "onResponse: " + result.toString());
                if (result != null) {
                    Gson gson = new Gson();
                    MenuType category = gson.fromJson(result, MenuType.class);
                    if (category.getRetCode().equals(Constants.REQUEST_SUCCESS)) {
                        tv_title.setText(category.getResult().getChilds().get(0).getCategoryInfo().getName());
                        for (int i = 0; i < category.getResult().getChilds().size(); i++) {
                            MenuType.ResultBean.ChildsBeanX childsBeanX = category.getResult().getChilds().get(i);
                            menuList.add(childsBeanX.getCategoryInfo());
                            showTitle.add(i);
                            homeList.addAll(childsBeanX.getChilds());
                        }
                        choiceHomeList.clear();
                        for (MenuType.ResultBean.ChildsBeanX.ChildsBean childsBean : homeList) {
                            String choiceMenuCtgId = menuList.get(0).getCtgId();
                            if (childsBean.getCategoryInfo().getParentId().equals(choiceMenuCtgId)) {
                                choiceHomeList.add(childsBean);
                            }
                        }
                        menuAdapter.notifyDataSetChanged();
                        homeAdapter.notifyDataSetChanged();
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(MenuQueryActivity.this, "获取数据成功");
                    } else {
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(MenuQueryActivity.this, "服务器数据异常，请稍后再试！");
                    }
                } else {
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(MenuQueryActivity.this, "服务器数据异常，请稍后再试！");
                }

            }

            @Override
            public void onFail(String result) {
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(MenuQueryActivity.this, "菜谱信息查询失败" + result);
            }

            @Override
            public void onCancel(Callback.CancelledException cex) {

            }
        });


    }

    @Event(value = R.id.lv_menu, type = ListView.OnItemClickListener.class)
    private void OnMenuOneItemClick(AdapterView<?> parent, View view, int position, long id) {
        menuAdapter.setSelectItem(position);
        menuAdapter.notifyDataSetInvalidated();
        tv_title.setText(menuList.get(position).getName());
        lv_home.setSelection(showTitle.get(position));
        choiceHomeList.clear();
        for (MenuType.ResultBean.ChildsBeanX.ChildsBean childsBean : homeList) {
            String choiceMenuCtgId = menuList.get(position).getCtgId();
            if (childsBean.getCategoryInfo().getParentId().equals(choiceMenuCtgId)) {
                choiceHomeList.add(childsBean);
            }
        }
        homeAdapter.notifyDataSetChanged();
    }

    @Event(value = R.id.lv_home, type = ListView.OnItemClickListener.class)
    private void OnMenuTwoItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MenuQueryActivity.this, MenuTypeActivity.class);
        intent.putExtra("cid", choiceHomeList.get(position).getCategoryInfo().getCtgId());
        intent.putExtra("name", choiceHomeList.get(position).getCategoryInfo().getName());
        startActivity(intent);
    }

    /**
     * 搜索查找菜品图片按钮
     *
     * @param
     */
    @Event(value = {R.id.menu_search_iv}) //type默认为OnClickListener
    private void menuSearch(View v) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            Intent intent = new Intent(MenuQueryActivity.this, MenuSearchActivity.class);
            startActivity(intent);
        }
    }

    // 返回
    @Event(R.id.back_btn)
    private void onBack(View v) {
        finish();
    }
}
