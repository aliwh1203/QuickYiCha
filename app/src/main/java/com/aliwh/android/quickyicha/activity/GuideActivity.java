package com.aliwh.android.quickyicha.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.aliwh.android.quickyicha.MyApplication;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.utils.ScreenUtils;
import com.aliwh.android.quickyicha.utils.SharePreferenceUtil;

import java.util.ArrayList;

/**
 * 引导页面
 */
public class GuideActivity extends Activity {
    private ViewPager viewPager;
    private ArrayList<View> pageViews;
    private ImageView imageView;
    private ImageView[] imageViews;
    // 包裹小圆点的LinearLayout
    private ViewGroup group;
    private SharePreferenceUtil mSpUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        mSpUtil = MyApplication.getInstance().sharePreference;
        pageViews = new ArrayList<View>();
        pageViews.add(getLayoutInflater().inflate(R.layout.guide_item_one, null));
        pageViews.add(getLayoutInflater().inflate(R.layout.guide_item_two, null));
        pageViews.add(getLayoutInflater().inflate(R.layout.guide_item_three, null));

        imageViews = new ImageView[pageViews.size()];

        group = (ViewGroup) findViewById(R.id.viewGroup);
        viewPager = (ViewPager) findViewById(R.id.guidePages);

        for (int i = 0; i < pageViews.size(); i++) {
            imageView = new ImageView(GuideActivity.this);
            imageView.setLayoutParams(new LayoutParams(ScreenUtils.dip2px(GuideActivity.this, 20), ScreenUtils.dip2px(GuideActivity.this, 20)));
            imageView.setPadding(ScreenUtils.dip2px(GuideActivity.this, 10), 0, ScreenUtils.dip2px(GuideActivity.this, 10), 0);
            imageViews[i] = imageView;

            if (i == 0) {
                // 默认选中第一张图片
                imageViews[i]
                        .setBackgroundResource(R.mipmap.page_indicator_focused);
            } else {
                imageViews[i].setBackgroundResource(R.mipmap.page_indicator);
            }
            group.addView(imageViews[i]);
        }
        viewPager.setAdapter(new GuidePageAdapter());
        viewPager.addOnPageChangeListener(new GuidePageChangeListener());
    }

    // 指引页面数据适配器
    class GuidePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(pageViews.get(arg1));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(pageViews.get(arg1));
            return pageViews.get(arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }
    }

    // 指引页面更改事件监听器
    class GuidePageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
//			if (arg0 == 2 && arg1 == 0.0 && scrollState && isFirst) {
//				isFirst = false;
//				Intent intent = new Intent(getApplicationContext(),
//						LoginActivity.class);
//				startActivity(intent);
//				GuideActivity.this.finish();
//			}
        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0]
                        .setBackgroundResource(R.mipmap.page_indicator_focused);

                if (arg0 != i) {
                    imageViews[i]
                            .setBackgroundResource(R.mipmap.page_indicator);
                }
            }
        }
    }

    public void goUseApp(View view) {
        Intent intent = new Intent(GuideActivity.this,
                LoginActivity.class);
        startActivity(intent);
        GuideActivity.this.finish();
        mSpUtil.setIsFirst(false);
    }

}