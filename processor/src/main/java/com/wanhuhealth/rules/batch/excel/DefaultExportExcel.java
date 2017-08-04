package com.wanhuhealth.rules.batch.excel;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * 此类非线程安全
 * */
public class DefaultExportExcel implements ExportExcel{
	
	private final int defaultHeight = 25;
	
	private final int defaultWidth = 22;
	
	public static DefaultExportExcel newInstance(List<String> headers, List<String> titles, List<String> props, SheetBuilder sheetBuilder,
                                                 RowBuilder rowBuilder, CellBuilder cellBuilder) {
		DefaultExportExcel def = new DefaultExportExcel();
		if(headers != null)
			def.setHeaders(headers);
		if (titles != null)
			def.setExportTitles(titles);
		if (props != null)
			def.setExportProperties(props);
		if (sheetBuilder != null)
			def.setSheetBuilder(sheetBuilder);
		if (rowBuilder != null)
			def.setRowBuilder(rowBuilder);
		if (cellBuilder != null)
			def.setCellBuilder(cellBuilder);
		return def;
	}

	public static DefaultExportExcel newInstance() {
		return newInstance(null,null, null, null, null, null);
	}
	
	public static DefaultExportExcel newInstance(CellBuilder cellBuilder) {
		return newInstance(null,null, null, null, null, cellBuilder);
	}

	public static DefaultExportExcel newInstance(List<String> headers, List<String> titles, CellBuilder cellBuilder) {
		return newInstance(headers,titles, null, null, null, cellBuilder);
	}
	
	private List<String> props = new ArrayList<String>();
	
	private int maxCell = 127;
	
	private List<String> headers = new ArrayList<String>();
	
	private List<String> titles = new ArrayList<String>();
	
	private List<String> headerTitles = new ArrayList<String>();
	
	private List<Integer> sumColumns = new ArrayList<Integer>();
	private List<Integer> endColumns = new ArrayList<Integer>();
	private List<String> endColumnDatas= new ArrayList<String>();
	
	private SheetBuilder sheetBuilder = new SheetBuilder(){

		public Sheet builder(Workbook wb,String sheetName) {
			Sheet sheet = null;
			if(sheetName != null) sheet = wb.createSheet(sheetName);
			else sheet = wb.createSheet();
			sheet.setDefaultColumnWidth(defaultWidth);
			return sheet;
		}
		
	};
	
	private RowBuilder rowBuilder = new RowBuilder(){

		public Row builder(Sheet sheet, int index, Object data) {
			Row row = sheet.createRow(index);
			row.setHeightInPoints(defaultHeight);
			return row;
		}
		
	};
	
	private CellBuilder cellBuilder = new CellBuilder(){
		private CellStyle style;
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cell builder(Row row, int index, Object data) {
			try{
				if(data == null)return null;
				Object value = null;
				if(0 == props.size()){
					if(data instanceof List){
						if(index >= ((List) data).size()){
							return null;
						}
						value = ((List) data).get(index);
					}else if(data.getClass().isArray()){
						if(index >= Array.getLength(data)){
							return null;
						}
						value = Array.get(data, index);
					}else if(data instanceof Map){
						props.addAll(((Map)data).keySet());
						setMaxCell(props.size());
					}else{
						BeanInfo binfo = Introspector.getBeanInfo(data.getClass());
						for (PropertyDescriptor pd : binfo.getPropertyDescriptors()) {
							Class<?> fClazz = pd.getPropertyType();
							if (fClazz.isPrimitive()) {
								props.add(pd.getName());
							} else if (fClazz == String.class || fClazz == Double.class || fClazz == Float.class
									|| fClazz == Integer.class || fClazz == BigDecimal.class
									|| fClazz == BigInteger.class || fClazz == Date.class|| fClazz == Long.class) {
								props.add(pd.getName());
							}
						}
						setMaxCell(props.size());
					}
				}
				Cell cell = row.createCell(index);
				if(value == null){
					value = BeanUtils.getProperty(data, getExportProperty(index));
				}
				if(value != null){
					if(NumberUtils.isNumber(value.toString())){
						if(style == null){
							style = row.getSheet().getWorkbook().createCellStyle();
							DataFormat fmt = row.getSheet().getWorkbook().createDataFormat();
							style.setDataFormat(fmt.getFormat("0.00"));
						}
						cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(style);
						cell.setCellValue(Double.parseDouble(value.toString()));
					}else{
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(value.toString());
					}
				}else{
					cell.setCellType(XSSFCell.CELL_TYPE_BLANK);
				}
				return cell;
			}catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
	};

	public void setExportProperties(List<String> props) {
		this.props.clear();
		this.props.addAll(props);
		setMaxCell(props.size());
	}

	public String getExportProperty(int index) {
		if(index >= props.size()) return null;
		return this.props.get(index);
	}

	public void setExportTitles(List<String> titles) {
		this.titles.clear();
		this.titles.addAll(titles);
	}

	public String getExportTitle(int index) {
		if(index >= titles.size()) return null;
		return this.titles.get(index);
	}

	public void setHeaderTitles(List<String> headerTitles) {
		this.headerTitles.clear();
		this.headerTitles.addAll(headerTitles);
	}

	public Workbook write(List<?> datas) {
		return write(null, null,datas,null);
	}

	public Workbook write(Workbook wb, String sheetName, List<?> datas, List<String> headerDatas) {
		if(wb == null){
			wb = new SXSSFWorkbook();
		}
		Sheet sheet = this.sheetBuilder.builder(wb, sheetName);
		int row = 0;
		if(this.headers.size() > 0){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			for(int i= 0,j = this.headers.size();i < j;i++){
				String header = this.headers.get(i);
				sheet.addMergedRegion(new CellRangeAddress(row, row, 0, this.maxCell - 1));
				Row r = sheet.createRow(row);
				r.setHeightInPoints(30);
				Cell cell = r.createCell(0,XSSFCell.CELL_TYPE_STRING);
				if(i == 0){
					CellStyle style1 = wb.createCellStyle();
					Font font1 = wb.createFont();
					font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
					style1.setFont(font);
					style1.setAlignment(XSSFCellStyle.ALIGN_CENTER);
					style1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
					cell.setCellStyle(style1);
				}else{
					cell.setCellStyle(style);
				}
				cell.setCellValue(header);
				row++;
			}
		}
		if(this.headerTitles!=null&&this.headerTitles.size() > 0){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			Row r = sheet.createRow(row++);
			r.setHeightInPoints(defaultHeight);
			for(int c=0,l = this.headerTitles.size();c < l;c++){
				Cell cell = r.createCell(c, XSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(style);
				cell.setCellValue(this.headerTitles.get(c));
			}
		}
		if(headerDatas!=null&&headerDatas.size() > 0){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			Row r = sheet.createRow(row++);
			r.setHeightInPoints(defaultHeight);
			for(int c=0,l =headerDatas.size();c < l;c++){
				Cell cell = r.createCell(c, XSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(style);
				cell.setCellValue(headerDatas.get(c));
			}
		}
		if(this.titles.size() > 0){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			Row r = sheet.createRow(row++);
			r.setHeightInPoints(defaultHeight);
			for(int c=0,l = this.titles.size();c < l;c++){
				Cell cell = r.createCell(c, XSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(style);
				cell.setCellValue(this.titles.get(c));
			}
		}
		if(datas == null || datas.size() == 0){
			return wb;
		}
		int initRow = row + 1;
		for(Object data:datas){
			Row r = this.rowBuilder.builder(sheet, row++, data);
			for(int c = 0;c < this.maxCell;c++){
				if(this.cellBuilder.builder(r, c, data) == null){
					break;
				}
			}
		}
		
		if(this.sumColumns.size() > 0){
			int dataLastRow = row;
			Row r = sheet.createRow(row++);
			r.setHeightInPoints(defaultHeight);
			if(this.sumColumns.get(0) != 0){
				Cell cell = r.createCell(0);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("合计");
			}
			CellStyle style = r.getSheet().getWorkbook().createCellStyle();
			style.setDataFormat(r.getSheet().getWorkbook().createDataFormat().getFormat("0.00"));
			for(int i = 0, j = this.sumColumns.size();i < j;i++){
				Cell cell = r.createCell(this.sumColumns.get(i));
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellStyle(style);
				String column = getColumnChar(this.sumColumns.get(i));
				cell.setCellFormula("sum("+column+initRow+":"+column+dataLastRow+")");
			}
			sheet.setForceFormulaRecalculation(true);
		}
		if(this.endColumns.size() > 0){ 
			Row r = sheet.createRow(row++);
			r.setHeightInPoints(defaultHeight); 
			CellStyle style = r.getSheet().getWorkbook().createCellStyle();
			style.setDataFormat(r.getSheet().getWorkbook().createDataFormat().getFormat("0.00"));
			for(int i = 0, j = this.endColumns.size();i < j;i++){
				Cell cell = r.createCell(this.endColumns.get(i));
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(style);
				cell.setCellValue(this.endColumnDatas.get(i));


			}
			sheet.setForceFormulaRecalculation(true);
		}
		return wb;
	}

	public void write(String sheetName, List<?> datas, OutputStream stream) {
		Workbook wb = write(null, sheetName, datas,null);
		try {
			wb.write(stream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void writePreSheet(List<SheetObj> sheetDatas, OutputStream stream) {
		Workbook wb = new SXSSFWorkbook();
		for (SheetObj sheetObj :sheetDatas) {
			List<?> datas = sheetObj.getDatas();
			String sheetName = sheetObj.getSheetName();
			List<String> headerDatas = sheetObj.getHeaderDatas();
			write(wb, sheetName, datas,headerDatas);
		}
		try {
			wb.write(stream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void write(List<?> datas, OutputStream stream) {
		write(null,datas,stream);
	}

	public void setSheetBuilder(SheetBuilder builder) {
		this.sheetBuilder = builder;
	}

	public void setRowBuilder(RowBuilder builder) {
		this.rowBuilder = builder;
	}

	public void setCellBuilder(CellBuilder builder) {
		this.cellBuilder = builder;
	}

	public void setMaxCell(int maxCell) {
		this.maxCell = maxCell;
	}

	@Override
	public void setHeaders(List<String> headers) {
		this.headers.clear();
		this.headers.addAll(headers);
	}

	 
	@Override
	public void setEndColumns(List<Integer> columns, List<String> foots) {
		// TODO Auto-generated method stub
		this.endColumns.clear();
		this.endColumns.addAll(columns);
		this.endColumnDatas.clear(); 
		this.endColumnDatas.addAll(foots);
		Collections.sort(columns);
	}

	@Override
	public void setSumColumns(List<Integer> columns) {
		this.sumColumns.clear();
		this.sumColumns.addAll(columns);
		Collections.sort(columns);
	}
	
	private String getColumnChar(int i) {
	    StringBuilder columnChar = new StringBuilder();
	    int integer = 0;
	    int remainder = 0;
	    do{
	    	integer = i/26;
	    	remainder = i%26;
	    	i = integer;
	    	columnChar.append((char)(remainder + 65));
	    }while(integer != 0);
	    return columnChar.reverse().toString();
	} 
	
	public Workbook writeVertical(Workbook wb, String sheetName, List<?> datas, List<String> headerDatas) {
		if(wb == null){
			wb = new SXSSFWorkbook();
		}
		Sheet sheet = this.sheetBuilder.builder(wb, sheetName);
		int row = 0;
		if(this.headers.size() > 0){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			for(int i= 0,j = this.headers.size();i < j;i++){
				String header = this.headers.get(i);
				sheet.addMergedRegion(new CellRangeAddress(row, row, 0, this.maxCell - 1));
				Row r = sheet.createRow(row);
				r.setHeightInPoints(30);
				Cell cell = r.createCell(0,XSSFCell.CELL_TYPE_STRING);
				if(i == 0){
					CellStyle style1 = wb.createCellStyle();
					Font font1 = wb.createFont();
					font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
					style1.setFont(font);
					style1.setAlignment(XSSFCellStyle.ALIGN_CENTER);
					style1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
					cell.setCellStyle(style1);
				}else{
					cell.setCellStyle(style);
				}
				cell.setCellValue(header);
				row++;
			}
		}
		if(this.headerTitles!=null&&this.headerTitles.size() > 0){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			Row r = sheet.createRow(row++);
			r.setHeightInPoints(defaultHeight);
			for(int c=0,l = this.headerTitles.size();c < l;c++){
				Cell cell = r.createCell(c, XSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(style);
				cell.setCellValue(this.headerTitles.get(c));
			}
		}
		if(headerDatas!=null&&headerDatas.size() > 0){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			Row r = sheet.createRow(row++);
			r.setHeightInPoints(defaultHeight);
			for(int c=0,l =headerDatas.size();c < l;c++){
				Cell cell = r.createCell(c, XSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(style);
				cell.setCellValue(headerDatas.get(c));
			}
		}
		if(this.titles.size() > 0){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			Row r = sheet.createRow(row++);
			r.setHeightInPoints(defaultHeight);
			for(int c=0,l = this.titles.size();c < l;c++){
				Cell cell = r.createCell(c, XSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(style);
				cell.setCellValue(this.titles.get(c));
			}
		}
		if(datas == null || datas.size() == 0){
			return wb;
		}
		int initRow = row + 1;
		Row ro = this.rowBuilder.builder(sheet, row++, null);
		for(int c = 1;c <= datas.size();c++){
			if(this.cellBuilder.builder(ro, c, datas.get(c-1)) == null){
				break;
			}
		}
		
		if(this.sumColumns.size() > 0){
			int dataLastRow = row;
			Row r = sheet.createRow(row++);
			r.setHeightInPoints(defaultHeight);
			if(this.sumColumns.get(0) != 0){
				Cell cell = r.createCell(0);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("合计");
			}
			CellStyle style = r.getSheet().getWorkbook().createCellStyle();
			style.setDataFormat(r.getSheet().getWorkbook().createDataFormat().getFormat("0.00"));
			for(int i = 0, j = this.sumColumns.size();i < j;i++){
				Cell cell = r.createCell(this.sumColumns.get(i));
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellStyle(style);
				String column = getColumnChar(this.sumColumns.get(i));
				cell.setCellFormula("sum("+column+initRow+":"+column+dataLastRow+")");
			}
			sheet.setForceFormulaRecalculation(true);
		}
		return wb;
	}
	
	/**
	 * 生成新的excel sheet，并将数据生成到sheet中，最后导出到提供的输出流
	 * datas 集合的大小为excel的列数
	 * */
	public void writeVertical(String sheetName, List<?> datas, OutputStream stream) {
		Workbook wb = writeVertical(null, sheetName, datas,null);
		try {
			wb.write(stream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Workbook writeVertical1(Workbook wb, String sheetName, List<?> datas, List<String> headerDatas, String[]p, String type) {
		if(wb == null){
			wb = new SXSSFWorkbook();
		}
		Sheet sheet = this.sheetBuilder.builder(wb, sheetName);
		int row = 0;
		if(this.headers.size() > 0){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			for(int i= 0,j = this.headers.size();i < j;i++){
				String header = this.headers.get(i);
				sheet.addMergedRegion(new CellRangeAddress(row, row, 0, this.maxCell - 1));
				Row r = sheet.createRow(row);
				r.setHeightInPoints(30);
				Cell cell = r.createCell(0,XSSFCell.CELL_TYPE_STRING);
				if(i == 0){
					CellStyle style1 = wb.createCellStyle();
					Font font1 = wb.createFont();
					font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
					style1.setFont(font);
					style1.setAlignment(XSSFCellStyle.ALIGN_CENTER);
					style1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
					cell.setCellStyle(style1);
				}else{
					cell.setCellStyle(style);
				}
				cell.setCellValue(header);
				row++;
			}
		}
		if(this.headerTitles!=null&&this.headerTitles.size() > 0){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			Row r = sheet.createRow(row++);
			r.setHeightInPoints(defaultHeight);
			for(int c=0,l = this.headerTitles.size();c < l;c++){
				Cell cell = r.createCell(c, XSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(style);
				cell.setCellValue(this.headerTitles.get(c));
			}
		}
		if(headerDatas!=null&&headerDatas.size() > 0){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			Row r = sheet.createRow(row++);
			r.setHeightInPoints(defaultHeight);
			for(int c=0,l =headerDatas.size();c < l;c++){
				Cell cell = r.createCell(c, XSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(style);
				cell.setCellValue(headerDatas.get(c));
			}
		}
		if(this.titles.size() > 0){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			Row r = sheet.createRow(row++);
			r.setHeightInPoints(defaultHeight);
			for(int c=0,l = this.titles.size();c < l;c++){
				Cell cell = r.createCell(c, XSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(style);
				cell.setCellValue(this.titles.get(c));
			}
		}
		if(datas == null || datas.size() == 0){
			return wb;
		}
		int initRow = row + 1;
		
		if("1".equals(type)){
			Row ro = this.rowBuilder.builder(sheet, row++, null);
			for(int c = 1;c <= datas.size();c++){
				if(this.cellBuilder.builder(ro, c, datas.get(c-1)) == null){
					break;
				}
			}
		}else if("2".equals(type)){
			for(int i=0;i<p.length;i++){
				Row ro = this.rowBuilder.builder(sheet, row++, null);
				for(int c = 0;c < datas.size();c++){
					if(c%(p.length)==i){
						if(this.cellBuilder.builder(ro, c/(p.length), datas.get(c)) == null){
							break;
						}
					}
				}
			}
		}else if("3".equals(type)){
			for(int i=0;i<p.length;i++){
				Row ro = this.rowBuilder.builder(sheet, row++, null);
				for(int c = 0;c < datas.size();c++){
					if(this.cellBuilder.builder(ro, c, datas.get(c)) == null){
						break;
					}
				}
			}
		}
		
		
		
		
		
		if(this.sumColumns.size() > 0){
			int dataLastRow = row;
			Row r = sheet.createRow(row++);
			r.setHeightInPoints(defaultHeight);
			if(this.sumColumns.get(0) != 0){
				Cell cell = r.createCell(0);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("合计");
			}
			CellStyle style = r.getSheet().getWorkbook().createCellStyle();
			style.setDataFormat(r.getSheet().getWorkbook().createDataFormat().getFormat("0.00"));
			for(int i = 0, j = this.sumColumns.size();i < j;i++){
				Cell cell = r.createCell(this.sumColumns.get(i));
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellStyle(style);
				String column = getColumnChar(this.sumColumns.get(i));
				cell.setCellFormula("sum("+column+initRow+":"+column+dataLastRow+")");
			}
			sheet.setForceFormulaRecalculation(true);
		}
		return wb;
	}
	
	/**
	 * 生成新的excel sheet，并将数据生成到sheet中，最后导出到提供的输出流
	 * datas 集合的大小为excel的列数
	 * */
	public void writeVertical1(String sheetName, String[]p, List<?> datas, OutputStream stream, String type) {
		Workbook wb = writeVertical1(null, sheetName, datas,null,p,type);
		try {
			wb.write(stream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据不同浏览器标题转为不同编码
	 * @param request
	 * @param fileNames
	 * @return
	 */
	public static String processFileName(HttpServletRequest request, String fileNames) {
		String codedfilename = null;
		try {
			String agent = request.getHeader("USER-AGENT");
			if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident")) {// ie

				String name = java.net.URLEncoder.encode(fileNames, "UTF8");

				codedfilename = name;
			}else if(null != agent && -1 != agent.indexOf("Edge")){
				String name = java.net.URLEncoder.encode(fileNames, "UTF8");

				codedfilename = name;
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等

				codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codedfilename;
	}
}
