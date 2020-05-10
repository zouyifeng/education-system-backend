package com.laboratory.test;


import java.io.IOException;

import org.apache.http.ParseException;

import net.sf.json.JSONObject;

import com.laboratory.po.wechat.WechatAccessToken;
import com.laboratory.util.WechatUtil;

public class WechatTest {
	public static void main(String[] args) throws ParseException, IOException{
		WechatAccessToken token = WechatUtil.getAccessToken();
		System.out.println("票据： " + token.getToken());
		System.out.println("有效时间：" + token.getExpiresIn());
		
//		String path = "E:/images.jpg";
		try {
//			String mediaId = WechatUtil.upload(path, token.getToken(), "thumb");
//			System.out.println(mediaId);
//			String result = WechatUtil.translate("今天，我很开心，我和妈妈去超市了");
//			System.out.println(result);
//			WechatUtil.send_template_message();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String menu = JSONObject.fromObject(WechatUtil.initMenu()).toString();
		int result = 1;
		if(menu != null && token.getToken() != null){
			result = WechatUtil.createMenu(token.getToken(), menu);			
		}
		if(result == 0) {
			System.out.println("创建菜单成功");
		}else{
			System.out.println("错误码：" + result);
		}
	}
}
