package com.aliwh.android.quickyicha.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;

import com.aliwh.android.quickyicha.Constants;

import java.io.File;

/**
 * @功能: 数据库帮助类
 * @作者: 李文华
 * @版本: v1.2
 * @日期: 2015-2-25下午5:07:03
 * @修改日期:
 * @修改人:
 * @修改内容简述:
 */
public class DBHelper extends SQLiteOpenHelper {
    private static File sdcardDir = Environment.getExternalStorageDirectory();
    private static DBHelper helper = null;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        // 必须通过super调用父类当中的构造函数
        super(context, name, null, version);
    }

    public DBHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public DBHelper(Context context, String name) {
        this(context, name, 1);
    }

    public DBHelper(Context context) {
        this(context, Constants.DBNAME);
        if (Build.VERSION.SDK_INT >= 11) {
            if (getWritableDatabase() != null) {
                getWritableDatabase().enableWriteAheadLogging();
            }
        }
    }

    public synchronized static DBHelper getInstance(Context context) {
        if (helper == null) {
            helper = new DBHelper(context);
        }
        return helper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE user ADD COLUMN other TEXT");
    }

//    public SQLiteDatabase getWritableDatabase() {
//        SQLiteDatabase db = null;
//        if (Environment.getExternalStorageState().equals(
//                Environment.MEDIA_MOUNTED)) {
//            File dbp = new File(sdcardDir.getPath() + Constants.DATA_PATH);
//            if (!dbp.exists()) {
//                dbp.mkdirs();
//            }
//            File dbf = new File(dbp, Constants.DBNAME);
//            boolean isCreate = false;
//            if (!dbf.exists()) {
//                try {
//                    isCreate = dbf.createNewFile();
//                    OutputStream outStream = new FileOutputStream(dbf);
//                    outStream.flush();
//                    outStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                isCreate = true;
//            }
//            if (isCreate) {
//                db = SQLiteDatabase.openOrCreateDatabase(dbf, null);
//            }
//            return db;
//        } else {
//            return null;
//        }
//
//    }

}
