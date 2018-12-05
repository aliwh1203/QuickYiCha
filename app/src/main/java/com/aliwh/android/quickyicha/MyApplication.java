package com.aliwh.android.quickyicha;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.widget.ImageView;

import com.aliwh.android.quickyicha.utils.CrashHandler;
import com.aliwh.android.quickyicha.utils.SharePreferenceUtil;
import com.baidu.mapapi.SDKInitializer;
import com.mob.MobSDK;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * @author LuLei
 * @date 2015年10月21日 上午10:25:59
 */

public class MyApplication extends MultiDexApplication {

    public static final String SAVE_USER = "quickyicha";// 保存信息的xml文件名
    public static final String LOGO_NAME = "logo.png";
    public SharePreferenceUtil sharePreference;// 共享文件
    private static MyApplication mApplication;
    public String share_pic;
    public String RecommenJson = "";
    public double longitude = 114.049775; // 经度
    public double latitude = 22.532832; // 纬度
    public String address;// 当前定位地址
    public int choiceWay = 1; //选择方式标志值，在心情详细、发表日志里面要用到
    public String moodEnglish = "Neutral";//默认心情英文
    public int isCheckFriend = 0;

    public synchronized static MyApplication getInstance() {
        return mApplication;
    }

    public static Context getContext() {
        return mApplication.getApplicationContext();
    }

    /**
     * 分割 Dex 支持
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static ImageOptions.Builder builder = new ImageOptions.Builder()
            .setFadeIn(true)//设置为淡入效果
            .setSquare(true)//设置图片显示为正方形
            .setCrop(true)//如果ImageView的大小不是定义为wrap_content, 不要crop.
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)//设置图片的缩放模式
            .setFailureDrawableId(R.mipmap.load_default)//设置加载图片失败的图片
            .setLoadingDrawableId(R.mipmap.load_default)//设置加载中的图片
            .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)//加载中或错误图片的ScaleType
            .setIgnoreGif(false)//设置为忽略Gif图片
            .setRadius(10)//设置拐角的弧度(四个角的弧度)
            .setParamsBuilder(new ImageOptions.ParamsBuilder() {
                @Override
                public RequestParams buildParams(RequestParams params, ImageOptions options) {
                    params.setCacheDirName("mySDCache");
                    return params;
                }
            })
            .setUseMemCache(true);//设置使用缓存(优先sd卡),默认为true

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //Mob初始化
        MobSDK.init(this);

        //初始化xutil3
        x.Ext.init(this);
        //是否是开发、调试模式
        x.Ext.setDebug(BuildConfig.DEBUG);//是否输出debug日志，开启debug会影响性能
        LogUtil.customTagPrefix = "allwhere"; // 方便调试时过滤 adb logcat 输出
//        createDir();
//        initImagePath();
        CrashHandler crashHandler = CrashHandler.getInstance(); // 捕获异常崩溃信息
        crashHandler.init(getApplicationContext());
        sharePreference = new SharePreferenceUtil(this, SAVE_USER);
    }

//    public void createDir() {
//        String log_path;
//        try {
//            // 得到一个路径，内容是sdcard的文件夹路径和名字
//            log_path = Environment.getExternalStorageDirectory()
//                    .getCanonicalPath() + Constants.DLOG;
//            File log = new File(log_path);
//            if (!log.exists()) {
//                // 若不存在，创建目录，可以在应用启动的时候创建
//                log.mkdirs();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    /**
//     * 初始化图片路径
//     */
//    private void initImagePath() {
//        try {
//            if (Environment.MEDIA_MOUNTED.equals(Environment
//                    .getExternalStorageState())
//                    && Environment.getExternalStorageDirectory().exists()) {
//                share_pic = Environment.getExternalStorageDirectory()
//                        .getAbsolutePath() + Constants.DATA_PATH + LOGO_NAME;
//            } else {
//                share_pic = getFilesDir().getAbsolutePath()
//                        + Constants.DATA_PATH + LOGO_NAME;
//            }
//            // 获得封装 文件的InputStream对象
//            InputStream is = getResources().openRawResource(
//                    R.raw.logo);
//            // 创建图片文件夹
//            File file = new File(share_pic);
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            FileOutputStream fos = new FileOutputStream(share_pic);
//            byte[] buffer = new byte[8192];
//            int count = 0;
//            // 开始复制Logo图片文件
//            while ((count = is.read(buffer)) > 0) {
//                fos.write(buffer, 0, count);
//            }
//            fos.close();
//            is.close();
//        } catch (Throwable t) {
//            t.printStackTrace();
//            share_pic = null;
//        }
//    }

}
