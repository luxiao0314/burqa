package com.mvvm.lux.burqa.model.response;

import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/8 14:33
 * @Version
 */
public class ComicResponse  {

    /**
     * id : 34079
     * islong : 2
     * direction : 2
     * title : 守望先锋
     * is_dmzj : 0
     * cover : http://images.dmzj.com/webpic/13/shouwangxianfengV2.jpg
     * description : 《守望先锋》(简称OW)是一款第一人称射击游戏，该游戏中的英雄都身处于一个充满纷争的时代，拥有超强技能的强力角色在各位熟悉却又陌生的战场上厮杀。《守望先锋》的故事发生在科学技术高度发达的未来地球。在全球危机时期，全球范围内的优秀军人、科学家、冒险者和奇人异士集结在一起，组成了一支旨在拯救这个被战争撕碎的世界的特别部队，他们就是“守望先锋”。许多年过去了，该组织的影响力日渐消散，并最终被迫解散。守望先锋们尽管已经离开了我们……但这个世界仍然需要英雄的守护。
     * last_updatetime : 1483753567
     * copyright : 0
     * first_letter : s
     * hot_num : 2226126
     * hit_num : 24637865
     * uid : null
     * types : [{"tag_id":4,"tag_name":"冒险"},{"tag_id":3248,"tag_name":"热血"}]
     * status : [{"tag_id":2309,"tag_name":"连载中"}]
     * authors : [{"tag_id":7981,"tag_name":"BLIZZARD"}]
     * subscribe_num : 34301
     * chapters : [{"title":"连载","data":[{"chapter_id":58053,"chapter_title":"10话","updatetime":1483753567,"filesize":2886620,"chapter_order":210},{"chapter_id":55498,"chapter_title":"9话","updatetime":1476240145,"filesize":978605,"chapter_order":200},{"chapter_id":52796,"chapter_title":"8话","updatetime":1469167897,"filesize":799273,"chapter_order":190},{"chapter_id":52560,"chapter_title":"7话","updatetime":1468636679,"filesize":1263095,"chapter_order":180},{"chapter_id":50213,"chapter_title":"6话","updatetime":1464078582,"filesize":1200938,"chapter_order":160},{"chapter_id":50163,"chapter_title":"5话","updatetime":1463980971,"filesize":1047944,"chapter_order":50},{"chapter_id":50162,"chapter_title":"4话","updatetime":1463980936,"filesize":535687,"chapter_order":40},{"chapter_id":50161,"chapter_title":"3话","updatetime":1463980898,"filesize":1275245,"chapter_order":30},{"chapter_id":50171,"chapter_title":"2话","updatetime":1463982519,"filesize":919824,"chapter_order":20},{"chapter_id":50160,"chapter_title":"1话","updatetime":1463980860,"filesize":983453,"chapter_order":10}]},{"title":"其他系列","data":[{"chapter_id":51761,"chapter_title":"同人图06","updatetime":1467360143,"filesize":3197207,"chapter_order":170},{"chapter_id":50177,"chapter_title":"同人图05","updatetime":1463986115,"filesize":1708713,"chapter_order":150},{"chapter_id":50176,"chapter_title":"同人图04","updatetime":1463986080,"filesize":1919700,"chapter_order":140},{"chapter_id":50175,"chapter_title":"同人图03","updatetime":1463986049,"filesize":1926924,"chapter_order":130},{"chapter_id":50174,"chapter_title":"同人图02","updatetime":1463985997,"filesize":1632541,"chapter_order":120},{"chapter_id":50173,"chapter_title":"同人图01","updatetime":1463985946,"filesize":1742683,"chapter_order":110},{"chapter_id":50172,"chapter_title":"同人漫画","updatetime":1463982743,"filesize":444344,"chapter_order":100},{"chapter_id":50168,"chapter_title":"官方壁纸04","updatetime":1463981253,"filesize":1895015,"chapter_order":90},{"chapter_id":50167,"chapter_title":"官方壁纸03","updatetime":1463981218,"filesize":1487136,"chapter_order":80},{"chapter_id":50166,"chapter_title":"官方壁纸02","updatetime":1463981182,"filesize":652967,"chapter_order":70},{"chapter_id":50165,"chapter_title":"官方壁纸01","updatetime":1463981127,"filesize":1036536,"chapter_order":60}]}]
     * comment : {"comment_count":989,"latest_comment":[{"comment_id":1645123,"uid":102318843,"content":"这也行","createtime":1483836949,"nickname":"refine-","avatar":"http://images.dmzj.com/user/50/66/5066f6f39829b01ab6d761ce6053fb84.png"},{"comment_id":1645088,"uid":102469006,"content":"死神那张图里那个女的是谁？跟猎空在一起的是谁？看起来眼熟啊","createtime":1483835820,"nickname":"冰蓝柠檬","avatar":"http://images.dmzj.com/user/d0/3f/d03f183ed6b97b595260df335ce284ea.png"}]}
     */

    private int id;
    private int islong;
    private int direction;
    private String title;
    private int is_dmzj;
    private String cover;
    private String description;
    private int last_updatetime;
    private int copyright;
    private String first_letter;
    private int hot_num;
    private int hit_num;
    private Object uid;
    private int subscribe_num;
    private CommentBean comment;
    private List<TypesBean> types;
    private List<StatusBean> status;
    private List<AuthorsBean> authors;
    private List<ChaptersBean> chapters;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIslong() {
        return islong;
    }

    public void setIslong(int islong) {
        this.islong = islong;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIs_dmzj() {
        return is_dmzj;
    }

    public void setIs_dmzj(int is_dmzj) {
        this.is_dmzj = is_dmzj;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLast_updatetime() {
        return last_updatetime;
    }

    public void setLast_updatetime(int last_updatetime) {
        this.last_updatetime = last_updatetime;
    }

    public int getCopyright() {
        return copyright;
    }

    public void setCopyright(int copyright) {
        this.copyright = copyright;
    }

    public String getFirst_letter() {
        return first_letter;
    }

    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }

    public int getHot_num() {
        return hot_num;
    }

    public void setHot_num(int hot_num) {
        this.hot_num = hot_num;
    }

    public int getHit_num() {
        return hit_num;
    }

    public void setHit_num(int hit_num) {
        this.hit_num = hit_num;
    }

    public Object getUid() {
        return uid;
    }

    public void setUid(Object uid) {
        this.uid = uid;
    }

    public int getSubscribe_num() {
        return subscribe_num;
    }

    public void setSubscribe_num(int subscribe_num) {
        this.subscribe_num = subscribe_num;
    }

    public CommentBean getComment() {
        return comment;
    }

    public void setComment(CommentBean comment) {
        this.comment = comment;
    }

    public List<TypesBean> getTypes() {
        return types;
    }

    public void setTypes(List<TypesBean> types) {
        this.types = types;
    }

    public List<StatusBean> getStatus() {
        return status;
    }

    public void setStatus(List<StatusBean> status) {
        this.status = status;
    }

    public List<AuthorsBean> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorsBean> authors) {
        this.authors = authors;
    }

    public List<ChaptersBean> getChapters() {
        return chapters;
    }

    public void setChapters(List<ChaptersBean> chapters) {
        this.chapters = chapters;
    }

    public static class CommentBean {
        /**
         * comment_count : 989
         * latest_comment : [{"comment_id":1645123,"uid":102318843,"content":"这也行","createtime":1483836949,"nickname":"refine-","avatar":"http://images.dmzj.com/user/50/66/5066f6f39829b01ab6d761ce6053fb84.png"},{"comment_id":1645088,"uid":102469006,"content":"死神那张图里那个女的是谁？跟猎空在一起的是谁？看起来眼熟啊","createtime":1483835820,"nickname":"冰蓝柠檬","avatar":"http://images.dmzj.com/user/d0/3f/d03f183ed6b97b595260df335ce284ea.png"}]
         */

        private int comment_count;
        private List<LatestCommentBean> latest_comment;

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public List<LatestCommentBean> getLatest_comment() {
            return latest_comment;
        }

        public void setLatest_comment(List<LatestCommentBean> latest_comment) {
            this.latest_comment = latest_comment;
        }

        public static class LatestCommentBean {
            /**
             * comment_id : 1645123
             * uid : 102318843
             * content : 这也行
             * createtime : 1483836949
             * nickname : refine-
             * avatar : http://images.dmzj.com/user/50/66/5066f6f39829b01ab6d761ce6053fb84.png
             */

            private int comment_id;
            private int uid;
            private String content;
            private int createtime;
            private String nickname;
            private String avatar;

            public int getComment_id() {
                return comment_id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getCreatetime() {
                return createtime;
            }

            public void setCreatetime(int createtime) {
                this.createtime = createtime;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }

    public static class TypesBean {
        /**
         * tag_id : 4
         * tag_name : 冒险
         */

        private int tag_id;
        private String tag_name;

        public int getTag_id() {
            return tag_id;
        }

        public void setTag_id(int tag_id) {
            this.tag_id = tag_id;
        }

        public String getTag_name() {
            return tag_name;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }
    }

    public static class StatusBean {
        /**
         * tag_id : 2309
         * tag_name : 连载中
         */

        private int tag_id;
        private String tag_name;

        public int getTag_id() {
            return tag_id;
        }

        public void setTag_id(int tag_id) {
            this.tag_id = tag_id;
        }

        public String getTag_name() {
            return tag_name;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }
    }

    public static class AuthorsBean {
        /**
         * tag_id : 7981
         * tag_name : BLIZZARD
         */

        private int tag_id;
        private String tag_name;

        public int getTag_id() {
            return tag_id;
        }

        public void setTag_id(int tag_id) {
            this.tag_id = tag_id;
        }

        public String getTag_name() {
            return tag_name;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }
    }

    public static class ChaptersBean {
        /**
         * title : 连载
         * data : [{"chapter_id":58053,"chapter_title":"10话","updatetime":1483753567,"filesize":2886620,"chapter_order":210},{"chapter_id":55498,"chapter_title":"9话","updatetime":1476240145,"filesize":978605,"chapter_order":200},{"chapter_id":52796,"chapter_title":"8话","updatetime":1469167897,"filesize":799273,"chapter_order":190},{"chapter_id":52560,"chapter_title":"7话","updatetime":1468636679,"filesize":1263095,"chapter_order":180},{"chapter_id":50213,"chapter_title":"6话","updatetime":1464078582,"filesize":1200938,"chapter_order":160},{"chapter_id":50163,"chapter_title":"5话","updatetime":1463980971,"filesize":1047944,"chapter_order":50},{"chapter_id":50162,"chapter_title":"4话","updatetime":1463980936,"filesize":535687,"chapter_order":40},{"chapter_id":50161,"chapter_title":"3话","updatetime":1463980898,"filesize":1275245,"chapter_order":30},{"chapter_id":50171,"chapter_title":"2话","updatetime":1463982519,"filesize":919824,"chapter_order":20},{"chapter_id":50160,"chapter_title":"1话","updatetime":1463980860,"filesize":983453,"chapter_order":10}]
         */

        private String title;
        private List<DataBean> data;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * chapter_id : 58053
             * chapter_title : 10话
             * updatetime : 1483753567
             * filesize : 2886620
             * chapter_order : 210
             */

            private int chapter_id;
            private String chapter_title;
            private int updatetime;
            private int filesize;
            private int chapter_order;

            public int getChapter_id() {
                return chapter_id;
            }

            public void setChapter_id(int chapter_id) {
                this.chapter_id = chapter_id;
            }

            public String getChapter_title() {
                return chapter_title;
            }

            public void setChapter_title(String chapter_title) {
                this.chapter_title = chapter_title;
            }

            public int getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(int updatetime) {
                this.updatetime = updatetime;
            }

            public int getFilesize() {
                return filesize;
            }

            public void setFilesize(int filesize) {
                this.filesize = filesize;
            }

            public int getChapter_order() {
                return chapter_order;
            }

            public void setChapter_order(int chapter_order) {
                this.chapter_order = chapter_order;
            }
        }
    }
}
