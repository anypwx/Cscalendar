package com.calendar.cus.cscalendar.mview;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.calendar.cus.cscalendar.R;
import com.calendar.cus.cscalendar.adapter.ListViewDataAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grg on 2017/3/9.
 */

public class POPTimeSelector{
    private Context context;
    private View popView;
    private PopupWindow popupWindow;;

    private ListView ll_year;
    private ListView ll_mouth;
    private ListView ll_day;
    private ListView ll_week;
    private ListView ll_hours;
    private ListView ll_minute;
    private ListView ll_second;

    private List<String> yearList = new ArrayList<>();
    private List<String> mouthList = new ArrayList<>();
    private List<String> dayList = new ArrayList<>();
    private List<String> weekList = new ArrayList<>();
    private List<String> hoursList = new ArrayList<>();
    private List<String> minuteList = new ArrayList<>();
    private List<String> secondList = new ArrayList<>();

    public POPTimeSelector(Context context) {
        this.context = context;
        initView();
        initPop();
        initData();

    }

    private void initView(){
        popView = LayoutInflater.from(context).inflate(R.layout.view_datatime_selector, null,true);
        ll_year = (ListView) popView.findViewById(R.id.ll_year);
        ll_mouth = (ListView) popView.findViewById(R.id.ll_mouth);
        ll_day = (ListView) popView.findViewById(R.id.ll_day);
        ll_week = (ListView) popView.findViewById(R.id.ll_week);
        ll_hours = (ListView) popView.findViewById(R.id.ll_hours);
        ll_minute = (ListView) popView.findViewById(R.id.ll_minute);
        ll_second = (ListView) popView.findViewById(R.id.ll_second);

    }

    private void initPop(){
        popupWindow = new PopupWindow(popView, WindowManager.LayoutParams.MATCH_PARENT, 500);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    private void initData(){
        //年
        for(int i = 2017; i<=2050; i++){
            yearList.add(i+"年");
        }
        //月
        for(int i = 1; i<=12; i++){
            mouthList.add(i+"月");
        }
        //日
        for(int i = 1; i<=30; i++){
            dayList.add(i+"日");
        }
        // 周
        for(int i = 1; i<=7; i++){
            weekList.add("周"+i);
        }

        //时
        for(int i = 1; i<=24; i++){
            hoursList.add(i+"时");
        }
        //分
        for(int i = 1; i<=60; i++){
            minuteList.add(i+"分");
        }
        //秒
        for(int i = 1; i<=60; i++){
            secondList.add(i+"秒");
        }

        initListView();
    }

    private void initListView(){
        ll_year.setAdapter(new ListViewDataAdapter(context,yearList));
        ll_mouth.setAdapter(new ListViewDataAdapter(context,mouthList));
        ll_day.setAdapter(new ListViewDataAdapter(context,dayList));
        ll_week.setAdapter(new ListViewDataAdapter(context,weekList));
        ll_hours.setAdapter(new ListViewDataAdapter(context,hoursList));
        ll_minute.setAdapter(new ListViewDataAdapter(context,minuteList));
        ll_second.setAdapter(new ListViewDataAdapter(context,secondList));
    }

    public void show(View p) {
        popupWindow.showAtLocation(p, Gravity.BOTTOM,0,0);
    }

    public void dismiss() {
        popupWindow.dismiss();
    }
}
