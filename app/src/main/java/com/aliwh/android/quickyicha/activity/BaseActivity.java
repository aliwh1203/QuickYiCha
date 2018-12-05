package com.aliwh.android.quickyicha.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.utils.StatusBarUtil;

import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Activity基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    public ImageOptions.Builder builder;
    public int resId = R.mipmap.load_default;
//    public int resId = R.mipmap.icon_user_male;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
            StatusBarUtil.setColorNoTranslucent(BaseActivity.this, getResources().getColor(R.color.top_bar));
        }
        x.view().inject(this);
//        initImageBuilder();
    }

    public void initImageBuilder() {
        //设置下载的图片参数
        builder = new ImageOptions.Builder();
        builder.setFadeIn(true);//设置为淡入效果
        //builder.setCircular(true);//设置图片显示为圆形
        builder.setSquare(true);//设置图片显示为正方形
        builder.setCrop(true);//如果ImageView的大小不是定义为wrap_content, 不要crop.
        builder.setImageScaleType(ImageView.ScaleType.CENTER_CROP);//设置图片的缩放模式
        builder.setFailureDrawableId(resId);//设置加载图片失败的图片
        builder.setLoadingDrawableId(resId);//设置加载中的图片
        builder.setPlaceholderScaleType(ImageView.ScaleType.MATRIX);//加载中或错误图片的ScaleType
        builder.setIgnoreGif(false);//设置为忽略Gif图片
        builder.setRadius(50);//设置拐角的弧度(四个角的弧度)
        //设置参数的使用
        builder.setParamsBuilder(new ImageOptions.ParamsBuilder() {
            @Override
            public RequestParams buildParams(RequestParams params, ImageOptions options) {
                params.setCacheDirName("mySDCache");//设置sd卡上的缓存文件名,默认为xUtils_img
                long cacheSize = params.getCacheSize();
                Log.i("tag", "缓存大小:" + cacheSize / 1024);
                return params;
            }
        });
        builder.setUseMemCache(true);//设置使用缓存(优先sd卡),默认为true

    }

    public void closeKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
