package com.ujuit.udata.service;

import java.util.List;

import com.ujuit.udata.bean.MarketBase;
import com.ujuit.udata.bean.MarketEqud;
import com.ujuit.udata.bean.MarketIndex;

/**
 * @Description 把文件保存成对应的文件
 * 
 * @author Erick
 * @date 2017年6月8日
 * @company 有据信息技术有限公司
 * @version 1.0
 */
public interface MarketToCvsService {

	/**
	 * @Description 把日行情存成CVS文件
	 * @return
	 * @param mbase
	 * @param type
	 *            0表示股票 1表示指数
	 * @date 2017年6月8日
	 * @author Erick
	 */
	public void marketToCvs(List<MarketEqud> mbase);

	public void indexToCvs(List<MarketIndex> mbase);
}
