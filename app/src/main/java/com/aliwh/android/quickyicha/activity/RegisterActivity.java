package com.aliwh.android.quickyicha.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.DoubleClickUtil;
import com.aliwh.android.quickyicha.utils.RegexUtils;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.aliwh.android.quickyicha.view.ClearEditText;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;
import com.mob.ums.User;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 注册页面
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    @ViewInject(R.id.register_getverify_btn)
    private Button mGetVerify;

    @ViewInject(R.id.register_btn)
    private Button mRegisterBtn;

    @ViewInject(R.id.register_phone_input)
    private ClearEditText mPhone_Input;

    @ViewInject(R.id.register_password_input)
    private ClearEditText mPassword_Input;

    @ViewInject(R.id.register_password_last_input)
    private ClearEditText mPassword_last_Input;

    @ViewInject(R.id.register_verify_input)
    private ClearEditText mVerify_Input;

    private boolean phone_success = false;
    private boolean password_success = false;
    private boolean verify_noEmpty = false;
    private boolean pwdError = true;
    private boolean pwd_last;
    public final int VERIFYTIME = 0x1001;
    public final int VERIFYSTATE = 0x1002;
    private String login_name;
    private String login_password;

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case VERIFYTIME:
                    int time = (Integer) msg.obj;
                    mGetVerify.setText("(" + time + ")" + getResources().getString(R.string.findpwd_time));
                    break;
                case VERIFYSTATE:
                    mGetVerify.setText(getResources().getString(R.string.register_retry));
                    mGetVerify.setEnabled(true);
                    mGetVerify.setBackgroundResource(R.color.submit);
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTextChangedListener();
    }

    // 设置TextChangedListener事件
    private void setTextChangedListener() {
        mPassword_last_Input.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checklastPwdState();
                setClickState();
            }
        });

        mPhone_Input.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkPhoneState();
                setClickState();
            }
        });
        mPassword_Input.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkPasswordState();
                setClickState();
            }
        });
        mVerify_Input.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkVerifyState();
                setClickState();
            }
        });
    }


    @Event(R.id.register_getverify_btn)
    private void OnGetVerifyBtn(View view) {
        final String phoneTextString = mPhone_Input.getText().toString();
        if (!DoubleClickUtil.isFastDoubleClick()) {
            if (!RegexUtils.isMobile(phoneTextString)) {
                ToastUtil.showToast(this, getResources().getString(R.string.findpwd_phone_null_hint));
                return;
            }
            DialogUtil.showRequestDialog(RegisterActivity.this, "正在加载中...");
            UMSSDK.sendVerifyCodeForRegitser("86", phoneTextString, new OperationCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    super.onSuccess(aBoolean);
                    countDown(); //开始倒计时
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(RegisterActivity.this, getResources().getString(R.string.register_verify_send));
                }

                @Override
                public void onFailed(Throwable throwable) {
                    super.onFailed(throwable);
                    mGetVerify.setText(getResources().getString(R.string.register_retry));
                    mGetVerify.setEnabled(true);
                    mGetVerify.setBackgroundResource(R.color.submit);
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(RegisterActivity.this, throwable.getMessage());
                }

                @Override
                public void onCancel() {
                    super.onCancel();
                    DialogUtil.closeRequestDialog();
                }
            });
        }
    }

    @Event(R.id.register_btn)
    private void OnNextBtn(View view) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            login_name = mPhone_Input.getText().toString().trim();
            login_password = mPassword_Input.getText().toString().trim();
            checkPasswordState();
            checkPhoneState();
            checkVerifyState();
            if (!phone_success) {
                ToastUtil.showToast(this, getResources().getString(R.string.findpwd_phone_null));
                return;
            } else if (pwdError) {
                ToastUtil.showToast(this, getResources().getString(R.string.register_pwd_length_error));
                return;
            } else if (!RegexUtils.isMobile(mPhone_Input.getText().toString())) {
                ToastUtil.showToast(this, getResources().getString(R.string.findpwd_phone_error));
                return;
            } else if (!verify_noEmpty) {
                ToastUtil.showToast(this, getResources().getString(R.string.findpwd_verify_null));
                return;
            } else if (!password_success) {
                ToastUtil.showToast(this, getResources().getString(R.string.findpwd_pwd_null));
                return;
            } else if (!pwd_last) {
                ToastUtil.showToast(this, getResources().getString(R.string.findpwd_pwd_error));
                return;
            }
            DialogUtil.showRequestDialog(RegisterActivity.this, getResources().getString(R.string.register_dailog));
            if (!TextUtils.isEmpty(mVerify_Input.getText().toString())) {
                UMSSDK.registerWithPhoneNumber("86", login_name, mVerify_Input.getText().toString(), login_password, new User(), new OperationCallback<User>() {
                    @Override
                    public void onSuccess(User user) {
                        super.onSuccess(user);
                        Log.e("RegisterActivity", "onSuccess: " + user.toString());
                        DialogUtil.closeRequestDialog();
                        Intent intent = new Intent();
                        intent.setClass(RegisterActivity.this, RegisterDataActivity.class);
                        intent.putExtra("userId", user.id.get());
                        startActivity(intent);
                        RegisterActivity.this.finish();
                        ToastUtil.showToast(RegisterActivity.this, getResources().getString(R.string.register_text_success));
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        super.onFailed(throwable);
                        Log.e("RegisterActivity", "onFailed: " + throwable);
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(RegisterActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();
                        DialogUtil.closeRequestDialog();
                    }
                });

            }
        }
    }

    @Event(R.id.back_btn)
    private void OnBack(View view) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            this.finish();
        }
    }


    // 验证手机号输入状态
    public void checkPhoneState() {
        if (!RegexUtils.isMobile(mPhone_Input.getText().toString())) {
            mGetVerify.setBackgroundResource(R.drawable.no_submit_btn_bg);
        } else {
            mGetVerify.setBackgroundResource(R.drawable.submit_btn_bg);
        }
        if (TextUtils.isEmpty(mPhone_Input.getText())) {
            mPhone_Input.setHint(R.string.findpwd_phone_null);
            phone_success = false;
            mPhone_Input.setShakeAnimation();
        } else {
            phone_success = true;
        }
    }

    // 验证密码输入状态
    public void checkPasswordState() {
        if (TextUtils.isEmpty(mPassword_Input.getText())) {
            mPassword_Input.setHint(R.string.findpwd_pwd_null);
            password_success = false;
            pwdError = true;
            mPassword_Input.setShakeAnimation();
        } else {
            if (mPassword_Input.getText().length() < 6) {
                password_success = false;
                pwdError = true;
            } else {
                password_success = true;
                pwdError = false;
            }
        }
    }

    // 验证再次输入的密码输入状态
    public void checklastPwdState() {
        if (TextUtils.isEmpty(mPassword_last_Input.getText())) {
            mPassword_last_Input.setHint(R.string.findpwd_pwd_null);
            pwd_last = false;
            mPassword_last_Input.setShakeAnimation();
            mPassword_last_Input.setTextColor(Color.BLACK);
        } else if (!TextUtils.equals(mPassword_Input.getText().toString(), mPassword_last_Input.getText().toString())) {
            pwd_last = false;
            mPassword_last_Input.setTextColor(Color.RED);
        } else {
            pwd_last = true;
            mPassword_last_Input.setTextColor(Color.BLACK);
        }
    }

    // 验证码输入状态
    public void checkVerifyState() {
        if (TextUtils.isEmpty(mVerify_Input.getText())) {
            mVerify_Input.setHint(R.string.findpwd_verify_null);
            verify_noEmpty = false;
            mVerify_Input.setShakeAnimation();
        } else {
            verify_noEmpty = true;
        }
    }

    /**
     * 设置button点击状态
     */
    public void setClickState() {
        if (phone_success && password_success && verify_noEmpty && pwd_last) {
            mRegisterBtn.setBackgroundResource(R.drawable.submit_btn_bg);
        } else {
            mRegisterBtn.setBackgroundResource(R.drawable.no_submit_btn_bg);
        }
    }


    // 倒计时
    public void countDown() {
        mGetVerify.setEnabled(false);
        mGetVerify.setBackgroundResource(R.color.not_submit);
        new Thread() {
            public void run() {
                for (int time = 60; time >= 0; time--) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = VERIFYTIME;
                    msg.obj = time;
                    mHandler.sendMessage(msg);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (time == 0) {
                        Message m = mHandler.obtainMessage();
                        m.what = VERIFYSTATE;
                        mHandler.sendMessage(m);
                    }
                }
            }

            ;
        }.start();
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

}
