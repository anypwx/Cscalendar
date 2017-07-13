package com.calendar.cus.cscalendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.calendar.cus.cscalendar.application.Myapplication;
import com.calendar.cus.cscalendar.db.DbUtil;
import com.calendar.cus.cscalendar.entity.CalendaCaseEntity;
import com.calendar.cus.cscalendar.fragment.CalendarFragment;
import com.calendar.cus.cscalendar.interfacesc.ICaseToCalendar;
import com.calendar.cus.cscalendar.interfacesc.impl.CaseToCalendarImpl;
import com.calendar.cus.cscalendar.mview.CaseView;
import com.calendar.cus.cscalendar.mview.MyDiaLog;
import com.calendar.cus.cscalendar.utils.AppCache;
import com.calendar.cus.cscalendar.utils.MyUitls;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    private String dateStr = "";
    private int yearFlag = 0; //记录年  年量级  每12个月为 一个量级
    private int year;
    private int mouth;
    private int day;
    private int maxDate = 1000;
    private int flag = maxDate / 2;
    private ViewPager vp_test;
    private Calendar calendar;
    private TextView tv_title;
    private TextView tv_right;
    private TextView tv_left;
    private LinearLayout ll_case_content;
    private Receiver receiver;
    private FragmentAdapter fragmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initBrodCase();
        String s = String.valueOf(MyUitls.FormateTimeLong(dateStr,"yyyy-MM-dd"));
        initCase(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void initView(){
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR); //当前年
        mouth = calendar.get(Calendar.MONTH) + 1; //java的月份是从0开始计算的
        day = calendar.get(Calendar.DAY_OF_MONTH);//当前天
        dateStr = year+"-"+mouth+"-"+day;
        vp_test = (ViewPager) findViewById(R.id.vp_test);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_left = (TextView) findViewById(R.id.tv_left);
        ll_case_content = (LinearLayout) findViewById(R.id.ll_case_content);
        vp_test.addOnPageChangeListener(this);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        vp_test.setAdapter(fragmentAdapter);
        vp_test.setCurrentItem(flag);

        tv_right.setVisibility(View.GONE);
        tv_left.setVisibility(View.GONE);

    }

    private void initCase(String day){
        ll_case_content.removeAllViews();
        List<CalendaCaseEntity> entities = DbUtil.getInstance().getCalendaCaseEntity(day);
        for (final CalendaCaseEntity entity : entities){
            final CaseView caseView = new CaseView(this);
            caseView.setCase(entity.getCaseStr());
            caseView.setTime(entity.getTimes());
            caseView.setTag(entity.getId());
            caseView.setViewItemListener(new CaseView.ViewItemListener() {
                @Override
                public void LongClick(final String caseStr, final long id) {
                    final MyDiaLog myDiaLog = new MyDiaLog();
                    Bundle bundle = new Bundle();
                    bundle.putString("text","是否确定要删除此日程!");
                    myDiaLog.setArguments(bundle);
                    myDiaLog.setDiaLogBtnListener(new MyDiaLog.DiaLogBtnListener() {
                        @Override
                        public void leftBtn() {
                            //从系统日历中删除
                            MyUitls.deleteCalendarEvent(MainActivity.this,caseStr);
                            //从数据库中删除
                            DbUtil.getInstance().delCalendaCaseEntity(id);

                            Intent intent2 = new Intent();
                            intent2.setAction("dot2");
                            intent2.putExtra("day",entity.getTimeTemp());
                            sendBroadcast(intent2);

                            ll_case_content.removeView(caseView);
                            myDiaLog.dismiss();

                        }

                        @Override
                        public void rightBtn() {
                            myDiaLog.dismiss();
                        }
                    });
                    myDiaLog.show(getSupportFragmentManager(),"dialog");
                }
            });
            ll_case_content.addView(caseView);
        }
    }

    //这个广播是控制显示日程的
    private void initBrodCase(){
        receiver = new Receiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("case");
        registerReceiver(receiver,filter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.i("aaa","pos:"+position+", positionOffset: "+positionOffset+",positionOffsetPixels: "+positionOffsetPixels);
    }


    @Override
    public void onPageSelected(int position) {
        if(flag < position){ //左
            Log.i("pwx","滑动方向:左");
            flag = position;
            mouth++;
            if(12 < mouth){
                mouth = 1;
                yearFlag++;
            }
        }else if(flag > position){  //右
            Log.i("pwx","滑动方向:右");
            flag = position;
            mouth--;
            if(1 > mouth){
                mouth = 12;
                yearFlag--;
            }
        }else {
            mouth = calendar.get(Calendar.MONTH)+1; //java的月份是从0开始计算的;
        }

        //这里每经过一个年（12个月）就是增加或者减少1
        year = year+yearFlag;
        yearFlag = 0; //这里的年量级需要重置
        tv_title.setText(year+"年"+mouth+"月");
        fragmentAdapter.notifyDataSetChanged();
        AppCache.getInstance().putString("timeStr",year+"-"+mouth);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }





    private class FragmentAdapter extends FragmentStatePagerAdapter{

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            return CalendarFragment.createFragment(mouth);
            return getMouthFragment(position);
        }

        @Override
        public int getCount() {
            return maxDate;
        }
    }


    //viewPager预加载处理
    private Fragment getMouthFragment(int pos){
        if(pos == flag){
            Log.i("pwx1","-----mouth3333333:"+mouth);
            return CalendarFragment.createFragment(mouth,year);
        }
        if(pos > flag){
            Log.i("pwx1","-----mouth11111:"+mouth);
            return CalendarFragment.createFragment(mouth + 1,year);
        }
        if(pos < flag){
            Log.i("pwx1","-----mouth222222:"+mouth);
            return CalendarFragment.createFragment(mouth - 1,year);
        }
        return CalendarFragment.createFragment(mouth,year);
    }


    private class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case "case":
                    String day = intent.getStringExtra("day");
                    initCase(day);
                    break;
                case "save":

                    break;
            }
        }
    }
}
