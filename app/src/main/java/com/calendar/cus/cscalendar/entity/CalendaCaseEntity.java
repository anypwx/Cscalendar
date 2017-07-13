package com.calendar.cus.cscalendar.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by grg on 2017/3/7.
 */

@Entity
public class CalendaCaseEntity {
    @Id(autoincrement = true)
    private Long id;
    private String caseStr;
    private String timeTemp;//日期
    private String times; //时分
    @Generated(hash = 734232889)
    public CalendaCaseEntity(Long id, String caseStr, String timeTemp,
            String times) {
        this.id = id;
        this.caseStr = caseStr;
        this.timeTemp = timeTemp;
        this.times = times;
    }
    @Generated(hash = 602418900)
    public CalendaCaseEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCaseStr() {
        return this.caseStr;
    }
    public void setCaseStr(String caseStr) {
        this.caseStr = caseStr;
    }
    public String getTimeTemp() {
        return this.timeTemp;
    }
    public void setTimeTemp(String timeTemp) {
        this.timeTemp = timeTemp;
    }
    public String getTimes() {
        return this.times;
    }
    public void setTimes(String times) {
        this.times = times;
    }
}
