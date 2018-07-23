package com.ujuit.udata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.uju.ujutd.netty.struct.NettyMessageProto.BusinessRespMessage;
import com.uju.ujutd.netty.struct.NettyMessageProto.UJU_TDF_INDEX_DATA;
import com.uju.ujutd.netty.struct.NettyMessageProto.UJU_TDF_MARKET_DATA;
import com.ujuit.udata.bean.MarketEqud;
import com.ujuit.udata.bean.MarketIndex;
import com.ujuit.udata.bean.StockPropertyBean;
import com.ujuit.udata.dao.MarketEqudDao;
import com.ujuit.udata.dao.StockPropertyBeanMapper;
import com.ujuit.udata.service.MarketEqudService;
import com.ujuit.udata.service.MarketIndexService;
import com.ujuit.udata.service.MarketToCvsService;
import com.ujuit.udata.utils.UJUUtils;

import cn.com.wind.td.tdf.TDF_MSG_ID;

/**
 * @Description 行情提取并保存
 * 
 * @author Erick
 * @date 2017年5月9日
 * @company 有据信息技术有限公司
 * @version 1.0
 */

@Component
public class MarketExtractor extends Thread {

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

	@Override
	public void run() {
		while (true) {
			BusinessRespMessage result = UdataAutoset.client.get(200);
			if (result != null) {
				int type = result.getDataType();
				switch (type) {
				case TDF_MSG_ID.MSG_DATA_MARKET: {
					addMarketData(result.getUjuTdfMarketDataList());
				}
					break;
				case TDF_MSG_ID.MSG_DATA_INDEX: {
					addIndexData(result.getUjuTdfIndexDataList());
				}
					break;
				}

			}
		}
	}

	// 定时任务，每天16:20分，进行存储，并关闭程序
	@Scheduled(cron = "0 00 16 * * ?")
	public void doTask() {
		System.out.println("每日定时任务执行");

		Application.isStart = true;
		try {
			sleep(10000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		// 我要在把对应的行情存成文件,防止哪天数据库连接不上
		try {
			marketToCvsService.marketToCvs(cacheMarketData);
			marketToCvsService.indexToCvs(cacheIndexData);
		} catch (Exception e) {
			// 发送异常消息
			System.out.println(e);
		}

		// 看看收盘价是不是0,如果是就用当前这一条的前收来补数据
		for (MarketEqud equd : cacheMarketData) {
			if (equd.getClose_price() == 0) {
				equd.setClose_price(equd.getPre_close());
			}
		}

		try {
			// 查询当前是否是交易日，如果不是交易日，就不做啥了
			if (UJUUtils.isTradingDay()) {
				System.out.println("交易日检测完成");
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
			}

			if (cacheIndexData.size() > 0) {
				marketIndexService.insertIndexDatas(cacheIndexData);
				cacheIndexData.clear();
			}

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

			System.out.println("插入数据完成");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("退出程序");
			System.exit(0);
		}

	}

	/**
	 * @Description 增加缓存的行情信息，只留最后一条
	 * @return
	 * @param markets
	 * @date 2017年5月9日
	 * @author Erick
	 */
	private void addMarketData(List<UJU_TDF_MARKET_DATA> markets) {

		if (!Application.isStart) {
			for (UJU_TDF_MARKET_DATA m : markets) {
				for (MarketEqud tmp : cacheMarketData) {
					if (m.getWindCode().equals(tmp.getStock_code() + "." + tmp.getSuffix())) {
						cacheMarketData.remove(tmp);
						break;
					}
				}
				cacheMarketData.add(coverMarketData(m));
			}
		}

	}

	/**
	 * @Description 增加缓存的指数信息，只留最后一条
	 * @return
	 * @param markets
	 * @date 2017年5月9日
	 * @author Erick
	 */
	private void addIndexData(List<UJU_TDF_INDEX_DATA> markets) {
		if (!Application.isStart) {
			for (UJU_TDF_INDEX_DATA m : markets) {
				for (MarketIndex tmp : cacheIndexData) {
					if (m.getWindCode().equals(tmp.getStock_code() + "." + tmp.getSuffix())) {
						cacheIndexData.remove(tmp);
						break;
					}
				}
				cacheIndexData.add(coverIndexData(m));
			}
		}

	}

	/**
	 * @Description 把发过来的行情信息转换成需要存储的实体
	 * @return
	 * @param market
	 * @return
	 * @date 2017年5月9日
	 * @author Erick
	 */
	private MarketEqud coverMarketData(UJU_TDF_MARKET_DATA market) {
		MarketEqud result = new MarketEqud();
		result.setClose_price(market.getIOPV());
		result.setHigh_price(market.getHigh());
		result.setLow_price(market.getLow());
		result.setOpen_price(market.getOpen());
		result.setPre_close(market.getPreClose());
		result.setStock_code(market.getCode());
		result.setSuffix(market.getWindCode().substring(market.getWindCode().indexOf(".") + 1));
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		try {
			result.setTrade_date(sf.parse(market.getTradingDay() + ""));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		result.setTurnover_value(market.getTurnover());
		result.setTurnover_vol(market.getVolume());

		return result;
	}

	/**
	 * @Description 把发过来的指数信息转换成需要存储的实体
	 * @return
	 * @param index
	 * @return
	 * @date 2017年5月9日
	 * @author Erick
	 */
	private MarketIndex coverIndexData(UJU_TDF_INDEX_DATA index) {
		MarketIndex result = new MarketIndex();
		result.setClose_price(index.getLastIndex());
		result.setHigh_price(index.getHighIndex());
		result.setLow_price(index.getLowIndex());
		result.setOpen_price(index.getOpenIndex());
		result.setPre_close(index.getPreCloseIndex());
		result.setStock_code(index.getCode());
		result.setSuffix(index.getWindCode().substring(index.getWindCode().indexOf(".") + 1));
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		try {
			result.setTrade_date(sf.parse(index.getTradingDay() + ""));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		result.setTurnover_value(index.getTurnover());
		result.setTurnover_vol(index.getTotalVolume());

		return result;
	}

}
