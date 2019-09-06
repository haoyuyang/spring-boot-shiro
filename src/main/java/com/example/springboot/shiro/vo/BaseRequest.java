package com.example.springboot.shiro.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class BaseRequest {
    private String token;
    private String creator;
    private String updater;
    @JSONField(serialzeFeatures = SerializerFeature.WriteDateUseDateFormat,format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JSONField(serialzeFeatures = SerializerFeature.WriteDateUseDateFormat,format="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public BaseRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
