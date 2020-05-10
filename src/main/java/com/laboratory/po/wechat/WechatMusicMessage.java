package com.laboratory.po.wechat;


public class WechatMusicMessage extends WechatBaseMessage{
	private WechatMusic Music;
	
	public WechatMusic getMusic(){
		return Music;
	}
	
	public void setMusic(WechatMusic music){
		Music = music;
	}
}
