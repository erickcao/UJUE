
package com.ujuit.udata.dao;

import java.util.List;

import com.ujuit.udata.bean.MarketEqud;

/**
 * @Description 行情dao
 * 
 * @author Erick
 * @date 2017年5月9日
 * @company 有据信息技术有限公司
 * @version 1.0
 */

public interface MarketEqudDao {

	void insert(MarketEqud marketEqud);

	/**
	 * @Description 批量插入行情信息
	 * @return
	 * @param list
	 * @date 2017年5月9日
	 * @author Erick
	 */
	void insertMarketDatas(List<MarketEqud> list);
	
	
	/**
	 * @Description 获取最新交易日的股票行情
	 * @return 
	 * @return
	 * @date 2018年3月9日
	 * @author Erick
	 */
	List<MarketEqud> selectLatestMarketDatas();
	
	
	
	/**
	 * @Description 获取某只股票的所有日行情
	 * @return 
	 * @param code
	 * @param suffix
	 * @return
	 * @date 2018年3月10日
	 * @author Erick
	 */
	List<MarketEqud> selectByStockCode(String code, String suffix);
	
	
	
	
}
