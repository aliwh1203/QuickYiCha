package com.aliwh.android.quickyicha.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.bean.WeiXinChoiceBean;
import com.aliwh.android.quickyicha.utils.DialogUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.sharesdk.onekeyshare.OnekeyShare;

@ContentView(R.layout.activity_wei_xin_choice_detail)
public class WeiXinChoiceDetailActivity extends BaseActivity {

    @ViewInject(R.id.webView1)
    private WebView mWebview;

    private WeiXinChoiceBean.ResultBean.ListBean listBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listBean = (WeiXinChoiceBean.ResultBean.ListBean) getIntent().getSerializableExtra("listBean");
        WebSettings mWebSettings = mWebview.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        //WebView加载页面优先使用缓存加载
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        if (listBean != null) {
            mWebview.loadUrl(listBean.getSourceUrl());
        }


        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {
            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                System.out.println("标题在这里");
            }

            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress < 100) {
//                    DialogUtil.showRequestDialog(WeiXinChoiceDetailActivity.this, "加载中...");
//                } else if (newProgress == 100) {
//                    DialogUtil.closeRequestDialog();
//                }
            }
        });

        //设置WebViewClient类
        mWebview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                System.out.println("开始加载了");
                DialogUtil.showRequestDialog(WeiXinChoiceDetailActivity.this, "加载中...");
            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                DialogUtil.closeRequestDialog();
            }
        });
    }

    @Event(R.id.share_image_btn)
    private void shareBtn(View view) {
        OnekeyShare oks = new OnekeyShare();
        // 关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(listBean.getSourceUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(listBean.getTitle());
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(listBean.getSourceUrl());
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(listBean.getSourceUrl());
        // 启动分享GUI
        oks.show(WeiXinChoiceDetailActivity.this);
    }


    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }

    // 返回
    @Event(R.id.back_btn)
    private void onBack(View v) {
        finish();
    }
}

