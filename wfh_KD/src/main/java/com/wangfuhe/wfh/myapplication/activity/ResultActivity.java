package com.wangfuhe.wfh.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wangfuhe.wfh.myapplication.R;
import com.wangfuhe.wfh.myapplication.adapter.ResultFollowAdapter;
import com.wangfuhe.wfh.myapplication.base.BaseActivity;
import com.wangfuhe.wfh.myapplication.base.BaseApp;
import com.wangfuhe.wfh.myapplication.entity.ResultEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 解析json信息
 */
public class ResultActivity extends BaseActivity {
    //标志快递是否已经达到
    public static final int STATE_RECEIVED = 0;
    public static final int STATE_ON_PASSAGE = 1;

    private ListView mlv;
    private ResultFollowAdapter adapter;
    private List<ResultEntity> rs = new ArrayList<ResultEntity>();
    private boolean sort_up;
    private int status = 0;
    private String expressId;
    private String comName;
    private String comid;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                  ResultActivity.this.finish();
        }
    };
    //    private
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mlv = (ListView) findViewById(R.id.follow_lv);
        mlv.setAdapter(adapter);
        Intent it=getIntent();
        expressId=it.getExtras().getString("expressId");
        comName=it.getExtras().getString("comName");
        comid=it.getExtras().getString("comid");
        parse(getIntent().getExtras().getString("jsonStr"));

//        findViewById(R.id.follow_tv).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent it=new Intent();
//                it.putExtra("json",getIntent().getExtras().getString("jsonStr"));
//                setResult(RESULT_OK,it);
//                finish();
//            }
//        });
    }

    //解析json数据
    public void parse(String jsonStr) {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<ResultEntity>>() {
            }.getType();
            JSONObject obj = new JSONObject(jsonStr);
            if(obj.get("reason").equals("成功的返回")){
                Log.i("wangfuhe",expressId+comid+comName);
                BaseApp.addLog(comName,comid,expressId);
                System.out.print("ture");
                JSONObject result=obj.getJSONObject("result");
                rs=gson.fromJson(result.getString("list"),type);
                Collections.reverse(rs);
                status=Integer.parseInt(obj.getString("error_code").toString());
                adapter=new ResultFollowAdapter(this,rs,sort_up,status);
                mlv.setAdapter(adapter);
                if(status==STATE_RECEIVED){
                    ((TextView) findViewById(R.id.follow_tv)).
                            setCompoundDrawablesWithIntrinsicBounds
                                    (0, 0, R.drawable.icon_success, 0);

                    findViewById(R.id.color_line_rl).setBackgroundResource(R.drawable.red_line_green);
                    findViewById(R.id.dot_full_tv).setVisibility(View.VISIBLE);
                    BaseApp.isAwrr(expressId, jsonStr);
                }

                if(status==STATE_ON_PASSAGE){
                    findViewById(R.id.color_line_rl).setBackgroundResource(R.drawable.red_line_blue);
                }else {
                    showToast(obj.getString("reason"));
                }


            }
            else{
                Toast.makeText(this,"输入的单号不存在请重新输入",Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"3秒后返回主界面",Toast.LENGTH_LONG).show();
                    handler.sendEmptyMessageDelayed(1,3000);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.push_down_in,R.anim.push_down_out);
        super.onBackPressed();
    }
}
