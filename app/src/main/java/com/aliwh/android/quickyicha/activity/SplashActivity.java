package com.aliwh.android.quickyicha.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.aliwh.android.quickyicha.MyApplication;
import com.aliwh.android.quickyicha.utils.NetworkUtil;
import com.aliwh.android.quickyicha.utils.SharePreferenceUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 启动页
 */
public class SplashActivity extends AppCompatActivity {

    public SharePreferenceUtil spUtil;
    public boolean isOnline = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
        spUtil = MyApplication.getInstance().sharePreference;
        isOnline = spUtil.getIsOline();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (spUtil.getIsFirst()) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent i = new Intent();
                    i.setClass(SplashActivity.this, GuideActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 2000);

        } else {
            if (NetworkUtil.isNetworkEnabled(this)) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (!isOnline) {
                            Intent i = new Intent();
                            i.setClass(SplashActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, 2000);
            } else {
                showSetNetworkDialog();
            }
        }
    }


    /**
     * 提示网络状态不可用，并进行设置
     */
    private void showSetNetworkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置网络");
        builder.setMessage("网络错误，请检查网络状态");
        builder.setCancelable(false);
        builder.setPositiveButton("设置网络", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_DATA_ROAMING_SETTINGS);
                startActivity(intent);

            }
        });
        builder.create().show();

    }


    /**
     * 禁止返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
