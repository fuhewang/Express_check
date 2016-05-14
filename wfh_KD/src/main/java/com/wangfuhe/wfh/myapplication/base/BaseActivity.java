package com.wangfuhe.wfh.myapplication.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.wangfuhe.wfh.myapplication.R;

/**
 * 对activity数据进行初始化
 * Created by wfh on 2016/3/11.
 */
public class BaseActivity extends Activity {
    public Toast mToast=null;
    public String[] companyLogos;
    public String[] companyname;
    public String[] companycodes;
    public static final  int com_req=1;
    public static final  int his_req=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        companycodes=getResources().getStringArray(R.array.company_code);
        companyLogos=getResources().getStringArray(R.array.company_logo);
        companyname=getResources().getStringArray(R.array.company_name);
    }
    public void showToast(String msg){
        if(mToast==null){
            mToast=Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);

        }else {
            mToast.setText(msg);

            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }


//    public void showLoadingDialog(String dialogStr){
//        dialog=
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
