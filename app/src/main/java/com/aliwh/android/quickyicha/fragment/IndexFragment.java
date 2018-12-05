package com.aliwh.android.quickyicha.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.XutilsHttp;
import com.aliwh.android.quickyicha.adapter.IndexFragmentPageAdapter;
import com.aliwh.android.quickyicha.bean.WeiXinChoiceType;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.aliwh.android.quickyicha.view.CustomViewPager;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.fragment_index_layout)
public class IndexFragment extends BaseFragment {

    @ViewInject(R.id.tab)
    private TabLayout tabLayout;
    @ViewInject(R.id.viewpager)
    private CustomViewPager viewpager;

    private List<WeiXinChoiceType.ResultBean> resultBeanList = new ArrayList<>();

    private Bundle savedState = new Bundle();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewpager.setViewPagerScrollSpeed();
        if (!restoreStateFromArguments()) {
            // 第一次加载数据
            loadData();
        } else {
            IndexFragmentPageAdapter indexFragmentPageAdapter = new IndexFragmentPageAdapter(getFragmentManager(), resultBeanList);
            viewpager.setAdapter(indexFragmentPageAdapter);
            //绑定
            tabLayout.setupWithViewPager(viewpager);
        }

    }

    /**
     * 加载微信精选分类数据
     */
    private void loadData() {
        DialogUtil.showRequestDialog(getActivity(), "加载中...");
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.MOB_API_KEY);
        XutilsHttp.getInstance().get(Constants.WEIXIN_JINGXUAN_TYPE_QUERY, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
//                Log.e("allwhere", "onResponse: " + BuildConfig.DEBUG);
//                Log.e("allwhere", "onResponse: " + result.toString());
                if (result != null) {
                    Gson gson = new Gson();
                    WeiXinChoiceType weixinjingChoice = gson.fromJson(result, WeiXinChoiceType.class);
                    if (weixinjingChoice.getRetCode().equals(Constants.REQUEST_SUCCESS)) {
                        resultBeanList = weixinjingChoice.getResult();
                        IndexFragmentPageAdapter indexFragmentPageAdapter = new IndexFragmentPageAdapter(getFragmentManager(), resultBeanList);
                        viewpager.setAdapter(indexFragmentPageAdapter);
                        //绑定
                        tabLayout.setupWithViewPager(viewpager);
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(getActivity(), "获取数据成功");
                    } else {
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(getActivity(), "服务器数据异常，请稍后再试！");
                    }
                } else {
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(getActivity(), "服务器数据异常，请稍后再试！");
                }

            }

            @Override
            public void onFail(String result) {
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(getActivity(), "手机归属地信息查询失败" + result);
            }

            @Override
            public void onCancel(Callback.CancelledException cex) {
                DialogUtil.closeRequestDialog();
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveStateToArguments();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveStateToArguments();
    }

    private void saveStateToArguments() {
        if (savedState != null) {
            savedState.putSerializable("resultBeanList", (Serializable) resultBeanList);
        }
    }

    private boolean restoreStateFromArguments() {
        if (savedState != null) {
            resultBeanList = (List<WeiXinChoiceType.ResultBean>) savedState.getSerializable("resultBeanList");
            if (resultBeanList != null && resultBeanList.size() > 0) {
                return true;
            }
        }
        return false;
    }

}