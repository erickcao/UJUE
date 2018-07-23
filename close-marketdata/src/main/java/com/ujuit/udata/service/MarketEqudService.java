
/**  
* @Title: MarketEqudService.java
* @Package com.ujuit.udata.service
* @Description: TODO(用一句话描述该文件做什么)
* @author tinkpad
* @date 2017年4月24日
* @version V1.0  
*/

package com.ujuit.udata.service;

import java.util.Date;
import java.util.List;

import com.ujuit.udata.bean.MarketEqud;

/**
 * @ClassName: MarketEqudService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tinkpad
 * @date 2017年4月24日
 *
 */

public interface MarketEqudService {

	void insert(MarketEqud marketEquds);

	/**
	 * @Description 批量插入行情数据
	 * @return
	 * @param list
	 * @date 2017年5月9日
	 * @author Erick
	 */
	void insertMarketDatas(List<MarketEqud> list);

}
