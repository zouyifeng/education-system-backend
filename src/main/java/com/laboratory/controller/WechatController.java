package com.laboratory.controller;

import java.io.IOException;
import java.text.ParseException;
 


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboratory.po.Admin;
import com.laboratory.po.CheckModel;
import com.laboratory.po.News;
import com.laboratory.po.Picture;
import com.laboratory.po.Student;
import com.laboratory.po.Teacher;
import com.laboratory.po.WechatUser;
import com.laboratory.po.wechat.Translate;
import com.laboratory.service.AdminService;
import com.laboratory.service.NewsService;
import com.laboratory.service.StudentService;
import com.laboratory.service.TeacherService;
import com.laboratory.service.TokenService;
import com.laboratory.service.WechatService;
import com.laboratory.service.WechatUserService;
import com.laboratory.util.JsonRequest;
import com.laboratory.util.JsonResponse;
import com.laboratory.util.WechatMessageUtil;
import com.laboratory.util.WechatUtil;

import net.sf.json.JSONObject;

@RestController
public class WechatController {
	@Autowired
	private TokenService tokenService;

	@Autowired
	private WechatService wechatService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private WechatUserService wechatUserService;
	
	/**
     * 开发者模式token校验
     *1
     * @param wxAccount 开发者url后缀
     * @param response
     * @param tokenModel
     * @throws ParseException
     * @throws IOException
     */
    @RequestMapping(value="/wechat/check", method=RequestMethod.GET, produces = "text/plain")
    public @ResponseBody String validate(CheckModel tokenModel) throws ParseException, IOException {
        return tokenService.validate("zouyifeng", tokenModel);
    }
    
    @RequestMapping("/wechat/translate")
    public JsonResponse translate(@RequestBody JsonRequest jsonRequest) throws org.apache.http.ParseException, IOException {
    	Translate trans = objectMapper.convertValue(jsonRequest.getData(), Translate.class);
    	trans.setDst(WechatUtil.translate(trans.getSrc()));
    	return JsonResponse.newOk(trans);
    }
    
    @RequestMapping(value="/wechat/check", method=RequestMethod.POST)
    public String weixinReply(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    	
    	req.setCharacterEncoding("UTF-8"); 
    	resp.setCharacterEncoding("UTF-8");
    	
    	try{
    		Map<String, String> map = WechatMessageUtil.xmlToMap(req);
        	String fromUserName = map.get("FromUserName");
        	String toUserName = map.get("ToUserName");
        	String msgType = map.get("MsgType");
        	String content = map.get("Content");
        	
        	String message = null;
        	if(WechatMessageUtil.MESSAGE_TEXT.equals(msgType)){
        		
        		if("1".equals(content)){
        			message = WechatMessageUtil.initText(toUserName, fromUserName, WechatMessageUtil.newsHistoryMenu(wechatService.findRecentNews()));
        		}else if("2".equals(content)){
        			message = WechatMessageUtil.initNewsMessage(toUserName, fromUserName, wechatService.getLastestNew(), wechatService.getLatestNewCover());
        		}else if("3".equals(content)){
        			message = WechatMessageUtil.initImageMessage(toUserName, fromUserName);
        		}else if("4".equals(content)){
        			message = WechatMessageUtil.initMusicMessage(toUserName, fromUserName);
        		}else if("?".equals(content) || "？".equals(content)){
        			message = WechatMessageUtil.initText(toUserName, fromUserName, WechatMessageUtil.menuText());
        		}
        	}else if(WechatMessageUtil.MESSAGE_EVENT.equals(msgType)){
        			
        			String eventType = map.get("Event");
        			
        		if(WechatMessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
        			WechatUser user = new WechatUser();
        			user.setOpenId(fromUserName);
        			wechatUserService.addWechatUser(user);
        			
        			message = WechatMessageUtil.initText(toUserName, fromUserName, WechatMessageUtil.menuText());
        			
        		}else if(WechatMessageUtil.MESSAGE_CLICK.equals(eventType)){
        			
        			message = WechatMessageUtil.initNewsMessage(toUserName, fromUserName , wechatService.getLastestNew(), wechatService.getLatestNewCover());
        			
        		}else if(WechatMessageUtil.MESSAGE_VIEW.equals(eventType)){
        			
        			String url = map.get("EventKey");
        			message = WechatMessageUtil.initText(toUserName, fromUserName, url);
        			
        		}else if(WechatMessageUtil.MESSAGE_SCANCODE.equals(eventType)){
        			
        			String key = map.get("EventKey");
        			message = WechatMessageUtil.initText(toUserName, fromUserName, key);
        			
        		}
        	}else if(WechatMessageUtil.MESSAGE_LOCATION.equals(msgType)){
        		
        		String label = map.get("Label");
        		message = WechatMessageUtil.initText(toUserName, fromUserName, label);
        		
        	}
        	return message;
    	}catch(DocumentException e){
    		e.printStackTrace();
    	}
    
    	return "无法处理";
    }
    
   @RequestMapping(value="/oauth", method = RequestMethod.GET)
   public void weixinOAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
       String CODE = request.getParameter("code");
       String STATE = request.getParameter("state");
       String APPID = WechatUtil.APPID;
       String SECRET = WechatUtil.APPSECRET;
       String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code".replace("APPID", APPID).replace("SECRET", SECRET).replace("CODE", CODE);
       JSONObject jsonStr = WechatUtil.doGetStr(URL);
       String openid = jsonStr.get("openid").toString();
       String access_token = jsonStr.get("access_token").toString();
       Admin admin = new Admin();
		admin.setOpenId(openid);
		if(STATE.equals("person")){
			if(adminService.selectSelective(admin).size() != 0) {
				admin = adminService.selectSelective(admin).get(0);
				if(admin.getType() == 1) {
					Student student = new Student();
					student.setId(admin.getUserId());
					response.sendRedirect("/pages/dist/h5.html?studentId=" + student.getId());
				}else if(admin.getType() == 2){
					Teacher teacher = new Teacher();
					teacher.setId(admin.getUserId());
					response.sendRedirect("/pages/dist/h5.html?teacherId=" + teacher.getId());
				}
			} else {
				response.sendRedirect("/pages/dist/h5.html?redirect=" + openid);
			}
		} else if(STATE.equals("reply")){
			response.sendRedirect("/pages/dist/h5.html?apply="+openid);
		}
   }
   
   @RequestMapping("/wechat/userLogin")
   public JsonResponse wechatUserLogin(@RequestBody JsonRequest jsonRequest) {
	   Admin admin = objectMapper.convertValue(jsonRequest.getData(), Admin.class);
	   Admin other = adminService.findByPrinciple(admin);
	   if(other != null && other.getUserId() != null){
		   other.setOpenId(admin.getOpenId());
		   adminService.updateSelective(other);
		   if(other.getType() == 1){
			   return JsonResponse.newOk(studentService.findById(other.getUserId()));
		   }else if(other.getType() == 2) {
			   return JsonResponse.newOk(teacherService.findById(other.getUserId()));
		   }
	   } else {
		   Map<String, String> ret = new HashMap<String, String>();
		   ret.put("msg", "无此用户！");
		   return JsonResponse.newOk(ret);
	   }
	   return JsonResponse.newOk(null);
   }
}
