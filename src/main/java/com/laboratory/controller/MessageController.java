package com.laboratory.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laboratory.po.Message;
import com.laboratory.service.MessageService;
import com.laboratory.util.DateUtil;
import com.laboratory.util.JsonRequest;
import com.laboratory.util.JsonResponse;
import com.laboratory.util.PageBean;
import com.laboratory.util.PageInfo;

@RestController
public class MessageController extends AbstractController {

	@Autowired
	private MessageService messageService;
	
	@RequestMapping("/message_list")
	public JsonResponse showMessageList(@RequestBody JsonRequest jsonRequest){		
		List<Message> message = messageService.findByPage(1, 7);
		
		return JsonResponse.newOk(message);
	}
	
	@RequestMapping("/message_add.action")
	public JsonResponse addArticle(@RequestBody JsonRequest jsonRequest){
		Message message = objectMapper.convertValue(jsonRequest.getData(), Message.class);
		Date date = new Date();
		message.setCreateTime(DateUtil.formatToString("yyyy-MM-dd", date));
		Map<String, String> ret = new HashMap<String, String>();
		
		int i = messageService.saveMessage(message);
		
		if( i>0 ){
			ret.put("state", "2000");
		}else{
			ret.put("state", "5000");
		}
		
		return JsonResponse.newOk(ret);
	}
	
	@RequestMapping("/admin/message_list.action")
	public JsonResponse addMessage (@RequestBody JsonRequest jsonRequest){
		Message message = objectMapper.convertValue(jsonRequest.getData(), Message.class);
		PageInfo pageInfo = objectMapper.convertValue(jsonRequest.getPageInfo(), PageInfo.class);
		List<Message> members = messageService.findMessageByCondition(message, jsonRequest.getPageInfo().getPageNum(), 10);
		PageBean<Message> page= new PageBean<Message>(members);
		
		return JsonResponse.newOk(page);
	}	
	
	@RequestMapping("/admin/message_delete.action")
	public JsonResponse deleteArticle(@RequestBody JsonRequest jsonRequest){
		
		Message message = objectMapper.convertValue(jsonRequest.getData(), Message.class);
		int i = messageService.deleteMessage(message.getId());
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("state", i>0 ? "2000" : "5000");
		return JsonResponse.newOk(ret);
		
	}
	
	//修改成员
	@RequestMapping("/admin/message_edit.action")
	public JsonResponse editMember(@RequestBody JsonRequest jsonRequest){
		Message message = objectMapper.convertValue(jsonRequest.getData(), Message.class);
		Date date = new Date();
		message.setReplyTime(DateUtil.formatToString("yyyy-MM-dd", date));
		
		int i = messageService.updateMessage(message);
		Map<String, String> ret = new HashMap<String, String>();
		if (i>0){
			 ret.put("state", "2000");
		}else{
			 ret.put("state", "5000");
		}
		return JsonResponse.newOk(ret);
	}
}
