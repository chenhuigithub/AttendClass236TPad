package com.example.teaching236pad.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.teaching236pad.util.ValidateFormatUtils;

/**
 * 课件
 *
 * @author chenhui 2021.02.02
 */
public class Courseware {
    private String id;//唯一ID
    private String name;//课件名称
    private String thumbPath;
    private String bigPath;

    private int picResID;// 图片资源ID

    private boolean hasJoinPreview;// 是否已加入预览
    private int focusType = -1;// 状态（普通状态:-1，未选中:0，被选中:1）

    @JSONField(name = "DataID")
    public String getId() {
        if (ValidateFormatUtils.isEmpty(id)) {
            return "";
        }

        return id;
    }

    @JSONField(name = "DataID")
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JSONField(name = "ThumbPath")
    public String getThumbPath() {
        return thumbPath;
    }

    @JSONField(name = "ThumbPath")
    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    @JSONField(name = "BigPath")
    public String getBigPath() {
        return bigPath;
    }

    @JSONField(name = "BigPath")
    public void setBigPath(String bigPath) {
        this.bigPath = bigPath;
    }

    public int getFocusType() {
        return focusType;
    }

    public void setFocusType(int focusType) {
        this.focusType = focusType;
    }

    public boolean isHasJoinPreview() {
        return hasJoinPreview;
    }

    public void setHasJoinPreview(boolean hasJoinPreview) {
        this.hasJoinPreview = hasJoinPreview;
    }

    public int getPicResID() {
        return picResID;
    }

    public void setPicResID(int picResID) {
        this.picResID = picResID;
    }
}
