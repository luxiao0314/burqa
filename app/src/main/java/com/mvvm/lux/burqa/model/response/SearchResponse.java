package com.mvvm.lux.burqa.model.response;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/16 17:29
 * @Version
 */
public class SearchResponse {

    /**
     * id : 9949
     * status : 连载中
     * title : 一拳超人
     * last_name : 原作版109(5)
     * cover : http://images.dmzj.com/webpic/1/onepunchmanfengmianl.jpg
     * authors : 村田雄介/ONE
     * types : 冒险/欢乐向/格斗
     * hot_hits : 92103988
     */

    private int id;
    private String status;
    private String title;
    private String last_name;
    private String cover;
    private String authors;
    private String types;
    private int hot_hits;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public int getHot_hits() {
        return hot_hits;
    }

    public void setHot_hits(int hot_hits) {
        this.hot_hits = hot_hits;
    }
}
