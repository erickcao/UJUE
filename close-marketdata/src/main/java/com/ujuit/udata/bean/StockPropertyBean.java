package com.ujuit.udata.bean;

import java.util.Date;

public class StockPropertyBean {
	private Integer id;

	private Date date;

	private Integer stockBasicinfoId;

	private Date createTime;

	private String property;

	private TerminateBean tmb;//股票信息

	public TerminateBean getTmb() {
		return tmb;
	}

	public void setTmb(TerminateBean tmb) {
		this.tmb = tmb;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getStockBasicinfoId() {
		return stockBasicinfoId;
	}

	public void setStockBasicinfoId(Integer stockBasicinfoId) {
		this.stockBasicinfoId = stockBasicinfoId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property == null ? null : property.trim();
	}
}