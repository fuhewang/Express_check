package com.wangfuhe.wfh.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangfuhe.wfh.myapplication.R;

/**
 * 快递公司的选择adapter
 * Created by wfh on 2016/3/11.
 */
public class CompanyAdapter extends BaseAdapter {

    private Context mcontext;
    private String[] companylogo;
    private String[] companyname;
    private String[] companycodes;

    public CompanyAdapter(String[] companyname,
                          String[] companycodes,
                          String[] companylogo, Context context) {
        this.companyname = companyname;
        this.companycodes = companycodes;
        this.companylogo = companylogo;
        this.mcontext = context;
    }


    @Override
    public int getCount() {
        if (companycodes == null) {
            return 0;
        }
        return companycodes.length;

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(mcontext).inflate(R.layout.item_comp, null);
        TextView company_name = (TextView) itemView.findViewById(R.id.company_item_name_tv);
        TextView company_codes = (TextView) itemView.findViewById(R.id.company_code_tv);
        ImageView company_logo = (ImageView) itemView.findViewById(R.id.company_logo_iv);
        company_codes.setText(companycodes[position]);
        company_name.setText(companyname[position]);
        company_logo.setImageBitmap(getBmp(companylogo[position]));
        return itemView;

    }
    public Bitmap getBmp(String imagename){

        int imgid= mcontext.getResources().getIdentifier(imagename,"drawable","com.wangfuhe.wfh.myapplication");
        return BitmapFactory.decodeResource(mcontext.getResources(),imgid);
    }
}
