package com.laboratory.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.laboratory.service.NewsService;
import com.laboratory.po.Classes;
import com.laboratory.po.News;
import com.laboratory.service.ClassesService;
import com.laboratory.util.JsonResponse;

@RestController
public class HomeController extends AbstractController{

	@Autowired
	ClassesService researchService;
	
	@Autowired
	NewsService articleService;
	
	@RequestMapping("/index")
	public JsonResponse index(){
		List<Classes> researchs = researchService.findByPage(1, 4);
		List<News> articles =  articleService.findByPage(1,5);
//		mv.addObject("researchs", researchs);
//		mv.addObject("articles", articles);
//		mv.setViewName("/index");
//		return mv;
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("researchs", researchs);
		ret.put("articles", articles);
		return JsonResponse.newOk(ret);
	}
	
	@RequestMapping("/admin/index")
	public String loginUI(){
		return "/admin/login";
	}
}
