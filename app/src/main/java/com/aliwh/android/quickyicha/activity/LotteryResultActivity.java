package com.aliwh.android.quickyicha.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.aliwh.android.quickyicha.BuildConfig;
import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.XutilsHttp;
import com.aliwh.android.quickyicha.adapter.LotteryResultAdapter;
import com.aliwh.android.quickyicha.bean.LotteryResult;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.aliwh.android.quickyicha.view.FlowRadioGroup;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 中国彩票开奖结果页面
 */
@ContentView(R.layout.activity_lottery_result)
public class LotteryResultActivity extends BaseActivity {
    @ViewInject(R.id.lottery_radio_group)
    private FlowRadioGroup lotteryRadioGroup;

    @ViewInject(R.id.result_info_number)
    private TextView resultInfoNumber;

    @ViewInject(R.id.result_info_text)
    private TextView resultInfoText;

    @ViewInject(R.id.lottery_result_listview)
    private ListView lotteryResultListview;

    private List<String> lotteryTypeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLotteryTypeData();
    }


    private void loadLotteryTypeData() {
        DialogUtil.showRequestDialog(LotteryResultActivity.this, "加载中...");
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.MOB_API_KEY);
        XutilsHttp.getInstance().get(Constants.LOTTERY_TYPE_QUERY, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("allwhere", "onResponse: " + BuildConfig.DEBUG);
                Log.e("allwhere", "onResponse: " + result.toString());
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String retCode = jsonObject.getString("retCode");
                        if (retCode.equals(Constants.REQUEST_SUCCESS)) {
                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                lotteryTypeList.add((String) jsonArray.get(i));
                                RadioButton mRadioButton = new RadioButton(LotteryResultActivity.this);
                                mRadioButton.setText((String) jsonArray.get(i));
                                setRadioButtonOnCheckedChangeListener(mRadioButton);
                                //选项添加到RadioGroup
                                lotteryRadioGroup.addView(mRadioButton);
                            }

                            DialogUtil.closeRequestDialog();
                            ToastUtil.showToast(LotteryResultActivity.this, "获取数据成功");
                        } else {
                            DialogUtil.closeRequestDialog();
                            ToastUtil.showToast(LotteryResultActivity.this, "服务器数据异常，请稍后再试！");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(LotteryResultActivity.this, "服务器数据异常，请稍后再试！");
                }

            }

            @Override
            public void onFail(String result) {
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(LotteryResultActivity.this, "彩票开奖信息查询失败" + result);
            }

            @Override
            public void onCancel(Callback.CancelledException cex) {

            }
        });
    }

    private void loadLotteryResultData(String name) {
        DialogUtil.showRequestDialog(LotteryResultActivity.this, "加载中...");
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.MOB_API_KEY);
        map.put("name", name);
        XutilsHttp.getInstance().get(Constants.LOTTERY_RESULT_QUERY, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("allwhere", "onResponse: " + BuildConfig.DEBUG);
                Log.e("allwhere", "onResponse: " + result.toString());
                if (result != null) {
                    Gson gson = new Gson();
                    LotteryResult lotteryResult = gson.fromJson(result, LotteryResult.class);
                    if (lotteryResult.getRetCode().equals(Constants.REQUEST_SUCCESS)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("彩种：" + lotteryResult.getResult().getName() + "\n")
                                .append("期次：" + lotteryResult.getResult().getPeriod() + "\n")
                                .append("中奖日期：" + lotteryResult.getResult().getAwardDateTime() + "\n");
                        sb.append("奖池金额：" + new BigDecimal(lotteryResult.getResult().getPool()).longValue() + "元" + "\n")
                                .append("销售金额：" + new BigDecimal(lotteryResult.getResult().getSales()).longValue() + "元" + "\n")
                                .append("中奖信息如下：");
                        String lotteryNumber = lotteryResult.getResult().getLotteryNumber().toString();
                        lotteryNumber = lotteryNumber.substring(1, lotteryNumber.length() - 1);
                        resultInfoNumber.setText("开奖号码：" + lotteryNumber);
                        resultInfoText.setText(sb.toString());
                        LotteryResultAdapter LotteryResultAdapter = new LotteryResultAdapter(LotteryResultActivity.this, lotteryResult.getResult().getLotteryDetails());
                        lotteryResultListview.setAdapter(LotteryResultAdapter);
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(LotteryResultActivity.this, "获取数据成功");
                    } else {
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(LotteryResultActivity.this, "服务器数据异常，请稍后再试！");
                    }
                } else {
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(LotteryResultActivity.this, "服务器数据异常，请稍后再试！");
                }

            }

            @Override
            public void onFail(String result) {
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(LotteryResultActivity.this, "开奖信息查询失败" + result);
            }

            @Override
            public void onCancel(Callback.CancelledException cex) {

            }
        });
    }

    /**
     * 设置RadioButton点击事件
     *
     * @param radioButton
     */
    private void setRadioButtonOnCheckedChangeListener(RadioButton radioButton) {
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String choiceName = (String) buttonView.getText();
                    Log.e("choiceName", "onCheckedChanged: " + choiceName);
                    loadLotteryResultData(choiceName);
                }
            }
        });
    }

    /**
     * 返回按钮
     */
    @Event(R.id.back_btn)
    private void onBack(View view) {
        this.finish();
    }


}
