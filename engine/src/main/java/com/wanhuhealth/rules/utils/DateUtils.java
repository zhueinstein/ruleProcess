package com.wanhuhealth.rules.utils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author Wu Liepeng
 * @version 2014-03-25
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {
	
	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy-MM", "yyyyMM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd）
	 */
	public static String formatDate(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}
	public static int getYearInt() {
		return Integer.parseInt(formatDate(new Date(), "yyyy"));
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}
	public static int getMonthInt() {
		return Integer.parseInt(formatDate(new Date(), "MM"));
	}
	/**
	 * 获取上一月
	 * @param month
	 * @return
	 */
	public static String getBackMonth(String month){
		if(StringUtils.isNotEmpty(month)){
			int m = Integer.parseInt(month);
			if(m == 1)
				return "12";
			return String.valueOf(m - 1);
		}
		return month;
	}
	public static int getBackMonth(int month){
		if(month > 0 && month < 12){
			if(month == 1)
				return 12;
			return month - 1;
		}
		return month;
	}
	/**
	 * 获取上一个年月的月份
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getBackYearMonth(String year, String month){
		if(StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(month)){
			int m = getBackMonth(Integer.parseInt(month));
			if("1".equals(month)){
				int y = Integer.parseInt(year);
				if(m < 10)
					return (y - 1) + "-0" + m; 
				return (y - 1) + "-" + m; 
			}
			if(m < 10)
				return year + "-0" + m; 
			return year + "-" + m; 
		}
		return year + "-" + month;
	}
	public static String getBackYearMonth(int year, int month){
		if(month > 0 && month < 13){
			int m = getBackMonth(month);
			if(month == 1){
				return (year - 1) + "-12"; 
			}
			if(m -1 < 10 )
				return year + "-0" + (m-1); 
			return year  + "-" + (m - 1); 
		}
		return year + "-" + month;
	}
	/**
	 * 用过年月 转换成年-月(2012-07) 月前加0
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getYearAndMonth(int year , int month){
		if(month > 0 && month < 13 ){
			if(month <10)
				return year + "-0" + month;
		}
		return year + "-" + month;
	}
	/**
	 * 获取月份字符串 小于10月的前面填0
	 * @param month
	 * @return
	 */
	public static String getMonthStr(int month){
		if(month > 0 && month < 13 ){
			if(month <10)
				return "0" + month;
		}
		return String.valueOf(month);
	}
	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}
	/**
	 * 获取过去的月份列表
	 * @param year 获取月份列表的年  为空是获取为当前年的月列表
	 * @return
	 */
	public static List<String> pastMonths(String year){
		String month = getMonth();
		String cyear = getYear();
		if(StringUtils.isNotEmpty(month)){
			List<String> monthList = new ArrayList<String>();
			int m = 12;
			if(year == null || cyear.equals(year))
				m = Integer.parseInt(month);
			for (int i = m; i >= 1; i--) {
				monthList.add(String.valueOf(i));
			}
			return monthList;
		}
		return null;
	}
	/**
	 * 获取过去的年列表
	 * @return
	 */
	public static List<String> pastYears(){
		String year = getYear();
		if(StringUtils.isNotEmpty(year)){
			int N = 5;//获取过去5年的年份
			List<String> YearList = new ArrayList<String>();
			int y = Integer.parseInt(year);
			for (int i = 0; i < N; i++) {
				YearList.add(String.valueOf(y --));
			}
			return YearList;
		}
		return null;
	}
	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}
	
    
	public static Date getDateStart(Date date) {
		if(date==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date= sdf.parse(formatDate(date, "yyyy-MM-dd")+" 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date getDateEnd(Date date) {
		if(date==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date= sdf.parse(formatDate(date, "yyyy-MM-dd") +" 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 获取延期的时间（long）
	 * 只要小于即是符合的时间
	 * @param n
	 * @return null代表传入值有问题，否则代表正确
	 */
	public static Long getLaterLong(Date date, Integer n){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowDateString = sdf.format(date);
		try {
			Date newDate = sdf.parse(nowDateString);
			if(n!=null){
				System.out.println(new Date((long)(newDate.getTime()+1000*60*60*24*(n))));
				return (long)(newDate.getTime()+1000*60*60*24*(n));
			}else{
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public static long getNowDateLong(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = sdf.format(date);
		try {
			return sdf.parse(dateString).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date().getTime();
		}
	}
	/**将当天的时间化为0点的整型
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		/*List<String> months = pastMonths("2014");
		for (String string : months) {
			System.out.println(string);
		}*/
		DateUtils du = new DateUtils();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(du.getNowDateLong()));
		
	}
	/**
	 * 获取用户的年龄
	 */
	public static int getUserAge(String birthDay){
		if(StringUtils.isNotBlank(birthDay)){
			String birthYear = birthDay.substring(0, birthDay.indexOf("-"));
			return DateUtils.getYearInt() - Integer.parseInt(birthYear);
		}else{
			return 0;
		}
	}
	/**
	 * 获取用户的年龄
	 */
	public static int getUserAge(Date birthday){
		if(birthday!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return getUserAge(sdf.format(birthday));
		}else{
			return 0;
		}
	}
	/**
	 * 比较两个Date型时间的是否相等，比较到日
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareToDate(Date date1, Date date2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		boolean flag = false;
		if(date1==null&&date2==null){
			flag = true;
		}else if(date1==null&&date2!=null){
			flag = false;
		}else if(date1!=null&&date2==null){
			flag = false;
		}else if(date1!=null&&date2!=null){
			if(sdf.format(date1).equals(sdf.format(date2))){
				flag = true;
			}else{
				flag = false;
			}
		}
		return flag;
	}
	/**
	 * 将当天的日期格式化为精确到日的Date型
	 * @param date
	 * @return
	 */
	public static Date getDayDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dayDateString = sdf.format(date);
		try {
			return sdf.parse(dayDateString);
		} catch (ParseException e) {
			return null;
		}
	}
	/**
	 * 获取今天的时间，格式为yyyy-MM-dd
	 * @param
	 * @return
	 */
	public static Date getDayNowDate(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dayDateString = sdf.format(date);
		try {
			return sdf.parse(dayDateString);
		} catch (ParseException e) {
			return null;
		}
	}
	/**
	 * 获取两个日期的时间差（天），
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static long getTwoDateSubDays(Date beginDate, Date endDate){
		if(beginDate!=null&&endDate!=null){
			return (getDayDate(endDate).getTime()-getDayDate(beginDate).getTime())/(1000*60*60*24);
		}else{
			return 0l;
		}
	}
	
	/**
	 * 获取指定天数后的时间
	 * 
	 * @return
	 */
	public static Date getNextDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}
	

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String getSeason(String date) {
		if(StringUtils.isNotEmpty(date)&&date.length()>7){
			if(date.substring(5, 7).equals("01")){
				return "第一季度";
			}
			if(date.substring(5, 7).equals("04")){
				return "第二季度";
			}
			if(date.substring(5, 7).equals("07")){
				return "第三季度";
			}
			if(date.substring(5, 7).equals("10")){
				return "第四季度";
			}
		}
		return "";
	}

	public static long getTimeGapToNow(Date input){
		return Math.abs(System.currentTimeMillis() - input.getTime());
	}

	public static boolean compareDateToNow(Date input){
		return System.currentTimeMillis() - input.getTime() > 0? true: false;
	}
	
	/*  
    * @param year 
    *            int 年份 
    * @param month 
    *            int 月份 
    *  
    * @return int 某年某月的最后一天 
    */  
	public static Date getLastDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
        c.setTime(date);    
        c.set(Calendar.DATE, 1);
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, -1);
        date = c.getTime();    
        return date;   
   }
	
	/*  
    * @param year 
    *            int 年份 
    * @param month 
    *            int 月份 
    *  
    * @return int 某年某月的最后一天 
    */  
	public static String getMondayByDate(String date, String format) {
		SimpleDateFormat sdf=new SimpleDateFormat(format); //设置时间格式
        Calendar cal = Calendar.getInstance();
        Date time = null;
		try {
			time = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        cal.setTime(time);  
//        System.out.println("要计算日期为:"+sdf.format(cal.getTime())); //输出要计算日期  
        
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if(1 == dayWeek) {  
           cal.add(Calendar.DAY_OF_MONTH, -1);
        }  
        
       cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
       
       int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
       cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
       return sdf.format(cal.getTime());   
   }
}
