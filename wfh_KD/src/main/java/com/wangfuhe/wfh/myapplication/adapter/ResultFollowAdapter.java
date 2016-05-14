package com.wangfuhe.wfh.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wangfuhe.wfh.myapplication.R;
import com.wangfuhe.wfh.myapplication.base.BaseApp;
import com.wangfuhe.wfh.myapplication.entity.ResultEntity;

import java.util.List;

/**
 * 用于快递结果列表
 * Created by wfh on 2016/3/14.
 */
public class ResultFollowAdapter extends BaseAdapter {
    private Context mcontext;
    private List<ResultEntity> followList;
    boolean sort_up = true;
    private TextView time, content;
    int state;

    public ResultFollowAdapter(Context context,
                               List<ResultEntity> followList,
                               boolean sort_up, int state) {
        this.mcontext = context;
        this.followList = followList;
        this.sort_up = sort_up;
        this.state = state;
    }



    @Override
    public int getCount() {
        return followList.size();
    }

    @Override
    public Object getItem(int position) {
        return followList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_result, null);
        ResultEntity map = followList.get(position);
        time = (TextView) view.findViewById(R.id.item_time_tv);

        content = (TextView) view.findViewById(R.id.item_content_tv);
        time.setText(map.getTime());
        if (map.getZone().endsWith("")) {
            content.setText(map.getContext());
        } else {
            content.setText(map.getZone() + ":" +
                    map.getContext());
        }
        return view;
    }

//    public void setFollowList(ArrayList<ResultEntity> followList) {
//        this.followList = followList;
//    }
//
//    public void SetSort_up(boolean sort_up) {
//        this.sort_up = sort_up;
//
//    }

}
