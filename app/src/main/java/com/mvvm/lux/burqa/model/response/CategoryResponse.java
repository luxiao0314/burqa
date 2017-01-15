package com.mvvm.lux.burqa.model.response;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/15 17:52
 * @Version
 */
public class CategoryResponse {

    /**
     * tag_id : 3262
     * title : 少年
     * cover : http://images.dmzj.com/tuijian/222_222/kenan.jpg
     */

    private int tag_id;
    private String title;
    private String cover;

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
