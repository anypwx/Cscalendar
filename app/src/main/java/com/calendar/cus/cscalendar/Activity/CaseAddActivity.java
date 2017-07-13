package com.calendar.cus.cscalendar.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.calendar.cus.cscalendar.R;
import com.calendar.cus.cscalendar.db.DbUtil;
import com.calendar.cus.cscalendar.entity.CalendaCaseEntity;
import com.calendar.cus.cscalendar.utils.MyUitls;

import org.feezu.liuli.timeselector.TimeSelector;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by grg on 2017/3/8.
 */

public class CaseAddActivity extends Activity implements View.OnClickListener {
    private String day;
    private TextView tv_left;
    private TextView tv_title;
    private TextView tv_right;
    private EditText et_case;
    private EditText et_time;
    private Button btn_select;
    private LinearLayout rootView;
    private TimeSelector timeSelector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case);
        day = getIntent().getStringExtra("day");
        initView();
        initListener();
        initTimeSelector();
    }
    private void initView(){
        rootView = (LinearLayout) findViewById(R.id.rootView);
        btn_select = (Button) findViewById(R.id.btn_select);
        et_time = (EditText) findViewById(R.id.et_time);
        et_case = (EditText) findViewById(R.id.et_case);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_title.setText("添加日程");
        tv_right.setText("完成");
        tv_left.setText("返回");

//        et_time.setText(day);
    }
    private void initListener(){
        tv_right.setOnClickListener(this);
        tv_left.setOnClickListener(this);
        btn_select.setOnClickListener(this);
    }

    private void initTimeSelector(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate =  new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        String time2 = MyUitls.formateTimeString(str,2);

         timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                et_time.setText(time);
//                Toast.makeText(getApplicationContext(), time, Toast.LENGTH_LONG).show();
            }
        },day+" "+time2, "2050-12-30 23:59","00:00","23:59");
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_right) {//插入系统日历
            String time = et_time.getText().toString();
            String t2 = MyUitls.formateTimeString(time, 1);
            long tt = MyUitls.FormateTimeLong(time, "yyyy-MM-dd HH:mm");
            MyUitls.addCalendarEvent(this, et_case.getText().toString(), tt);
            //插入数据库
            CalendaCaseEntity calendaCaseEntity = new CalendaCaseEntity();
            calendaCaseEntity.setCaseStr(et_case.getText().toString());
            calendaCaseEntity.setTimeTemp(String.valueOf(MyUitls.FormateTimeLong(t2, "yyyy-MM-dd")));
            calendaCaseEntity.setTimes(MyUitls.formateTimeString(time, 2));
            DbUtil.getInstance().insertCalendaCaseEntity(calendaCaseEntity);
            //更新主页的事件列表
            Intent intent = new Intent();
            intent.setAction("case");
            intent.putExtra("day", String.valueOf(MyUitls.FormateTimeLong(t2, "yyyy-MM-dd")));
            sendBroadcast(intent);
            //更新是否有事件的圆点
            Intent intent2 = new Intent();
            intent2.setAction("dot");
            intent2.putExtra("day", String.valueOf(MyUitls.FormateTimeLong(t2, "yyyy-MM-dd")));
            sendBroadcast(intent2);
            finish();

        } else if (i == R.id.btn_select) {
            timeSelector.setMode(TimeSelector.MODE.YMDHM);
            ;
            timeSelector.show();

        } else if (i == R.id.tv_left) {
            finish();

        }
    }
}
