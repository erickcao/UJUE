package com.ujuit.udata.bean;

import java.util.Date;

public class TerminateBean {
    private Integer id;

    private String code;

    private Integer exchangeBasicinfoId;

    private Integer stockCategoryId;

    private Date createTime;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getExchangeBasicinfoId() {
        return exchangeBasicinfoId;
    }

    public void setExchangeBasicinfoId(Integer exchangeBasicinfoId) {
        this.exchangeBasicinfoId = exchangeBasicinfoId;
    }

    public Integer getStockCategoryId() {
        return stockCategoryId;
    }

    public void setStockCategoryId(Integer stockCategoryId) {
        this.stockCategoryId = stockCategoryId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}