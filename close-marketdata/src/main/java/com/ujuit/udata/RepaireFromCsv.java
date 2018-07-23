package com.ujuit.udata;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ujuit.udata.bean.CsvBean;
import com.ujuit.udata.bean.MarketBase;
import com.ujuit.udata.bean.MarketEqud;
import com.ujuit.udata.bean.MarketIndex;
import com.ujuit.udata.bean.StockPropertyBean;
import com.ujuit.udata.dao.MarketEqudDao;
import com.ujuit.udata.dao.StockPropertyBeanMapper;
import com.ujuit.udata.service.MarketEqudService;
import com.ujuit.udata.service.MarketIndexService;
import com.ujuit.udata.service.MarketToCvsService;
import com.ujuit.udata.utils.CVSReader;
import com.ujuit.udata.utils.DateUtils;
import com.ujuit.udata.utils.PropertyRead;
import com.ujuit.udata.utils.UJUUtils;

/**
 * @Description 通过csv修复某日的股票
 * @author Erick
 * @date 2018年5月11日
 * @company 有据信息技术有限公司
 * @version 1.0
 */

//重要说明：
//必须要配置好文件路径和类型

public class RepaireFromCsv {
	@Autowired
	MarketEqudService marketEqudService;

	@Autowired
	MarketIndexService marketIndexService;

	@Autowired
	MarketToCvsService marketToCvsService;

	@Autowired
	StockPropertyBeanMapper spbDao;// 获取所有终止上市的股票

	@Autowired
	MarketEqudDao marketDao;

	// 股票行情缓存
	public static List<MarketEqud> cacheMarketData = new ArrayList<MarketEqud>();
	// 指数行情缓存
	public static List<MarketIndex> cacheIndexData = new ArrayList<MarketIndex>();

	private final Properties property = PropertyRead.getProperties("/config.properties");

	public void doTask() {

		String path = property.getProperty("repairePath");
		String type = property.getProperty("repaireType");

		System.out.println("通过csv修复行情文件");

		// 通过指定路径和类型
		List<CsvBean> result = new ArrayList<CsvBean>();
		CVSReader reader = new CVSReader(path);
		result = reader.parsToBean(2);

		for (CsvBean tmp : result) {
			if (type.equals("stock")) {
				MarketEqud equd = new MarketEqud();
				equd.setOpen_price(Long.parseLong(tmp.getOpen_price()));
				equd.setClose_price(Long.parseLong(tmp.getClose_price()));
				equd.setHigh_price(Long.parseLong(tmp.getHigh_price()));
				equd.setLow_price(Long.parseLong(tmp.getLow_price()));
				equd.setPre_close(Long.parseLong(tmp.getPre_close()));
				equd.setTurnover_value(Long.parseLong(tmp.getTurnover_value()));
				equd.setTurnover_vol(Long.parseLong(tmp.getTurnover_vol()));
				equd.setTrade_date(DateUtils.parse("yyyyMMdd", tmp.getTrade_date()));
				equd.setStock_code(tmp.getStock_code().substring(0, 6));
				equd.setSuffix(tmp.getStock_code().substring(7));
				System.out.println(tmp.getPre_close());
				cacheMarketData.add(equd);
			} else {
				MarketIndex equd = new MarketIndex();
				equd.setOpen_price(Long.parseLong(tmp.getOpen_price()));
				equd.setClose_price(Long.parseLong(tmp.getClose_price()));
				equd.setPre_close(Long.parseLong(tmp.getPre_close()));
				equd.setHigh_price(Long.parseLong(tmp.getHigh_price()));
				equd.setLow_price(Long.parseLong(tmp.getLow_price()));
				equd.setTurnover_value(Long.parseLong(tmp.getTurnover_value()));
				equd.setTurnover_vol(Long.parseLong(tmp.getTurnover_vol()));
				equd.setTrade_date(DateUtils.parse("yyyyMMdd", tmp.getTrade_date()));
				equd.setStock_code(tmp.getStock_code().substring(0, 6));
				equd.setSuffix(tmp.getStock_code().substring(7));
				cacheIndexData.add(equd);
			}

		}

		// 看看收盘价是不是0,如果是就用当前这一条的前收来补数据
		for (MarketEqud equd : cacheMarketData) {
			if (equd.getClose_price() == 0) {
				equd.setClose_price(equd.getPre_close());
			}
		}

		try {
			if (type.equals("stock")) {
				// 查询当前是否是交易日，如果不是交易日，就不做啥了
				// 查询前一交易日的股票数据
				List<MarketEqud> listEqud = marketDao.selectLatestMarketDatas();
				List<StockPropertyBean> listTerminated = spbDao.selectAllTerminated();

				for (int i = 0; i < listTerminated.size(); ++i) {
					StockPropertyBean terminated = listTerminated.get(i);

					for (int j = 0; j < listEqud.size(); ++j) {
						MarketEqud equd = listEqud.get(j);
						if (equd.getStock_code().equals(terminated.getTmb().getCode())) {
							listEqud.remove(equd);
							j--;
						}
					}
				}

				// 把当前没有的股票补进去
				for (MarketEqud equd : listEqud) {
					boolean isHave = false;
					for (int i = 0; i < cacheMarketData.size(); ++i) {
						MarketEqud nowEqud = cacheMarketData.get(i);
						if (nowEqud.getStock_code().equals(equd.getStock_code())) {
							isHave = true;
							break;
						}
					}

					if (!isHave) {
						System.out.println("没有--------"+ equd.getClose_price());
						MarketEqud tmp = new MarketEqud();
						tmp.setClose_price(equd.getClose_price());
						tmp.setOpen_price(0);
						tmp.setLow_price(0);
						tmp.setHigh_price(0);
						tmp.setTurnover_value(0);
						tmp.setTurnover_vol(0);
						tmp.setPre_close(equd.getClose_price());
						tmp.setStock_code(equd.getStock_code());
						tmp.setTrade_date(cacheMarketData.get(0).getTrade_date());
						tmp.setSuffix(equd.getSuffix());
						cacheMarketData.add(tmp);
					}
				}

				System.out.println("补数据完成");

				if (cacheMarketData.size() > 0) {
					int index = 0;
					List<MarketEqud> tmp = new ArrayList<MarketEqud>();
					for (MarketEqud tt : cacheMarketData) {
						tmp.add(tt);
						if (++index % 1000 == 0) {
							 marketEqudService.insertMarketDatas(tmp);
							tmp.clear();
						}
					}
					if (tmp.size() > 0) {
						 marketEqudService.insertMarketDatas(tmp);
					}
					cacheMarketData.clear();
				}
			} else {

				 marketIndexService.insertIndexDatas(cacheIndexData);

			}

			System.out.println("插入数据完成");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("退出程序");
			System.exit(0);
		}

	}

}
