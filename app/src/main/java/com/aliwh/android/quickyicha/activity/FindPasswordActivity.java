package com.aliwh.android.quickyicha.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.DoubleClickUtil;
import com.aliwh.android.quickyicha.utils.RegexUtils;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.aliwh.android.quickyicha.view.ClearEditText;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 找回密码页面
 *
 * @author Kevin
 */
@ContentView(R.layout.activity_find_password)
public class FindPasswordActivity extends BaseActivity {

    @ViewInject(R.id.findpwd_getverify_btn)
    private Button mGetVerify;

    @ViewInject(R.id.findpwd_confirm)
    private Button mConfirm;

    @ViewInject(R.id.findpwd_phone_input)
    private ClearEditText mPhone_Input;

    @ViewInject(R.id.findpwd_password_input)
    private ClearEditText mPassword_Input;

    @ViewInject(R.id.findpwd_password_last_input)
    private ClearEditText mPassword_last;

    @ViewInject(R.id.findpwd_verify_input)
    private ClearEditText mVerify_Input;


    private boolean phone_success = false;
    private boolean password_success = false;
    private boolean verify_noEmpty = false;
    private boolean pwd_last = false;
    public final int VERIFYTIME = 0x1001;
    public final int VERIFYSTATE = 0x1002;
    private boolean pwdError = true;


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

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTextChangedListener();
    }

    /**
     * 设置TextChangedListener事件
     */
    private void setTextChangedListener() {
        mPassword_last.addTextChangedListener(new TextWatcher() {
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

    @Event(R.id.findpwd_getverify_btn)
    private void getVerifyBtn(View view) {
        final String phoneTextString = mPhone_Input.getText().toString();
        if (!DoubleClickUtil.isFastDoubleClick()) {
            if (!RegexUtils.isMobile(phoneTextString)) {
                ToastUtil.showToast(this, getResources().getString(R.string.findpwd_phone_null_hint));
                return;
            }
            DialogUtil.showRequestDialog(FindPasswordActivity.this, "正在加载中...");
            UMSSDK.sendVerifyCodeForResetPassword("86", phoneTextString, new OperationCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    super.onSuccess(aBoolean);
                    countDown(); //开始倒计时
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(FindPasswordActivity.this, getResources().getString(R.string.register_verify_send));
                }

                @Override
                public void onFailed(Throwable throwable) {
                    super.onFailed(throwable);
                    mGetVerify.setText(getResources().getString(R.string.register_retry));
                    mGetVerify.setEnabled(true);
                    mGetVerify.setBackgroundResource(R.color.submit);
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(FindPasswordActivity.this, throwable.getMessage());
                }

                @Override
                public void onCancel() {
                    super.onCancel();
                    DialogUtil.closeRequestDialog();
                }
            });
        }
    }

    @Event(R.id.findpwd_confirm)
    private void findConfirmBtn(View view) {
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
        DialogUtil.showRequestDialog(FindPasswordActivity.this, getResources().getString(R.string.dialog_waiting));
        if (!TextUtils.isEmpty(mVerify_Input.getText().toString())) {
            UMSSDK.resetPasswordWithPhoneNumber("86", mPhone_Input.getText().toString(), mVerify_Input.getText().toString(), mPassword_last.getText().toString(), new OperationCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    super.onSuccess(aVoid);
                    DialogUtil.closeRequestDialog();
                    Intent intent = new Intent();
                    intent.setClass(FindPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailed(Throwable throwable) {
                    super.onFailed(throwable);
                    DialogUtil.closeRequestDialog();
                    ToastUtil.showToast(FindPasswordActivity.this, getResources().getString(R.string.findpwd_verify_error) + throwable.getMessage());
                }

                @Override
                public void onCancel() {
                    super.onCancel();
                    DialogUtil.closeRequestDialog();
                }
            });

        }
    }

    @Event(R.id.back_btn)
    private void onBack(View view) {
        this.finish();
    }

    /**
     * 验证手机号输入状态
     */
    public void checkPhoneState() {
        if (TextUtils.isEmpty(mPhone_Input.getText())) {
            mPhone_Input.setHint(R.string.findpwd_phone_null);
            phone_success = false;
            mPhone_Input.setShakeAnimation();
        } else {
            phone_success = true;
        }
        if (!RegexUtils.isMobile(mPhone_Input.getText().toString())) {
            mGetVerify.setBackgroundResource(R.drawable.no_submit_btn_bg);
        } else {
            mGetVerify.setBackgroundResource(R.drawable.submit_btn_bg);
        }
    }

    /**
     * 验证密码输入状态
     */
    public void checkPasswordState() {
        if (TextUtils.isEmpty(mPassword_Input.getText())) {
            mPassword_Input.setHint(R.string.findpwd_pwd_null);
            password_success = false;
            mPassword_Input.setShakeAnimation();
        } else {
            password_success = true;
        }
    }

    /**
     * 验证码输入状态
     */
    public void checkVerifyState() {
        if (TextUtils.isEmpty(mVerify_Input.getText())) {
            mVerify_Input.setHint(R.string.findpwd_verify_null);
            verify_noEmpty = false;
            pwdError = true;
            mVerify_Input.setShakeAnimation();
        } else {
            if (mPassword_Input.getText().length() < 6) {
                password_success = false;
                pwdError = true;
            } else {
                verify_noEmpty = true;
                pwdError = false;
            }
        }
    }

    /**
     * 确认输入状态
     */
    public void checklastPwdState() {
        if (TextUtils.isEmpty(mPassword_last.getText())) {
            mPassword_last.setHint(R.string.findpwd_pwd_null);
            pwd_last = false;
            mPassword_last.setShakeAnimation();
            mPassword_last.setTextColor(Color.BLACK);
        } else if (!TextUtils.equals(mPassword_Input.getText().toString(), mPassword_last.getText().toString())) {
            pwd_last = false;
            mPassword_last.setTextColor(Color.RED);
        } else {
            mPassword_last.setTextColor(Color.BLACK);
            pwd_last = true;
        }
    }

    /**
     * 设置button点击状态
     */
    public void setClickState() {
        if (phone_success && password_success && verify_noEmpty && pwd_last) {
            mConfirm.setBackgroundResource(R.drawable.submit_btn_bg);
        } else {
            mConfirm.setBackgroundResource(R.drawable.no_submit_btn_bg);
        }
    }

    /**
     * 倒计时
     */
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

}
