
    /**  
    * @Title: MarketIndexServiceImpl.java
    * @Package com.ujuit.udata.service.impl
    * @Description: TODO(用一句话描述该文件做什么)
    * @author tinkpad
    * @date 2017年4月25日
    * @version V1.0  
    */
    
package com.ujuit.udata.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ujuit.udata.bean.MarketIndex;
import com.ujuit.udata.dao.MarketIndexDao;
import com.ujuit.udata.service.MarketIndexService;

/**
    * @ClassName: MarketIndexServiceImpl
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author tinkpad
    * @date 2017年4月25日
    *
    */
@Service
public class MarketIndexServiceImpl implements MarketIndexService{
	
	@Autowired
	MarketIndexDao marketIndexDao;
	
	public void insert(MarketIndex index){
		marketIndexDao.insert(index);
	}

	@Override
	public void insertIndexDatas(List<MarketIndex> list) {
		marketIndexDao.insertIndexDatas(list);
	}
}
