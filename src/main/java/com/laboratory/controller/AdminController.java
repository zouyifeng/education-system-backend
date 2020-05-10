package com.laboratory.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laboratory.po.Admin;
import com.laboratory.service.AdminService;
import com.laboratory.util.CustomObjectMapper;
import com.laboratory.util.JsonRequest;
import com.laboratory.util.JsonResponse;

@RestController
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CustomObjectMapper objectMapper;
	
	@RequestMapping("/admin/add_admin")
	public JsonResponse addAdmin(@RequestBody JsonRequest jsonRequest) {
		Admin admin = objectMapper.convertValue(jsonRequest.getData(), Admin.class);
		int i = adminService.insertSelective(admin);
		HashMap<String, String> ret = new HashMap<String, String>();
		if(i > 0) {
			ret.put("msg", "新增管理员成功！");
		} else {
			ret.put("msg", "新增管理员失败！");
		}
		return JsonResponse.newOk(ret);
	}
	
	@RequestMapping("/admin/update_admin")
	public JsonResponse updateAdmin(@RequestBody JsonRequest jsonRequest) {
		Admin admin = objectMapper.convertValue(jsonRequest.getData(), Admin.class);
		int i = adminService.updateSelective(admin);
		HashMap<String, String> ret = new HashMap<String, String>();
		if(i > 0) {
			ret.put("msg", "修改管理员成功！");
		} else {
			ret.put("msg", "修改管理员失败！");
		}
		return JsonResponse.newOk(ret);
	}
	
	@RequestMapping("/admin/delete_admin")
	public JsonResponse deleteAdmin(@RequestBody JsonRequest jsonRequest) {
		Admin admin = objectMapper.convertValue(jsonRequest.getData(), Admin.class);
		int i = adminService.deleteByPrimaryKey(admin.getId());
		HashMap<String, String> ret = new HashMap<String, String>();
		if(i > 0) {
			ret.put("msg", "删除管理员成功！");
		} else {
			ret.put("msg", "删除管理员失败！");
		}
		return JsonResponse.newOk(ret);
	}
	
	@RequestMapping("/admin/all_admin")
	public JsonResponse selectAllAdmin() {
		List<Admin> adminList = adminService.findAllAdmin();
		return JsonResponse.newOk(adminList);
	}
	
}
