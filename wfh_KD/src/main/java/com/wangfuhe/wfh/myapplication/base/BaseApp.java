package com.wangfuhe.wfh.myapplication.base;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Update;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.SpeechUtility;
import com.wangfuhe.wfh.myapplication.entity.KDlog;

import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用初始化
 * Created by wfh on 2016/3/14.
 */
public class BaseApp extends Application {
//    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;
    public static List<KDlog>logs=new ArrayList<KDlog>();

//    Type type =new TypeToken<List<KDlog>>(){}.getType();
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        logs=KDlog.getAll();
//        sp=getSharedPreferences("log", MODE_PRIVATE);
//        editor=sp.edit();
        x.Ext.init(this);
//        SpeechUtility.createUtility(this, "appid="+"56e8f854");
//        List<KDlog> list=new Gson().fromJson(sp.getString("logs",""),type);
//        if(null!=list){
//            logs.addAll(list);
//
//        }
    }
    public static void addLog(String comName,String comCodes,String expressId){
//        boolean contains=false;
//        for (KDlog l:logs){
//            if(l.getExpressId().equals(log.getExpressId())){
//                contains=true;
//                break;
//            }
//        }
//        if (!contains){
//            logs.add(log);
//            editor.putString("logs", new Gson().toJson(logs));
//            editor.commit();
//        }
        if(null==KDlog.isExit(expressId)){
                 KDlog mdlog=new KDlog();
            mdlog.expressId=expressId;
            mdlog.comCodes=comCodes;
            mdlog.comName=comName;
            mdlog.isArr=1;
            mdlog.save();
            logs=KDlog.getAll();
        }
        if(null!=KDlog.isExit(expressId)){
            new Update(KDlog.class).set("comCodes=?,"+"comName=?",comCodes,comName).execute();
        }
    }
    public static void removeLog(String expressId,KDlog log){
       new Delete().from(KDlog.class).where("expressId=?",expressId).execute();
        logs.remove(log);
    }

    public static void isAwrr(String expressId,String json){
        if(expressId!=null){
            KDlog.updateKD(expressId,json);
        }
    }
}
