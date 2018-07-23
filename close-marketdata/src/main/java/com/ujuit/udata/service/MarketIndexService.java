
/**  
* @Title: MarketIndexService.java
* @Package com.ujuit.udata.service
* @Description: TODO(用一句话描述该文件做什么)
* @author tinkpad
* @date 2017年4月25日
* @version V1.0  
*/

package com.ujuit.udata.service;

import java.util.List;

import com.ujuit.udata.bean.MarketIndex;

/**
 * @ClassName: MarketIndexService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tinkpad
 * @date 2017年4月25日
 *
 */

public interface MarketIndexService {

	void insert(MarketIndex index);

	/**
	 * @Description 批量插入指数信息
	 * @return
	 * @param list
	 * @date 2017年5月9日
	 * @author Erick
	 */
	void insertIndexDatas(List<MarketIndex> list);
}
