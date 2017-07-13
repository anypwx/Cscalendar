package com.calendar.cus.cscalendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calendar.cus.cscalendar.R;

import java.util.List;

/**
 * Created by grg on 2017/3/9.
 */

public class ListViewDataAdapter extends BaseAdapter {
    private List<String> strings;
    private LayoutInflater mInflater;

    public ListViewDataAdapter(Context context,List<String> strings) {
        this.strings = strings;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(null != strings){
           return strings.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return strings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHelper viewHelper;
        if(null == view){
            view = mInflater.inflate(R.layout.list_data_item,viewGroup,false);
            viewHelper = new ViewHelper(view);
            view.setTag(viewHelper);
        }else {
            viewHelper = (ViewHelper) view.getTag();
        }
        String val = strings.get(i);
        viewHelper.tv_data_time.setText(val);
        return view;
    }

    class ViewHelper{
        private TextView tv_data_time;

        public ViewHelper(View v) {
            tv_data_time = (TextView) v.findViewById(R.id.tv_data_time);
        }
    }
}
