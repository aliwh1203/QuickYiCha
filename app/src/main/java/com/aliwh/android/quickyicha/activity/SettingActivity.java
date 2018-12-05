package com.aliwh.android.quickyicha.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.aliwh.android.quickyicha.BuildConfig;
import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.MyApplication;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.UpdateService;
import com.aliwh.android.quickyicha.utils.CleanMessageUtil;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.NetworkUtil;
import com.aliwh.android.quickyicha.utils.SharePreferenceUtil;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 应用设置
 */

@ContentView(R.layout.activity_sets)
public class SettingActivity extends BaseActivity {


    @ViewInject(R.id.settings_nodisturbing_starttime)
    private RelativeLayout mStartTime_item;

    @ViewInject(R.id.settings_nodisturbing_stoptime)
    private RelativeLayout mStopTime_item;

    @ViewInject(R.id.settings_start_time)
    private TextView mStartTime;

    @ViewInject(R.id.settings_stop_time)
    private TextView mStopTime;

    @ViewInject(R.id.cache_size)
    private TextView cacheSizeText;

    @ViewInject(R.id.settings_nodisturbing)
    private ToggleButton mAvoidDisturb_btn;

    private SharePreferenceUtil mSpUtil;
    // 当前版本号
    public int versionCode = 0;
    // 当前版本名称
    public String versionName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpUtil = MyApplication.getInstance().sharePreference;
        mAvoidDisturb_btn.setChecked(mSpUtil.getIsAvoidDisturb());
        setViewVisibility();
        getCacheSize();
        getCurrentVersion();

    }

    // 是否开启免打扰
    @Event(value = R.id.settings_nodisturbing, type = CompoundButton.OnCheckedChangeListener.class)
    private void noDisturbing(CompoundButton buttonView, boolean isChecked) {
//        if (isChecked) {
//            mSpUtil.setIsAvoidDisturb(true);
//            if (isInTime()) {
//                mSpUtil.setTimeInAvoidDisturb(true);
//                chatOptions.setNoticeBySound(false); // 关掉声音
//                chatOptions.setNoticedByVibrate(false); // 关掉震动
//                EMChatManager.getInstance().setChatOptions(chatOptions);
//                HXSDKHelper.getInstance().getModel().setSettingMsgSound(false);
//                HXSDKHelper.getInstance().getModel()
//                        .setSettingMsgVibrate(false);
//            } else {
//                mSpUtil.setTimeInAvoidDisturb(false);
//            }
//            setViewVisibility();
//        } else {
//            mSpUtil.setIsAvoidDisturb(false);
//            mSpUtil.setTimeInAvoidDisturb(false);
//            chatOptions.setNoticeBySound(true);
//            chatOptions.setNoticedByVibrate(true);
//            EMChatManager.getInstance().setChatOptions(chatOptions);
//            HXSDKHelper.getInstance().getModel().setSettingMsgSound(true);
//            HXSDKHelper.getInstance().getModel().setSettingMsgVibrate(true);
//            setViewVisibility();
//        }
    }

    // 设置免打扰时间item是否可见
    private void setViewVisibility() {
        if (mSpUtil.getIsAvoidDisturb()) {
            // 打开
            mStartTime_item.setVisibility(View.VISIBLE);
            mStopTime_item.setVisibility(View.VISIBLE);
            // 回显时间
            int startHourTemp = mSpUtil.getStartAvoidDisturbHour();
            int startMinuteTemp = mSpUtil.getStartAvoidDisturbMinute();
            int stopHourTemp = mSpUtil.getStopAvoidDisturbHour();
            int stopMinuteTemp = mSpUtil.getStopAvoidDisturbMinute();

            mStartTime.setText(new StringBuilder()
                    .append(startHourTemp < 10 ? "0" + startHourTemp
                            : startHourTemp)
                    .append(":")
                    .append(startMinuteTemp < 10 ? "0" + startMinuteTemp
                            : startMinuteTemp));

            mStopTime.setText(new StringBuilder()
                    .append(stopHourTemp < 10 ? "0" + stopHourTemp
                            : stopHourTemp)
                    .append(":")
                    .append(stopMinuteTemp < 10 ? "0" + stopMinuteTemp
                            : stopMinuteTemp));

        } else {
            // 关闭
            mStartTime_item.setVisibility(View.GONE);
            mStopTime_item.setVisibility(View.GONE);
        }
    }

    // 点击设置开始时间
    @Event(R.id.settings_nodisturbing_starttime)
    private void setStartTime(View v) {
        new TimePickerDialog(SettingActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        mSpUtil.setStartAvoidDisturbHour(hour);
                        mSpUtil.setStartAvoidDisturbMinute(minute);
                        // 更新TextViewe控件时间 小于10加0
                        mStartTime.setText(new StringBuilder()
                                .append(hour < 10 ? "0" + hour : hour)
                                .append(":")
                                .append(minute < 10 ? "0" + minute : minute));
//                        if (isInTime()) {
//                            chatOptions.setNoticeBySound(false); // 关掉声音
//                            chatOptions.setNoticedByVibrate(false); // 关掉震动
//                            EMChatManager.getInstance().setChatOptions(
//                                    chatOptions);
//                            HXSDKHelper.getInstance().getModel()
//                                    .setSettingMsgSound(false);
//                            HXSDKHelper.getInstance().getModel()
//                                    .setSettingMsgVibrate(false);
//                        } else {
//                            mSpUtil.setTimeInAvoidDisturb(false);
//                        }
                    }
                }, mSpUtil.getStartAvoidDisturbHour(), mSpUtil
                .getStartAvoidDisturbMinute(), true).show();

    }

    // 点击设置结束时间
    @Event(R.id.settings_nodisturbing_stoptime)
    private void setStopTime(View v) {
        new TimePickerDialog(SettingActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        mSpUtil.setStopAvoidDisturbHour(hour);
                        mSpUtil.setStopAvoidDisturbMinute(minute);
                        // 更新TextViewe控件时间 小于10加0
                        mStopTime.setText(new StringBuilder()
                                .append(hour < 10 ? "0" + hour : hour)
                                .append(":")
                                .append(minute < 10 ? "0" + minute : minute));
//                        if (isInTime()) {
//                            chatOptions.setNoticeBySound(false); // 关掉声音
//                            chatOptions.setNoticedByVibrate(false); // 关掉震动
//                            EMChatManager.getInstance().setChatOptions(
//                                    chatOptions);
//                            HXSDKHelper.getInstance().getModel()
//                                    .setSettingMsgSound(false);
//                            HXSDKHelper.getInstance().getModel()
//                                    .setSettingMsgVibrate(false);
//                        } else {
//                            mSpUtil.setTimeInAvoidDisturb(false);
//                        }
                    }
                }, mSpUtil.getStopAvoidDisturbHour(), mSpUtil
                .getStopAvoidDisturbMinute(), true).show();

    }

    // 清除缓存
    @Event(R.id.clear_memory)
    private void onClearMemoryClick(View v) {
        View dialogView = LayoutInflater.from(this).inflate(
                R.layout.custom_dialog, null);
        TextView tv_title = (TextView) dialogView.findViewById(R.id.tv_title);
        TextView dialog_msg = (TextView) dialogView
                .findViewById(R.id.dialog_msg);
        tv_title.setText("温馨提示");
        dialog_msg.setText("您确定要清除本地缓存?");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogView, 0, 0, 0, 0);
        // 点击屏幕外侧，dialog不消失
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Button btnOK = (Button) dialogView.findViewById(R.id.btn_ok);
        btnOK.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                x.image().clearCacheFiles();//清除磁盘缓存的文件夹
                x.image().clearMemCache();//清除内部缓存
                CleanMessageUtil.clearAllCache(getApplicationContext());
                getCacheSize();
                ToastUtil.showToast(SettingActivity.this, "清除本地缓存成功");
                dialog.dismiss();
            }
        });
        Button btnCancel = (Button) dialogView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    /**
     * 检测更新
     */
    public void checkAppUpdate() {
        // 检测网络
        if (!NetworkUtil.isNetworkEnabled(this)) {
            return;
        }
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                DialogUtil.showRequestDialog(SettingActivity.this, "检测新版本...");
            }

            @Override
            protected String doInBackground(Void... params) {
                String jsonStr = null;
                jsonStr = loadJson(Constants.UPDATEDATA);
                return jsonStr;
            }

            @Override
            protected void onPostExecute(String result) {
                DialogUtil.closeRequestDialog();
                if (result != null) {
                    Gson gson = new Gson();
                    Map<String, String> retMap = gson.fromJson(result, new TypeToken<Map<String, String>>() {
                    }.getType());
                    if (null != retMap) {
                        int serviceCode = Integer.valueOf(retMap.get("version"));
                        // 版本判断retMap
                        if (serviceCode > versionCode) {
                            String info = retMap.get("info");
                            String name = retMap.get("name");
                            String url = retMap.get("url");
                            updateDialog(info, url, name);
                        } else {
                            ToastUtil.showToast(SettingActivity.this, "已是最新版本，无需更新");
                        }
                    } else {
                        ToastUtil.showToast(SettingActivity.this, "服务器异常，请稍候再试");
                    }
                } else {
                    ToastUtil.showToast(SettingActivity.this, "网络不给力，请稍候再试");
                }
            }
        }.execute();

    }

    /**
     * 用户评价
     *
     * @param v
     */
    @Event(R.id.user_evaluate)
    private void userEvaluate(View v) {
        // 判断应用宝是否存在
        if (isAvilible("com.tencent.android.qqdownloader")) {  // 市场存在
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // 跳转到市场评分
//			ComponentName cn = new ComponentName("com.baidu.appsearch",
//					"com.baidu.appsearch.search.SearchActivity");
//			intent.setComponent(cn);
            intent.setData(Uri
                    .parse("market://details?id=" + getPackageName()));
            startActivity(intent);

        } else {
            // 市场不存在
            ToastUtil.showToast(getApplicationContext(), "请下载腾讯应用宝");
        }

    }

    private void showLogOutViewWindow() {
        View dialogView = LayoutInflater.from(this).inflate(
                R.layout.custom_dialog, null);
        TextView tv_title = (TextView) dialogView.findViewById(R.id.tv_title);
        TextView dialog_msg = (TextView) dialogView
                .findViewById(R.id.dialog_msg);
        tv_title.setText("温馨提示");
        dialog_msg.setText("你确定要注销登录?");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogView, 0, 0, 0, 0);
        // 点击屏幕外侧，dialog不消失
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Button btnOK = (Button) dialogView.findViewById(R.id.btn_ok);
        btnOK.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                UMSSDK.logout(new OperationCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        super.onSuccess(aVoid);
                        Log.e("Setting", "onSuccess: ");
                        Intent intent = new Intent("data.broadcast.action.gotologin");
                        sendBroadcast(intent);
                        SettingActivity.this.finish();
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        super.onFailed(throwable);
                        Intent intent = new Intent("data.broadcast.action.gotologin");
                        sendBroadcast(intent);
                        SettingActivity.this.finish();
                        ToastUtil.showToast(SettingActivity.this, "登录信息过期请重新登录");
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();
                    }
                });
                dialog.dismiss();

            }
        });
        Button btnCancel = (Button) dialogView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 检测更新
     *
     * @param view
     */
    @Event(R.id.check_update)
    private void checkUpdate(View view) {
        checkAppUpdate();
//        installApk(new File(Constants.DOWNLOAD_APK_PATH + File.separator + "QuickYiCha.apk"));
    }

    public void installApk(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
    }


    // 注销登陆
    @Event(R.id.exit_btn)
    private void onLogout(View view) {
        showLogOutViewWindow();

    }

    // 返回
    @Event(R.id.back_btn)
    private void onBack(View v) {
        finish();
    }


    public void updateDialog(String msg, final String url, final String fileName) {
        View dialogView = LayoutInflater.from(SettingActivity.this).inflate(
                R.layout.custom_dialog, null);
        TextView tv_title = (TextView) dialogView.findViewById(R.id.tv_title);
        TextView dialog_msg = (TextView) dialogView
                .findViewById(R.id.dialog_msg);
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        final AlertDialog dialog = builder.create();
        // 点击屏幕外侧，dialog不消失
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(dialogView, 0, 0, 0, 0);
        dialog.show();
        tv_title.setText("版本更新");
        dialog_msg.setText(msg);
        // 点击屏幕外侧，dialog不消失
        dialog.setCanceledOnTouchOutside(false);
        Button btnOK = (Button) dialogView.findViewById(R.id.btn_ok);
        btnOK.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent updateIntent = new Intent(SettingActivity.this, UpdateService.class);
                updateIntent.putExtra("app_name", fileName);
                updateIntent.putExtra("down_url", url);
                startService(updateIntent);
                dialog.dismiss();
            }
        });
        Button btnCancel = (Button) dialogView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                delFile();
                dialog.dismiss();
            }
        });
    }

    /**
     * 判断时间是否在设定的时间范围内
     *
     * @return
     */
    private boolean isInTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        int strDateBeginH = mSpUtil.getStartAvoidDisturbHour();
        int strDateBeginM = mSpUtil.getStartAvoidDisturbMinute();

        int strDateEndH = mSpUtil.getStopAvoidDisturbHour();
        int strDateEndM = mSpUtil.getStopAvoidDisturbMinute();

        int minuteNum = hour * 60 + minute;
        int startMinuteNum = strDateBeginH * 60 + strDateBeginM;
        int stopMinuteNum = strDateEndH * 60 + strDateEndM;
        if (stopMinuteNum > startMinuteNum) {
            if (minuteNum >= startMinuteNum && minuteNum <= stopMinuteNum) {
                return true;
            } else {
                return false;
            }
        } else if (stopMinuteNum == startMinuteNum) {
            return false;
        } else {
            if (minuteNum >= stopMinuteNum && minuteNum <= startMinuteNum) {
                return false;
            } else {
                return true;
            }
        }
    }

    public String loadJson(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    /**
     * 判断市场是否存在的方法
     *
     * @param packageName
     * @return
     */
    private boolean isAvilible(String packageName) {
        final PackageManager packageManager = getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    /**
     * 获取缓存大小
     */
    private void getCacheSize() {
        String cacheSizeStr = null;
        try {
            cacheSizeStr = CleanMessageUtil.getTotalCacheSize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cacheSizeText.setText(cacheSizeStr);
    }

    /**
     * 获得当前版本信息
     */
    public void getCurrentVersion() {
        try {
            // 获取应用包信息
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            this.versionCode = info.versionCode;
            this.versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除临时路径里的安装包
     */
    public void delFile() {
        File myFile = new File(Constants.DOWNLOAD_APK_PATH);
        if (myFile.exists()) {
            myFile.delete();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
