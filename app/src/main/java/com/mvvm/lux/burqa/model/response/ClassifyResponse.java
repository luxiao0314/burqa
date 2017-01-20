package com.mvvm.lux.burqa.model.response;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/16 10:25
 * @Version
 */
public class ClassifyResponse {

    /**
     * id : 15988
     * title : 拳愿阿修罗
     * authors : サンドロビッチ·ヤバ子/だろめおん
     * status : 连载中
     * cover : http://images.dmzj.com/webpic/18/0206quanyuanaxiuluofml.jpg
     * types : 格斗/竞技/颜艺
     * last_updatetime : 1483752758
     * num : 16550507
     */

    private int id;
    private String title;
    private String authors;
    private String status;
    private String cover;
    private String types;
    private int last_updatetime;
    private int num;

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
}
