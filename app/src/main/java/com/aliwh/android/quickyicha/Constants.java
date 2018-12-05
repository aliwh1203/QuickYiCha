package com.aliwh.android.quickyicha;


import android.os.Environment;

import java.io.File;

/**
 * 常量类
 */
public class Constants {

    public static final String DBNAME = "quickyicha.db";// 数据库名称
    public static final String DOWNLOAD_URL = "http://47.106.107.189/QuickYiCha/QuickYiCha.apk";

    public static final String INSTALL_APK = "com.aliwh.android.action.installapk";

    public static final String REQUEST_SUCCESS = "200"; //请求成功

    public static final String MOB_API_KEY = "2533dcacebab2"; //mob api key值

    //检测软件更新 配置参数文件
    public static final String UPDATEDATA = "http://47.106.107.189/QuickYiCha/version_android.json";

    //菜谱分类标签查询
    public static final String MENU_TYPE_TAG = "http://apicloud.mob.com/v1/cook/category/query";
    //按标签查询菜谱接口
    public static final String Query_MENU_BY_TAG = "http://apicloud.mob.com/v1/cook/menu/search";
    //菜谱查询接口
    public static final String MENU_QUERY = "http://apicloud.mob.com/v1/cook/menu/query";
    //手机号码归属地查询接口
    public static final String PHONE_ADDRESS_QUERY = "http://apicloud.mob.com/v1/mobile/address/query";
    //微信精选分类查询
    public static final String WEIXIN_JINGXUAN_TYPE_QUERY = "http://apicloud.mob.com/wx/article/category/query";
    //微信精选列表查询
    public static final String WEIXIN_JINGXUAN_LIST_QUERY = "http://apicloud.mob.com/wx/article/search";
    //健康知识查询接口
    public static final String HEALTH_KNOWLEDGE_QUERY = "http://apicloud.mob.com/appstore/health/search";
    //彩票支持彩种列表接口
    public static final String LOTTERY_TYPE_QUERY = "http://apicloud.mob.com/lottery/list";
    //彩票开奖结果查询接口
    public static final String LOTTERY_RESULT_QUERY = "http://apicloud.mob.com/lottery/query";
    //实时票房查询接口
    public static final String BOX_OFFICE_TIME_QUERY = "http://apicloud.mob.com/boxoffice/day/query";
    //周票房查询接口
    public static final String BOX_OFFICE_WEEK_QUERY = "http://apicloud.mob.com/boxoffice/week/query";
    //周末票房查询接口
    public static final String BOX_OFFICE_WEEKEND_QUERY = "http://apicloud.mob.com/boxoffice/weekend/query";
    //成语查询接口
    public static final String PHRASE_QUERY = "http://apicloud.mob.com/appstore/idiom/query";
    //汽车品牌查询接口
    public static final String CAR_BRAND_QUERY = "http://apicloud.mob.com/car/brand/query";
    //车型信息查询接口
    public static final String CAR_TYPE_QUERY = "http://apicloud.mob.com/car/seriesname/query";
    //车型详细信息查询查询接口
    public static final String CAR_DETAIL_QUERY = "http://apicloud.mob.com/car/series/query";


    //项目存储基路径
    public static final String BaseDir = Environment.getExternalStorageDirectory()
            + File.separator + "Android" + File.separator + "data" + File.separator
            + BuildConfig.APPLICATION_ID;

    //头像截取临时文件名称
    public static final String AvatarTempPic = Constants.BaseDir + File.separator + "clip_temp.jpg";
    //更新apk下载存储路径
    public static final String DOWNLOAD_APK_PATH = Constants.BaseDir + File.separator + "files" + File.separator + "apk";

}
