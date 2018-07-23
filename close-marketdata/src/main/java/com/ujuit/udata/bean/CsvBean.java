package com.ujuit.udata.bean;

import com.opencsv.bean.CsvBind;

/**
 * @Description
 * 
 * @author luyang
 * @date 2017年5月23日
 * @company 有据信息技术有限公司
 * @version 1.0
 */
public class CsvBean {
	
	@CsvBind
	private String trade_date; // 交易日
	@CsvBind
	private String open_price; // 开盘价
	@CsvBind
	private String close_price; // 收盘价
	@CsvBind
	private String high_price; // 最高价
	@CsvBind
	private String low_price; // 最低价
	@CsvBind
	private String turnover_vol; // 交易量
	@CsvBind
	private String turnover_value; // 交易金额
	@CsvBind
	private String pre_close; // 昨日收盘价
	@CsvBind
	private String stock_code; // 股票代码

	public String getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(String trade_date) {
		this.trade_date = trade_date;
	}

	public String getOpen_price() {
		return open_price;
	}

	public void setOpen_price(String open_price) {
		this.open_price = open_price;
	}

	public String getClose_price() {
		return close_price;
	}

	public void setClose_price(String close_price) {
		this.close_price = close_price;
	}

	public String getHigh_price() {
		return high_price;
	}

	public void setHigh_price(String high_price) {
		this.high_price = high_price;
	}

	public String getLow_price() {
		return low_price;
	}

	public void setLow_price(String low_price) {
		this.low_price = low_price;
	}

	public String getStock_code() {
		return stock_code;
	}

	public void setStock_code(String stock_code) {
		this.stock_code = stock_code;
	}

	public String getTurnover_vol() {
		return turnover_vol;
	}

	public void setTurnover_vol(String turnover_vol) {
		this.turnover_vol = turnover_vol;
	}

	public String getTurnover_value() {
		return turnover_value;
	}

	public void setTurnover_value(String turnover_value) {
		this.turnover_value = turnover_value;
	}

	public String getPre_close() {
		return pre_close;
	}

	public void setPre_close(String pre_close) {
		this.pre_close = pre_close;
	}
}
