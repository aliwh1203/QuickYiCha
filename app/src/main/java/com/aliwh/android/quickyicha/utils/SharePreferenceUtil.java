package com.aliwh.android.quickyicha.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 持久化数据保存SharePreference
 *
 * @author Kevin
 * 2015-10-31上午9:44:23
 */
public class SharePreferenceUtil {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public SharePreferenceUtil(Context context, String file) {
        sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    // 第一次使用apk状态存取
    public void setIsFirst(boolean IsFirst) {
        editor.putBoolean("ISFIRST", IsFirst);
        editor.commit();
    }

    public boolean getIsFirst() {
        return sp.getBoolean("ISFIRST", true);
    }

    // 登录获取的token存取
    public void setToken(String token) {
        editor.putString("TOKEN", token);
        editor.commit();
    }

    public String getToken() {
        return sp.getString("TOKEN", "");
    }


    // 用户登录名
    public String getLoginName() {
        return sp.getString("loginName", "");
    }

    public void setLoginName(String loginName) {
        editor.putString("loginName", loginName);
        editor.commit();
    }

    /**
     * 保存用户登录密码
     *
     * @return
     */
    public String getLoginPassword() {
        return sp.getString("loginPassword", "");
    }

    public void setLoginPassword(String loginPassword) {
        editor.putString("loginPassword", loginPassword);
        editor.commit();
    }

    // 在线状态存取
    public void setIsOnline(boolean isonline) {
        editor.putBoolean("ISONLINE", isonline);
        editor.commit();
    }

    public boolean getIsOline() {
        return sp.getBoolean("ISONLINE", false);
    }

    /**
     * 未读消息条数
     *
     * @param count
     */
    public void setUnreadMsgCountTotal(int count) {
        editor.putInt("COUNT", count);
        editor.commit();
    }

    public int getUnreadMsgCountTotal() {
        return sp.getInt("COUNT", 0);
    }

    /**
     * 是否开启免打扰
     */
    public void setIsAvoidDisturb(boolean b) {
        editor.putBoolean("AVOIDDISTURB", b);
        editor.commit();
    }

    public boolean getIsAvoidDisturb() {
        return sp.getBoolean("AVOIDDISTURB", false);
    }

    /**
     * 保存免打扰开始时间(小时)
     *
     * @return
     */
    public void setStartAvoidDisturbHour(int startHourTime) {
        editor.putInt("STARTHOURTIME", startHourTime);
        editor.commit();
    }

    public int getStartAvoidDisturbHour() {
        return sp.getInt("STARTHOURTIME", 23);
    }

    /**
     * 保存免打扰开始时间(分钟)
     *
     * @param startMinuteTime
     */
    public void setStartAvoidDisturbMinute(int startMinuteTime) {
        editor.putInt("STARTMINUTETIME", startMinuteTime);
        editor.commit();
    }

    public int getStartAvoidDisturbMinute() {
        return sp.getInt("STARTMINUTETIME", 0);
    }

    /**
     * 保存免打扰结束时间(小时)
     *
     * @return
     */
    public void setStopAvoidDisturbHour(int stopHourTime) {
        editor.putInt("STOPHOURTIME", stopHourTime);
        editor.commit();
    }

    public int getStopAvoidDisturbHour() {
        return sp.getInt("STOPHOURTIME", 8);
    }

    /**
     * 保存免打扰结束时间(分钟)
     */
    public void setStopAvoidDisturbMinute(int stopMinuteTime) {
        editor.putInt("STOPMINUTETIME", stopMinuteTime);
        editor.commit();
    }

    public int getStopAvoidDisturbMinute() {
        return sp.getInt("STOPMINUTETIME", 0);
    }

    /**
     * 是否在免打扰模式中
     */
    public void setTimeInAvoidDisturb(boolean b) {
        editor.putBoolean("TimeInAvoidDisturb", b);
        editor.commit();
    }

    public boolean getTimeInAvoidDisturb() {
        return sp.getBoolean("TimeInAvoidDisturb", false);
    }


    /**
     * 保存是否设置兴趣标签
     *
     * @param isSet
     */
    public void setInterestLabel(boolean isSet) {
        editor.putBoolean("InterestLabel", isSet);
        editor.commit();
    }

    public boolean haveSetInterestLabel() {
        return sp.getBoolean("InterestLabel", false);
    }

}
