package com.aliwh.android.quickyicha.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aliwh.android.quickyicha.BuildConfig;
import com.mob.ums.User;

import java.text.SimpleDateFormat;

public class UserDB {

    private SQLiteDatabase db;

    public UserDB(Context context) {
        db = DBHelper.getInstance(context).getWritableDatabase();
        createTable();
    }

    public void createTable() {
        String sql = "CREATE table IF NOT EXISTS user"
                + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, userId TEXT,followerNum INTEGER," +
                " eachFollowNum INTEGER,friendNum INTEGER, nickname TEXT,avatar TEXT,"
                + " phone TEXT,concernsNum INTEGER, snsUserId TEXT,loginAt TEXT,birthday TEXT,email TEXT,age INTEGER,sex INTEGER,address TEXT)";
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            Log.i("err", "create table failed");
        }
    }


    /**
     * 保存数据到数据表中
     *
     * @param user
     */
    public void saveUserInfo(User user, String touxiang, String loginAt) {
        String sql = "insert into user(userId,followerNum,eachFollowNum,friendNum,nickname,avatar,phone,concernsNum," +
                "snsUserId,loginAt,birthday,email,age,sex,address)"
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Log.e("allwhere", "user.gender: " + user.gender.get());
        Log.e("allwhere", "user.birthday: " + user.birthday.get());
        Log.e("allwhere", "user.email: " + user.email.get());
        Log.e("allwhere", "user.age: " + user.age.get());
        Log.e("allwhere", "user.address: " + user.address.get());
        String birthday = null;
        String email = null;
        String age = null;
        int sex = 3; //不知道默认就是 保密
        String address = null;
        String city = null;
        if (user.birthday.get() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            birthday = formatter.format(user.birthday.get());
        }
        if (user.email.get() != null) {
            email = user.email.get();
        }
        if (user.gender.get() != null) {
            sex = user.gender.get().code();
        }
        if (user.address.get() != null) {
            address = user.address.get();
        }

        db.execSQL(sql, new Object[]{user.id.get(), user.followings.get(),
                user.fans.get(), user.friends.get(),
                user.nickname.get(), touxiang, user.phone.get(),
                user.rFriends.get(), user.phone.get(), loginAt,
                birthday, email, user.age.get(), sex, address});

    }


    /**
     * 获取用户手机号
     *
     * @return
     */
    public String getUserPhone() {
        String phone = "";
        Cursor c = null;
        try {
            c = db.rawQuery("select * from user", null);
            while (c.moveToNext()) {
                phone = c.getString(c.getColumnIndex("phone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return phone;
    }

    /**
     * 获取用户头像
     *
     * @return
     */
    public String getUserAvatar() {
        String avatar = null;
        Cursor c = null;
        try {
            c = db.rawQuery("select * from user", null);
            while (c.moveToNext()) {
                avatar = c.getString(c.getColumnIndex("avatar"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return avatar;
    }

    /**
     * 获取用户昵称
     *
     * @return
     */
    public String getNickName() {
        String nickname = "";
        Cursor c = null;
        try {
            c = db.rawQuery("select * from user", null);
            while (c.moveToNext()) {
                nickname = c.getString(c.getColumnIndex("nickname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return nickname;
    }

    /**
     * 获取用户邮箱
     *
     * @return
     */
    public String getUserEmail() {
        String email = "";
        Cursor c = null;
        try {
            c = db.rawQuery("select * from user", null);
            while (c.moveToNext()) {
                email = c.getString(c.getColumnIndex("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return email;
    }

    /**
     * 获取用户id
     *
     * @return
     */
    public int getUserID() {
        int user_id = 0;
        Cursor c = null;
        try {
            c = db.rawQuery("select * from user", null);
            while (c.moveToNext()) {
                user_id = c.getInt(c.getColumnIndex("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return user_id;
    }


    /**
     * 获取用户性别
     *
     * @return
     */
    public int getUserSex() {
        int sex = 0;
        Cursor c = null;
        try {
            c = db.rawQuery("select * from user", null);
            while (c.moveToNext()) {
                sex = c.getInt(c.getColumnIndex("sex"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return sex;
    }

    /**
     * 获取用户所在地址城市
     *
     * @return
     */
    public String getUserAddress() {
        String city = "";
        Cursor c = null;
        try {
            c = db.rawQuery("select * from user", null);
            while (c.moveToNext()) {
                city = c.getString(c.getColumnIndex("address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return city;
    }

    /**
     * 获取用户年龄
     *
     * @return
     */
    public int getUserAge() {
        int age = 0;
        Cursor c = null;
        try {
            c = db.rawQuery("select * from user", null);
            while (c.moveToNext()) {
                age = c.getInt(c.getColumnIndex("age"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return age;
    }

    /**
     * 更新年龄
     *
     * @param age
     */
    public void updateAge(int age) {
        db.execSQL("update user set age=?", new Object[]{age});
    }

    /**
     * 更新性别
     *
     * @param sex
     */
    public void updateSex(int sex) {
        db.execSQL("update user set sex=?", new Object[]{sex});
    }

    /**
     * 更新手机号
     *
     * @param phone
     */
    public void updatePhone(String phone) {
        db.execSQL("update user set phone=?", new Object[]{phone});
    }

    /**
     * 更新昵称
     *
     * @param nickname
     */
    public void updateNickName(String nickname) {
        db.execSQL("update user set nickname=?", new Object[]{nickname});
    }

    /**
     * 更新邮箱
     *
     * @param email
     */
    public void updateEmail(String email) {
        db.execSQL("update user set email=?", new Object[]{email});
    }

    /**
     * 更新用户头像url
     *
     * @param url
     */
    public void updateUserAvatar(String url) {
        db.execSQL("update user set avatar=?", new Object[]{url});
    }

    /**
     * 更新用户地址
     *
     * @param address
     */
    public void updateUserAddress(String address) {
        db.execSQL("update user set address=?", new Object[]{address});
    }

    /**
     * 删除数据库
     */
    public void delete() {
        db.execSQL("delete from user");
    }

}
