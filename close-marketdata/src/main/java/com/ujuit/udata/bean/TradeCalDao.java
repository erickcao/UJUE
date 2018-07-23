package com.ujuit.udata.bean;

import java.util.Date;

public class TradeCalDao {
    private Integer id;

    private String exchangecd;

    private Date calendardate;

    private Boolean isopen;

    private Date prevtradedate;

    private Boolean isweekend;

    private Boolean ismonthend;

    private Boolean isquarterend;

    private Boolean isyearend;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExchangecd() {
        return exchangecd;
    }

    public void setExchangecd(String exchangecd) {
        this.exchangecd = exchangecd == null ? null : exchangecd.trim();
    }

    public Date getCalendardate() {
        return calendardate;
    }

    public void setCalendardate(Date calendardate) {
        this.calendardate = calendardate;
    }

    public Boolean getIsopen() {
        return isopen;
    }

    public void setIsopen(Boolean isopen) {
        this.isopen = isopen;
    }

    public Date getPrevtradedate() {
        return prevtradedate;
    }

    public void setPrevtradedate(Date prevtradedate) {
        this.prevtradedate = prevtradedate;
    }

    public Boolean getIsweekend() {
        return isweekend;
    }

    public void setIsweekend(Boolean isweekend) {
        this.isweekend = isweekend;
    }

    public Boolean getIsmonthend() {
        return ismonthend;
    }

    public void setIsmonthend(Boolean ismonthend) {
        this.ismonthend = ismonthend;
    }

    public Boolean getIsquarterend() {
        return isquarterend;
    }

    public void setIsquarterend(Boolean isquarterend) {
        this.isquarterend = isquarterend;
    }

    public Boolean getIsyearend() {
        return isyearend;
    }

    public void setIsyearend(Boolean isyearend) {
        this.isyearend = isyearend;
    }
}