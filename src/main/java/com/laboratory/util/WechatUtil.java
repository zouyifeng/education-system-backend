package com.laboratory.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.java.browser.plugin2.DOM;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;import org.apache.http.impl.client.TargetAuthenticationStrategy;
import org.apache.http.util.EntityUtils;

import com.laboratory.po.Apply;
import com.laboratory.po.News;
import com.laboratory.po.wechat.ApplyTemplate;
import com.laboratory.po.wechat.Data;
import com.laboratory.po.wechat.Data_remark;
import com.laboratory.po.wechat.Data_title;
import com.laboratory.po.wechat.Data_type;
import com.laboratory.po.wechat.Data_welcomeMsg;
import com.laboratory.po.wechat.NewsTemplate;
import com.laboratory.po.wechat.WechatAccessToken;
import com.laboratory.po.wechat.WechatButton;
import com.laboratory.po.wechat.WechatClickButton;
import com.laboratory.po.wechat.WechatImageMessage;
import com.laboratory.po.wechat.WechatMenu;
import com.laboratory.po.wechat.WechatViewButton;

import net.sf.json.JSONObject;

/**
 * @author ZouYifeng
 *
 */
/**
 * @author ZouYifeng
 *
 */
public class WechatUtil {
//	private static final String APPID = "wxb99f18526010538a";
	public static final String APPID = "wx6795830453c89e95";

//	private static final String APPSECRET = "e8b943c7688fc01a877b21227eddfc8d";
	public static final String APPSECRET = "0284068c2ddb31e9b8db4b337c35e5ab";

	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	private static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	
	private static final String REDIRECT_USER_LOGIN = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";

	public static final String DOMAIN = "http://k3jq3g.natappfree.cc";
	/**
	 * get请求
	 * @param url
	 * @return
	 */
	public static JSONObject doGetStr(String url){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;

	}
	
	
	/**
	 * post 请求
	 * @param url
	 * @param outStr
	 * @return
	 */
	public static JSONObject doPostStr(String url, String outStr) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr,"UTF-8"));
		HttpResponse response = client.execute(httpost);
		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
	
	/**
	 * 获取access_token
	 * @return
	 */
	public static WechatAccessToken getAccessToken(){
		WechatAccessToken token = new WechatAccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject != null){
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	
	/**
	 *菜单组装 
	 * @return
	 */
	public static WechatMenu initMenu(){
		WechatMenu menu = new WechatMenu();
		WechatClickButton button11 = new WechatClickButton();
		button11.setName("新动态");
		button11.setType("click");
		button11.setKey("11");	
		
		WechatViewButton button21 = new WechatViewButton();
		button21.setName("个人中心");
		button21.setType("view");
//		button21.setUrl("http://zouyifeng.imwork.net/pages/dist/index.html#/h5QA");
		button21.setUrl(REDIRECT_USER_LOGIN
				.replace("APPID", APPID)
				.replace("APPSECRET", APPSECRET)
				.replace("STATE", "person")
				.replaceAll("REDIRECT_URI", DOMAIN+"/oauth.action"));
		
//		WechatClickButton button31 = new WechatClickButton();
//		button31.setName("扫码事件");
//		button31.setType("scancode_push");
//		button31.setKey("31");
		
		WechatViewButton button31 = new WechatViewButton();
		button31.setName("翻译助手");
		button31.setType("view");
		button31.setUrl(DOMAIN + "/pages/dist/index.html#/h5Translate");
		
		WechatViewButton button32 = new WechatViewButton();
		button32.setName("预报名");
		button32.setType("view");
		button32.setUrl(REDIRECT_USER_LOGIN
				.replace("APPID", APPID)
				.replace("APPSECRET", APPSECRET)
				.replace("STATE", "reply")
				.replaceAll("REDIRECT_URI", DOMAIN + "/oauth.action"));
		
//		WechatClickButton button32 = new WechatClickButton();
//		button32.setName("地理位置");
//		button32.setType("location_select");
//		button32.setKey("32");
		
		WechatButton button = new WechatButton();
		button.setName("更多");
		button.setSub_button(new WechatButton[]{button31, button32});
		
		menu.setButton(new WechatButton[]{button11, button21, button});
		return menu;
	}
	
	public static int createMenu(String token, String menu)  throws ParseException, IOException {

		int result = 2;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);

		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	
	/**
	 * 上传
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public static String upload(String filePath, String accessToken, String type) throws IOException{
		File file = new File(filePath);
		if(!file.exists() || !file.isFile()){
			throw new IOException("文档不存在");
		}
		
		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		
		URL urlObj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
	
		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");	
		
		String BOUNDARY = "-----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
		
		StringBuilder sb = new StringBuilder();
		sb.append("---");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		
		byte[] head = sb.toString().getBytes("utf-8");

		//获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		//输出表头
		out.write(head);

		//文件正文部分
		//把文件以流的方式推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		//结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//¶¨Òå×îºóÊý¾Ý·Ö¸ôÏß

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			//定义BufferReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id"; 
		if(!"image".equals(type)){
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	public static String translate(String source) throws ParseException, IOException  {
		String appId = "20170406000044230";
		String appSecret = "duG7zRN9RlPl9k6CVvgl";
		String salt = String.valueOf(System.currentTimeMillis());
		String sign = MD5.md5(appId + source + salt + appSecret);
		String encodSource = URLEncoder.encode(source, "UTF-8");
		
		String url = "http://api.fanyi.baidu.com/api/trans/vip/translate?q=" + encodSource + "&from=auto&to=auto&appid=" + appId + "&salt="+ salt +"&sign="+ sign +"";
		JSONObject jsonObject = doGetStr(url);
//		TransResultList transResultObject = (TransResultObject) JSONObject.toBean(jsonObject, TransResultObject.class);
		List<Map> list = (List<Map>) jsonObject.get("trans_result");
//		TransResult transResult = (TransResult) transResultObject.getTrans_result().get(0);
		String type = jsonObject.get("from") == "zh" ? "中译英" : "英译中";
		StringBuffer dst = new StringBuffer();
		StringBuffer src = new StringBuffer();
		for(Map map : list){
			src.append(map.get("src"));
			dst.append(map.get("dst"));
		}
//		System.out.println(transResultObject.getTrans_result());
		String result = "翻译类型：" + type + ";    \"" + src.toString() + "\" 翻译为 \"" + dst.toString() + "\"";

		return result;
		
	}
	
	/**
     * 发送模板消息
     * appId 公众账号的唯一标识
     * appSecret 公众账号的密钥
     * openId 用户标识
     */
    public static void send_template_message(News news, String openId) throws IOException{

        WechatAccessToken token = getAccessToken();
        String access_token = token.getToken();
        String url = SEND_TEMPLATE_URL + access_token;
        String newsType = "";

        NewsTemplate temp = new NewsTemplate();
        Data data = new Data();
        Data_remark remark = new Data_remark();
        Data_title title = new Data_title();
        Data_welcomeMsg welcomeMsg = new Data_welcomeMsg();
        Data_type type = new Data_type();

        welcomeMsg.setValue("同学，你收到了一条最新发布的动态哦，赶紧去看看吧!");
        title.setValue(news.getTitle());
        
        switch(news.getType()) {
        	case  1: 
        		newsType = "班级活动";
        		break;
        	case  2: 
        		newsType = "交流成果";
        		break;
        	case  3: 
        		newsType = "学习成果";
        		break;
        	case  4: 
        		newsType = "通知公告";
        		break;
        }
        
        type.setValue(newsType);
        type.setColor("#173177");
        remark.setValue("猛戳详情，查看第一手资料");
        remark.setColor("#173177");

		data.setTitle(title);
		data.setType(type);
		data.setWelcomeMsg(welcomeMsg);
        data.setRemark(remark);
        temp.setTouser(openId);
        temp.setTemplate_id("G7-TK5YwaYeMy_0eHe3tYnSMX6OWTu1YOBGYMjAAgGM");
        temp.setUrl(WechatMessageUtil.OUTER_NET + "#/h5NewsDetail/" + news.getId());
        temp.setTopcolor("#173177");
        temp.setData(data);
        
        String jsonString = JSONObject.fromObject(temp).toString().replace("day", "Day");
        JSONObject jsonObject = doPostStr(url, jsonString);
        System.out.println(jsonObject);
        int result = 0;
        if (null != jsonObject) {  
             if (0 != jsonObject.getInt("errcode")) {  
                 result = jsonObject.getInt("errcode");  
                 System.out.println("错误 errcode:{} errmsg:{}  " + "   " +jsonObject.getInt("errcode") + "  " + jsonObject.getString("errmsg"));  
             }  
        }
//      log.info("模板消息发送结果："+result);
        System.out.println("模板消息发送结果："+result);
    }
    
    public static void send_apply_template_message(Apply apply) throws IOException{

        WechatAccessToken token = getAccessToken();
        String access_token = token.getToken();
        String url = SEND_TEMPLATE_URL + access_token;

        ApplyTemplate temp = new ApplyTemplate();
        Data data = new Data();
        Data_remark remark = new Data_remark();
        Data_title title = new Data_title();
        Data_welcomeMsg welcomeMsg = new Data_welcomeMsg();


        welcomeMsg.setValue("亲，下面是你的预报名回复哦！");
        title.setValue(apply.getCourse());
        remark.setValue(apply.getReply());


		data.setTitle(title);
		data.setWelcomeMsg(welcomeMsg);
        data.setRemark(remark);
        temp.setTouser(apply.getCreator());
        temp.setTemplate_id("usXb_BksBJuS9bKAn65pJyBwUZBTZF53s4UYlbBeLYM");
        temp.setTopcolor("#173177");
        temp.setData(data);
        
        String jsonString = JSONObject.fromObject(temp).toString().replace("day", "Day");
        JSONObject jsonObject = doPostStr(url, jsonString);
        System.out.println(jsonObject);
        int result = 0;
        if (null != jsonObject) {  
             if (0 != jsonObject.getInt("errcode")) {  
                 result = jsonObject.getInt("errcode");  
                 System.out.println("错误 errcode:{} errmsg:{}  " + "   " +jsonObject.getInt("errcode") + "  " + jsonObject.getString("errmsg"));  
             }  
        }
//      log.info("模板消息发送结果："+result);
        System.out.println("模板消息发送结果："+result);
    }

    /**
     * 底部按钮重定向链接
     * @return
     */
    public static String getRedirectUrl(){
//    	String a = REDIRECT_USER_LOGIN.replace("APPID", APPID).replace("APPSECRET", APPSECRET).replaceAll("REDIRECT_URI", "www.baidu.com");
    	return REDIRECT_USER_LOGIN
    			.replace("APPID", APPID)
    			.replace("APPSECRET", APPSECRET)
    			.replaceAll("REDIRECT_URI", DOMAIN + "/oauth.action");
    }
}
