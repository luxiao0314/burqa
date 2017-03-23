package com.mvvm.lux.burqa.model.response;

import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/15 17:52
 * @Version
 */
public class CategoryResponse1 {

    /**
     * tag_id : 3262
     * title : 少年
     * cover : http://images.dmzj.com/tuijian/222_222/kenan.jpg
     */

    private int tag_id;
    private String title;
    private String cover;
    /**
     * message : 请求成功
     * body : [{"tag_id":3262,"title":"少年","cover":"http://images.dmzj.com/tuijian/222_222/kenan.jpg"},{"tag_id":2308,"title":"国漫","cover":"http://images.dmzj.com/tuijian/222_222/mowang.jpg"},{"tag_id":3263,"title":"少女","cover":"http://images.dmzj.com/tuijian/222_222/yuanqi.jpg"},{"tag_id":2304,"title":"日本","cover":"http://images.dmzj.com/tuijian/222_222/ribenmanhua.jpg"},{"tag_id":2305,"title":"韩国","cover":"http://images.dmzj.com/tuijian/222_222/1112hanman.jpg"},{"tag_id":2306,"title":"欧美","cover":"http://images.dmzj.com/tuijian/222_222/oumeimanh.jpg"},{"tag_id":2310,"title":"完结","cover":"http://images.dmzj.com/tuijian/222_222/wanjie.jpg"},{"tag_id":4,"title":"冒险","cover":"http://images.dmzj.com/tuijian/222_222/maoxian.jpg"},{"tag_id":13,"title":"校园","cover":"http://images.dmzj.com/tuijian/222_222/xiaoyuan2.jpg"},{"tag_id":6,"title":"格斗","cover":"http://images.dmzj.com/tuijian/222_222/gedou.jpg"},{"tag_id":8,"title":"爱情","cover":"http://images.dmzj.com/tuijian/222_222/aiqing.jpg"},{"tag_id":3245,"title":"悬疑","cover":"http://images.dmzj.com/tuijian/222_222/kongbu.jpg"},{"tag_id":3327,"title":"美食","cover":"http://images.dmzj.com/tuijian/222_222/meishi2.jpg"},{"tag_id":3243,"title":"百合","cover":"http://images.dmzj.com/tuijian/222_222/baihe2.jpg"},{"tag_id":6316,"title":"轻小说改","cover":"http://images.dmzj.com/tuijian/222_222/qingxiaoshuo2.jpg"},{"tag_id":10,"title":"竞技","cover":"http://images.dmzj.com/tuijian/222_222/jingji.jpg"},{"tag_id":5848,"title":"奇幻","cover":"http://images.dmzj.com/tuijian/222_222/qihuan.jpg"},{"tag_id":3246,"title":"耽美","cover":"http://images.dmzj.com/tuijian/222_222/danmei.jpg"},{"tag_id":3244,"title":"伪娘","cover":"http://images.dmzj.com/tuijian/222_222/akb49.jpg"},{"tag_id":6437,"title":"颜艺","cover":"http://images.dmzj.com/tuijian/222_222/1029luoxuanjuan.jpg"},{"tag_id":7900,"title":"仙侠","cover":"http://images.dmzj.com/tuijian/222_222/heibai.jpg"},{"tag_id":7568,"title":"搞笑","cover":"http://images.dmzj.com/tuijian/222_222/gaoxiao2.jpg"},{"tag_id":5077,"title":"东方","cover":"http://images.dmzj.com/tuijian/222_222/1029dongfang.jpg"},{"tag_id":3328,"title":"职场","cover":"http://images.dmzj.com/tuijian/222_222/zhichang.jpg"}]
     * code : 10000
     * zoneCode : null
     */

    private String message;
    private int code;
    private Object zoneCode;
    private List<CategoryResponse1> body;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(Object zoneCode) {
        this.zoneCode = zoneCode;
    }

    public List<CategoryResponse1> getBody() {
        return body;
    }

    public void setBody(List<CategoryResponse1> body) {
        this.body = body;
    }
}
