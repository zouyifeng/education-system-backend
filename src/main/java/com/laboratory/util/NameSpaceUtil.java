package com.laboratory.util;

public class NameSpaceUtil {
	
	public final static String PREFIX = "com.laboratory.dao"; 
	
	public final static String SUFFIX = "Mapper";
	
	public static String getNameSpace(String entity){
		String name = entity.substring(entity.lastIndexOf("."));
		return PREFIX + name + SUFFIX;
	}
}
