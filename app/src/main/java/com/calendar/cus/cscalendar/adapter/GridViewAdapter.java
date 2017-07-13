package com.calendar.cus.cscalendar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.calendar.cus.cscalendar.R;
import com.calendar.cus.cscalendar.entity.DayAttrEntity;
import com.calendar.cus.cscalendar.utils.MyUitls;

import java.util.List;

/**
 * Created by grg on 2017/3/2.
 */

public class GridViewAdapter extends BaseAdapter {
    private List<DayAttrEntity> strings;
    private LayoutInflater mInflater;
    private CalendarLisnter calendarLisnter;

    public GridViewAdapter(Context context, List<DayAttrEntity> strings) {
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
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoler viewHoler;
        if(null == view){
            view = mInflater.inflate(R.layout.view_day,viewGroup,false);
            viewHoler = new ViewHoler(view);
            view.setTag(viewHoler);
        }else{
            viewHoler = (ViewHoler) view.getTag();
        }
        final DayAttrEntity dayAttrEntity = strings.get(i);
//        if(dayAttrEntity.getDays().equals("1002")){
//            viewHoler.tv_day.setText("");
//        }else
        if(dayAttrEntity.isCurDay() && !dayAttrEntity.isDayAction()){
            viewHoler.rl_view.setBackgroundResource(R.color.blue);
//            viewHoler.tv_day.setText(""+ MyUitls.FormateTime(dayAttrEntity.getDays()));
        }else if(!dayAttrEntity.isCurDay() && dayAttrEntity.isDayAction()){
            viewHoler.rl_view.setBackgroundResource(R.color.red);
//            viewHoler.tv_day.setText(""+MyUitls.FormateTime(dayAttrEntity.getDays()));
        }else if(dayAttrEntity.isCurDay() ){
            viewHoler.rl_view.setBackgroundResource(R.color.blue);
//            viewHoler.tv_day.setText(""+MyUitls.FormateTime(dayAttrEntity.getDays()));
        }else{
            viewHoler.rl_view.setBackgroundResource(R.color.beige);
//            viewHoler.tv_day.setText(""+MyUitls.FormateTime(dayAttrEntity.getDays()));
        }
        if(dayAttrEntity.isHaveCase()){ //此日期是否存在日程
            viewHoler.case_dot.setVisibility(View.VISIBLE);
        }else {
            viewHoler.case_dot.setVisibility(View.GONE);
        }
        viewHoler.tv_day.setText(""+ MyUitls.FormateTime(dayAttrEntity.getDays()));
        viewHoler.tv_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != calendarLisnter){
                    calendarLisnter.selectOneDay(dayAttrEntity.getDays());
                }
            }
        });

        viewHoler.tv_day.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(null != calendarLisnter){
                    calendarLisnter.itemLongClickListener(dayAttrEntity.getDays());
                }
                return false;
            }
        });
        return view;
    }

    class ViewHoler{
        private TextView tv_day;
        private TextView case_dot;
        private RelativeLayout rl_view;
        public ViewHoler(View view) {
            tv_day = (TextView) view.findViewById(R.id.tv_day);
            case_dot = (TextView) view.findViewById(R.id.case_dot);
            rl_view = (RelativeLayout) view.findViewById(R.id.rl_view);
        }
    }

    public void setCalendarLisnter(CalendarLisnter calendarLisnter){
        this.calendarLisnter = calendarLisnter;
    }

    public interface CalendarLisnter{
        void selectOneDay(String day);
        void itemLongClickListener(String day);
    }
}
