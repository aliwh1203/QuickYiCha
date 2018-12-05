# QuickYiCha
本APP应用集成xutils3.0、Gson库以及YunWangVideoSDK视频录制,生活应用查询服务，提供四个Tab模块
数据来源Mob开源数据
![效果图](https://github.com/1548055800/QuickYiCha/blob/master/picture/show_picture.jpg)

 compile fileTree(include: ['*.jar'], dir: 'libs')
 compile 'com.android.support:support-v4:27.1.1'
 compile 'com.android.support:appcompat-v7:27.1.1'
 compile 'com.android.support:design:27.1.1'
 compile 'com.android.support:multidex:1.0.0'
 //xutils框架
 compile 'org.xutils:xutils:3.4.0'
 compile 'com.belerweb:pinyin4j:2.5.1'
 //Gson库
 compile 'com.google.code.gson:gson:2.7'
 //智能下拉刷新框架-SmartRefreshLayout
 compile 'com.scwang.smartrefresh:SmartRefreshLayout:1.0.3'
 compile project(':YunWangVideoSDK')