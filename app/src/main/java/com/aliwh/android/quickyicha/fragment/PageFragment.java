package com.aliwh.android.quickyicha.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.XutilsHttp;
import com.aliwh.android.quickyicha.activity.WeiXinChoiceDetailActivity;
import com.aliwh.android.quickyicha.adapter.WeiXinChoiceAdapter;
import com.aliwh.android.quickyicha.bean.WeiXinChoiceBean;
import com.aliwh.android.quickyicha.utils.DoubleClickUtil;
import com.aliwh.android.quickyicha.utils.ToastUtil;
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

@ContentView(R.layout.fragment_page)
public class PageFragment extends BaseFragment {

    private int PAGE = 1;
    private String cid;
    private List<WeiXinChoiceBean.ResultBean.ListBean> listBean = new ArrayList<>();


    @ViewInject(R.id.weixin_choice_listView)
    private ListView weixinListView;

    @ViewInject(R.id.weixin_choice_refreshLayout)
    private RefreshLayout weixinChoiceRefreshLayout;

    private WeiXinChoiceAdapter weiXinChoiceAdapter;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cid = getArguments().getString("cid");
        weiXinChoiceAdapter = new WeiXinChoiceAdapter(getActivity(), listBean);
        weixinListView.setAdapter(weiXinChoiceAdapter);
        loadData(String.valueOf(PAGE), true);
        weixinChoiceRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                PAGE = 1;
                loadData(String.valueOf(PAGE), true);
                refreshlayout.finishRefresh(2000);
            }
        });
        weixinChoiceRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                PAGE += 1;
                loadData(String.valueOf(PAGE), false);
                refreshlayout.finishLoadmore(2000);
            }
        });
        //设置 Header 为 Material样式
        weixinChoiceRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        //设置 Footer 为 球脉冲
        weixinChoiceRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
    }

    private void loadData(String page, final boolean isPullDown) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.MOB_API_KEY);
        map.put("cid", cid);
        map.put("page", page);
        map.put("size", "21");
        XutilsHttp.getInstance().get(Constants.WEIXIN_JINGXUAN_LIST_QUERY, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
//                Log.e("allwhere", "onResponse: " + BuildConfig.DEBUG);
//                Log.e("allwhere", "onResponse: " + result.toString());
                if (result != null) {
                    Gson gson = new Gson();
                    WeiXinChoiceBean weiXinChoiceList = gson.fromJson(result, WeiXinChoiceBean.class);
                    if (weiXinChoiceList.getRetCode().equals(Constants.REQUEST_SUCCESS)) {
                        if (isPullDown) {
                            listBean.clear();
                        }
                        listBean.addAll(weiXinChoiceList.getResult().getList());
                        weiXinChoiceAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showToast(getActivity(), "服务器数据异常，请稍后再试！");
                    }
                } else {
                    ToastUtil.showToast(getActivity(), "服务器数据异常，请稍后再试！");
                }
            }

            @Override
            public void onFail(String result) {
                ToastUtil.showToast(getActivity(), "加载数据失败" + result);
            }

            @Override
            public void onCancel(Callback.CancelledException cex) {
            }
        });
    }

    @Event(value = {R.id.weixin_choice_listView}, type = ListView.OnItemClickListener.class)
    private void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            Intent intent = new Intent(getActivity(), WeiXinChoiceDetailActivity.class);
            intent.putExtra("listBean", (Serializable) listBean.get(position));
            startActivity(intent);
        }

    }

}