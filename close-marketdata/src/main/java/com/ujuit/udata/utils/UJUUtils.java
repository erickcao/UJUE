package com.ujuit.udata.utils;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.ujuit.udata.bean.TDBean;

public class UJUUtils {

	public static TDBean tradingDayBean = null;

	/**
	 * @Description 当前日期是否为交易日
	 * @return
	 * @return
	 * @date 2017年12月29日
	 * @author Erick
	 */
	public static boolean isTradingDay() {
		boolean isTradingDay = false;
		boolean isSucc = false;

		int count = 0;

		// 请求当前日期是否为交易日
		// https://apicenter.ujuqt.com/api-center/data/queryTradeCal.m?userId=100000000&tradeDate=20171207

		while (!isSucc) {
			try {
				String url = "https://apicenter.ujuqt.com/api-center/data/queryTradeCal.m?userId=100000000&tradeDate="
						+ DateUtils.format("yyyyMMdd", new Date());
				String s = SimpleHttpUtils.get(url);
				tradingDayBean = JSON.parseObject(s, TDBean.class);
				if (tradingDayBean == null) {
					isSucc = false;
				} else {
					isSucc = true;
				}

				Thread.sleep(10000);

				if (++count == 10) {
					break;
				}
			} catch (Exception e) {
				isSucc = false;
				e.printStackTrace();
			}
		}

		if (tradingDayBean != null) {
			isTradingDay = tradingDayBean.getData().getIsOpen().equals("1") ? true : false;
		}
		
		return isTradingDay;
	}
}
