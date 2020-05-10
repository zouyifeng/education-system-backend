package com.laboratory.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Element;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.laboratory.po.News;
import com.laboratory.po.wechat.WechatImage;
import com.laboratory.po.wechat.WechatImageMessage;
import com.laboratory.po.wechat.WechatMusic;
import com.laboratory.po.wechat.WechatMusicMessage;
import com.laboratory.po.wechat.WechatNews;
import com.laboratory.po.wechat.WechatNewsMessage;
import com.laboratory.po.wechat.WechatTextMessage;
import com.laboratory.service.NewsService;
import com.thoughtworks.xstream.XStream;

public class WechatMessageUtil {
	
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_MUSIC = "MUSIC";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE= "scancode_push";
	
	public static final String OUTER_NET = WechatUtil.DOMAIN+"/pages/dist/index.html";
	
	/**
	 * xml转集合
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		
		Element root = doc.getRootElement();
		
		List<Element> list = root.elements();
		
		for(Element e : list){
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	
	/**
	 * 文本对象转xml
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(WechatTextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	/**
	 * 图文对象转xml
	 * @param textMessage
	 * @return
	 */
	public static String newsMessageToXml(WechatNewsMessage wechatNewsMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", wechatNewsMessage.getClass());
		xstream.alias("item", new WechatNews().getClass());
		return xstream.toXML(wechatNewsMessage);
	}
	
	/**
	 * 图文对象转xml
	 * @param textMessage
	 * @return
	 */
	public static String imageMessageToXml(WechatImageMessage wechatImageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", wechatImageMessage.getClass());
		return xstream.toXML(wechatImageMessage);
	}
	
	/**
	 * 音乐对象转xml
	 * @param textMessage
	 * @return
	 */
	public static String musicMessageToXml(WechatMusicMessage wechatMusicMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", wechatMusicMessage.getClass());
		return xstream.toXML(wechatMusicMessage);
	}
	
	/**
	 * 图文消息组装
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName, String fromUserName, News news, String cover){
		String message = null;
		List<WechatNews> newsList = new ArrayList<WechatNews>();
		WechatNewsMessage newsMessage = new WechatNewsMessage();
		
		WechatNews wechatNews  = new WechatNews();
		
		wechatNews.setTitle(news.getTitle());
		wechatNews.setDescription(news.getSource());
		wechatNews.setPicUrl(cover);
		wechatNews.setUrl(OUTER_NET + "#/h5NewsDetail/" + news.getId());
		newsList.add(wechatNews);
		
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime()+"");
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
		
		message = newsMessageToXml(newsMessage);
		return message;
	}
	
	/**
	 * 组装图片消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName, String fromUserName){
		String message = null;
		WechatImage image = new WechatImage();
		image.setMediaId("pmVNwnOmAd-8Wlh9nXfeoMQhZ_kSl49uIbO-20r5-AczIW0ftdalFMUiv4XIA2lF");
		WechatImageMessage wechatImageMessage = new WechatImageMessage();
		wechatImageMessage.setFromUserName(toUserName);
		wechatImageMessage.setToUserName(fromUserName);
		wechatImageMessage.setMsgType(WechatMessageUtil.MESSAGE_IMAGE);
		wechatImageMessage.setCreateTime(new Date().getTime() + "");
		wechatImageMessage.setImage(image);
//		wechatImageMessage.setMediaId();
		message = imageMessageToXml(wechatImageMessage);
		return message;
	}
	
	/**
	 * 组装音乐消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName, String fromUserName){
		String message = null;
		WechatMusic music = new WechatMusic();
		music.setThumbMediaId("d_nyZRIp7QTQudROxU5w9XCsu8FBVcp08el30YER0qhOrFO5UXTilfg266uPDSEZ");
		music.setTitle("see you again");
		music.setDescription("速度7片尾曲");
		music.setMusicUrl("http://zouyifeng.tunnel.qydev.com/resources/Kalimba.mp3");
		music.setHQMusicUrl("http://zouyifeng.tunnel.qydev.com/resources/Kalimba.mp3");
		
		WechatMusicMessage wechatMusicMessage = new WechatMusicMessage();
		wechatMusicMessage.setFromUserName(toUserName);
		wechatMusicMessage.setToUserName(fromUserName);
		wechatMusicMessage.setMsgType(WechatMessageUtil.MESSAGE_MUSIC);
		wechatMusicMessage.setCreateTime(new Date().getTime() + "");
		wechatMusicMessage.setMusic(music);
//		wechatImageMessage.setMediaId();
		message = musicMessageToXml(wechatMusicMessage);
		return message;
	}
	
	/**
	 * 初始化菜单
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName, String fromUserName, String content) {
		WechatTextMessage text = new WechatTextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(WechatMessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime() + "");
		text.setContent(content);
		return textMessageToXml(text);
	}
	
	/**
	 * 主菜单
	 * @return
	 */
	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎关注我爱学习！\r\r");
		sb.append("回复 1  查看机构历史纪录\r\r");
		return sb.toString();
	}
	
	/**
	 * 输入框回复一
	 * @return
	 */
	public static String newsHistoryMenu(List<News> news) {
		StringBuffer sb = new StringBuffer();
		sb.append("历史回顾：\r\r\r");
		for(News n : news) {
			sb.append("<a href=\"" + OUTER_NET + "#/h5NewsDetail/"  + n.getId()+ "\">" + n.getTitle() + "</a>\r\r");
		}
		return sb.toString();
	}
	
	/**
	 * 输入框回复二
	 * @return
	 */
	public static String secondMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("这是第二条选项结果");
		return sb.toString();
	}
}
