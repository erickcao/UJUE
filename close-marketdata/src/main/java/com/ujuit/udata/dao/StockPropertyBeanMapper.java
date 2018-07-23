package com.ujuit.udata.dao;

import java.util.List;

import com.ujuit.udata.bean.StockPropertyBean;
import com.ujuit.udata.bean.TerminateBean;

public interface StockPropertyBeanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockPropertyBean record);

    int insertSelective(StockPropertyBean record);

    StockPropertyBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockPropertyBean record);

    int updateByPrimaryKey(StockPropertyBean record);
    
	/**
	 * @Description 获取所有退市的股票的代码
	 * @return 
	 * @return
	 * @date 2018年3月9日
	 * @author Erick
	 */
	List<StockPropertyBean> selectAllTerminated();
    
}