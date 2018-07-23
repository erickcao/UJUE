package com.ujuit.udata.dao;

import java.util.List;

import com.ujuit.udata.bean.TradeCalDao;

public interface TradeCalDaoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TradeCalDao record);

    int insertSelective(TradeCalDao record);

    TradeCalDao selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TradeCalDao record);

    int updateByPrimaryKey(TradeCalDao record);
    
    
    /**
     * @Description 获取所有的交易日
     * @return 
     * @return
     * @date 2018年3月10日
     * @author Erick
     */
    List<String> selectAllTradeDay();
    
    
}