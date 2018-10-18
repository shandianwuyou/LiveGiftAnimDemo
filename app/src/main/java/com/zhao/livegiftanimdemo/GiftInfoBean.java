package com.zhao.livegiftanimdemo;

/**
 * 创建者 ：赵鹏   时间：2018/10/5
 */
public class GiftInfoBean {

    private String userIcon;
    private String userName;
    private String userId;
    private int giftNum;
    private String giftId;
    private String giftName;
    private int doubleHitNum;
    private long updateTime;
    private boolean isDoubleHitAnimOver;
    private boolean isInExitAnim;

    public boolean isInExitAnim() {
        return isInExitAnim;
    }

    public void setInExitAnim(boolean inExitAnim) {
        isInExitAnim = inExitAnim;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }

    public int getDoubleHitNum() {
        return doubleHitNum;
    }

    public void setDoubleHitNum(int doubleHitNum) {
        this.doubleHitNum = doubleHitNum;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isDoubleHitAnimOver() {
        return isDoubleHitAnimOver;
    }

    public void setDoubleHitAnimOver(boolean doubleHitAnimOver) {
        isDoubleHitAnimOver = doubleHitAnimOver;
    }
}
