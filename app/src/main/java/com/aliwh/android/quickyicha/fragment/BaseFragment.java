package com.aliwh.android.quickyicha.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aliwh.android.quickyicha.R;

import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

public abstract class BaseFragment extends Fragment {

    public ImageOptions.Builder builder;
    public int resId = R.mipmap.icon_user_male;
    private boolean injected = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        injected = true;
//        initImageBuilder();
        return x.view().inject(this, inflater, container);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
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
        builder.setParamsBuilder(new ImageOptions.ParamsBuilder(){
            @Override
            public RequestParams buildParams(RequestParams params, ImageOptions options) {
                params.setCacheDirName("mySDCache");//设置sd卡上的缓存文件名,默认为xUtils_img
                long cacheSize = params.getCacheSize();
                Log.i("tag", "缓存大小:"+cacheSize/1024);
                return params;
            }
        });
        builder.setUseMemCache(true);//设置使用缓存(优先sd卡),默认为true

    }


}