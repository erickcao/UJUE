package com.ujuit.udata.dao;

import java.util.List;

import com.ujuit.udata.bean.TerminateBean;

public interface TerminateBeanMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(TerminateBean record);

	int insertSelective(TerminateBean record);

	TerminateBean selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(TerminateBean record);

	int updateByPrimaryKey(TerminateBean record);

	
	/**
	 * @Description 获取所有A股信息
	 * @return 
	 * @return
	 * @date 2018年3月10日
	 * @author Erick
	 */
	List<TerminateBean> selectAllStockA();

}