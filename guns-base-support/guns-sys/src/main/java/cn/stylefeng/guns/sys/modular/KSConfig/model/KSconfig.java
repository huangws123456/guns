package cn.stylefeng.guns.sys.modular.KSConfig.model;

import java.io.Serializable;

public class KSconfig  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 分享链接
     */
    private String schemeUrl;
    /**
     * 快手号
     */
    private String userId;
    /**
     * 快手昵称
     */
    private String userName;
    /**
     * 是否自动评论
     */
    private String isComment ;
    /**
     * 评论规则
     */
    private String comment;
    /**
     * 是否顺序评论
     */
    private String order;
    /**
     * 是否随机
     */
    private String ifRandom;
    /**
     * 评论间隔
     */
    private String intervalTime;
    /**
     * 评论次数
     */
    private String commentNum;
    /**
     * 是否自动关注
     */
    private String concern;
    /**
     * 是否自动点赞
     */
    private String like;
    /**
     * 点赞次数
     */
    private String approveNum;
    /**
     * app类型
     */
    private String apptype;
    /**
     * 命令来源
     */
    private String from;
    /**
     * 指令
     */
    private String cmid;
    /**
     * 设备号
     */
    private String deveice;
    /**
     * 是否随机关注
     */
    private String ifRandomGz;
    /**
     * 是否随机点赞
     */
    private String ifRandomDz;

    /**
     * 滑动时间间隔单位s
     */
    private String sleepTime;

    public String getSchemeUrl() {
        return schemeUrl;
    }

    public void setSchemeUrl(String schemeUrl) {
        this.schemeUrl = schemeUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getIfRandom() {
        return ifRandom;
    }

    public void setIfRandom(String ifRandom) {
        this.ifRandom = ifRandom;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getConcern() {
        return concern;
    }

    public void setConcern(String concern) {
        this.concern = concern;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getApproveNum() {
        return approveNum;
    }

    public void setApproveNum(String approveNum) {
        this.approveNum = approveNum;
    }

    public String getApptype() {
        return apptype;
    }

    public void setApptype(String apptype) {
        this.apptype = apptype;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCmid() {
        return cmid;
    }

    public void setCmid(String cmid) {
        this.cmid = cmid;
    }

    public String getDeveice() {
        return deveice;
    }

    public void setDeveice(String deveice) {
        this.deveice = deveice;
    }

    public String getIfRandomGz() {
        return ifRandomGz;
    }

    public void setIfRandomGz(String ifRandomGz) {
        this.ifRandomGz = ifRandomGz;
    }

    public String getIfRandomDz() {
        return ifRandomDz;
    }

    public void setIfRandomDz(String ifRandomDz) {
        this.ifRandomDz = ifRandomDz;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }
}
