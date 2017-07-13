package com.calendar.cus.cscalendar.interfacesc.impl;

import android.content.Context;

import com.calendar.cus.cscalendar.interfacesc.ICaseToCalendar;
import com.calendar.cus.cscalendar.utils.MyUitls;

/**
 * Created by grg on 2017/3/10.
 */

public class CaseToCalendarImpl implements ICaseToCalendar {
    @Override
    public void toSysCalendar(Context context, String caseStr, long startTime) {
        MyUitls.addCalendarEvent(context,caseStr.toString(),startTime);
    }

    @Override
    public void delFromSysCalendar(Context context, String caseStr) {
        MyUitls.deleteCalendarEvent(context,caseStr);
    }
}
