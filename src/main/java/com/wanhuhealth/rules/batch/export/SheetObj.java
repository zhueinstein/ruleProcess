package com.wanhuhealth.rules.batch.export;

import java.util.List;

public class SheetObj {

	private List<String> headerDatas;
	
	private String sheetName;
	
	private List<?> datas;
	
	public List<String> getHeaderDatas() {
		return headerDatas;
	}
	public void setHeaderDatas(List<String> headerDatas) {
		this.headerDatas = headerDatas;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public List<?> getDatas() {
		return datas;
	}
	public void setDatas(List<?> datas) {
		this.datas = datas;
	}
	
	 
	
}
