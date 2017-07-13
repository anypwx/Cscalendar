package com.calendar.cus.cscalendar.utils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import com.calendar.cus.cscalendar.application.Myapplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by grg on 2017/3/8.
 */

public class MyUitls {
    private static long ONE_HOURS = 60 * 60 * 1000;

    private static String CALANDER_URL = "content://com.android.calendar/calendars";
    private static String CALANDER_EVENT_URL = "content://com.android.calendar/events";
    private static String CALANDER_REMIDER_URL = "content://com.android.calendar/reminders";

    private static String CALENDARS_NAME = "test";
    private static String CALENDARS_ACCOUNT_NAME = "test@gmail.com";
    private static String CALENDARS_ACCOUNT_TYPE = "com.android.exchange";
    private static String CALENDARS_DISPLAY_NAME = "测试账户";




    /**
     * 时间格式
     * @param s
     * @return
     */
    public static String FormateTime(String s){
        String str = "";
        if(null != s && !"".equals(s)){
            String[] arr = s.split("-");
            str = arr[2];
        }
        return str;
    }


    public static long FormateTimeLong(String s,String fa){
        long str = -1;
        if(null != s && !"".equals(s)){
            SimpleDateFormat formatter = new SimpleDateFormat(fa);
            try {
                str = formatter.parse(s).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return str;
    }


    /**
     *  flag 为1  返回年月日  否则 返回时分
     * @param s
     * @return
     */
    public static String formateTimeString(String s,int flag){
        String str = "";
        SimpleDateFormat formatter = null;
        SimpleDateFormat formatter2;
        formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            long lo = formatter2.parse(s).getTime();
            Date curDate =  new Date(lo);
            if(null != s && !"".equals(s)){
                if(1 == flag){
                    formatter = new SimpleDateFormat("yyyy-MM-dd");
                }else {
                    formatter = new SimpleDateFormat("HH:mm");
                }

            }
            str = formatter.format(curDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    /**
     * 检查是否存在日历账户  存在返回账户id，不存在返回 -1
     * @param context
     * @return
     */
    private static int checkCalendarAccount(Context context){
        Cursor cursor = context.getContentResolver().query(Uri.parse(CALANDER_URL),null,null,null,null);
        if(null == cursor){
            return -1;
        }
        try{
            int count = cursor.getCount();
            if(0 < count){
                cursor.moveToFirst();
                return cursor.getInt(cursor.getColumnIndex(CalendarContract.Calendars._ID));
            }else {
                return -1;
            }

        }finally {
            if(null != cursor){
                cursor.close();
            }
        }
    }


    /**
     * 添加账户。账户创建成功则返回账户id，否则返回-1
     * @param context
     * @return
     */
    private static long addCalendarAccount(Context context){
        TimeZone timeZone = TimeZone.getDefault();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Calendars.NAME,CALENDARS_NAME);
        values.put(CalendarContract.Calendars.ACCOUNT_NAME,CALENDARS_ACCOUNT_NAME);
        values.put(CalendarContract.Calendars.ACCOUNT_TYPE,CALENDARS_ACCOUNT_TYPE);
        values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDARS_DISPLAY_NAME);
        values.put(CalendarContract.Calendars.VISIBLE, 1);
        values.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE);
        values.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        values.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        values.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        values.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME);
        values.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);
        Uri uri = Uri.parse(CALANDER_URL);
        uri = uri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
                .build();

        Uri result = context.getContentResolver().insert(uri, values);
        long id = result == null ? -1 : ContentUris.parseId(result);
        return id;

    }

    private static int checkAndAddCalendarAccount(Context context){
        int oldId = checkCalendarAccount(context);
        if( oldId >= 0 ){
            return oldId;
        }else{
            long addId = addCalendarAccount(context);
            if (addId >= 0) {
                return checkCalendarAccount(context);
            } else {
                return -1;
            }
        }
    }


    /**
     * 添加日程到系统日历
     * @param title
     * @param beginTime
     */
    public static void addCalendarEvent(Context context,String title, long beginTime){
        int calId = MyUitls.checkAndAddCalendarAccount(Myapplication.getmContext());
        if(0 > calId){ //获取日历账户失败则不能添加case
            Log.i("pwx","获取日历账户失败则不能添加case");
            return;
        }
        ContentValues event = new ContentValues();
        event.put("title", title);
        event.put("description", "");
        // 插入账户的id
        event.put("calendar_id", calId);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(beginTime);//设置开始时间
        long start = mCalendar.getTime().getTime();
        mCalendar.setTimeInMillis(start + ONE_HOURS);//设置终止时间
        long end = mCalendar.getTime().getTime();
        event.put(CalendarContract.Events.DTSTART, start);
        event.put(CalendarContract.Events.DTEND, end);
        event.put(CalendarContract.Events.HAS_ALARM, 1);//设置有闹钟提醒
        event.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai");  //这个是时区，必须有\
        //添加事件
        Uri newEvent = context.getContentResolver().insert(Uri.parse(CALANDER_EVENT_URL), event);
        if (newEvent == null) {
            // 添加日历事件失败直接返回
            Log.i("pwx","添加日历事件失败直接返回");
            return;
        }

        //事件提醒的设定
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, ContentUris.parseId(newEvent));
        // 提前10分钟有提醒
        values.put(CalendarContract.Reminders.MINUTES, 10);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        Uri uri = context.getContentResolver().insert(Uri.parse(CALANDER_REMIDER_URL), values);
        if(uri == null) {
            // 添加闹钟提醒失败直接返回
            Log.i("pwx","添加闹钟提醒失败直接返回");
            return;
        }
    }

    /**
     * 从系统日历中删除日程，，包括在本地数据库中
     * @param context
     * @param title
     */
    public static void deleteCalendarEvent(Context context,String title){
        Cursor eventCursor = context.getContentResolver().query(Uri.parse(CALANDER_EVENT_URL), null, null, null, null);
        try {
            if (eventCursor == null)//查询返回空值
                return;
            if (eventCursor.getCount() > 0) {
                //遍历所有事件，找到title跟需要查询的title一样的项
                for (eventCursor.moveToFirst(); !eventCursor.isAfterLast(); eventCursor.moveToNext()) {
                    String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
                    if (!title.isEmpty() && title.equals(eventTitle)) {
                        int id = eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Calendars._ID));//取得id
                        Uri deleteUri = ContentUris.withAppendedId(Uri.parse(CALANDER_EVENT_URL), id);
                        int rows = context.getContentResolver().delete(deleteUri, null, null);
                        if (rows == -1) {
                            //事件删除失败
                            Log.i("pwx","事件删除失败");
                            return;
                        }
                    }
                }
            }
        } finally {
            if (eventCursor != null) {
                eventCursor.close();
            }
        }
    }


}
