package com.calendar.cus.cscalendar.interfacesc;

import android.content.Context;

/**
 * Created by grg on 2017/3/10.
 */

public interface ICaseToCalendar {
    void toSysCalendar(Context context,String caseStr,long startTime);
    void delFromSysCalendar(Context context,String caseStr);
}
