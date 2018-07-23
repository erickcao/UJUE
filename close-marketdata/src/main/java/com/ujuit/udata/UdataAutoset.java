
/**  
* @Title: UdataAutoset.java
* @Package com.ujuit.udata
* @Description: TODO(用一句话描述该文件做什么)
* @author tinkpad
* @date 2017年4月24日
* @version V1.0  
*/

package com.ujuit.udata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uju.ujutdclient.UjutdClient;
import com.ujuit.udata.bean.MarketEqud;
import com.ujuit.udata.bean.StockPropertyBean;
import com.ujuit.udata.bean.TerminateBean;
import com.ujuit.udata.dao.MarketEqudDao;
import com.ujuit.udata.dao.StockPropertyBeanMapper;
import com.ujuit.udata.dao.TerminateBeanMapper;
import com.ujuit.udata.dao.TradeCalDaoMapper;
import com.ujuit.udata.service.MarketEqudService;
import com.ujuit.udata.utils.DateUtils;

import cn.com.wind.td.tdf.TDF_MSG_ID;

/**
 * @Description 管理控制
 * 
 * @author Erick
 * @date 2017年5月9日
 * @company 有据信息技术有限公司
 * @version 1.0
 */

public class UdataAutoset {
	public static UjutdClient client = null;
	private String username = "dddddd";
	private String password = "0000000";

	@Autowired
	TradeCalDaoMapper tDao;

	@Autowired
	StockPropertyBeanMapper spbDao;

	@Autowired
	TerminateBeanMapper tbDao;

	@Autowired
	MarketEqudDao marketDao;

	@Autowired
	MarketEqudService marketEqudService;

	/**
	 * @Description 执行任务
	 * @return
	 * @date 2017年5月9日
	 * @author Erick
	 */
	public void doTask() {

		// repaireDaily();

		// 初始化订阅的类型
		List<Integer> typeList = new ArrayList<Integer>();
		typeList.add(TDF_MSG_ID.MSG_DATA_MARKET);
		typeList.add(TDF_MSG_ID.MSG_DATA_INDEX);

		// 初始化订阅的股票代码
		List<String> stocks = new ArrayList<String>();
		stocks.add("ALL");

		// 根据参数连接行情服务器
		client = new UjutdClient(username, password, typeList, stocks);
		new Thread(new Runnable() {
			public void run() {
				try {
					client.connect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		client.addStocks(stocks);

		// 启动去提取行情
		new MarketExtractor().start();
	}

	/**
	 * @Description 修复数据
	 * @return
	 * @date 2018年3月10日
	 * @author Erick
	 */
	public void repaireDaily() {

		// 选出退市的股票
		List<StockPropertyBean> listSPB = spbDao.selectAllTerminated();
		System.out.println("数据量：" + listSPB.size());

		// 查询所有的交易日
		List<String> l = tDao.selectAllTradeDay();

		// 查询所有的股票记录
		List<TerminateBean> allStocks = tbDao.selectAllStockA();
		int currIndex = 0;

		for (TerminateBean stock : allStocks) {
			List<MarketEqud> result = new ArrayList<MarketEqud>();

			// List<MarketEqud> lmarket = marketDao.selectByStockCode("900949",
			// "SH");
			List<MarketEqud> lmarket = marketDao.selectByStockCode(stock.getCode(),
					stock.getExchangeBasicinfoId().intValue() == 1 ? "SH" : "SZ");
			if (l.size() != lmarket.size()) {
				Date endDate = null;
				// 证明行情数量不够
				for (StockPropertyBean tmp : listSPB) {// 看是否是退市的

					if (tmp.getTmb().getCode().equals(stock.getCode()))// 证明是退市的股票
					{
						endDate = tmp.getDate();
						System.out.println("截止日：" + endDate);
					}
				}

				// 遍历所有交易日
				int index = 0;
				if (lmarket.size() == 0) {
					System.out.println("此股票没数据:" + stock.getCode());
					continue;
				}
				MarketEqud lastME = lmarket.get(0);
				for (String sdate : l) {
					// 如果在范围之内
					int pre = DateUtils.dateCompare(sdate,
							DateUtils.format("yyyy-MM-dd", lmarket.get(0).getTrade_date()));
					int next = -1;
					if (endDate != null) {
						next = DateUtils.dateCompare(sdate, DateUtils.format("yyyy-MM-dd", endDate));
					}

					if (pre != -1 && next != 1) {// 证明日期是在范围内的，需要有行情
						if (index < lmarket.size()) {
							MarketEqud tm = lmarket.get(index);
							// 看日期是不是一样
							int ee = DateUtils.dateCompare(sdate, DateUtils.format("yyyy-MM-dd", tm.getTrade_date()));

							if (ee == 0) {
								lastME = tm;
							} else {
								System.out.println("当天没数据：" + sdate + "股票" + tm.getStock_code() + tm.getSuffix());
								MarketEqud tmp = new MarketEqud();
								tmp.setClose_price(lastME.getClose_price());
								tmp.setOpen_price(0);
								tmp.setLow_price(0);
								tmp.setHigh_price(0);
								tmp.setTurnover_value(0);
								tmp.setTurnover_vol(0);
								tmp.setPre_close(lastME.getClose_price());
								tmp.setStock_code(lastME.getStock_code());
								tmp.setTrade_date(DateUtils.parse("yyyy-MM-dd", sdate));
								tmp.setSuffix(lastME.getSuffix());

								lastME = tmp;

								lmarket.add(index, tmp);

								result.add(tmp);

							}
						} else {
							MarketEqud tmp = new MarketEqud();
							tmp.setClose_price(lastME.getClose_price());
							tmp.setOpen_price(0);
							tmp.setLow_price(0);
							tmp.setHigh_price(0);
							tmp.setTurnover_value(0);
							tmp.setTurnover_vol(0);
							tmp.setPre_close(lastME.getClose_price());
							tmp.setStock_code(lastME.getStock_code());
							tmp.setTrade_date(DateUtils.parse("yyyy-MM-dd", sdate));
							tmp.setSuffix(lastME.getSuffix());

							lastME = tmp;
							lmarket.add(index, tmp);

							result.add(tmp);
							System.out.println("当天没数据：" + sdate + "股票" + lastME.getStock_code() + lastME.getSuffix());

						}

						index++;
					}

				}
			}

			if (result.size() > 0) {
				// marketEqudService.insertMarketDatas(result);
				result.clear();
			} else {
				System.out.println("不需要补数据");
			}

			System.out.println("总数量：" + allStocks.size() + "当前执行：" + ++currIndex);

		}

		System.out.println("修复结束了");

		// 根据股票代码及后缀 循环单只股票的所有记录 ---直到退市或者最后一个交易日，补齐数据

		// List<MarketEqud> lmarket = marketDao.selectByStockCode("000001",
		// "SZ");
		// System.out.println(lmarket.size());

	}

}
