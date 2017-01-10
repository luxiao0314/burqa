package com.mvvm.lux.burqa.model.response;

import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/10 15:44
 * @Version
 */
public class ComicPageResponse {

    /**
     * chapter_id : 61641
     * comic_id : 21782
     * title : 第61话
     * chapter_order : 670
     * direction : 1
     * page_url : ["http://imgsmall.dmzj.com/z/21782/61641/0.jpg","http://imgsmall.dmzj.com/z/21782/61641/1.jpg","http://imgsmall.dmzj.com/z/21782/61641/2.jpg","http://imgsmall.dmzj.com/z/21782/61641/3.jpg","http://imgsmall.dmzj.com/z/21782/61641/4.jpg","http://imgsmall.dmzj.com/z/21782/61641/5.jpg","http://imgsmall.dmzj.com/z/21782/61641/6.jpg","http://imgsmall.dmzj.com/z/21782/61641/7.jpg","http://imgsmall.dmzj.com/z/21782/61641/8.jpg","http://imgsmall.dmzj.com/z/21782/61641/9.jpg","http://imgsmall.dmzj.com/z/21782/61641/10.jpg","http://imgsmall.dmzj.com/z/21782/61641/11.jpg","http://imgsmall.dmzj.com/z/21782/61641/12.jpg","http://imgsmall.dmzj.com/z/21782/61641/13.jpg","http://imgsmall.dmzj.com/z/21782/61641/14.jpg","http://imgsmall.dmzj.com/z/21782/61641/15.jpg","http://imgsmall.dmzj.com/z/21782/61641/16.jpg","http://imgsmall.dmzj.com/z/21782/61641/17.jpg","http://imgsmall.dmzj.com/z/21782/61641/18.jpg"]
     * picnum : 19
     * comment_count : 0
     */

    private int chapter_id;
    private int comic_id;
    private String title;
    private int chapter_order;
    private int direction;
    private int picnum;
    private int comment_count;
    private List<String> page_url;

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public int getComic_id() {
        return comic_id;
    }

    public void setComic_id(int comic_id) {
        this.comic_id = comic_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChapter_order() {
        return chapter_order;
    }

    public void setChapter_order(int chapter_order) {
        this.chapter_order = chapter_order;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getPicnum() {
        return picnum;
    }

    public void setPicnum(int picnum) {
        this.picnum = picnum;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public List<String> getPage_url() {
        return page_url;
    }

    public void setPage_url(List<String> page_url) {
        this.page_url = page_url;
    }
}
