package com.calendar.cus.cscalendar.db;

import com.calendar.cus.cscalendar.entity.CalendaCaseEntity;
import com.calendar.cus.cscalendar.entity.CalendaCaseEntityDao;

import java.util.List;

/**
 * Created by grg on 2017/3/7.
 */

public class DbUtil {

    private static class SingleInstanceHolder{
        private static final DbUtil INSTANCE = new DbUtil();
    }

    public static DbUtil getInstance(){
        return DbUtil.SingleInstanceHolder.INSTANCE;
    }

    /**
     * 查询全部
     * @return
     */
    public List<CalendaCaseEntity> getCalendaCaseEntityAll(){
        List<CalendaCaseEntity> caseEntities =
                GreenDaoManager.getInstance().getDaoSession()
                        .getCalendaCaseEntityDao().loadAll();
        return caseEntities;
    }

    /**
     * 通过ID查找
     * @param id
     * @return
     */
    public CalendaCaseEntity getCalendaCaseEntity(Long id){
        CalendaCaseEntity caseEntities =
                GreenDaoManager.getInstance().getDaoSession()
                        .getCalendaCaseEntityDao().load(id);
        return caseEntities;
    }

    /**
     * 根据条件查询
     * @param s
     * @return
     */
    public List<CalendaCaseEntity> getCalendaCaseEntity(String s){
        List<CalendaCaseEntity> caseEntities =
                GreenDaoManager.getInstance().getDaoSession()
                        .getCalendaCaseEntityDao().queryBuilder()
                        .where(CalendaCaseEntityDao.Properties.TimeTemp.eq(s))
                        .orderAsc(CalendaCaseEntityDao.Properties.TimeTemp).list();
        return caseEntities;
    }

    /**
     * 更新一条或多条数据
     * @param entity
     */
    public void updateCalendaCaseEntity(CalendaCaseEntity... entity){
        GreenDaoManager.getInstance().getDaoSession()
                        .getCalendaCaseEntityDao().updateInTx(entity);
    }

    /**
     * By  CalendaCaseEntity删除一条或多条
     * @param entity
     */
    public void delCalendaCaseEntity(CalendaCaseEntity... entity){
        GreenDaoManager.getInstance().getDaoSession()
                .getCalendaCaseEntityDao().deleteInTx(entity);
    }

    /**
     * By  CalendaCaseEntity删除一条或多条
     * @param id
     */
    public void delCalendaCaseEntity(Long... id){
        GreenDaoManager.getInstance().getDaoSession()
                .getCalendaCaseEntityDao().deleteByKeyInTx(id);
    }


    /**
     * 插入一条或多条数据
     * @param entity
     */
    public void insertCalendaCaseEntity(CalendaCaseEntity... entity){
        GreenDaoManager.getInstance().getDaoSession()
                .getCalendaCaseEntityDao().insertInTx(entity);
    }

    /**
     * 插入或替换一条或多条数据
     * @param entity
     */
    public void insertOrReplaceCalendaCaseEntity(CalendaCaseEntity... entity){
        GreenDaoManager.getInstance().getDaoSession()
                .getCalendaCaseEntityDao().insertOrReplaceInTx(entity);
    }
}
