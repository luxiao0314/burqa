package com.mvvm.lux.burqa.model.db;

import com.mvvm.lux.burqa.model.response.ClassifyResponse;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/18 14:30
 * @Version 1.0.1
 */
public class RealmHelper {
    private Realm mRealm;
    private static RealmHelper instance;

    private RealmHelper() {
    }

    public static RealmHelper getInstance() {
        if (instance == null) {
            synchronized (RealmHelper.class) {
                if (instance == null)
                    instance = new RealmHelper();
            }
        }
        return instance;
    }


    private Realm getRealm() {
        if (mRealm == null || mRealm.isClosed())
            mRealm = Realm.getDefaultInstance();
        return mRealm;
    }

    //--------------------------------------------------收藏相关----------------------------------------------------

    /**
     * 增加 收藏记录:列表
     * 使用@PrimaryKey注解后copyToRealm需要替换为copyToRealmOrUpdate
     * @param bean
     */
    public void insertClassifyList(ClassifyResponse bean){
        getRealm().beginTransaction();
        getRealm().copyToRealmOrUpdate(bean);
        getRealm().commitTransaction();
    }

    /**
     * 收藏列表:通过存入的时间戳查询,并排序
     *
     * @return
     */
    public List<ClassifyResponse> getClassifyList() {
        //使用findAllSort ,先findAll再result.sort排序
        RealmResults<ClassifyResponse> results = getRealm()
                .where(ClassifyResponse.class)
                .findAllSorted("time", Sort.DESCENDING);
        return getRealm().copyFromRealm(results);
    }

    /**
     * 增加 收藏记录:漫画详情
     *
     * @param bean
     */
//    public void insertCollection(ComicResponse bean) {
//        getRealm().beginTransaction();
//        getRealm().copyToRealm(bean);
//        getRealm().commitTransaction();
//    }

    /**
     * 删除 收藏记录
     *
     * @param id
     *//*
    public void deleteCollection(String id) {
        Collection data = getRealm().where(Collection.class).equalTo("id", id).findFirst();
        getRealm().beginTransaction();
        data.deleteFromRealm();
        getRealm().commitTransaction();
    }

    *//**
     * 清空收藏
     *//*
    public void deleteAllCollection() {
        getRealm().beginTransaction();
        getRealm().delete(Collection.class);
        getRealm().commitTransaction();
    }

    */

    /**
     * 查询 收藏记录
     *
     * @param id
     * @return
     */
//    public boolean queryCollectionId(String id) {
//        RealmResults<ComicResponse> results = getRealm().where(ComicResponse.class).findAll();
//        for (ComicResponse item : results) {
//            if (String.valueOf(item.getId()).equals(id)) {
//                return true;
//            }
//        }
//        return false;
//    }

    //--------------------------------------------------播放记录相关----------------------------------------------------

    /**
     * 增加播放记录
     *
     * @param bean
     * @param maxSize 保存最大数量
     *//*
    public void insertRecord(Record bean, int maxSize) {
        if (maxSize != 0) {
            RealmResults<Record> results = getRealm().where(Record.class).findAllSorted("time", Sort.DESCENDING);
            if (results.size() >= maxSize) {
                for (int i = maxSize - 1; i < results.size(); i++) {
                    deleteRecord(results.get(i).getId());
                }
            }
        }
        getRealm().beginTransaction();
        getRealm().copyToRealm(bean);
        getRealm().commitTransaction();
    }


    *//**
     * 删除 播放记录
     *
     * @param id
     *//*
    public void deleteRecord(String id) {
        Record data = getRealm().where(Record.class).equalTo("id", id).findFirst();
        getRealm().beginTransaction();
        data.deleteFromRealm();
        getRealm().commitTransaction();
    }

    *//**
     * 查询 播放记录
     *
     * @param id
     * @return
     *//*
    public boolean queryRecordId(String id) {
        RealmResults<Record> results = getRealm().where(Record.class).findAll();
        for (Record item : results) {
            if (item.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public List<Record> getRecordList() {
        //使用findAllSort ,先findAll再result.sort排序
        RealmResults<Record> results = getRealm().where(Record.class).findAllSorted("time", Sort.DESCENDING);
        return getRealm().copyFromRealm(results);
    }

    *//**
     * 清空历史
     *//*
    public void deleteAllRecord() {
        getRealm().beginTransaction();
        getRealm().delete(Record.class);
        getRealm().commitTransaction();
    }

    *//**
     * 增加 搜索记录
     *
     * @param bean
     *//*
    public void insertSearchHistory(SearchKey bean) {
        //如果有不保存
        List<SearchKey> list = getSearchHistoryList(bean.getSearchKey());
        if (list == null || list.size() == 0) {
            getRealm().beginTransaction();
            getRealm().copyToRealm(bean);
            getRealm().commitTransaction();
        }
        //如果保存记录超过20条，就删除一条。保存最多20条
        List<SearchKey> listAll = getSearchHistoryListAll();
        if (listAll != null && listAll.size() >= 10) {
            deleteSearchHistoryList(listAll.get(listAll.size() - 1).getSearchKey());
        }
    }

    *//**
     * 获取搜索历史记录列表
     *
     * @return
     *//*
    public List<SearchKey> getSearchHistoryList(String value) {
        //使用findAllSort ,先findAll再result.sort排序
        RealmResults<SearchKey> results = getRealm().where(SearchKey.class).contains("searchKey", value).findAllSorted("insertTime", Sort.DESCENDING);
        return getRealm().copyFromRealm(results);
    }

    *//**
     * 删除指定搜索历史记录列表
     *
     * @return
     *//*
    public void deleteSearchHistoryList(String value) {
        SearchKey data = getRealm().where(SearchKey.class).equalTo("searchKey", value).findFirst();
        getRealm().beginTransaction();
        data.deleteFromRealm();
        getRealm().commitTransaction();
    }

    public void deleteSearchHistoryAll() {
        getRealm().beginTransaction();
        getRealm().delete(SearchKey.class);
        getRealm().commitTransaction();
    }

    *//**
     * 获取搜索历史记录列表
     *
     * @return
     *//*
    public List<SearchKey> getSearchHistoryListAll() {
        //使用findAllSort ,先findAll再result.sort排序
        RealmResults<SearchKey> results = getRealm().where(SearchKey.class).findAllSorted("insertTime", Sort.DESCENDING);
        return getRealm().copyFromRealm(results);
    }*/
}