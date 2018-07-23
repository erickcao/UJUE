package com.ujuit.udata.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.ujuit.udata.bean.CsvBean;

/**
 * csv文件读取
 * 
 * @author luyang
 *
 */
public class CVSReader {

	public int cols = 0;

	/**
	 * 文件路径
	 */
	private String cvsFile;

	/**
	 * 文件字段
	 */
	String[] columns = new String[] { "trade_date", "open_price", "close_price", "high_price", "low_price", "turnover_vol", 
			"turnover_value", "pre_close", "stock_code"};

	public CVSReader(String cvsFile) {
		this.cvsFile = cvsFile;
	}

	/**
	 * 将文件数据转化为javaBean对象
	 * 
	 * @return
	 */
	public List<CsvBean> parsToBean(int index) {
		String[] sTmp = null;
		CSVReader readerCols = createReader(1);
		try {
			sTmp = readerCols.readNext();
			readerCols.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (sTmp != null) {
			this.cols = sTmp.length;
		}

		CSVReader reader = createReader(index);
		try {
			ColumnPositionMappingStrategy<CsvBean> strat = new ColumnPositionMappingStrategy<CsvBean>();
			strat.setType(CsvBean.class);
			strat.setColumnMapping(columns);
			
			CsvToBean<CsvBean> csv = new CsvToBean<CsvBean>();
			List<CsvBean> list = csv.parse(strat, reader);
			return list;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 读取文件
	 * 
	 * @return
	 */
	private CSVReader createReader(int index) {
		DataInputStream in = null;
		CSVReader reader;
		try {
			in = new DataInputStream(new FileInputStream(new File(cvsFile)));
			// 以gbk编码方式读取，并忽略前两行菜单
			if (index == 0)
				index = 2;
			reader = new CSVReader(new InputStreamReader(in, "GBK"), ',', '\'', index);
			return reader;
		} catch (Exception e) {
			e.printStackTrace();
			// 文件不存在
			return null;
		}

	}

}
