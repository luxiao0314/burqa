package com.mvvm.lux.widget.banner;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 16/10/21 11:47
 * @Version $VALUE
 */
public class BannerEntity {
    public String title;

    public String img;

    public String link;

    public BannerEntity() {
    }

    public BannerEntity(String title, String img, String link) {
        this.title = title;
        this.img = img;
        this.link = link;
    }
}
