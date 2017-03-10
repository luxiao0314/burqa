package com.mvvm.lux.burqa.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mvvm.lux.framework.http.cookie.DaoMaster;

/**
 * Created by Growth on 2016/3/3.
 */
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        MigrationHelper.migrate(db,TestDataDao.class,TestData2Dao.class,TestData3Dao.class);
    }
}
