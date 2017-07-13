package com.calendar.cus.cscalendar.db;

import com.calendar.cus.cscalendar.application.Myapplication;
import com.calendar.cus.cscalendar.entity.DaoMaster;
import com.calendar.cus.cscalendar.entity.DaoSession;

/**
 * Created by grg on 2017/3/7.
 */

public class GreenDaoManager {
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private GreenDaoManager(){
        initDB();
    }

    private static class SingleInstanceHolder{
        private static final GreenDaoManager INSTANCE = new GreenDaoManager();
    }

    public static GreenDaoManager getInstance(){
        return SingleInstanceHolder.INSTANCE;
    }

    private void initDB(){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(Myapplication.getmContext(),"case-db");
        daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
    }


    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
