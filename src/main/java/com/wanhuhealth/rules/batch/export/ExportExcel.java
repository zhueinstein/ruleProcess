package com.wanhuhealth.rules.batch.export;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;
import java.util.List;

/**
 * 
 * excel导出接口
 * 
 * */
public interface ExportExcel {
	
	class Excel{
		
	}
	public interface SheetBuilder{
		
		public Sheet builder(Workbook wb, String sheetName);

	}

	public interface RowBuilder{

		public Row builder(Sheet sheet, int index, Object data);

	}

	public interface CellBuilder{

		/**
		 * 返回null 将进入下一行
		 * */
		public Cell builder(Row row, int index, Object data);

	}

	public void setSheetBuilder(SheetBuilder builder);

	public void setRowBuilder(RowBuilder builder);

	public void setCellBuilder(CellBuilder builder);

	/**
	 * 导出属性设置，不设置将导出所有数据对象值
	 * 可以重复调用
	 * */
	public void setExportProperties(List<String> props);

	public String getExportProperty(int index);

	/**
	 * 设置首行显示的title
	 * */
	public void setExportTitles(List<String> titles);

	public String getExportTitle(int index);

	public void setHeaderTitles(List<String> headerTitles) ;

	public Workbook write(List<?> datas);

	/**
	 * 生成新的excel sheet，并将数据生成到sheet中
	 * */
	public Workbook write(Workbook workbook, String sheetName, List<?> datas, List<String> headerDatas);

	/**
	 * 生成新的excel sheet，并将数据生成到sheet中，最后导出到提供的输出流
	 * */
	public void write(String sheetName, List<?> datas, OutputStream stream);

	public void write(List<?> datas, OutputStream stream);

	/**
	 * 支持多sheet页导出  多表头
	 * @param sheetDatas
	 * @param stream
	 */
	public void writePreSheet(List<SheetObj> sheetDatas, OutputStream stream);

	public void setMaxCell(int maxCell);

	public void setHeaders(List<String> headers);

	public void setSumColumns(List<Integer> columns);

	public void setEndColumns(List<Integer> columns, List<String> foots);

	/**
	 * 生成新的excel sheet，并将数据生成到sheet中，最后导出到提供的输出流
	 * datas 集合的大小为excel的列数
	 * */
	public void writeVertical(String sheetName, List<?> datas, OutputStream stream);
	
}
