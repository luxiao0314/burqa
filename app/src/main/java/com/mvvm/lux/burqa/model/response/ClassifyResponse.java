package com.mvvm.lux.burqa.model.response;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/16 10:25
 * @Version
 */
public class ClassifyResponse extends RealmObject implements Serializable {

    public ClassifyResponse() {
    }

    /**
     * id : 15988
     * title : 拳愿阿修罗
     * authors : サンドロビッチ·ヤバ子/だろめおん
     * type : 连载中
     * cover : http://images.dmzj.com/webpic/18/0206quanyuanaxiuluofml.jpg
     * types : 格斗/竞技/颜艺
     * last_updatetime : 1483752758
     * num : 16550507
     */

    @PrimaryKey
    private int id;
    private String title;
    private String authors;
    private String status;
    @Required   //强制禁止空值（null）被存储
    private String cover;
    private String types;
    private int last_updatetime;
    private int num;
    private long time;   //添加收藏的时间
    private int pagePosition;
    private int TagPosition;
    private String chapters;
    private String chapter_title;
    private String chapter_id;
    private boolean isCollection;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public int getLast_updatetime() {
        return last_updatetime;
    }

    public void setLast_updatetime(int last_updatetime) {
        this.last_updatetime = last_updatetime;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getPagePosition() {
        return pagePosition;
    }

    public void setPagePosition(int pagePosition) {
        this.pagePosition = pagePosition;
    }

    public int getTagPosition() {
        return TagPosition;
    }

    public void setTagPosition(int tagPosition) {
        TagPosition = tagPosition;
    }

    public String getChapters() {
        return chapters;
    }

    public void setChapters(String chapters) {
        this.chapters = chapters;
    }

    public String getChapter_title() {
        return chapter_title;
    }

    public void setChapter_title(String chapter_title) {
        this.chapter_title = chapter_title;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }
}
