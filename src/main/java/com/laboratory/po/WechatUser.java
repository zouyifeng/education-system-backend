package com.laboratory.po;

import org.apache.ibatis.type.Alias;

@Alias("WechatUser")
public class WechatUser {
	private Integer id;
	private String openId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
