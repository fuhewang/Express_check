package com.wangfuhe.wfh.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.wangfuhe.wfh.myapplication.R;
import com.wangfuhe.wfh.myapplication.base.BaseActivity;
import com.wangfuhe.wfh.myapplication.base.BaseApp;
import com.wangfuhe.wfh.myapplication.conn.ConnHelper;
import com.wangfuhe.wfh.myapplication.entity.KDlog;
import com.wangfuhe.wfh.myapplication.tools.JsonParser;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

/**
 * 快递查询的主界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, InitListener, RecognizerDialogListener {

    private final static String APPID = "56e8f854";//接口key
    private MenuDrawer mMenuDrawer;//实现菜单
    private Button mCompc, search;
    private String comName = "";
    private String comid = "";
    private EditText mExpressId;
    private Handler hd;
    private Button mHistory;
    private Button mBack;
    private ImageButton mSpeech;
    private RecognizerDialog iatDialog;
    private SpeechRecognizer iatRecognizer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMenu();
        comName = companyname[0];
        comid = companycodes[0];
        mCompc = (Button) findViewById(R.id.compch_btn);
        search = (Button) findViewById(R.id.search_btn);
        mExpressId = (EditText) findViewById(R.id.expressld_et);
        mHistory = (Button) findViewById(R.id.menu_history_btn);
        mBack= (Button) findViewById(R.id.menu_back_btn);
        mSpeech = (ImageButton) findViewById(R.id.speech_ib);
        initSpeech();
        mCompc.setOnClickListener(this);
        mBack.setOnClickListener(this);
        search.setOnClickListener(this);
        mHistory.setOnClickListener(this);
        mSpeech.setOnClickListener(this);
        hd = getHandler();


    }

    //初始化菜单
    private void initMenu() {
        mMenuDrawer = MenuDrawer.attach(this, Position.BOTTOM);
        mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
        mMenuDrawer.setDropShadowSize(0);
        mMenuDrawer.setContentView(R.layout.activity_main);
        mMenuDrawer.setMenuView(R.layout.menu_layout);
        mMenuDrawer.setMenuSize(150);

    }


    //由于短线程传递消息，回调当连接完成进行传递消息
    public Handler getHandler() {
        return new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
//                    KDlog log = new KDlog();
//                    log.comName=comName;
//                    log.comCodes=comid;
//                    log.expressId=(mExpressId.getText().toString());
//                    log.save();

                    Intent it = new Intent(MainActivity.this, ResultActivity.class);
                    it.putExtra("jsonStr", msg.obj.toString());
                    it.putExtra("expressId",mExpressId.getText().toString());
                    it.putExtra("comName",comName);
                    it.putExtra("comid",comid);
                    startActivityForResult(it, his_req);
                    overridePendingTransition(R.anim.push_up_in, R.anim.push_down_out);
                }
                if (msg.what == -1) {
                    showToast("error");
                }


            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        mMenuDrawer.toggleMenu();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.compch_btn:
                startActivityForResult(new Intent(getApplicationContext(), CompActivity.class), com_req);
                break;
            case R.id.search_btn:
                ConnHelper.search(comid, mExpressId.getText().toString(), hd);
                break;
            case R.id.menu_history_btn:
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                mMenuDrawer.closeMenu();
                break;
            case R.id.speech_ib:
                showlatDialog();
                break;
            case R.id.menu_back_btn:
                this.finish();
                mMenuDrawer.closeMenu();
                break;

        }
    }

    //回调，监听回调信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == com_req) {

            mCompc.setText(data.getExtras().getString("comName"));
            comid = data.getExtras().getString("comId");
            comName = data.getExtras().getString("comName");
//            if (resultCode == RESULT_OK && requestCode == his_req){
//                Log.i("wangfuhe","有返回");
//                BaseApp.isAwrr(mExpressId.getText().toString(),data.getExtras().getString("json"));
//            }

        }

    }


    //初始化讯飞语音控件
    public void initSpeech() {
        SpeechUtility.createUtility(MainActivity.this, "appid=" + APPID);
        RecognizerDialog iatRecognizer = new RecognizerDialog(this, this);
        com.iflytek.cloud.SpeechRecognizer iatDialog =
                com.iflytek.cloud.SpeechRecognizer.createRecognizer(this, this);
    }

    //设置讯飞语音dialog
    public void showlatDialog() {
        if (null == iatDialog) {
            iatDialog = new RecognizerDialog(this, this);
        }
        String engine = "iat_engine";
        iatDialog.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
        iatDialog.setParameter(SpeechConstant.DOMAIN, engine);
        iatDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
        mExpressId.setText(null);
        iatDialog.setListener(this);
        iatDialog.show();
        showToast("请开始说话……");
    }

    @Override
    public void onInit(int i) {

        if (i != ErrorCode.SUCCESS) {
            Toast.makeText(MainActivity.this, "解码失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResult(RecognizerResult recognizerResult, boolean b) {
        String text = JsonParser.parseIatResult(recognizerResult.getResultString());
        mExpressId.append(text.replace("。", ""));
        mExpressId.setSelection(mExpressId.length());
    }

    @Override
    public void onError(SpeechError speechError) {

        showToast(speechError.getMessage());
    }

    @Override
    protected void onStop() {

        if (null != iatRecognizer) {
            iatRecognizer.cancel();
        }
        if (null != iatDialog) {
            iatDialog.cancel();
        }
        super.onStop();
    }
}
