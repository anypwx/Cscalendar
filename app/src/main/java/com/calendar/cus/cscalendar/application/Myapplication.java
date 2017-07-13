package com.calendar.cus.cscalendar.application;

import android.app.Application;
import android.content.Context;

import com.calendar.cus.cscalendar.db.GreenDaoManager;
import com.calendar.cus.cscalendar.utils.AppCache;

/**
 * Created by grg on 2017/3/7.
 */

public class Myapplication extends Application{
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        GreenDaoManager.getInstance();
        AppCache.getInstance();
    }

    public static Context getmContext(){
        return mContext;
    }
}
