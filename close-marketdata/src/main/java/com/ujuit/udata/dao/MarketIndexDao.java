
package com.ujuit.udata.dao;

import java.util.List;

import com.ujuit.udata.bean.MarketIndex;

/**
 * @Description 指数dao
 * 
 * @author Erick
 * @date 2017年5月9日
 * @company 有据信息技术有限公司
 * @version 1.0
 */
public interface MarketIndexDao {

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
