package com.wangfuhe.wfh.myapplication.activity;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wangfuhe.wfh.myapplication.R;
import com.wangfuhe.wfh.myapplication.adapter.HistoryAdapter;
import com.wangfuhe.wfh.myapplication.base.BaseActivity;
import com.wangfuhe.wfh.myapplication.base.BaseApp;
import com.wangfuhe.wfh.myapplication.conn.ConnHelper;

/**
 * 查询历史信息的activity
 */
public class HistoryActivity extends BaseActivity implements
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private TextView mCountTv;
    private ListView mHisLv;
    private Handler hd=new Handler();
    private Vibrator mVibrator = null;
    private HistoryAdapter mHisAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mHisAdapter = new HistoryAdapter(this, BaseApp.logs);
        mHisLv = (ListView) findViewById(R.id.history_lv);
        mHisLv.setAdapter(mHisAdapter);
        mHisLv.setOnItemClickListener(this);
        mHisLv.setOnItemLongClickListener(this);
        mCountTv = (TextView) findViewById(R.id.count_tv);
        mCountTv.setText("" + BaseApp.logs.size());

        hd=getHandler();
    }
    //实现回调，当查询成功是传递json信息
    private Handler getHandler(){
        return new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==0){
                    Intent it=new Intent(HistoryActivity.this,ResultActivity.class);
                    it.putExtra("jsonStr",msg.obj.toString());
                    startActivityForResult(it,his_req);
                    overridePendingTransition(R.anim.push_down_in,R.anim.push_down_out);
                }
                if(msg.what==-1){
                    showToast("error");
                }

            }
        };
    }

    //选择历史信息
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.i("wangfuhe", BaseApp.logs.get(position).isArr + "");
        if (BaseApp.logs.get(position).isArr == 0) {
            Intent it = new Intent(HistoryActivity.this, ResultActivity.class);
            it.putExtra("jsonStr", BaseApp.logs.get(position).json);
            Log.i("wangfuhe", BaseApp.logs.get(position).json);
            startActivity(it);
            overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
        } else {
            ConnHelper.search(BaseApp.logs.get(position).getComCodes(), BaseApp.logs.get(position).getExpressId(), hd);
        }
    }

    //删除历史信息
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
       mVibrator=(Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        mVibrator.vibrate(new long[]{50, 50, 0, 0}, -1);
        AlertDialog.Builder builder=new AlertDialog.Builder(HistoryActivity.this);
        builder.setTitle("确认删除");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                BaseApp.removeLog(BaseApp.logs.get(position).expressId,BaseApp.logs.get(position));
                mHisAdapter.notifyDataSetChanged();
                mCountTv.setText("" + BaseApp.logs.size());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_down_in,R.anim.push_down_out);
    }
}
