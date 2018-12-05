package com.aliwh.android.quickyicha.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.aliwh.android.quickyicha.MyApplication;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.DoubleClickUtil;
import com.aliwh.android.quickyicha.utils.RegexUtils;
import com.aliwh.android.quickyicha.utils.SharePreferenceUtil;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.aliwh.android.quickyicha.view.CircleImageView;
import com.aliwh.android.quickyicha.view.ClearEditText;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;
import com.mob.ums.datatype.Gender;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Calendar;
import java.util.HashMap;


/**
 * 填写注册资料页面
 *
 * @author Kevin
 */
@ContentView(R.layout.activity_register_data)
public class RegisterDataActivity extends BaseActivity {

    private static final int CITY_CHOICE = 0x1001;

    @ViewInject(R.id.registerdata_email)
    private ClearEditText mEmail;

    @ViewInject(R.id.registerdata_sex_tv)
    private TextView mSex;

    @ViewInject(R.id.registerdata_name)
    private ClearEditText mName;

    @ViewInject(R.id.registerdata_birthday_tv)
    private TextView mBirthday;

    @ViewInject(R.id.registerdata_city_tv)
    private TextView mCity;

    @ViewInject(R.id.registerdata_set_btn)
    private Button mSet;

    private Gender sex = null;
    private int age = 0;
    private String city;
    private String birthday;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getIntent().getStringExtra("userId");
        setTextChangedListener();
    }


    @Event(R.id.registerdata_sex_tv)
    private void onRegisterSex(View view) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            final String[] arraySex = new String[]{"男", "女", "保密"};
            Dialog alertDialog = new AlertDialog.Builder(this).setItems(
                    arraySex, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            switch (which) {
                                case 0:
                                    sex = Gender.valueOf(1);
                                    break;
                                case 1:
                                    sex = Gender.valueOf(2);
                                    break;
                                case 2:
                                    sex = Gender.valueOf(3);
                                    break;
                            }
                            mSex.setText(arraySex[which]);
                        }
                    }).create();
            alertDialog.show();
        }
    }

    @Event(R.id.registerdata_birthday_tv)
    private void showDateDialog(View view) {
        Calendar calendar = Calendar.getInstance();
        final int thisYear = calendar.get(Calendar.YEAR);
        final int thisMonth = calendar.get(Calendar.MONTH);
        final int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
        Dialog dialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String month;
                        String day;
                        if ((monthOfYear + 1) < 10) {
                            month = "0" + (monthOfYear + 1);
                        } else {
                            month = "" + (monthOfYear + 1);
                        }
                        if (dayOfMonth < 10) {
                            day = "0" + dayOfMonth;
                        } else {
                            day = "" + dayOfMonth;
                        }
                        birthday = year + "-" + month + "-" + day;
                        //年份相同都为0岁
                        if (thisYear > year) {
                            if (thisMonth < monthOfYear) {
                                age = thisYear - year - 1;
                                System.out.println("230:" + age);
                            } else if (thisMonth > monthOfYear) {
                                age = thisYear - year;
                                System.out.println("232:" + age);
                            } else if (thisMonth == monthOfYear) {
                                if (thisDay >= dayOfMonth) {
                                    age = thisYear - year;
                                    System.out.println("235:" + age);
                                } else {
                                    age = thisYear - year - 1;
                                    System.out.println("238:" + age);
                                }
                            }
                        } else {
                            age = 0;
                        }
                        mBirthday.setText(birthday);

                    }
                }, thisYear, thisMonth, thisDay);// 表示默认的年月日
        if (DoubleClickUtil.isFastDoubleClick()) {
            return;
        } else {
            dialog.show();
        }
    }

    @Event(R.id.registerdata_city_tv)
    private void onCityChoice(View view) {
        Intent intent = new Intent();
        intent.setClass(RegisterDataActivity.this, CityChoiceActivity.class);
        startActivityForResult(intent, CITY_CHOICE);
    }

    @Event(R.id.registerdata_set_btn)
    private void onRegisterSet(View view) {
        if (!DoubleClickUtil.isFastDoubleClick()) {
            if (TextUtils.isEmpty(mName.getText().toString())) {
                mName.setHint("昵称不能为空");
                mName.setShakeAnimation();
            } else {
                setData();
            }
        }
    }

    @Event(R.id.registerdata_skip)
    private void onSkip(View view) {
        Intent intent = new Intent();
        intent.setClass(RegisterDataActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    // 设置setTextChangedListener监听事件
    public void setTextChangedListener() {
        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkName();
            }
        });
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkEmail();
            }
        });
    }


    public void checkEmail() {
        if (!RegexUtils.isEmail(mEmail.getText().toString())) {
            mEmail.setTextColor(Color.RED);
        } else {
            mEmail.setTextColor(Color.BLACK);
        }
    }

    public void checkName() {
        if (TextUtils.isEmpty(mName.getText().toString())) {
            mName.setHint("昵称不能为空");
            mName.setShakeAnimation();
        }
    }

    // 设置数据提交更新操作
    public void setData() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", userId);
        map.put("email", mEmail.getText().toString());
        map.put("nickname", mName.getText().toString());
        map.put("gender", sex);
        map.put("age", age);

        DialogUtil.showRequestDialog(RegisterDataActivity.this, "正在加载中...");
        // 更新操作
        UMSSDK.updateUserInfo(map, new OperationCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                super.onSuccess(aVoid);
//                UserDB userDB = new UserDB(RegisterDataActivity.this);
//                userDB.updateSex(choicedSex.code());
//                user_sex.setText(sexString);
                DialogUtil.closeRequestDialog();
                Intent intent = new Intent();
                intent.setClass(RegisterDataActivity.this, LoginActivity.class);
                startActivity(intent);
                ToastUtil.showToast(RegisterDataActivity.this, "个人信息设置成功");
                finish();
            }

            @Override
            public void onFailed(Throwable throwable) {
                super.onFailed(throwable);
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(RegisterDataActivity.this, "个人信息设置失败" + throwable.getMessage());
            }

            @Override
            public void onCancel() {
                super.onCancel();
                DialogUtil.closeRequestDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CITY_CHOICE:
                if (data != null) {
                    Bundle b = data.getExtras();
                    city = b.getString("city");
                    mCity.setText(city);
                }
                break;
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
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom) {
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
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
