package com.aliwh.android.quickyicha.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.MyApplication;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.activity.AboutUsActivity;
import com.aliwh.android.quickyicha.activity.LoginActivity;
import com.aliwh.android.quickyicha.activity.MainActivity;
import com.aliwh.android.quickyicha.activity.SettingActivity;
import com.aliwh.android.quickyicha.activity.UserInfoActivity;
import com.aliwh.android.quickyicha.dao.UserDB;
import com.aliwh.android.quickyicha.utils.DoubleClickUtil;
import com.aliwh.android.quickyicha.utils.JavaBlurProcess;
import com.aliwh.android.quickyicha.utils.SharePreferenceUtil;
import com.aliwh.android.quickyicha.view.CircleImageView;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.sharesdk.onekeyshare.OnekeyShare;

@ContentView(R.layout.fragment_my_layout)
public class MyFragment extends BaseFragment {

    private View mView;

    @ViewInject(R.id.touxiang_bg)
    private ImageView touxiang_bg;


    @ViewInject(R.id.menu_iv_portrait)
    private CircleImageView circleImageView;

    @ViewInject(R.id.user_nick_name)
    private TextView user_nick_name;

    private SharePreferenceUtil mSpUtil;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSpUtil = MyApplication.getInstance().sharePreference;
        IntentFilter filter = new IntentFilter(
                "data.broadcast.action.gotologin");
        getActivity().registerReceiver(broadcastReceiver, filter);
    }

    /**
     * 头像被点击
     *
     * @param view
     */
    @Event(value = {R.id.menu_iv_portrait}) //type默认为OnClickListener
    private void userAvatar(View view) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            if (mSpUtil.getIsOline()) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), UserInfoActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * 个人信息被点击
     *
     * @param view
     */
    @Event(value = {R.id.person_msg}) //type默认为OnClickListener
    private void userMessage(View view) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            if (mSpUtil.getIsOline()) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), UserInfoActivity.class);
                startActivity(intent);
            }
        }
    }


    /**
     * 应用设置被点击
     *
     * @param view
     */
    @Event(value = {R.id.app_shezhi}) //type默认为OnClickListener
    private void userSetting(View view) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            if (mSpUtil.getIsOline()) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * 关于我们
     *
     * @param view
     */
    @Event(value = {R.id.menu_about}) //type默认为OnClickListener
    private void aboutUs(View view) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), AboutUsActivity.class);
            startActivity(intent);
        }
    }


    /**
     * 推荐分享
     *
     * @param
     */
    @Event(value = {R.id.menu_share}) //type默认为OnClickListener
    private void menuShare(View v) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            showShare(Constants.DOWNLOAD_URL);
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            mSpUtil.setIsOnline(false);
            mSpUtil.setInterestLabel(false);
            mSpUtil.setIsAvoidDisturb(false); // 退出登录时将免打扰模式关闭
            mSpUtil.setTimeInAvoidDisturb(false);
            mSpUtil.setStartAvoidDisturbHour(23); // 退出登录将免打扰默认开始时间小时设置为晚上23时
            mSpUtil.setStartAvoidDisturbMinute(0);// 退出登录将免打扰默认开始时间分钟设置为0分
            mSpUtil.setStopAvoidDisturbHour(8); // 退出登录将免打扰默认结束时间 小时 设置为上午8时
            mSpUtil.setStopAvoidDisturbMinute(0); // 退出登录将免打扰默认结束时间分钟设置为0分
            MyApplication.getInstance().isCheckFriend = 0; // 将选择的我与好友归空也就是说默认选择我
            MyApplication.getInstance().choiceWay = 1; //选择方式标志值改为默认
            MyApplication.getInstance().moodEnglish = "Neutral";//将默认心情值默认
            intent.setClass(getActivity(), LoginActivity.class);
            startActivity(intent);
            ((MainActivity) getActivity()).finish();
        }
    };

    private void showShare(String url) {
        OnekeyShare oks = new OnekeyShare();
        // 关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
        // oks.setNotification(R.drawable.ic_launcher,
        // getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是快易查，快来下载我吧");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath(photoUrl);
        // 确保SDcard下面存在此张图片
        // oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        // 启动分享GUI
        oks.show(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        UserDB userDB = new UserDB(getActivity());
        Log.e("userDB", "onResume: " + userDB.getUserAvatar());
        Log.e("userDB", "onResume: " + userDB.getNickName());
        if (mSpUtil.getIsOline()) {
            if (!TextUtils.isEmpty(userDB.getUserAvatar())) {
                x.image().bind(circleImageView, userDB.getUserAvatar(), new Callback.CommonCallback<Drawable>() {
                    @Override
                    public void onSuccess(Drawable result) {
                        BitmapDrawable bd = (BitmapDrawable) result;
                        blur(bd.getBitmap(), touxiang_bg, 8);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

            }

            if (!TextUtils.isEmpty(userDB.getNickName())) {
                user_nick_name.setText(userDB.getNickName());
            }
        } else {
            circleImageView.setBackgroundResource(R.mipmap.icon_user_male);
            user_nick_name.setText(R.string.menu_login);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    private void blur(Bitmap srcBitmap, ImageView view, float radius) {
        Bitmap overlay = null;
        //高斯模糊效率的快慢取决于图片的像素点的多少，所以进行了8倍的减少了像素点数量
        int scaleFactor = 8;
        if (overlay != null) {
            overlay.recycle();
        }
        JavaBlurProcess process = new JavaBlurProcess();
        //2、3参数为新位图所期望的宽高
        overlay = Bitmap.createScaledBitmap(srcBitmap, srcBitmap.getWidth() / scaleFactor, srcBitmap.getHeight() / scaleFactor, false);
        overlay = process.blur(overlay, radius);//高斯模糊
        view.setImageBitmap(overlay);
    }
}