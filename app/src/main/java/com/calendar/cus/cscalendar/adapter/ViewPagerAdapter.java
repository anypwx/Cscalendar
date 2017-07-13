package com.calendar.cus.cscalendar.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by grg on 2017/3/6.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private List<TextView> calendarViews;
    public ViewPagerAdapter(List<TextView> calendarViews) {
        this.calendarViews = calendarViews;
    }

    @Override
    public int getCount() {
        return calendarViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        calendarViews.remove(calendarViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(calendarViews.get(position));
        return calendarViews.get(position);
    }
}
