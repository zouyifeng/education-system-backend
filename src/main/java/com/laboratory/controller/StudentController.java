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

import com.laboratory.dto.StudentDto;
import com.laboratory.dto.TeacherDto;
import com.laboratory.po.Admin;
import com.laboratory.po.Classes;
import com.laboratory.po.Student;
import com.laboratory.po.Teacher;
import com.laboratory.po.UserClassesRel;
import com.laboratory.service.AdminService;
import com.laboratory.service.ClassesService;
import com.laboratory.service.StudentService;
import com.laboratory.service.UserClassesRelService;
import com.laboratory.util.CharUtil;
import com.laboratory.util.CustomObjectMapper;
import com.laboratory.util.JsonRequest;
import com.laboratory.util.JsonResponse;
import com.laboratory.util.PageBean;
import com.laboratory.util.PageInfo;
import com.laboratory.util.UploadUtil;

@RestController
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private UserClassesRelService userClassesRelService;
	
	@Autowired
	private ClassesService classesService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired 
	private CustomObjectMapper objectMapper;
	
	@RequestMapping("/student_list")
	public JsonResponse showStudents(@RequestBody JsonRequest jsonRequest){
		PageInfo pageInfo = objectMapper.convertValue(jsonRequest.getPageInfo(), PageInfo.class);
		Student student = objectMapper.convertValue(jsonRequest.getData(), Student.class);
		List<Student> students = studentService.findStudentsByCondition(student, 1, 16);
		PageBean<Student> page= new PageBean<Student>(students);
		return JsonResponse.newOk(page);
	}
	
	@RequestMapping("/student")
	public JsonResponse showStudent(@RequestBody JsonRequest jsonRequest){
		Student student = objectMapper.convertValue(jsonRequest.getData(), Student.class);
		Student newStudent = studentService.findById(student.getId());
//		String introduction = CharUtil.dealChar(newStudent.getIntroduction());
//		newStudent.setIntroduction(introduction);
		return JsonResponse.newOk(newStudent);
	}
	
	/**
	 * 后台管理展示实验室成员*
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/admin/student_list.action")
	public JsonResponse showStudentsByAdmin(@RequestBody JsonRequest jsonRequest){
		PageInfo pageInfo = objectMapper.convertValue(jsonRequest.getPageInfo(), PageInfo.class);
//		Student student = objectMapper.convertValue(jsonRequest.getData(), Student.class);
		List<Student> students = studentService.findByPage(pageInfo.getPageNum(), pageInfo.getPageSize());
		PageBean<Student> page= new PageBean<Student>(students);
		return JsonResponse.newOk(page);
	}
	
	 
	/**
	 * 后台管理通过名字或者研究方向查询*
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/admin/student_select")
	public JsonResponse findStudents(@RequestBody JsonRequest jsonRequest){
		Student student = objectMapper.convertValue(jsonRequest.getData(), Student.class);
		PageInfo pageInfo = objectMapper.convertValue(jsonRequest.getPageInfo(), PageInfo.class);
		List<Student> students = studentService.findStudentsByCondition(student, pageInfo.getPageNum(), pageInfo.getPageSize());
		PageBean<Student> page = new PageBean<Student>(students);
		return JsonResponse.newOk(page);
	}
	
	
	//后台分页显示成员
	@RequestMapping("/admin/student_page")
	public JsonResponse showStudentByPage(@RequestBody JsonRequest jsonRequest){
		Student student = objectMapper.convertValue(jsonRequest.getData(), Student.class);
		PageInfo pageInfo = objectMapper.convertValue(jsonRequest.getPageInfo(), PageInfo.class);
		List<Student> students = null;
		if(student.getName() != null || student.getTelephone() != null){
			students = studentService.findStudentsByCondition(student, pageInfo.getPageNum(), 6);
		}else{
			students = studentService.findByPage(pageInfo.getPageNum(), 6);
		}
		PageBean<Student> page = new PageBean<Student>(students);
		return JsonResponse.newOk(page);
	}
	
	//删除成员 还要删除成员图片*
	@RequestMapping("/admin/student_delete")
	public JsonResponse removeStudent(@RequestBody JsonRequest jsonRequest){
		Student student = objectMapper.convertValue(jsonRequest.getData(), Student.class);
		int i = studentService.deleteStudent(student.getId());
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("msg", i > 0 ? "删除学生成功！" : "删除学生失败！");
		return JsonResponse.newOk(ret);
	} 
	
	//增加和修改的成员页面*
	@RequestMapping("/student_editUI")
	public JsonResponse addUI(@RequestBody JsonRequest jsonRequest){
		Student student = objectMapper.convertValue(jsonRequest.getData(), Student.class);
		StudentDto studentDto = new StudentDto();
		UserClassesRel uesrClassesRel = new UserClassesRel();
		if(student.getId() != 0){
			student = studentService.findById(student.getId());
			uesrClassesRel.setUserId(student.getId());
			uesrClassesRel.setType(1);
			List<UserClassesRel>  uesrClassesRelList = userClassesRelService.selectSelective(uesrClassesRel);
			List<Classes> classesList = new ArrayList<Classes>();
			for(UserClassesRel i: uesrClassesRelList) {
				Classes c = classesService.findOne(i.getClassesId());
				classesList.add(c);
			}
			studentDto.setClasses(classesList);
			studentDto.setClassesCount(student.getClassesCount());
			studentDto.setCode(student.getCode());
			studentDto.setEmail(student.getEmail());
			studentDto.setFace(student.getFace());
			studentDto.setId(student.getId());
			studentDto.setIntroduction(student.getIntroduction());
			studentDto.setName(student.getName());
			studentDto.setParentName(student.getParentName());
			studentDto.setSchool(student.getSchool());
			studentDto.setTelephone(student.getTelephone());
		}
		return JsonResponse.newOk(studentDto);
	}
	
	//增加成员(还有全局异常没有处理)*
	@RequestMapping("/admin/student_add")
	public JsonResponse addStudent(@RequestBody JsonRequest jsonRequest){
		Map<String, Object> ret = new HashMap<String, Object>();
		StudentDto studentDto = objectMapper.convertValue(jsonRequest.getData(), StudentDto.class);
		List<Classes> classes = studentDto.getClasses();
		List<UserClassesRel> userClassesRelList = new ArrayList<UserClassesRel>();
		
		Admin admin = new Admin();
		admin.setPassword("888");
		admin.setType(1);
		admin.setUserId(studentDto.getId());
		admin.setUsername(studentDto.getName());
		adminService.insertSelective(admin);
		
		try{
			int i = studentService.saveStudent(studentDto);
			if(i > 0){
				ret.put("message", "添加成员成功");
				ret.put("account", admin);
			} else {
				ret.put("message", "添加成员失败");
			}
		}catch(IllegalStateException e){
			e.printStackTrace();
		}
		if(classes != null) {
			for(Classes i: classes) {
				UserClassesRel u = new UserClassesRel();
				u.setType(1);
				u.setUserId(studentDto.getId());
				u.setClassesId(i.getId());
				userClassesRelList.add(u);
			}
			userClassesRelService.batchAdd(userClassesRelList);
		}
		return JsonResponse.newOk(ret);
	}
	
	/**
	 * 图片上传*
	 * @param pic
	 * @return
	 */
	@RequestMapping("/admin/student_pic_upload.action")
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
	@RequestMapping("/admin/student_edit.action")
	public JsonResponse editStudent(@RequestBody JsonRequest jsonRequest){
		StudentDto studentDto = objectMapper.convertValue(jsonRequest.getData(), StudentDto.class);
		List<Classes> classes = studentDto.getClasses();
		List<UserClassesRel> userClassesRelList = new ArrayList<UserClassesRel>();
		try {
			Student m = studentService.findById(studentDto.getId());
			UploadUtil.deletePicture(m.getFace());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} 
		
		UserClassesRel a = new UserClassesRel();
		a.setUserId(studentDto.getId());
		userClassesRelService.delete(a);
		
		if(classes != null) {
			for(Classes i: classes) {
				UserClassesRel u = new UserClassesRel();
				u.setType(1);
				u.setUserId(studentDto.getId());
				u.setClassesId(i.getId());
				if(userClassesRelService.selectSelective(u).size() > 0) {
					userClassesRelService.delete(u);		
				}
				userClassesRelList.add(u);
			}
			userClassesRelService.batchAdd(userClassesRelList);
		}
		
		try {
			Student m = studentService.findById(studentDto.getId());
			UploadUtil.deletePicture(m.getFace());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} 
		int i = studentService.updateStudent(studentDto);
		Map<String, String> ret = new HashMap<String, String>();
		if (i>0){
			 ret.put("state", "2000");
		}else{
			 ret.put("state", "5000");
		}
		return JsonResponse.newOk(ret);
	}
	
}
