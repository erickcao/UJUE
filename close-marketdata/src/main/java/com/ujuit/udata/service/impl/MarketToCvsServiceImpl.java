package com.ujuit.udata.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.ujuit.udata.utils.DateUtils;
import com.ujuit.udata.bean.MarketBase;
import com.ujuit.udata.bean.MarketEqud;
import com.ujuit.udata.bean.MarketIndex;
import com.ujuit.udata.service.MarketToCvsService;
import com.ujuit.udata.utils.CSVUtils;
import com.ujuit.udata.utils.PropertyRead;

@Service
public class MarketToCvsServiceImpl implements MarketToCvsService {
	private final Properties property = PropertyRead.getProperties("/config.properties");
	// 表格标题
	private static final String MARKET_TITLE = "日期,开盘价,收盘价,最高价,最低价,成交量,成交额,前收盘价,股票代码\r";

	public void marketToCvs(List<MarketEqud> mbase) {
		String path = property.getProperty("exportPath");
		path += DateUtils.getDate(System.currentTimeMillis(), DateUtils.YEAR_MONTH_DAY);
		path += "_stock";
		path += ".csv";

		CSVUtils.exportCsv(path, MARKET_TITLE);

		StringBuffer content = new StringBuffer();

		for (MarketEqud tmp : mbase) {
			content.append(tmp.getCsv());
		}

		CSVUtils.exportCsv(path, content.toString());
	}

	public void indexToCvs(List<MarketIndex> mbase) {
		String path = property.getProperty("exportPath");
		path += DateUtils.getDate(System.currentTimeMillis(), DateUtils.YEAR_MONTH_DAY);
		path += "_index";
		path += ".csv";
		CSVUtils.exportCsv(path, MARKET_TITLE);

		StringBuffer content = new StringBuffer();

		for (MarketIndex tmp : mbase) {
			content.append(tmp.getCsv());
		}

		CSVUtils.exportCsv(path, content.toString());
	}

	public static void main(String[] args) {

		List<MarketEqud> mbase = new ArrayList<MarketEqud>();
		for (int i = 0; i < 10000; ++i) {
			MarketEqud m = new MarketEqud();
			m.setClose_price(1000);
			m.setTrade_date(new Date());
			m.setHigh_price(1000000);
			m.setStock_code("000001.SZ");

			mbase.add(m);
		}

		new MarketToCvsServiceImpl().marketToCvs(mbase);
	}

}
