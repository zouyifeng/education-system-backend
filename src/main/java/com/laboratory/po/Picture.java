package com.laboratory.po;

import org.apache.ibatis.type.Alias;

@Alias("Picture")
public class Picture {
	
	private String path;
	
	private Integer newsId;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	
	
}
