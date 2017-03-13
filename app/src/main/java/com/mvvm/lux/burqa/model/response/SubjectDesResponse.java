package com.mvvm.lux.burqa.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/10 16:20
 * @Version
 */
public class SubjectDesResponse {

    @SerializedName("mobile_header_pic")
    private String mobile_header_pic; // FIXME check this code
    private String title;
    private String description;
    private int comment_amount;
    private List<ComicsBean> comics;

    public String getMobile_header_pic() {
        return mobile_header_pic;
    }

    public void setMobile_header_pic(String mobile_header_pic) {
        this.mobile_header_pic = mobile_header_pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getComment_amount() {
        return comment_amount;
    }

    public void setComment_amount(int comment_amount) {
        this.comment_amount = comment_amount;
    }

    public List<ComicsBean> getComics() {
        return comics;
    }

    public void setComics(List<ComicsBean> comics) {
        this.comics = comics;
    }


    public static class ComicsBean {
        /**
         * cover : http://images.dmzj.com/webpic/6/jichangluludemali.jpg
         * recommend_brief : 新作力推性转男主
         * recommend_reason : 恶魔奶爸作者带着特价389日元男主角重回JUMP！啊刚才他变成女主角了
         * id : 39900
         * name : 饥肠辘辘的玛丽
         * alias_name :
         */

        private String cover;
        private String recommend_brief;
        private String recommend_reason;
        private int id;
        private String name;
        private String alias_name;

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getRecommend_brief() {
            return recommend_brief;
        }

        public void setRecommend_brief(String recommend_brief) {
            this.recommend_brief = recommend_brief;
        }

        public String getRecommend_reason() {
            return recommend_reason;
        }

        public void setRecommend_reason(String recommend_reason) {
            this.recommend_reason = recommend_reason;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlias_name() {
            return alias_name;
        }

        public void setAlias_name(String alias_name) {
            this.alias_name = alias_name;
        }
    }
}
