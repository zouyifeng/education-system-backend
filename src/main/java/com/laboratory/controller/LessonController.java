package com.laboratory.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboratory.dto.ClassDto;
import com.laboratory.po.Classes;
import com.laboratory.po.Lesson;
import com.laboratory.service.LessonService;
import com.laboratory.util.CustomObjectMapper;
import com.laboratory.util.JsonRequest;
import com.laboratory.util.JsonResponse;

@RestController
public class LessonController{
	
	@Autowired
	LessonService lessonService;
	
	@Autowired 
	private CustomObjectMapper objectMapper;
	
//	@RequestMapping("/admin/lesson_list1")
//	public JsonResponse showClassesLesson(@RequestBody JsonRequest jsonRequest) {
//		Classes aClass = objectMapper.convertValue(jsonRequest.getData(), Classes.class);
//		List<Lesson> lessons = lessonService.selectClassLesson(aClass.getId());
//		
//		return JsonResponse.newOk(lessons);
//	}
	
	@RequestMapping("/admin/update_lesson")
	public JsonResponse updateLesson(@RequestBody JsonRequest jsonRequest) {
		Lesson lesson = objectMapper.convertValue(jsonRequest.getData(), Lesson.class);
		int i = lessonService.updateLesson(lesson);
		HashMap<String, String> ret = new HashMap<String, String>();
		if(i > 0) {
			ret.put("msg", "修改成功");
		} else {
			ret.put("msg", "修改失败");
		} 
		return JsonResponse.newOk(ret);
 	}
	
	@RequestMapping("/admin/delete_lesson")
	public JsonResponse delelteLesson(@RequestBody JsonRequest jsonRequest) {
		Lesson lesson = objectMapper.convertValue(jsonRequest.getData(), Lesson.class);
		int i = lessonService.deleteLesson(lesson.getId());
		HashMap<String, String> ret = new HashMap<String, String>();
		if(i > 0) {
			ret.put("msg", "修改成功");
		} else {
			ret.put("msg", "修改失败");
		} 
		return JsonResponse.newOk(ret);
	}
	
	@RequestMapping("/admin/add_lesson")
	public JsonResponse addLesson(@RequestBody JsonRequest jsonRequest) {
		ClassDto classDto = objectMapper.convertValue(jsonRequest.getData(), ClassDto.class);
		for(Lesson l : classDto.getLesson()){
			lessonService.addLesson(l);			
		}
		HashMap<String, String> ret = new HashMap<String, String>();
		ret.put("msg", "新增成功");
		return JsonResponse.newOk(ret);
	}
	
	@RequestMapping("/lesson_list")
	public JsonResponse findLessonByDateRange(@RequestBody JsonRequest jsonRequest) {
		Lesson lesson = objectMapper.convertValue(jsonRequest.getData(), Lesson.class);
		List<Lesson> ret = lessonService.findLessonByDateRange(lesson);
		return JsonResponse.newOk(ret);
	}
	
}