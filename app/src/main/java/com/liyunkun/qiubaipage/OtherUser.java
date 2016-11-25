package com.liyunkun.qiubaipage;

/**
 * Created by liyunkun on 2016/9/3 0003.
 */
public class OtherUser {
    private String img;
    private String name;
    private String content;

    public OtherUser() {
    }

    public OtherUser(String img, String content,String name) {
        this.img = img;
        this.content = content;
        this.name=name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
