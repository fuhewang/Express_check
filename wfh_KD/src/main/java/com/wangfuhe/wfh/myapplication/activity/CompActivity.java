package com.wangfuhe.wfh.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wangfuhe.wfh.myapplication.R;
import com.wangfuhe.wfh.myapplication.adapter.CompanyAdapter;
import com.wangfuhe.wfh.myapplication.base.BaseActivity;

/**
 * 用于实现快递公司选择，dialog
 */

public class CompActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView mCompLv;
    private CompanyAdapter mComypanyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compc);
        mCompLv = (ListView) findViewById(R.id.comps_lv);
        mComypanyAdapter =new CompanyAdapter(companyname,companycodes,companyLogos,this);
        mCompLv.setAdapter(mComypanyAdapter);
        mCompLv.setOnItemClickListener(this);

    }

    //用于实现快递公司的选择
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it=new Intent();
        it.putExtra("comName",companyname[position]);
        it.putExtra("comId",companycodes[position]);
        setResult(RESULT_OK,it);
        onBackPressed();
    }
}
