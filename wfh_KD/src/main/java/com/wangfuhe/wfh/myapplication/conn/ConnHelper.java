package com.wangfuhe.wfh.myapplication.conn;

import android.os.Message;
import android.os.Handler;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 网络请求
 * Created by wfh on 2016/3/14.
 */
public class ConnHelper {
    public static final String AppKey = "61b37a9b5a9c403138fb4bf895fd089f";
    public static final String EXPRESS_API_HOST = "http://v.juhe.cn/exp/index";
    public static void search(final String comCodes, final String expressNO, final Handler hd) {

        RequestParams params = new RequestParams(EXPRESS_API_HOST);
        params.addBodyParameter("key", AppKey);
        params.addBodyParameter("com", comCodes);
        params.addBodyParameter("no", expressNO);
        x.http().get(params, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {
                Message msg = new Message();
                msg.what = 0;
                msg.obj = result;
                hd.sendMessage(msg);
                Log.i("wangfuhe",result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                hd.sendEmptyMessage(-1);
                Log.i("wangfuhe","请求失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }


//    static HttpUtils hu=new HttpUtils();
//    public  static void  Search(final String comCodes,final String expressNO, final Handler hd){
//        StringBuffer sb=new StringBuffer();
////        http://v.juhe.cn/exp/index?key=key&com=sf&no=575677355677
//        sb.append(Express_API_HOST).append("?key=").append(AppKey).append("&com=").append(comCodes).
//                append("&no=").append(expressNO);
//        String url=sb.toString();
//        hu.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//
//                Message msg=new Message();
//                msg.what=0;
//                msg.obj=responseInfo.result;
//                hd.sendMessage(msg);
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//
//                hd.sendEmptyMessage(-1);
//            }
//        });
//    }

}
