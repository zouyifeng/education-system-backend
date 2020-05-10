package com.laboratory.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 时间工具类
 * @author wq_c
 *
 */
public class DateUtil {
	
	
	/**
	 *  把时间按固定格式输出
	 */
	public static String formatToString(String pattern , Date date){
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		if(null != date && null!=pattern && pattern.length()>0){
			return dateFormat.format(date);
		}else{
			return "";
		}
	}
	
}
