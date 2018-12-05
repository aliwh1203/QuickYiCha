package com.aliwh.android.quickyicha.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aliwh.android.quickyicha.MyApplication;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.dao.UserDB;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.DoubleClickUtil;
import com.aliwh.android.quickyicha.utils.SharePreferenceUtil;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.aliwh.android.quickyicha.view.ClearEditText;
import com.mob.jimu.biz.ReadWriteProperty;
import com.mob.ums.OperationCallback;
import com.mob.ums.SocialNetwork;
import com.mob.ums.UMSSDK;
import com.mob.ums.User;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.text.SimpleDateFormat;


/**
 * 登录界面
 *
 * @author Kevin
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button mLogin_btn;//登录按钮

    private ClearEditText mUser;//用户名输入框

    private ClearEditText mPassword;//密码输入框

    private TextView mRegister;//注册新用户

    private TextView mForget;//忘记密码

    private boolean user_success = false;
    private boolean password_success = false;
    private String login_name;
    private String login_password;
    private SharePreferenceUtil mSpUtil;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpUtil = MyApplication.getInstance().sharePreference;
        initView();
    }

    private void initView() {
        mUser = (ClearEditText) findViewById(R.id.login_user_input);
        mPassword = (ClearEditText) findViewById(R.id.login_password_input);
        mLogin_btn = (Button) findViewById(R.id.login_btn);
        mRegister = (TextView) findViewById(R.id.login_register);
        mForget = (TextView) findViewById(R.id.login_forget);


        mLogin_btn.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mForget.setOnClickListener(this);

        mUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                user_state();
                setClickState();
            }
        });
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                password_state();
                setClickState();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击登录按钮
            case R.id.login_btn:
                if (!DoubleClickUtil.isFastDoubleClick()) {
                    login_name = mUser.getText().toString().trim();
                    login_password = mPassword.getText().toString().trim();
                    user_state();
                    password_state();
                    setClickState();
                    if (user_success && password_success) {
                        loginOpt(login_name, login_password);
                    }
                }
                break;
            //新用户注册
            case R.id.login_register:
                Intent loginIntent = new Intent();
                loginIntent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(loginIntent);
                break;
            //忘记密码
            case R.id.login_forget:
                Intent forgetIntent = new Intent();
                forgetIntent.setClass(LoginActivity.this, FindPasswordActivity.class);
                startActivity(forgetIntent);
                break;
            default:
                break;
        }

    }


    @Event(R.id.qq_login_btn)
    private void onQQLogin(View view) {
        DialogUtil.showRequestDialog(LoginActivity.this, "加载中...");
        UMSSDK.loginWithSocialAccount(SocialNetwork.QQ, new OperationCallback<User>() {
            @Override
            public void onSuccess(User user) {
                super.onSuccess(user);
                Log.e("LoginActivity", "onSuccess11111: " + user.toString());
                DialogUtil.closeRequestDialog();
                mSpUtil.setIsOnline(true);
                saveUserData(user);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
                ToastUtil.showToast(LoginActivity.this, getResources().getString(R.string.login_success));
            }

            @Override
            public void onFailed(Throwable throwable) {
                super.onFailed(throwable);
                Log.e("LoginActivity", "onFailed: " + throwable.getMessage());
            }

            @Override
            public void onCancel() {
                super.onCancel();
                DialogUtil.closeRequestDialog();
            }
        });
    }

    @Event(R.id.weixin_login_btn)
    private void onWeiXinLogin(View v) {
        DialogUtil.showRequestDialog(LoginActivity.this, "加载中...");
        UMSSDK.loginWithSocialAccount(SocialNetwork.WECHAT, new OperationCallback<User>() {
            @Override
            public void onSuccess(User user) {
                super.onSuccess(user);
                Log.e("LoginActivity", "onSuccess11111: " + user.toString());
                DialogUtil.closeRequestDialog();
                mSpUtil.setIsOnline(true);
                saveUserData(user);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
                ToastUtil.showToast(LoginActivity.this, getResources().getString(R.string.login_success));
            }

            @Override
            public void onFailed(Throwable throwable) {
                super.onFailed(throwable);
                Log.e("LoginActivity", "onFailed: " + throwable.getMessage());
            }

            @Override
            public void onCancel() {
                super.onCancel();
                DialogUtil.closeRequestDialog();
            }
        });
    }

    private void loginOpt(String phoneNumber, String password) {
        DialogUtil.showRequestDialog(LoginActivity.this, "登录中...");
        // 执行登录
        UMSSDK.loginWithPhoneNumber("86", phoneNumber, password, new OperationCallback<User>() {
            @Override
            public void onSuccess(User user) {
                // 执行成功的操作
                Log.e("LoginActivity", "onSuccess: " + user.toString());
                DialogUtil.closeRequestDialog();
                //存取用户名
                mSpUtil.setLoginName(login_name);
                mSpUtil.setLoginPassword(login_password);
                mSpUtil.setIsOnline(true);
                saveUserData(user);
                DialogUtil.closeRequestDialog();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
                ToastUtil.showToast(LoginActivity.this, getResources().getString(R.string.login_success));
            }

            @Override
            public void onCancel() {
                // 执行取消的操作
                DialogUtil.closeRequestDialog();
            }

            @Override
            public void onFailed(Throwable t) {
                // 提示错误信息
                Log.e("LoginActivity", "onFailed: " + t);
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(LoginActivity.this, t.getMessage());
            }
        });
    }

    private void saveUserData(User user) {
        ReadWriteProperty<String[]> avatars = user.avatar;
        String[] avatarArr = avatars.get();
        String touxiang = avatarArr[0];
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String loginAt = formatter.format(user.lastLogin.get());
        Log.e("LoginActivity", "onSuccess: " + loginAt.toString());
        Log.e("LoginActivity", "avatarId: " + user.avatarId);
        UserDB userDB = new UserDB(LoginActivity.this);
        userDB.delete();
        userDB.saveUserInfo(user, touxiang, loginAt);
    }

    // 当前用户名（手机号）输入状态
    public void user_state() {
        if (TextUtils.isEmpty(mUser.getText())) {
            mUser.setHint(R.string.findpwd_phone_null);
            user_success = false;
            mUser.setShakeAnimation();
        } else {
            user_success = true;
        }
    }

    // 当前密码输入状态
    public void password_state() {
        if (TextUtils.isEmpty(mPassword.getText())) {
            mPassword.setHint(R.string.findpwd_pwd_null);
            password_success = false;
            mPassword.setShakeAnimation();
        } else {
            password_success = true;
        }
    }

    /**
     * 设置button点击状态
     */
    public void setClickState() {
        if (user_success && password_success) {
            mLogin_btn.setBackgroundResource(R.drawable.submit_btn_bg);
        } else {
            mLogin_btn.setBackgroundResource(R.drawable.no_submit_btn_bg);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getRawX() > left && event.getRawX() < right && event.getRawY() > top && event.getRawY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 捕获返回键
     */
    @Override
    public void onBackPressed() {
        // 应用退到后台，这个方法是直接返回到主界面
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
