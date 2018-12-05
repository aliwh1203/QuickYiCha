package com.aliwh.android.quickyicha.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;

import com.aliwh.android.quickyicha.MyApplication;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.activity.BoxOfficeActivity;
import com.aliwh.android.quickyicha.activity.CarChoiceActivity;
import com.aliwh.android.quickyicha.activity.HealthKnowledgeSearchActivity;
import com.aliwh.android.quickyicha.activity.LotteryResultActivity;
import com.aliwh.android.quickyicha.activity.MenuQueryActivity;
import com.aliwh.android.quickyicha.activity.PhoneAddressActivity;
import com.aliwh.android.quickyicha.activity.PhraseQueryActivity;
import com.aliwh.android.quickyicha.module.makevideo.YWRecordVideoActivity;
import com.aliwh.android.quickyicha.utils.DoubleClickUtil;
import com.aliwh.android.quickyicha.utils.PermissionHelper;
import com.aliwh.android.quickyicha.utils.SharePreferenceUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.io.File;

@ContentView(R.layout.fragment_three_layout)
public class ThirdFragment extends BaseFragment {
    private SharePreferenceUtil mSpUtil;
    //权限申请帮助类
    private PermissionHelper permissionHelper;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSpUtil = MyApplication.getInstance().sharePreference;
        permissionHelper = new PermissionHelper(getActivity());
    }

    /**
     * 彩票开奖结果查询
     *
     * @param v
     */
    @Event(value = {R.id.lottery_result_query_layout}) //type默认为OnClickListener
    private void lotteryResultQuery(View v) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            if (mSpUtil.getIsOline()) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), LotteryResultActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * 菜谱查询
     *
     * @param
     */
    @Event(value = {R.id.menu_query_layout}) //type默认为OnClickListener
    private void menuQuery(View v) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            if (mSpUtil.getIsOline()) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MenuQueryActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * 手机号归属地查询
     *
     * @param v
     */
    @Event(value = {R.id.phone_address_query_layout}) //type默认为OnClickListener
    private void phoneAddressQuery(View v) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            if (mSpUtil.getIsOline()) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), PhoneAddressActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * 电影票房查询
     *
     * @param v
     */
    @Event(value = {R.id.box_office_query_layout}) //type默认为OnClickListener
    private void boxOfficeQuery(View v) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            if (mSpUtil.getIsOline()) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), BoxOfficeActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * 成语查询
     *
     * @param v
     */
    @Event(value = {R.id.phrase_query_layout}) //type默认为OnClickListener
    private void phraseQuery(View v) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            if (mSpUtil.getIsOline()) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), PhraseQueryActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * 健康知识查询
     *
     * @param v
     */
    @Event(value = {R.id.health_knowledge_layout}) //type默认为OnClickListener
    private void healthKnowledgeQuery(View v) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            if (mSpUtil.getIsOline()) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), HealthKnowledgeSearchActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * 汽车查询
     *
     * @param v
     */
    @Event(value = {R.id.car_query_layout}) //type默认为OnClickListener
    private void carQuery(View v) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            if (mSpUtil.getIsOline()) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), CarChoiceActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * 视频录制
     *
     * @param v
     */
    @Event(value = {R.id.make_video_layout}) //type默认为OnClickListener
    private void makeVideo(View v) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            if (mSpUtil.getIsOline()) {
                permissionHelper.requestPermissions("需要拍照、麦克风和写入SD卡的权限", new PermissionHelper.PermissionListener() {
                    @Override
                    public void doAfterGrand(String... permissions) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), YWRecordVideoActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void doAfterDenied(String... permissions) {
                        permissionHelper.openPermissionsSetting("需要相应的权限, 前往设置>权限页面打开对应权限");
                    }
                }, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            }
        }
    }

    //将权限交给permissionhelper处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}