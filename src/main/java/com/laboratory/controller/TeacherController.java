package com.laboratory.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.laboratory.dto.TeacherDto;
import com.laboratory.po.Admin;
import com.laboratory.po.Classes;
import com.laboratory.po.Teacher;
import com.laboratory.po.UserClassesRel;
import com.laboratory.service.AdminService;
import com.laboratory.service.ClassesService;
import com.laboratory.service.TeacherService;
import com.laboratory.service.UserClassesRelService;
import com.laboratory.util.CharUtil;
import com.laboratory.util.CustomObjectMapper;
import com.laboratory.util.JsonRequest;
import com.laboratory.util.JsonResponse;
import com.laboratory.util.PageBean;
import com.laboratory.util.PageInfo;
import com.laboratory.util.UploadUtil;

@RestController
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private UserClassesRelService userClassesRelService;
	
	@Autowired
	private ClassesService classesService;
	
	@Autowired 
	private CustomObjectMapper objectMapper;
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("/teacher_list")
	public JsonResponse showTeachers(@RequestBody JsonRequest jsonRequest){
		Teacher teacher = objectMapper.convertValue(jsonRequest.getData(), Teacher.class);
		List<Teacher> teachers = teacherService.findTeachersByCondition(teacher, 1, 16);
		PageBean<Teacher> page= new PageBean<Teacher>(teachers);
		return JsonResponse.newOk(page);
	}
	
	@RequestMapping("/teacher")
	public JsonResponse showTeacher(@RequestBody JsonRequest jsonRequest){
		Teacher teacher = objectMapper.convertValue(jsonRequest.getData(), Teacher.class);
		Teacher newTeacher = teacherService.findById(teacher.getId());
		String introduction = CharUtil.dealChar(newTeacher.getIntroduction());
		newTeacher.setIntroduction(introduction);
		return JsonResponse.newOk(newTeacher);
	}
	
	/**
	 * 后台管理展示实验室成员*
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/admin/teacher_list")
	public JsonResponse showTeachersByAdmin(@RequestBody JsonRequest jsonRequest){
		PageInfo pageInfo = objectMapper.convertValue(jsonRequest.getPageInfo(), PageInfo.class);
		Teacher teacher = objectMapper.convertValue(jsonRequest.getData(), Teacher.class);
		List<Teacher> teachers = teacherService.findTeachersByCondition(teacher, pageInfo.getPageNum(), pageInfo.getPageSize());
		PageBean<Teacher> page= new PageBean<Teacher>(teachers);
		return JsonResponse.newOk(page);
	}
	 
	/**
	 * 后台管理通过名字或者研究方向查询*
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/admin/teacher_select")
	public JsonResponse findTeachers(@RequestBody JsonRequest jsonRequest){
		Teacher teacher = objectMapper.convertValue(jsonRequest.getData(), Teacher.class);
		PageInfo pageInfo = objectMapper.convertValue(jsonRequest.getPageInfo(), PageInfo.class);
		List<Teacher> teachers = teacherService.findTeachersByCondition(teacher, pageInfo.getPageNum(), pageInfo.getPageSize());
		PageBean<Teacher> page = new PageBean<Teacher>(teachers);
		return JsonResponse.newOk(page);
	}
	
	
	//后台分页显示成员
	@RequestMapping("/admin/teacher_page")
	public JsonResponse showTeacherByPage(@RequestBody JsonRequest jsonRequest){
		Teacher teacher = objectMapper.convertValue(jsonRequest.getData(), Teacher.class);
		PageInfo pageInfo = objectMapper.convertValue(jsonRequest.getPageInfo(), PageInfo.class);
		List<Teacher> teachers = null;
		if(teacher.getName() != null || teacher.getTelephone() != null){
			teachers = teacherService.findTeachersByCondition(teacher, pageInfo.getPageNum(), 6);
		}else{
			teachers = teacherService.findByPage(pageInfo.getPageNum(), 6);
		}
		PageBean<Teacher> page = new PageBean<Teacher>(teachers);
		return JsonResponse.newOk(page);
	}
	
	//删除成员 还要删除成员图片*
	@RequestMapping("/admin/teacher_delete")
	public JsonResponse removeTeacher(@RequestBody JsonRequest jsonRequest){
		Teacher teacher = objectMapper.convertValue(jsonRequest.getData(), Teacher.class);
		Teacher newTeacher = teacherService.findById(teacher.getId());
		int i = teacherService.deleteTeacher(teacher.getId());
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("state", i>0 ? "2000" : "5000");
		return JsonResponse.newOk(ret);
	} 
	
	//增加和修改的成员页面*
	@RequestMapping("/teacher_editUI")
	public JsonResponse addUI(@RequestBody JsonRequest jsonRequest){
		Teacher teacher = objectMapper.convertValue(jsonRequest.getData(), Teacher.class);
		TeacherDto teacherDto = new TeacherDto();
		UserClassesRel uesrClassesRel = new UserClassesRel();
		
		if(teacher.getId() != 0){
			teacherDto = (TeacherDto) teacherService.findById(teacher.getId());
			uesrClassesRel.setUserId(teacher.getId());
			uesrClassesRel.setType(2);
			List<UserClassesRel>  uesrClassesRelList = userClassesRelService.selectSelective(uesrClassesRel);
			List<Classes> classesList = new ArrayList<Classes>();
			for(UserClassesRel i: uesrClassesRelList) {
				Classes c = classesService.findOne(i.getClassesId());
				classesList.add(c);
			}
			teacherDto.setClasses(classesList);
		}
		return JsonResponse.newOk(teacherDto);
	}
	
	//增加成员(还有全局异常没有处理)*
	@RequestMapping("/admin/teacher_add")
	public JsonResponse addTeacher(@RequestBody JsonRequest jsonRequest){
		Map<String, Object> ret = new HashMap<String, Object>();
		TeacherDto teacherDto = objectMapper.convertValue(jsonRequest.getData(), TeacherDto.class);
		List<Classes> classes = teacherDto.getClasses();
		List<UserClassesRel> userClassesRelList = new ArrayList<UserClassesRel>();
		
		try{
			int i = teacherService.saveTeacher(teacherDto);
			Admin admin = new Admin();
			admin.setPassword("888");
			admin.setType(2);
			admin.setUserId(teacherDto.getId());
			admin.setUsername(teacherDto.getName());
			adminService.insertSelective(admin);
			if(i > 0){
				ret.put("message", "添加成员成功");
				ret.put("account", admin);
			} else {
				ret.put("message", "添加成员失败");
			}
		}catch(IllegalStateException e){
			e.printStackTrace();
		}
		for(Classes i: classes) {
			UserClassesRel u = new UserClassesRel();
			u.setType(2);
			u.setUserId(teacherDto.getId());
			u.setClassesId(i.getId());
			userClassesRelList.add(u);
		}
		userClassesRelService.batchAdd(userClassesRelList);
		return JsonResponse.newOk(ret);
	}
	
	/**
	 * 图片上传*
	 * @param pic
	 * @return
	 */
	@RequestMapping("/admin/teacher_pic_upload")
	public JsonResponse picUpload(MultipartFile pic) {
		String category = "face";
		String picName = "";
		Map<String, String> ret = new HashMap<String, String>();
		try{
			picName = UploadUtil.uploadPicture(pic, category);
		}catch(IOException e){
			e.printStackTrace();
		}
		ret.put("picUrl", picName);
		return JsonResponse.newOk(ret);
	}
	
	//修改成员(全局异常没有处理)*
	@RequestMapping("/admin/teacher_edit")
	public JsonResponse editTeacher(@RequestBody JsonRequest jsonRequest){
		TeacherDto teacherDto = objectMapper.convertValue(jsonRequest.getData(), TeacherDto.class);
		List<Classes> classes = teacherDto.getClasses();
		List<UserClassesRel> userClassesRelList = new ArrayList<UserClassesRel>();
		try {
			Teacher m = teacherService.findById(teacherDto.getId());
			UploadUtil.deletePicture(m.getFace());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} 
		
		
		UserClassesRel a = new UserClassesRel();
		a.setUserId(teacherDto.getId());
		userClassesRelService.delete(a);
		
		
		for(Classes i: classes) {
			UserClassesRel u = new UserClassesRel();
			u.setType(2);
			u.setUserId(teacherDto.getId());
			u.setClassesId(i.getId());
			if(userClassesRelService.selectSelective(u).size() > 0) {
				userClassesRelService.delete(u);		
			}
			userClassesRelList.add(u);
		}
		userClassesRelService.batchAdd(userClassesRelList);
		int i = teacherService.updateTeacher(teacherDto);
		Map<String, String> ret = new HashMap<String, String>();
		if (i>0){
			 ret.put("state", "2000");
		}else{
			 ret.put("state", "5000");
		}
		return JsonResponse.newOk(ret);
	}
	
	
}
