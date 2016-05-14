package com.wangfuhe.wfh.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wangfuhe.wfh.myapplication.R;
import com.wangfuhe.wfh.myapplication.entity.KDlog;

import java.util.List;

/**
 * 用于历史列表
 * Created by wfh on 2016/3/15.
 */
public class HistoryAdapter extends BaseAdapter {
    private Context mcontext;
    private List<KDlog> datalist;

    public HistoryAdapter(Context context, List<KDlog> datalist) {
        this.mcontext = context;
        this.datalist = datalist;
    }

    @Override
    public int getCount() {
        if (datalist == null) {
            return 0;
        }
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.item_history,null);
        TextView comName= (TextView) view.findViewById(R.id.company_name_tv);
        TextView number= (TextView) view.findViewById(R.id.item_number_tv);
        TextView comCode= (TextView) view.findViewById(R.id.company_code_tv);
        number.setText(datalist.get(position).getExpressId());
        comCode.setText(datalist.get(position).getComCodes());
        comName.setText(datalist.get(position).getComName());
        return view;
    }
//    public void setDatalist(List<KDlog> datalist){
//        this.datalist=datalist;
//    }
}
