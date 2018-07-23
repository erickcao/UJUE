
/**  
* @Title: MarketEqudServiceImpl.java
* @Package com.ujuit.udata.service.impl
* @Description: TODO(用一句话描述该文件做什么)
* @author tinkpad
* @date 2017年4月24日
* @version V1.0  
*/

package com.ujuit.udata.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ujuit.udata.bean.MarketEqud;
import com.ujuit.udata.dao.MarketEqudDao;
import com.ujuit.udata.service.MarketEqudService;

/**
 * @ClassName: MarketEqudServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author tinkpad
 * @date 2017年4月24日
 *
 */
@Service
public class MarketEqudServiceImpl implements MarketEqudService {

	@Autowired
	MarketEqudDao dao;

	public void insert(MarketEqud marketEqud) {
		dao.insert(marketEqud);
	}

	@Override
	public void insertMarketDatas(List<MarketEqud> list) {
		dao.insertMarketDatas(list);
	}

}
