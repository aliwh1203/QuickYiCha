package com.aliwh.android.quickyicha.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.dao.UserDB;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * 个人信息页面修改邮箱页面
 */
@ContentView(R.layout.activity_update_email)
public class UpdateEmailActivity extends BaseActivity {
    @ViewInject(R.id.email)
    private EditText emailText;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = getIntent().getStringExtra("email");
        if (!TextUtils.isEmpty(email)) {
            emailText.setText(email);
            emailText.setSelection(email.length());  //将光标移动到文字末尾
        }
    }

    @Event(R.id.submit_update_email)
    private void submitUpdate(View v) {
        final String emailStr = emailText.getText().toString().trim();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("email", emailStr);
        DialogUtil.showRequestDialog(UpdateEmailActivity.this, "加载中...");
        // 执行更新操作
        UMSSDK.updateUserInfo(map, new OperationCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                super.onSuccess(aVoid);
                Log.e("Activity", "onSuccess:邮箱");
                UserDB userDB = new UserDB(UpdateEmailActivity.this);
                userDB.updateEmail(emailStr);
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(UpdateEmailActivity.this, "邮箱修改成功");
                UpdateEmailActivity.this.finish();
            }

            @Override
            public void onFailed(Throwable throwable) {
                super.onFailed(throwable);
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(UpdateEmailActivity.this, "邮箱修改失败");
            }

            @Override
            public void onCancel() {
                super.onCancel();
                DialogUtil.closeRequestDialog();
            }
        });
    }


    /**
     * 返回按钮
     */
    @Event(R.id.back_btn)
    private void onBack(View view) {
        this.finish();
    }
}
