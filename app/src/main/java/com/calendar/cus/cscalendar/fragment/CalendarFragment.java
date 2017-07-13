package com.calendar.cus.cscalendar.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.calendar.cus.cscalendar.Activity.CaseAddActivity;
import com.calendar.cus.cscalendar.R;
import com.calendar.cus.cscalendar.adapter.GridViewAdapter;
import com.calendar.cus.cscalendar.db.DbUtil;
import com.calendar.cus.cscalendar.entity.CalendaCaseEntity;
import com.calendar.cus.cscalendar.entity.DayAttrEntity;
import com.calendar.cus.cscalendar.utils.MyUitls;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by grg on 2017/3/6.
 */

public class CalendarFragment extends Fragment {
//    private CalendarView cv_calendar;
    private Receiver2 receiver2;
    private GridView gv_container;
    private int day;
    private int mouthCurr;//当前月
    private int yearCurr;//当前月
    private int initDay = 1;
    private int mouthSum;
    private List<DayAttrEntity> strings = new ArrayList<>();
    private GridViewAdapter gridViewAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        gv_container = (GridView) view.findViewById(R.id.gv_container);
        int mouth = getArguments().getInt("mouth");
        int year  = getArguments().getInt("year");
        Log.i("pwx","mouth:"+mouth+" ----  year:"+year);
        initCalendarView(mouth,year);
        initBrodCase();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(receiver2);
    }

    public static Fragment createFragment(int mouth, int year){
        //这里也是处理viewPager预加载  ，不这里处理会出现预加载数据乱掉
        if(12 < mouth){
            mouth = 1;
            year++;
        }
        if(1 > mouth){
            mouth = 12;
            year--;
        }
        CalendarFragment calendarFragment = new CalendarFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("mouth",mouth);
        bundle.putInt("year",year);
        calendarFragment.setArguments(bundle);
        return calendarFragment;
    }

    private void initCalendarView(int mouth,int year){
        //先计算出当月的1号是星期几，这样为了方便排版
        Calendar calendar = Calendar.getInstance();
//        //先获取当前系统的具体年月日
        yearCurr = calendar.get(Calendar.YEAR); //当前年
        mouthCurr = calendar.get(Calendar.MONTH)+1; // 当前月   java的月份是从0开始计算的
        day = calendar.get(Calendar.DAY_OF_MONTH);  //当前天
        //在指定某个年月日  计算出实际的周几？
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,mouth - 1);
        calendar.set(Calendar.DAY_OF_MONTH,initDay);
        mouthSum = calendar.getActualMaximum(Calendar.DATE); //当前月有多少天
        Log.i("pwx","当前"+(mouth)+"月有"+mouthSum+"天");
        int weekNo = calendar.get(Calendar.DAY_OF_WEEK);//计算出周几
        initAdapter();
        gv_container.setNumColumns(7);
        gv_container.setAdapter(gridViewAdapter);
        isWeek(weekNo - 1,mouth,year); // 初始化起始位置，也就是每个月的1号
    }

    private void isWeek(int w,int m,int y){
        //这个switch没有用，我这是用来打印星期的
        switch (w){
            case 0://星期日
                Log.i("pwx","星期日 ");
                break;
            case 1://星期一
                Log.i("pwx","星期一 ");
                break;
            case 2://星期二
                Log.i("pwx","星期二 ");
                break;
            case 3://星期三
                Log.i("pwx","星期三 ");
                break;
            case 4://星期四
                Log.i("pwx","星期四 ");
                break;
            case 5://星期五
                Log.i("pwx","星期五 ");
                break;
            case 6://星期六
                Log.i("pwx","星期六 ");
                break;
        }
        strings.clear();
        //这一步的循环是当实际时间不在星期一的位置上做一个预留空间的处理
        if(0 == w){ //特殊情况 星期日
            for (int i = 1; i < 7; i++){
                initSpaceDay();
            }
        }else{
            for (int i = 1; i < w; i++){
                initSpaceDay();
            }
        }

        //这里开始初始化月份了
        for (int i = 1; i <= mouthSum; i++){
            DayAttrEntity dayAttrEntity = new DayAttrEntity();
            dayAttrEntity.setDays(y +"-"+ m +"-"+ i);
            dayAttrEntity.setDayAction(false);
            dayAttrEntity.setHaveCase(false);
            if(m == mouthCurr && y == yearCurr){ //是否是当前月,当前年
                if(i == day){ //判断是否为当前天
                    dayAttrEntity.setCurDay(true);
                }
            }else if(i == 1){ //不是当前月，那么每月的一号高亮显示
                dayAttrEntity.setDayAction(true);
            }
            //因为没有服务器，所以当用户第一次加载日历时，如果某个单位时间有日程，那么就要初始化。目前日程保存在数据库中
            String s = String.valueOf(MyUitls.FormateTimeLong(dayAttrEntity.getDays(),"yyyy-MM-dd"));
            List<CalendaCaseEntity> entities = DbUtil.getInstance().getCalendaCaseEntity(s);
            if(null != entities && 0 < entities.size()){
                dayAttrEntity.setHaveCase(true);
            }
            strings.add(dayAttrEntity);
        }
        Log.i("pwx","list集合里size： "+strings.size());
        gridViewAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化没有日期的格子    置空
     */
    private void initSpaceDay(){
        DayAttrEntity dayAttrEntity = new DayAttrEntity();
        dayAttrEntity.setDays("");
        dayAttrEntity.setCurDay(false);
        dayAttrEntity.setDayAction(false);
        dayAttrEntity.setHaveCase(false);
        strings.add(dayAttrEntity);
    }

    private void initAdapter(){
        gridViewAdapter = new GridViewAdapter(getContext(),strings);
        gridViewAdapter.setCalendarLisnter(new GridViewAdapter.CalendarLisnter() {
            @Override
            public void selectOneDay(String day) {
                //用户点击的事件
                for (DayAttrEntity attrEntity : strings){
                    attrEntity.setDayAction(false);
                    if(attrEntity.getDays().equals(day)){
                        attrEntity.setDayAction(true);
                    }
                }
                gridViewAdapter.notifyDataSetChanged();
                String s = String.valueOf(MyUitls.FormateTimeLong(day,"yyyy-MM-dd"));
                Intent intent=new Intent();
                intent.setAction("case");
                intent.putExtra("day",s);
                getActivity().sendBroadcast(intent);
            }

            @Override
            public void itemLongClickListener(String day) {
                //用户长按添加日程事件
                Intent intent = new Intent(getActivity(), CaseAddActivity.class);
                intent.putExtra("day",day);
                startActivity(intent);
            }
        });
    }


    //这个广播是用来更新对应日期是否有日程，如果有就显示一个原点
    private void initBrodCase(){
        receiver2 = new Receiver2();
        IntentFilter filter = new IntentFilter();
        filter.addAction("dot");
        filter.addAction("dot2");
        getActivity().registerReceiver(receiver2,filter);
    }

    private class Receiver2 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case "dot":
                    String day = intent.getStringExtra("day");
                    for (DayAttrEntity attrEntity : strings){
                        String s = String.valueOf(MyUitls.FormateTimeLong(attrEntity.getDays(),"yyyy-MM-dd"));
                        if(s.equals(day)){
                            attrEntity.setHaveCase(true);
                        }
                    }
                    gridViewAdapter.notifyDataSetChanged();
                    break;

                case "dot2":
                    //删除事件后取消圆点
                    String day2 = intent.getStringExtra("day");
                    for (DayAttrEntity attrEntity : strings){
                        String s = String.valueOf(MyUitls.FormateTimeLong(attrEntity.getDays(),"yyyy-MM-dd"));
                        if(s.equals(day2)){
                            attrEntity.setHaveCase(false);
                        }
                    }
                    gridViewAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
