package com.aliwh.android.quickyicha.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;

import com.aliwh.android.quickyicha.BuildConfig;
import com.aliwh.android.quickyicha.Constants;
import com.aliwh.android.quickyicha.R;
import com.aliwh.android.quickyicha.dao.UserDB;
import com.aliwh.android.quickyicha.module.clipheadphoto.ClipImageActivity;
import com.aliwh.android.quickyicha.utils.CleanMessageUtil;
import com.aliwh.android.quickyicha.utils.DialogUtil;
import com.aliwh.android.quickyicha.utils.DoubleClickUtil;
import com.aliwh.android.quickyicha.utils.PermissionHelper;
import com.aliwh.android.quickyicha.utils.PictureUtils;
import com.aliwh.android.quickyicha.utils.ToastUtil;
import com.aliwh.android.quickyicha.view.CircleImageView;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;
import com.mob.ums.datatype.Gender;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.HashMap;

@ContentView(R.layout.activity_user_info) //加载布局
public class UserInfoActivity extends BaseActivity {

    private static final int PHOTO_ALBUM = 1;
    private static final int PHOTO_CAREMA = 2;
    private static final int PHOTO_CUT = 3;
    private static final int ADDRESS_MSG = 4;

    public static final String RESULT_PATH = "result_path";
    public static final String PASS_PATH = "pass_path";

    @ViewInject(R.id.user_touxiang)
    private CircleImageView user_touxiang;

    @ViewInject(R.id.user_nick_name)
    private TextView user_nick_name;

    @ViewInject(R.id.user_phone)
    private TextView user_phone;

    @ViewInject(R.id.user_email)
    private TextView user_email;

    @ViewInject(R.id.user_age)
    private TextView user_age;

    @ViewInject(R.id.user_city)
    private TextView user_city;

    @ViewInject(R.id.user_sex)
    private TextView user_sex;

    private String picPath;// 头像图片存储路径

    private UserDB userDB;
    private Dialog dialog;
    private Gender choicedSex;
    private String sexString;
    private PermissionHelper permissionHelper;
    //拍照的临时文件
    private Uri mTakePhotoUri;
    private int age = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionHelper = new PermissionHelper(this);
        userDB = new UserDB(UserInfoActivity.this);
        x.image().bind(user_touxiang, userDB.getUserAvatar());

    }

    private void showData() {
        if (!TextUtils.isEmpty(userDB.getNickName())) {
            user_nick_name.setText(userDB.getNickName());
        }

        if (!TextUtils.isEmpty(userDB.getUserPhone())) {
            user_phone.setText(userDB.getUserPhone());
        }

        if (!TextUtils.isEmpty(userDB.getUserEmail())) {
            user_email.setText(userDB.getUserEmail());
        } else {
            user_email.setText(Html.fromHtml("<font color = '#cccccc'>[暂无]</font>"));
        }

        int setFlag = userDB.getUserSex();
        if (setFlag != 0) {
            choicedSex = Gender.valueOf(setFlag);
            if (choicedSex.equals(Gender.Male.INSTANCE)) {
                user_sex.setText("男");
            } else if (choicedSex.equals(Gender.Female.INSTANCE)) {
                user_sex.setText("女");
            } else {
                user_sex.setText("保密");
            }
        }

        user_age.setText(userDB.getUserAge() + "");

        if (!TextUtils.isEmpty(userDB.getUserAddress())) {
            user_city.setText(userDB.getUserAddress());
        }
    }

    @Event(R.id.user_info_img)
    private void changePhoto(View v) {
        showHeadChoiceDialog();
    }

    @Event(R.id.user_info_name)
    private void updateName(View v) {
        Intent intent = new Intent();
        intent.setClass(UserInfoActivity.this, UpdateNameActivity.class);
        intent.putExtra("nickname", userDB.getNickName());
        startActivity(intent);
    }


    @Event(R.id.user_info_email)
    private void updateEmail(View v) {
        Intent intent = new Intent();
        intent.setClass(UserInfoActivity.this, UpdateEmailActivity.class);
        intent.putExtra("email", userDB.getUserEmail());
        startActivity(intent);
    }

    @Event(R.id.user_info_sex)
    private void updateSex(View v) {
        showSexChoice();
    }

    @Event(R.id.user_info_phone)
    private void updatePhone(View v) {
        if (TextUtils.isEmpty(userDB.getUserPhone())) {
            Intent intent = new Intent();
            intent.setClass(UserInfoActivity.this, BindPhoneActivity.class);
            startActivity(intent);
        } else {

        }
    }

    @Event(R.id.user_info_age)
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
                        final String birthday = year + "-" + month + "-" + day;
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

                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("age", age);
                        // 执行更新
                        UMSSDK.updateUserInfo(map, new OperationCallback<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                super.onSuccess(aVoid);
                                Log.e("Activity", "onSuccess:年龄");
                                UserDB userDB = new UserDB(UserInfoActivity.this);
                                userDB.updateAge(age);
//                                user_age.setText(birthday);
                                user_age.setText(age + "");
                                ToastUtil.showToast(UserInfoActivity.this, "年龄修改成功");
                            }

                            @Override
                            public void onFailed(Throwable throwable) {
                                super.onFailed(throwable);
                                ToastUtil.showToast(UserInfoActivity.this, "年龄修改失败");
                                Log.e("Activity", "onFailed:年龄" + throwable.getMessage());
                            }

                            @Override
                            public void onCancel() {
                                super.onCancel();
                            }
                        });
                    }
                }, thisYear, thisMonth, thisDay);// 表示默认的年月日
        if (DoubleClickUtil.isFastDoubleClick()) {
            return;
        } else {
            dialog.show();
        }
    }

    @Event(R.id.user_info_city)
    private void updateCity(View v) {
        Intent intent = new Intent();
        intent.setClass(UserInfoActivity.this, CityChoiceActivity.class);
        startActivityForResult(intent, ADDRESS_MSG);
    }

    @Event(R.id.back_btn)
    private void onBack(View view) {
        this.finish();
    }


    private void showHeadChoiceDialog() {
        View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog,
                null);
        dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        wl.x = 0;
        wl.y = size.y;
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = LayoutParams.MATCH_PARENT;
        wl.height = LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围取消
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void on_click(View v) {
        switch (v.getId()) {
            case R.id.openCamera:
                camera();// 用户点击了从照相机获取
                break;
            case R.id.openPhones:
                gallery();// 从相册中去获取
                break;
            case R.id.cancel:
                dialog.cancel();
                break;
            default:
                break;
        }
    }

    /*
     * 从相册获取
     */
    private void gallery() {
        permissionHelper.requestPermissions("需要读取SD卡的权限", new PermissionHelper.PermissionListener() {
            @Override
            public void doAfterGrand(String... permissions) {
                Intent intentFromGallery;
                if (Build.VERSION.SDK_INT >= 19) { // 判断是不是4.4
                    intentFromGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                } else {
                    intentFromGallery = new Intent(Intent.ACTION_GET_CONTENT);
                }
                intentFromGallery.setType("image/*"); // 设置文件类型
                startActivityForResult(intentFromGallery, PHOTO_ALBUM);
            }

            @Override
            public void doAfterDenied(String... permissions) {
                permissionHelper.openPermissionsSetting("需要相应的权限, 前往设置>权限页面打开对应权限");
            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE);
        dialog.dismiss();
    }

    /*
     * 从拍照获取
     */
    private void camera() {
        permissionHelper.requestPermissions("需要拍照和读取SD卡的权限", new PermissionHelper.PermissionListener() {
            @Override
            public void doAfterGrand(String... permissions) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    //拍照后图片存储位置
                    File mTmpFile = PictureUtils.createImageFile(getExternalFilesDir("avatar").getPath());
                    if (Build.VERSION.SDK_INT < 24) {
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT
                                , Uri.fromFile(mTmpFile));
                        startActivityForResult(cameraIntent, PHOTO_CAREMA);
                    } else {
                        //适配安卓7.0
                        ContentValues contentValues = new ContentValues(1);
                        contentValues.put(MediaStore.Images.Media.DATA,
                                mTmpFile.getAbsolutePath());
                        mTakePhotoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                        grantUriPermission(BuildConfig.APPLICATION_ID, mTakePhotoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mTakePhotoUri);
                        startActivityForResult(cameraIntent, PHOTO_CAREMA);
                    }

                }
            }

            @Override
            public void doAfterDenied(String... permissions) {
                permissionHelper.openPermissionsSetting("需要相应的权限, 前往设置>权限页面打开对应权限");
            }
        }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        dialog.dismiss();
    }

    //将权限交给permissionhelper处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PHOTO_CUT:
                picPath = data.getStringExtra(RESULT_PATH);
                DialogUtil.showRequestDialog(UserInfoActivity.this, "加载中...");
                //在此处来做图片的上传处理
                UMSSDK.uploadAvatar(picPath, new OperationCallback<HashMap<String, Object>>() {
                    @Override
                    public void onSuccess(HashMap<String, Object> stringObjectHashMap) {
                        super.onSuccess(stringObjectHashMap);
                        Log.e("Activity", "onSuccess: " + stringObjectHashMap.toString());
                        String avatarId = (String) stringObjectHashMap.get("id");
                        final String avatar = (String) stringObjectHashMap.get("avatar");
                        HashMap<String, Object> uploadAvatarMap = new HashMap<String, Object>();
                        uploadAvatarMap.put("avatarId", avatarId);
                        uploadAvatarMap.put("avatar", new String[]{avatar});
                        // 执行登录
                        UMSSDK.updateUserInfo(uploadAvatarMap, new OperationCallback<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                super.onSuccess(aVoid);
                                UserDB userDB = new UserDB(UserInfoActivity.this);
                                userDB.updateUserAvatar(avatar);
                                Log.e("Activity", "onSuccess: " + avatar);
                                x.image().bind(user_touxiang, userDB.getUserAvatar());
                                //将头像临时文件删除
                                CleanMessageUtil.deleteFileByPath(Constants.AvatarTempPic);
                                DialogUtil.closeRequestDialog();
                                ToastUtil.showToast(UserInfoActivity.this, "头像修改成功");
                            }

                            @Override
                            public void onFailed(Throwable throwable) {
                                super.onFailed(throwable);
                                DialogUtil.closeRequestDialog();
                                ToastUtil.showToast(UserInfoActivity.this, "头像修改失败" + throwable.getMessage());
                                Log.e("onActivityResult", "onActivityResult: " + throwable.getMessage());
                            }

                            @Override
                            public void onCancel() {
                                super.onCancel();
                                DialogUtil.closeRequestDialog();
                            }
                        });

                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        super.onFailed(throwable);
                        DialogUtil.closeRequestDialog();
                        ToastUtil.showToast(UserInfoActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();
                        DialogUtil.closeRequestDialog();
                    }
                });
                break;
            case PHOTO_ALBUM:
                startCropImageActivity(getFilePath(data.getData()));
                break;
            case PHOTO_CAREMA:
                // 照相机程序返回的,再次调用图片剪辑程序去修剪图片
                startCropImageActivity(getFilePath(mTakePhotoUri));
                break;
            case ADDRESS_MSG:
                final String choiceCity = data.getStringExtra("city");
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("addr", choiceCity);
                // 执行登录
                UMSSDK.updateUserInfo(map, new OperationCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        super.onSuccess(aVoid);
                        UserDB userDB = new UserDB(UserInfoActivity.this);
                        userDB.updateUserAddress(choiceCity);
                        user_city.setText(choiceCity);
                        ToastUtil.showToast(UserInfoActivity.this, "城市修改成功");
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        super.onFailed(throwable);
                        ToastUtil.showToast(UserInfoActivity.this, "城市修改失败");
                        ToastUtil.showToast(UserInfoActivity.this, throwable.getMessage());
                        Log.e("onActivityResult", "onActivityResult: " + throwable.getMessage());
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();
                        DialogUtil.closeRequestDialog();
                    }
                });

        }
    }

    private void startCropImageActivity(String path) {
        Intent intent = new Intent(this, ClipImageActivity.class);
        intent.putExtra(PASS_PATH, path);
        startActivityForResult(intent, PHOTO_CUT);
    }

    /**
     * 通过uri获取文件路径
     *
     * @param mUri
     * @return
     */
    private String getFilePath(Uri mUri) {
        try {
            if (mUri.getScheme().equals("file")) {
                return mUri.getPath();
            } else {
                return getFilePathByUri(mUri);
            }
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    // 获取文件路径通过url
    private String getFilePathByUri(Uri mUri) throws FileNotFoundException {
        Cursor cursor = getContentResolver().query(mUri, null, null, null, null);
        cursor.moveToFirst();
        return cursor.getString(1);
    }


    public void showSexChoice() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                new ContextThemeWrapper(UserInfoActivity.this, R.style.Theme_Transparent));
        builder.setTitle("请选择性别");
        final String[] sex = {"男", "女", "保密"};
        int checkedItem = 0;
        int setFlag = userDB.getUserSex();
        if (setFlag != 0) {
            choicedSex = Gender.valueOf(setFlag);
            if (choicedSex.equals(Gender.Male.INSTANCE)) {
                checkedItem = 0;
                sexString = "男";
            } else if (choicedSex.equals(Gender.Female.INSTANCE)) {
                checkedItem = 1;
                sexString = "女";
            } else {
                checkedItem = 2;
                sexString = "保密";
            }
        }

        // 设置一个单项选择下拉框
        /**
         * 第一个参数指定我们要显示的一组下拉单选框的数据集合 第二个参数代表索引，指定默认哪一个单选框被勾选上，0表示默认'男' 会被勾选上
         * 第三个参数给每一个单选项绑定一个监听器
         */
        builder.setSingleChoiceItems(sex, checkedItem,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sexString = sex[which];
                        if (sexString.equals("男")) {
                            choicedSex = Gender.valueOf(1);
                        } else if (sexString.equals("女")) {
                            choicedSex = Gender.valueOf(2);
                        } else {
                            choicedSex = Gender.valueOf(3);
                        }
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("gender", choicedSex);
                // 执行更新
                UMSSDK.updateUserInfo(map, new OperationCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        super.onSuccess(aVoid);
                        Log.e("Activity", "onSuccess:性别");
                        UserDB userDB = new UserDB(UserInfoActivity.this);
                        userDB.updateSex(choicedSex.code());
                        user_sex.setText(sexString);
                        ToastUtil.showToast(UserInfoActivity.this, "性别修改成功");
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        super.onFailed(throwable);
                        ToastUtil.showToast(UserInfoActivity.this, "性别修改失败");
                        Log.e("Activity", "onSuccess:性别" + throwable.getMessage());
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();
                    }
                });


            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showData();
    }
}
