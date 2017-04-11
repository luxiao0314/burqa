package com.mvvm.lux.burqa.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/31 15:01
 * @Version
 */
@Entity
public class SearchHistoryInfo {
    @Id(autoincrement = true)
    private Long id;
    /*keyword*/
    private String keyword;
    /*时间*/
    private long time;
    @Generated(hash = 190685691)
    public SearchHistoryInfo(Long id, String keyword, long time) {
        this.id = id;
        this.keyword = keyword;
        this.time = time;
    }
    @Generated(hash = 1071675110)
    public SearchHistoryInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getKeyword() {
        return this.keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
}
