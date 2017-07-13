package com.calendar.cus.cscalendar.entity;

/**
 *day 天的属性
 * Created by grg on 2017/3/3.
 */

public class DayAttrEntity {
    private String days;
    private boolean isCurDay; //标识是否为当天
    private boolean dayAction; //被点击的day 也可以认为当前被操作的day
    private boolean isHaveCase; //是否有日历

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public boolean isCurDay() {
        return isCurDay;
    }

    public void setCurDay(boolean curDay) {
        isCurDay = curDay;
    }

    public boolean isDayAction() {
        return dayAction;
    }

    public void setDayAction(boolean dayAction) {
        this.dayAction = dayAction;
    }

    public boolean isHaveCase() {
        return isHaveCase;
    }

    public void setHaveCase(boolean haveCase) {
        isHaveCase = haveCase;
    }
}
