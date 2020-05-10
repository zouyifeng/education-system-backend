package com.laboratory.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laboratory.po.Apply;
import com.laboratory.service.ApplyService;
import com.laboratory.util.DateUtil;
import com.laboratory.util.JsonRequest;
import com.laboratory.util.JsonResponse;
import com.laboratory.util.PageBean;
import com.laboratory.util.PageInfo;

@RestController
public class ApplyController extends AbstractController {

	@Autowired
	private ApplyService applyService;
	
	@RequestMapping("/apply_list")
	public JsonResponse showApplyList(@RequestBody JsonRequest jsonRequest){		
		List<Apply> apply = applyService.findByPage(1, 7);
		
		return JsonResponse.newOk(apply);
	}
	
	@RequestMapping("/apply_add.action")
	public JsonResponse addArticle(@RequestBody JsonRequest jsonRequest){
		Apply apply = objectMapper.convertValue(jsonRequest.getData(), Apply.class);
		Date date = new Date();
		Map<String, String> ret = new HashMap<String, String>();
		
		int i = applyService.saveApply(apply);
		
		if( i>0 ){
			ret.put("state", "2000");
		}else{
			ret.put("state", "5000");
		}
		
		return JsonResponse.newOk(ret);
	}
	
	@RequestMapping("/admin/apply_list.action")
	public JsonResponse addApply (@RequestBody JsonRequest jsonRequest){
		Apply apply = objectMapper.convertValue(jsonRequest.getData(), Apply.class);
		PageInfo pageInfo = objectMapper.convertValue(jsonRequest.getPageInfo(), PageInfo.class);
		List<Apply> members = applyService.findApplyByCondition(apply, jsonRequest.getPageInfo().getPageNum(), 10);
		PageBean<Apply> page= new PageBean<Apply>(members);
		
		return JsonResponse.newOk(page);
	}	
	
	@RequestMapping("/admin/apply_delete.action")
	public JsonResponse deleteArticle(@RequestBody JsonRequest jsonRequest){
		
		Apply apply = objectMapper.convertValue(jsonRequest.getData(), Apply.class);
		int i = applyService.deleteApply(apply.getId());
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("state", i>0 ? "2000" : "5000");
		return JsonResponse.newOk(ret);
		
	}
	
	//修改成员
	@RequestMapping("/admin/apply_edit.action")
	public JsonResponse editMember(@RequestBody JsonRequest jsonRequest) throws IOException{
		Apply apply = objectMapper.convertValue(jsonRequest.getData(), Apply.class);
		Date date = new Date();
		
		int i = applyService.updateApply(apply);
		Map<String, String> ret = new HashMap<String, String>();
		if (i>0){
			 ret.put("state", "2000");
		}else{
			 ret.put("state", "5000");
		}
		return JsonResponse.newOk(ret);
	}
}
