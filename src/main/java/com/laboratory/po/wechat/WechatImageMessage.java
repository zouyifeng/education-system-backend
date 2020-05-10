package com.laboratory.po.wechat;



public class WechatImageMessage extends WechatBaseMessage {
	private WechatImage Image;
	
	public WechatImage getImage() {
		return Image;
	}
	
	public void setImage(WechatImage image){
		Image = image;
	}
//	private String MediaId;
//
//	public String getMediaId() {
//		return MediaId;
//	}
//
//	public void setMediaId(String mediaId) {
//		this.MediaId = mediaId;
//	}
	
}
