package com.aliwh.android.quickyicha.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.fragment.IndexFragment;
import com.aliwh.android.quickyicha.fragment.MyFragment;
import com.aliwh.android.quickyicha.fragment.SecondFragment;
import com.aliwh.android.quickyicha.fragment.ThirdFragment;
import com.aliwh.android.quickyicha.utils.IntentUtils;
import com.aliwh.android.quickyicha.view.CustomViewPager;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.content_viewpager)
    private CustomViewPager mPager;
    @ViewInject(R.id.group)
    private RadioGroup mGroup;

    private IndexFragment mfragment1;
    private SecondFragment mfragment2;
    private ThirdFragment mfragment3;
    private MyFragment mfragment4;

    private List<Fragment> fragmentList;

    private MyBroadcast myBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mfragment1 = new IndexFragment();
        mfragment2 = new SecondFragment();
        mfragment3 = new ThirdFragment();
        mfragment4 = new MyFragment();

        fragmentList = new ArrayList<>();
        fragmentList.add(mfragment1);
        fragmentList.add(mfragment2);
        fragmentList.add(mfragment3);
        fragmentList.add(mfragment4);

        mGroup.setOnCheckedChangeListener(new CheckedChangeListener());

        mPager.setNoScroll(true);
        mPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        mPager.addOnPageChangeListener(new PageChangeListener());
        mPager.setOffscreenPageLimit(3);
        //去除滑动效果
        mPager.setViewPagerScrollSpeed();

        myBroadcast = new MyBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.INSTALL_APK);
        registerReceiver(myBroadcast, intentFilter);
    }


    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }

    private class CheckedChangeListener implements OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.one:
                    mPager.setCurrentItem(0);
                    break;
                case R.id.two:
                    mPager.setCurrentItem(1);
                    break;
                case R.id.three:
                    mPager.setCurrentItem(2);
                    break;
                case R.id.four:
                    mPager.setCurrentItem(3);
                    break;
            }
        }
    }

    private class PageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mGroup.check(R.id.one);
                    break;
                case 1:
                    mGroup.check(R.id.two);
                    break;
                case 2:
                    mGroup.check(R.id.three);
                    break;
                case 3:
                    mGroup.check(R.id.four);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }

    public class MyBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //接收到安装广播
            if (intent.getAction().equals(Constants.INSTALL_APK)) {
                String filePath = intent.getStringExtra("filePath");
                Log.e("onReceive", "onReceive: " + filePath);
                Intent installApkIntent = IntentUtils.getFileIntent(context, new File(filePath));
                startActivity(installApkIntent);
            }

        }

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myBroadcast);
        super.onDestroy();
    }
}
