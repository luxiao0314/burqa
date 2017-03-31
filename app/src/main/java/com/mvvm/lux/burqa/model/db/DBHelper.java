package com.mvvm.lux.burqa.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mvvm.lux.burqa.model.entity.SearchHistoryInfo;
import com.mvvm.lux.framework.BaseApplication;

import java.util.List;

import greendao.DaoMaster;
import greendao.SearchHistoryInfoDao;

/**
 * @Description 搜索历史/
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/31 14:58
 * @Version
 */
public class DBHelper {

    private static DBHelper db;
    private final static String dbName = "search_history_db";
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;
    private final SearchHistoryInfoDao mHistoryInfoDao;

    public DBHelper() {
        context = BaseApplication.getAppContext();
        openHelper = new DaoMaster.DevOpenHelper(context, dbName);
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        mHistoryInfoDao = daoMaster.newSession().getSearchHistoryInfoDao();
    }


    /**
     * 获取单例
     *
     * @return
     */
    public static DBHelper init() {
        if (db == null) {
            synchronized (DBHelper.class) {
                if (db == null) {
                    db = new DBHelper();
                }
            }
        }
        return db;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName);
        }
        return openHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName);
        }
        return openHelper.getWritableDatabase();
    }

    /**
     * 插入历史记录
     *
     * @param info
     */
    public void insertHistory(SearchHistoryInfo info) {
        mHistoryInfoDao.insert(info);
    }

    /**
     * 删除
     *
     * @param info
     */
    public void deleteHistory(SearchHistoryInfo info) {
        mHistoryInfoDao.delete(info);
    }

    public void deleteHistory() {
        mHistoryInfoDao.deleteAll();
    }

    /**
     * 更新
     *
     * @param info
     */
    public void updateHistory(SearchHistoryInfo info) {
        mHistoryInfoDao.update(info);
    }

    /**
     * 查找
     *
     * @return
     */
    public List<SearchHistoryInfo> queryHistoryAll() {
        return mHistoryInfoDao.queryBuilder()
                .distinct() //去重
                .orderDesc(SearchHistoryInfoDao.Properties.Time) //按照插入时间升序降序
                .limit(10)  //限制在十条
                .list();
    }

}
