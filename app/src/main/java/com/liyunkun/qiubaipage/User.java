package com.liyunkun.qiubaipage;

import java.io.Serializable;

/**
 * Created by liyunkun on 2016/9/3 0003.
 */
public class User implements Serializable{
    private String userImg;//用户头像
    private String userName;//用户名
    private String content;//内容
    private String contentImg;//内容图片
    private String type;//类型
    private int up;//赞
    private int down;//不赞
    private String userId;
    private String comments_count;//评论
    private String share;//分享

    public User() {
    }

    public User(String userId,String userImg, String userName, String content, String contentImg, String type, int up, int down, String comments_count, String share) {
        this.userId=userId;
        this.userImg = userImg;
        this.userName = userName;
        this.content = content;
        this.contentImg = contentImg;
        this.type = type;
        this.up = up;
        this.down = down;
        this.comments_count = comments_count;
        this.share = share;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public String getComments_count() {
        return comments_count;
    }

    public void setComments_count(String comments_count) {
        this.comments_count = comments_count;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
