package com.wangfuhe.wfh.myapplication.entity;

/**
 * 对于快递实例的初始化
 * Created by wfh on 2016/3/12.
 */
public class ResultEntity {
    private String datetime;
    private String remark;
    private String zone;

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getTime() {
        return datetime;
    }

    public void setTime(String time) {
        this.datetime = time;
    }

    public String getContext() {
        return remark;
    }

    public void setContext(String context) {
        this.remark = context;
    }

    public ResultEntity() {
    }

    public ResultEntity(String context, String time) {
        this.remark = context;
        this.datetime = time;
    }
}
