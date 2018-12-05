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
 * 个人信息页面修改昵称页面
 */
@ContentView(R.layout.activity_update_name)
public class UpdateNameActivity extends BaseActivity {
    @ViewInject(R.id.nick_name)
    private EditText nick_name;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nickname = getIntent().getStringExtra("nickname");
        if (!TextUtils.isEmpty(nickname)) {
            nick_name.setText(nickname);
            nick_name.setSelection(nickname.length());
        }

    }

    @Event(R.id.submit_update_name)
    private void submitUpdate(View v) {
        final String nickNameStr = nick_name.getText().toString().trim();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("nickname", nickNameStr);
        DialogUtil.showRequestDialog(UpdateNameActivity.this, "加载中...");
        // 执行更新操作
        UMSSDK.updateUserInfo(map, new OperationCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                super.onSuccess(aVoid);
                Log.e("Activity", "onSuccess:昵称");
                UserDB userDB = new UserDB(UpdateNameActivity.this);
                userDB.updateNickName(nickNameStr);
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(UpdateNameActivity.this, "昵称修改成功");
                UpdateNameActivity.this.finish();
            }

            @Override
            public void onFailed(Throwable throwable) {
                super.onFailed(throwable);
                DialogUtil.closeRequestDialog();
                ToastUtil.showToast(UpdateNameActivity.this, "昵称修改失败");
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
