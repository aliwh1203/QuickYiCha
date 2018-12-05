package com.aliwh.android.quickyicha.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.utils.VersionUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 关于我们页面
 *
 * @author Kevin
 */
@ContentView(R.layout.activity_about_us)
public class AboutUsActivity extends BaseActivity {

    @ViewInject(R.id.version_name)
    private TextView mVersionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = VersionUtils.getVerionName(this);// 版本名称
        mVersionName.setText(getResources().getString(R.string.app_name) + "" + name);
    }

    /**
     * 返回按钮
     */
    @Event(R.id.back_btn)
    private void onBack(View view) {
        finish();
    }

}
