package com.mvvm.lux.burqa.model.response;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/1 10:50
 * @Version
 */
public class SubjectResopnse {

    /**
     * id : 167
     * title : Comiket91本子专题
     * short_title : C91本子专题
     * create_time : 1487587585
     * small_cover : http://images.dmzj.com/subject/167/small_cover_1487587586.jpg
     * page_type : 3
     * sort : 1670
     * page_url : c91
     */

    private int id;
    private String title;
    private String short_title;
    private int create_time;
    private String small_cover;
    private int page_type;
    private int sort;
    private String page_url;

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

    public String getShort_title() {
        return short_title;
    }

    public void setShort_title(String short_title) {
        this.short_title = short_title;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getSmall_cover() {
        return small_cover;
    }

    public void setSmall_cover(String small_cover) {
        this.small_cover = small_cover;
    }

    public int getPage_type() {
        return page_type;
    }

    public void setPage_type(int page_type) {
        this.page_type = page_type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getPage_url() {
        return page_url;
    }

    public void setPage_url(String page_url) {
        this.page_url = page_url;
    }
}
