package com.laboratory.po.wechat;

public class WechatAccessToken {
	private String token;
	private int expiresIn;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int i) {
		this.expiresIn = i;
	}
}
