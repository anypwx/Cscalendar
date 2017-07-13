package com.calendar.cus.cscalendar.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.calendar.cus.cscalendar.application.Myapplication;

/**
 * Created by grg on 2017/3/8.
 */

public class AppCache {
    private static AppCache appCache;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;
    public AppCache() {
        getEditor();
    }

    public static AppCache getInstance(){
        if(null == appCache){
            appCache = new AppCache();
        }else {
           return appCache;
        }
        return appCache;
    }

    private void getEditor(){
            sp = Myapplication.getmContext()
                .getSharedPreferences("app",Context.MODE_PRIVATE);
            editor = sp.edit();
    }

    public void putString(String k,String v){
        editor.putString(k,v);
		editor.apply();
    }

    public void putInt(String k,int v){
        editor.putInt(k,v);
		editor.apply();
    }

    public void putBoolean(String k,boolean v){
        editor.putBoolean(k,v);
		editor.apply();
    }

    public void putFloat(String k,float v){
        editor.putFloat(k,v);
		editor.apply();
    }

    public void putLong(String k,long v){
        editor.putLong(k,v);
		editor.apply();
    }



    public String getString(String k,String defaul){
        return sp.getString(k,defaul);
    }

    public int getInt(String k,int defaul){
        return sp.getInt(k,defaul);
    }

    public boolean getBoolean(String k,boolean defaul){
        return sp.getBoolean(k,defaul);
    }

    public float getFloat(String k,float defaul){
        return sp.getFloat(k,defaul);
    }

    public long getLong(String k,long defaul){
        return sp.getLong(k,defaul);
    }


}
