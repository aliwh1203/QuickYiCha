package com.aliwh.android.quickyicha.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.aliwh.android.quickyicha.BuildConfig;
import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.XutilsHttp;
import com.aliwh.android.quickyicha.bean.PhoneAddress;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * 查询手机号码归属地页面
 */
@ContentView(R.layout.activity_phone_address)
public class PhoneAddressActivity extends BaseActivity {
    @ViewInject(R.id.phone_text)
    private EditText phoneText;

    @ViewInject(R.id.phone_info_text)
    private TextView phoneInfoText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Event(R.id.submit_phone_search)
    private void submitPhoneSearch(View v) {
        final String phoneTextStr = phoneText.getText().toString().trim();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.MOB_API_KEY);
        map.put("phone", phoneTextStr);
        DialogUtil.showRequestDialog(PhoneAddressActivity.this, "加载中...");
        XutilsHttp.getInstance().get(Constants.PHONE_ADDRESS_QUERY, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("allwhere", "onResponse: " + BuildConfig.DEBUG);
                Log.e("allwhere", "onResponse: " + result.toString());
                if (result != null) {
                    Gson gson = new Gson();
                    PhoneAddress phoneAddress = gson.fromJson(result, PhoneAddress.class);
                    if (phoneAddress.getRetCode().equals(Constants.REQUEST_SUCCESS)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("\n\t")
                                .append("城市：" + phoneAddress.getResult().getCity() + "\n\t")
                                .append("城市区号：" + phoneAddress.getResult().getCityCode() + "\n\t")
                                .append("手机号前7位：" + phoneAddress.getResult().getMobileNumber() + "\n\t")
                                .append("运营商信息：" + phoneAddress.getResult().getOperator() + "\n\t")
                                .append("省份：" + phoneAddress.getResult().getProvince() + "\n\t")
                                .append("邮编：" + phoneAddress.getResult().getZipCode() + "\n\t");
                        phoneInfoText.setText(sb.toString());
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(PhoneAddressActivity.this, "获取数据成功");
                    } else {
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(PhoneAddressActivity.this, "服务器数据异常，请稍后再试！");
                    }
                } else {
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(PhoneAddressActivity.this, "服务器数据异常，请稍后再试！");
                }

            }

            @Override
            public void onFail(String result) {
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(PhoneAddressActivity.this, "手机归属地信息查询失败" + result);
            }

            @Override
            public void onCancel(Callback.CancelledException cex) {
                DialogUtil.closeRequestDialog();
            }
        });


    }


    /**
     * 返回按钮
     */
    @Event(R.id.back_btn)
    private void onBack(View view) {
        closeKeyboard();
        this.finish();
    }


}
