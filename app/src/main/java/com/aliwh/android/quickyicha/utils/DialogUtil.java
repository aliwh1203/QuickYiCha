package com.aliwh.android.quickyicha.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.aliwh.android.quickyicha.R;


/**
 * Dialog帮助类
 *
 * @author Kevin
 * @包名 com.cmsz.android.baymax.util
 * @时间 2015-11-13下午1:08:40
 */
public class DialogUtil {
    private static Dialog mDialog = null;

    public static void showRequestDialog(Context context, String msg) {
        // 实例化一个Dialog对象
        try {
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }
        } catch (Exception e) {
        }
        mDialog = creatRequestDialog(context, msg);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    public static void closeRequestDialog() {
        if (mDialog != null) {
            try {
                mDialog.dismiss();
                mDialog = null;
            } catch (Exception e) {
            }
        }
    }

    public static Dialog creatRequestDialog(final Context context, String msg) {
//        Dialog loadingDialog = new Dialog(context, R.style.MyDialogStyle);// 创建自定义样式dialog
//        loadingDialog.setContentView(R.layout.customprogressdialog);
//        loadingDialog.setCancelable(true); // 是否可以按“返回键”消失
//        loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
//        /**
//         *将显示Dialog的方法封装在这里面
//         */
//        Window window = loadingDialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        int width = ScreenUtils.getScreenWidth(context);
//        lp.width = (int) (0.6 * width);
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setGravity(Gravity.CENTER);
//        window.setAttributes(lp);
//        window.setWindowAnimations(R.style.PopWindowAnimStyle);
//        loadingDialog.show();
//        TextView tipTextView = (TextView) loadingDialog.findViewById(R.id.tipTextView);// 提示文字
//        if (msg == null || msg.length() == 0) {
//            tipTextView.setText("正在发送请求...");
//        } else {
//            tipTextView.setText(msg);
//        }
//        return loadingDialog;
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_loading_layout);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        int width = ScreenUtils.getScreenWidth(context);
        lp.width = (int) (0.6 * width);
//        ImageView imageView = (ImageView) dialog
//                .findViewById(R.id.loadingImageView);
        TextView titleTxtv = (TextView) dialog
                .findViewById(R.id.id_tv_loadingmsg);
//        AnimationDrawable animationDrawable = (AnimationDrawable) imageView
//                .getBackground();
//        animationDrawable.start();
        if (msg == null || msg.length() == 0) {
            titleTxtv.setText("正在发送请求...");
        } else {
            titleTxtv.setText(msg);
        }
        return dialog;
    }

}
