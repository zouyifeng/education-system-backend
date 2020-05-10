package com.laboratory.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboratory.po.Admin;
import com.laboratory.po.Member;
import com.laboratory.service.AdminService;
import com.laboratory.service.MemberService;
import com.laboratory.util.JsonRequest;
import com.laboratory.util.JsonResponse;

@RestController
public class LoginController extends AbstractController{

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	// 登陆*
	@RequestMapping("/admin/login")
	public JsonResponse login(@RequestBody JsonRequest jsonRequest, HttpServletRequest request) { 
		Admin admin = objectMapper.convertValue(jsonRequest.getData(), Admin.class);
		Admin a = adminService.findByPrinciple(admin);
		if(a != null) { 
			request.getSession().setAttribute("user", a);
		}
		JsonResponse resp = JsonResponse.newOk(a);
		return resp;
	}
	
	// 登陆*
	@RequestMapping("/logout")
	public JsonResponse logout(HttpServletRequest request) { 
		request.getSession().removeAttribute("user");
		HashMap<String, String> ret = new HashMap<String, String>();
		ret.put("msg", "登陆超时");
//		Admin ret = new Admin();
		JsonResponse resp = JsonResponse.newOk(ret);
		return resp;
	}
}
