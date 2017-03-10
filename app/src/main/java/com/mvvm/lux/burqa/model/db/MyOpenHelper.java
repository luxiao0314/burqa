package com.mvvm.lux.burqa.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mvvm.lux.framework.http.cookie.DaoMaster;

import org.greenrobot.greendao.database.Database;

/**
 * 自定义Helper类重写升级的方法，通过for循环解决跨版本升级数据库的需求
 */
public class MyOpenHelper extends DaoMaster.OpenHelper {

    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {
                case 1:
                    upgradeToVersion2(db);
                    break;
                case 2:
                    upgradeToVersion3(db);
                    break;
                default:
                    break;
            }
        }
    }

    private void upgradeToVersion3(Database db) {
        //创建新表时，用如下方法
//        FourBeanDao.createTable(db, true);
    }

    private void upgradeToVersion2(Database db) {
        //添加ThreeBeanBao表的字段lastname，格式为text"，原表中数据不会清除
//        db.execSQL("alter table '" + ThreeBeanDao.TABLENAME + "' add 'lastname' TEXT;");
    }
}