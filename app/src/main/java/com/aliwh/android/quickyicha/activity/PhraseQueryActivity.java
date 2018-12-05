package com.aliwh.android.quickyicha.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.aliwh.android.quickyicha.BuildConfig;
import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.XutilsHttp;
import com.aliwh.android.quickyicha.bean.Phrase;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * 成语查询页面
 */
@ContentView(R.layout.activity_phrase_query)
public class PhraseQueryActivity extends BaseActivity {
    @ViewInject(R.id.phrase_text)
    private EditText phraseText;

    @ViewInject(R.id.phrase_info_text)
    private TextView phraseInfoText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Event(R.id.submit_phrase_search)
    private void submitPhoneSearch(View v) {
        final String phraseTextStr = phraseText.getText().toString().trim();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.MOB_API_KEY);
        map.put("name", phraseTextStr);
        DialogUtil.showRequestDialog(PhraseQueryActivity.this, "加载中...");
        XutilsHttp.getInstance().get(Constants.PHRASE_QUERY, map, new XutilsHttp.XCallBack() {
            @Override
            public void onResponse(String result) {
                Log.e("allwhere", "onResponse: " + BuildConfig.DEBUG);
                Log.e("allwhere", "onResponse: " + result.toString());
                if (result != null) {
                    Gson gson = new Gson();
                    Phrase phrase = gson.fromJson(result, Phrase.class);
                    if (phrase.getRetCode().equals(Constants.REQUEST_SUCCESS)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("\n\t")
                                .append("成语：" + phrase.getResult().getName() + "\n\t")
                                .append("拼音：" + phrase.getResult().getPinyin() + "\n\t")
                                .append("释义：" + phrase.getResult().getPretation() + "\n\t");
                        if (!TextUtils.isEmpty(phrase.getResult().getSource())) {
                            sb.append("出自：" + phrase.getResult().getSource() + "\n\t");
                        }
                        if (!TextUtils.isEmpty(phrase.getResult().getSample())) {
                            sb.append("示例：" + phrase.getResult().getSample() + "\n\t");
                        }
                        if (!TextUtils.isEmpty(phrase.getResult().getSampleFrom())) {
                            sb.append("示例出自：" + phrase.getResult().getSampleFrom() + "\n\t");
                        }
                        phraseInfoText.setText(sb.toString());
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(PhraseQueryActivity.this, "获取数据成功");
                    } else {
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(PhraseQueryActivity.this, "服务器数据异常，请稍后再试！");
                    }
                } else {
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(PhraseQueryActivity.this, "服务器数据异常，请稍后再试！");
                }

            }

            @Override
            public void onFail(String result) {
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(PhraseQueryActivity.this, "手机归属地信息查询失败" + result);
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
