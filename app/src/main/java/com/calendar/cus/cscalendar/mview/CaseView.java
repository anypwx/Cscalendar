package com.calendar.cus.cscalendar.mview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.calendar.cus.cscalendar.R;

/**
 * Created by grg on 2017/3/8.
 */

public class CaseView extends LinearLayout implements View.OnLongClickListener {
    private TextView tv_time;
    private TextView tv_case;
    private ViewItemListener viewItemListener;
    public CaseView(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.list_case_item,this);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_case = (TextView) view.findViewById(R.id.tv_case);
        tv_case.setOnLongClickListener(this);
    }

    public CaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTime(String s){
        if(null == s){
            return;
        }
        tv_time.setText(s);
    }

    public void setCase(String s){
        if(null == s){
            return;
        }
        tv_case.setText(s);
    }

    public void setViewItemListener(ViewItemListener viewItemListener){
        this.viewItemListener = viewItemListener;
    }

    @Override
    public boolean onLongClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_case) {
            if (null != viewItemListener) {
                String caseStr = tv_case.getText().toString();
                viewItemListener.LongClick(caseStr, (Long) getTag());
            }

        }
        return false;
    }

    public interface ViewItemListener{
        void LongClick(String caseStr,long id);
    }
}
