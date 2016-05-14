package com.wangfuhe.wfh.myapplication.entity;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.List;

/**
 * 快递实例采用activeandroid框架实现数控库操作
 * Created by wfh on 2016/3/14.
 */
@Table(name = "history")
public class KDlog extends Model {
    @Column(name = "comName")
    public String comName;
    @Column(name = "comCodes")
    public  String comCodes;
    @Column(name = "expressId")
    public String expressId;
    @Column(name = "isArr")
    public int isArr;
    @Column(name = "json")
    public String json;
    public KDlog() {
        super();
    }

    public KDlog(String comCodes, String comName, String expressId) {
        this.comCodes = comCodes;
        this.comName = comName;
        this.expressId = expressId;
        this.save();
    }

    public String getComName() {
        return comName;
    }
    public void setComName(String comName) {
        this.comName = comName;
    }
    public String getComCodes() {
        return comCodes;
    }

    public void setComCodes(String comCodes) {
        this.comCodes = comCodes;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }
    public static List<KDlog> getAll(){
        return new Select().from(KDlog.class).where(null).execute();
    }

    public static KDlog isExit(String expressId){
        return new Select().from(KDlog.class).
                where("expressId = ?", expressId).executeSingle();
    }
    public static void updateKD(String expressId,String json){
//        new Update(KDlog.class).set("isArr =?,"+"json =?",0,json).
//                where("expressId = ?",expressId).execute();

        KDlog log=KDlog.isExit(expressId);
        new Delete().from(KDlog.class).where("expressId = ?", expressId).execute();
        KDlog nlog=new KDlog();
        nlog.comName=log.comName;
        nlog.comCodes=log.comCodes;
        nlog.expressId=log.expressId;
        nlog.isArr=0;
        nlog.json=json;
        nlog.save();
        Log.i("wangfuhe", KDlog.isExit(expressId).isArr + "");
    }
}
