package com.laboratory.util;

import java.io.Serializable;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class JsonRequest implements Cloneable, Serializable{
	private static final long sericalVersionUID = 1L;
	public static final String key = "jsonRequest";
	
	Object data;
	
	PageInfo pageInfo;
	
	public void setData(Object data){
		this.data = data;
	}
	
	public Object getData(){
		return this.data;
	}

	public PageInfo getPageInfo() {
		return this.pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
}
