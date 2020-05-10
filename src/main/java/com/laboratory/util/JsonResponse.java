package com.laboratory.util;

import java.util.HashMap;
import java.util.Map;

import com.laboratory.util.PageBean;

public class JsonResponse implements java.io.Serializable, Cloneable{
	private static final long serialVersionUID = -5254768593025959969L;
	public static final String key = "jsonResponse";
	
	Object data;
	
	PageBean pageBean;

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public void setData(Object data){
		this.data = data;
	}
	
	public Object getData(){
		return data;
	}
	
	public static JsonResponse newOk(Object data){
		JsonResponse response = new JsonResponse();
		response.setData(data);
		return response;
	}
	
	public static JsonResponse newOk(Object data, PageBean pageBean){
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.putAll((Map<String, Object>) data);
		ret.put("list", pageBean.getList());
		ret.put("pageInfo", pageBean.getPageInfo());
		JsonResponse response = new JsonResponse();
		response.setData(ret);
		return response;
	}
}
