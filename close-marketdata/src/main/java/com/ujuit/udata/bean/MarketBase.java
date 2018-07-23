package com.ujuit.udata.bean;

import java.util.Date;

import com.ujuit.udata.utils.DateUtils;


/**
 * @Description  日行情基类
 * 
 * @author Erick
 * @date 2017年6月8日
 * @company 有据信息技术有限公司
 * @version 1.0
 */
public class MarketBase {
	private int id;
	private Date trade_date; // 交易日
	private long open_price; // 开盘价
	private long close_price; // 收盘价
	private long high_price; // 最高价
	private long low_price; // 最低价
	private String stock_code; // 股票代码
	private long turnover_vol; // 交易量
	private long turnover_value; // 交易金额
	private long pre_close; // 昨日收盘价
	private String suffix; // 股票代码后缀

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}

	public long getOpen_price() {
		return open_price;
	}

	public void setOpen_price(long open_price) {
		this.open_price = open_price;
	}

	public long getClose_price() {
		return close_price;
	}

	public void setClose_price(long close_price) {
		this.close_price = close_price;
	}

	public long getHigh_price() {
		return high_price;
	}

	public void setHigh_price(long high_price) {
		this.high_price = high_price;
	}

	public long getLow_price() {
		return low_price;
	}

	public void setLow_price(long low_price) {
		this.low_price = low_price;
	}

	public String getStock_code() {
		return stock_code;
	}

	public void setStock_code(String stock_code) {
		this.stock_code = stock_code;
	}

	public long getTurnover_vol() {
		return turnover_vol;
	}

	public void setTurnover_vol(long turnover_vol) {
		this.turnover_vol = turnover_vol;
	}

	public long getPre_close() {
		return pre_close;
	}

	public void setPre_close(long pre_close) {
		this.pre_close = pre_close;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public long getTurnover_value() {
		return turnover_value;
	}

	public void setTurnover_value(long turnover_value) {
		this.turnover_value = turnover_value;
	}

	public String getCsv() {
		StringBuilder sb = new StringBuilder();
		sb.append(DateUtils.getDate(this.getTrade_date())).append(",");
		sb.append(this.getOpen_price()).append(",");
		sb.append(this.getClose_price()).append(",");
		sb.append(this.getHigh_price()).append(",");
		sb.append(this.getLow_price()).append(",");
		sb.append(this.getTurnover_vol()).append(",");
		sb.append(this.getTurnover_value()).append(",");
		sb.append(this.getPre_close()).append(",");
		sb.append(this.getStock_code()).append(".").append(this.getSuffix()).append("\r");
		return sb.toString();
	}


}
