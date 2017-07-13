//package com.calendar.cus.cscalendar.mview;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.support.v4.view.ViewPager;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.GridView;
//import android.widget.LinearLayout;
//
//import com.calendar.cus.cscalendar.R;
//import com.calendar.cus.cscalendar.adapter.GridViewAdapter;
//import com.calendar.cus.cscalendar.entity.DayAttrEntity;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
///** 日历view
// * Created by grg on 2017/3/2.
// */
//
//public class CalendarView extends LinearLayout{
////    private int year;
////    private int mouth;
//    private int day;
//    private int initDay = 1;
//    private int mouthSum;
//    private List<DayAttrEntity> strings = new ArrayList<>();
//    private GridView vp_container;
//    private GridViewAdapter gridViewAdapter;
//
//
//    public CalendarView(Context context) {
//        super(context);
//    }
//
//    public CalendarView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        View view = LayoutInflater.from(context).inflate(R.layout.view_calendar,this);
//        vp_container = (GridView) view.findViewById(R.id.vp_container);
//
//    }
//
//    public void initCalendarViews(int m){
//        initCalendarView(m);
//    }
//
//    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//    }
//
//    private void initCalendarView(int mouth){
//        //先计算出当月的1号是星期几，这样为了方便排版
//        Calendar calendar = Calendar.getInstance();
////        //先获取当前系统的具体年月日
//        int year = calendar.get(Calendar.YEAR);
////        mouth = calendar.get(Calendar.MONTH)+1; //java的月份是从0开始计算的
//        day = calendar.get(Calendar.DAY_OF_MONTH);  //当前天
//        //在指定某个年月日  计算出实际的周几？
//        calendar.set(Calendar.YEAR,year);
//        calendar.set(Calendar.MONTH,mouth - 1);
//        calendar.set(Calendar.DAY_OF_MONTH,initDay);
//        mouthSum = calendar.getActualMaximum(Calendar.DATE); //当前月有多少天
//        Log.i("pwx","当前"+(mouth)+"月有"+mouthSum+"天");
//        int weekNo = calendar.get(Calendar.DAY_OF_WEEK);//计算出周几
//        initAdapter();
//        vp_container.setNumColumns(7);
//        vp_container.setAdapter(gridViewAdapter);
//        isWeek(weekNo - 1); // 初始化起始位置
//    }
//
//
//    private void isWeek(int w){
//        switch (w){
//            case 0://星期日
//                Log.i("pwx","星期日 ");
//                break;
//            case 1://星期一
//                Log.i("pwx","星期一 ");
//                break;
//            case 2://星期二
//                Log.i("pwx","星期二 ");
//                break;
//            case 3://星期三
//                Log.i("pwx","星期三 ");
//                break;
//            case 4://星期四
//                Log.i("pwx","星期四 ");
//                break;
//            case 5://星期五
//                Log.i("pwx","星期五 ");
//                break;
//            case 6://星期六
//                Log.i("pwx","星期六 ");
//                break;
//        }
//        strings.clear();
//        //这一步的循环是当实际时间不在星期一的位置上做一个预留空间的处理
//        if(0 == w){ //特殊情况 星期日
//            for (int i = 1; i < 7; i++){
//                DayAttrEntity dayAttrEntity = new DayAttrEntity();
//                dayAttrEntity.setDays(1002);
//                dayAttrEntity.setCurDay(false);
//                dayAttrEntity.setDayAction(false);
//                strings.add(dayAttrEntity);
//            }
//        }else{
//            for (int i = 1; i < w; i++){
//                DayAttrEntity dayAttrEntity = new DayAttrEntity();
//                dayAttrEntity.setDays(1002);
//                dayAttrEntity.setCurDay(false);
//                dayAttrEntity.setDayAction(false);
//                strings.add(dayAttrEntity);
//            }
//        }
//
//        for (int i = 1; i <= mouthSum; i++){
//            DayAttrEntity dayAttrEntity = new DayAttrEntity();
//            if(i == day){ //判断是否为当前天
//                dayAttrEntity.setCurDay(true);
//            }
//            dayAttrEntity.setDays(i);
//            dayAttrEntity.setDayAction(false);
//            strings.add(dayAttrEntity);
//        }
//        Log.i("pwx","list集合里size： "+strings.size());
//        gridViewAdapter.notifyDataSetChanged();
//    }
//
//    private void initAdapter(){
//        gridViewAdapter = new GridViewAdapter(getContext(),strings);
//        gridViewAdapter.setCalendarLisnter(new GridViewAdapter.CalendarLisnter() {
//            @Override
//            public void selectOneDay(int day) {
//                for (DayAttrEntity attrEntity : strings){
//                    attrEntity.setDayAction(false);
//                    if(attrEntity.getDays() == day){
//                        attrEntity.setDayAction(true);
//                    }
//                }
//                gridViewAdapter.notifyDataSetChanged();
//            }
//
//        });
//    }
//}
