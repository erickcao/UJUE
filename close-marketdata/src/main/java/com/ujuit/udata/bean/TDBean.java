package com.ujuit.udata.bean;

/**
 * @Description  交易日实体类
 * 
 * @author Erick
 * @date 2017年12月29日
 * @company 有据信息技术有限公司
 * @version 1.0
 */
public class TDBean {
	private int code;
	private TDDataBean data;
	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public TDDataBean getData() {
		return data;
	}

	public void setData(TDDataBean data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public class TDDataBean { // 内部类
		private String lastTradeDay;
		private String nextTradeDay;
		private String isOpen;

		public String getLastTradeDay() {
			return lastTradeDay;
		}

		public void setLastTradeDay(String lastTradeDay) {
			this.lastTradeDay = lastTradeDay;
		}

		public String getNextTradeDay() {
			return nextTradeDay;
		}

		public void setNextTradeDay(String nextTradeDay) {
			this.nextTradeDay = nextTradeDay;
		}

		public String getIsOpen() {
			return isOpen;
		}

		public void setIsOpen(String isOpen) {
			this.isOpen = isOpen;
		}
	}
}
