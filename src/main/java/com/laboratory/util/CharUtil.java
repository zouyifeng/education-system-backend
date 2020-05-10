package com.laboratory.util;

public class CharUtil {
	
	/**
	 * 将数据分段显示
	 * @param ch 要处理的段落
	 * @return
	 */
	public static String dealChar(String ch){
		String  ch1 = ch.replace("\n", "<br>");
		
		String ch2 = ch1.replace(" ", "&nbsp");
		return ch2;
	}
}
