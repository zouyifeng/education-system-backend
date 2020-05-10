package com.laboratory.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.laboratory.dto.ClassDto;
import com.laboratory.po.Classes;
import com.laboratory.po.Member;
import com.laboratory.po.Student;
import com.laboratory.po.Teacher;
import com.laboratory.po.UserClassesRel;
import com.laboratory.service.ClassesService;
import com.laboratory.service.StudentService;
import com.laboratory.service.TeacherService;
import com.laboratory.service.UserClassesRelService;
import com.laboratory.util.CharUtil;
import com.laboratory.util.DateUtil;
import com.laboratory.util.JsonRequest;
import com.laboratory.util.JsonResponse;
import com.laboratory.util.PageBean;
import com.laboratory.util.PageInfo;

@RestController
public class ClassesController extends AbstractController {

	@Autowired
	private ClassesService classesService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TeacherService teacherService;
	

	
	@Autowired
	private UserClassesRelService userClassesRelService;

	// 单独查找研究课题
	@RequestMapping("/classes")
	public JsonResponse showResearch(@RequestBody JsonRequest jsonRequest) {
		Classes classes = objectMapper.convertValue(jsonRequest.getData(), Classes.class);
		Classes aclass = classesService.findOne(classes.getId());
		classes.setContext(CharUtil.dealChar(classes.getContext()));
		return JsonResponse.newOk(aclass);
	}

	// 后台显示研究成果*
	@RequestMapping("/admin/classes_list.action")
	public JsonResponse showClassesByAdmin(@RequestBody JsonRequest jsonRequest) {
		List<Classes> researchs = classesService.findAllByPage(jsonRequest.getPageInfo().getPageNum(), 6);
		for(Classes c : researchs) {
//			List<Member> members = classesService.selectClassMember(c);
			UserClassesRel u = new UserClassesRel();
			u.setClassesId(c.getId());
			List<UserClassesRel> t = userClassesRelService.selectSelective(u);
			List<Student> students = userClassesRelService.findClassesStudents(t);
			List<Teacher> teachers = userClassesRelService.findClassesTeachers(t);

			c.setStudents(students);
			c.setTeachers(teachers);
		}
		PageBean<Classes> page = new PageBean<Classes>(researchs);
		return JsonResponse.newOk(page);
	}
	
	@RequestMapping("/admin/classes_student")
	public JsonResponse findStudents(@RequestBody JsonRequest jsonRequest) {
		UserClassesRel userClassesRel = objectMapper.convertValue(jsonRequest.getData(), UserClassesRel.class);
		List<UserClassesRel> userClassesRelList = userClassesRelService.selectSelective(userClassesRel);
		List<Student> studentList = userClassesRelService.findClassesStudents(userClassesRelList);
		return JsonResponse.newOk(studentList);
	}
	
	@RequestMapping("/admin/classes_teacher")
	public JsonResponse findTeachers(@RequestBody JsonRequest jsonRequest) {
		UserClassesRel userClassesRel = objectMapper.convertValue(jsonRequest.getData(), UserClassesRel.class);
		List<UserClassesRel> userClassesRelList = userClassesRelService.selectSelective(userClassesRel);
		List<Teacher> teacherList = userClassesRelService.findClassesTeachers(userClassesRelList);
		return JsonResponse.newOk(teacherList);
	}
	
	@RequestMapping("/admin/classes_add_student")
	public JsonResponse classesAddStudent(@RequestBody JsonRequest jsonRequest) {
		ClassDto classDto = objectMapper.convertValue(jsonRequest.getData(), ClassDto.class);
		List<Student> studentList = classDto.getStudent();
		for(Student s : studentList) {
			UserClassesRel u = new UserClassesRel();
			u.setUserId(s.getId());
			u.setClassesId(classDto.getClasses().getId());
			u.setType(1);
			userClassesRelService.add(u);
		}
		HashMap<String, String> ret = new HashMap<String, String>();
		ret.put("msg", "增加学生成功");
		return JsonResponse.newOk(ret);
	}
	
	@RequestMapping("/admin/classes_add_teacher")
	public JsonResponse classesAddTeacher(@RequestBody JsonRequest jsonRequest) {
		ClassDto classDto = objectMapper.convertValue(jsonRequest.getData(), ClassDto.class);
		List<Teacher> teacherList = classDto.getTeacher();
		for(Teacher t : teacherList) {
			UserClassesRel u = new UserClassesRel();
			u.setUserId(t.getId());
			u.setClassesId(classDto.getClasses().getId());
			u.setType(2);
			userClassesRelService.add(u);
		}
		HashMap<String, String> ret = new HashMap<String, String>();
		ret.put("msg", "增加教师成功");
		return JsonResponse.newOk(ret);
	}
	
	// 后台根据条件查询研究成果*
	@RequestMapping("/admin/classes_select")
	public JsonResponse findResearchs(@RequestBody JsonRequest jsonRequest) {
		// ModelAndView mv = new ModelAndView();
		Classes classes = objectMapper.convertValue(jsonRequest.getData(),
				Classes.class);
		List<Classes> classesList = classesService.findClassesByCondition(classes, 1, 6);
		PageBean<Classes> page = new PageBean<Classes>(classesList);
		// mv.addObject("page", page);
		HashMap<String, String> ret = new HashMap<String, String>();
		if (classes.getSubject() != null) {
			// mv.addObject("subject", research.getSubject());
			ret.put("subject", classes.getSubject());
		}
		if (classes.getSumary() != null) {
			ret.put("sumary", classes.getSumary());
			// mv.addObject("sumary", research.getSumary());
		}
		// mv.setViewName("/admin/research_select");
		// return mv;
		return JsonResponse.newOk(ret, page);
	}

	// 后台分页显示研究成果
	@RequestMapping("/admin/classes_page")
	// public JsonResponse showResearchByPage(@RequestParam(required=false)
	// String subject,@RequestParam(required=false)String sumary,int pageNum){
	public JsonResponse showClassesByPage(@RequestBody JsonRequest jsonRequest) {
		// ModelAndView mv = new ModelAndView();
		Classes research = null;
		List<Classes> researchs = null;
		Classes classes = objectMapper.convertValue(jsonRequest.getData(),
				Classes.class);

		HashMap<String, Object> ret = new HashMap<String, Object>();

		if (classes.getSubject() != null) {
			research = new Classes();
			research.setSubject(classes.getSubject());
			// mv.addObject("subject", classes.getSubject());
			ret.put("subject", classes.getSubject());
		}
		if (classes.getSumary() != null) {
			if (research == null) {
				research = new Classes();
			}
			research.setSumary(classes.getSumary());
			ret.put("summary", classes.getSumary());
			// mv.addObject("sumary", sumary);
		}
		if (research != null) {
			researchs = classesService.findClassesByCondition(research,
					jsonRequest.getPageInfo().getPageNum(), 6);
		} else {
			researchs = classesService.findAllByPage(jsonRequest.getPageInfo()
					.getPageNum(), 6);
		}
		PageBean<Classes> page = new PageBean<Classes>(researchs);
		// mv.addObject("page", page);
		// mv.setViewName("/admin/research_select");
		// return mv;
		return JsonResponse.newOk(page);
	}

	// 增加或者修改页面*
	@RequestMapping("/admin/classes_editUI")
	public JsonResponse editUI(@RequestBody JsonRequest jsonRequest) {
		Classes classes = objectMapper.convertValue(jsonRequest.getData(), Classes.class);
		Classes aClass = null;
		if (classes.getId() != null) {
			aClass = classesService.findOne(classes.getId());
		}
		return JsonResponse.newOk(aClass);
	}

	// 增加研究成果
	@RequestMapping("/admin/classes_add")
	public JsonResponse addResearch(@RequestBody JsonRequest jsonRequest) {
		// ModelAndView mv = new ModelAndView();
		Classes classes = objectMapper.convertValue(jsonRequest.getData(),
				Classes.class);
		if(classes.getDate() == "") {
			Date date = new Date();
			classes.setDate(DateUtil.formatToString("yyyy-MM-dd", date));
		}
		int i = classesService.saveClasses(classes);
		HashMap<String, String> ret = new HashMap<String, String>();
		if (i > 0) {
			ret.put("message", "添加班级成功");
			// mv.addObject("message", "添加研究成果成功");
		} else {
			ret.put("message", "添加班级成功");
			// mv.addObject("message", "添加研究成果失败");
		}
		// mv.setViewName("/admin/message");
		// return mv;
		return JsonResponse.newOk(ret);
	}

	// 修改研究成果
	@RequestMapping("/admin/classes_edit")
	public JsonResponse editClasses(@RequestBody JsonRequest jsonRequest) {
		Classes classes = objectMapper.convertValue(jsonRequest.getData(),
				Classes.class);
		int i = classesService.updateClasses(classes);
		HashMap<String, String> ret = new HashMap<String, String>();
		if (i > 0) {
			ret.put("message", "修改班级成功");
			// mv.addObject("message", "修改研究成果成功");
		} else {
			ret.put("message", "修改班级成功");
			// mv.addObject("message", "修改研究成果失败");
		}
		// mv.setViewName("/admin/message");
		return JsonResponse.newOk(ret);
		// return mv;
	}

	// 删除研究成果
	@RequestMapping("/admin/classes_delete")
	@ResponseBody
	public JsonResponse deleteClasses(@RequestBody JsonRequest jsonRequest) {
		Classes classes = objectMapper.convertValue(jsonRequest.getData(),
				Classes.class);
		int i = classesService.deleteClasses(classes.getId());
		HashMap<String, String> ret = new HashMap<String, String>();
		if (i > 0) {
			ret.put("message", "删除研究成果成功");
		} else {
			ret.put("message", "删除研究成果失败");
		}
		return JsonResponse.newOk(ret);
	}
	
	@RequestMapping("/admin/classes_delete_student")
	public JsonResponse classesDeleteStudent(@RequestBody JsonRequest jsonRequest) {
		UserClassesRel userClassesRel = objectMapper.convertValue(jsonRequest.getData(), UserClassesRel.class);
		int i = userClassesRelService.delete(userClassesRel);
		HashMap<String, String> ret = new HashMap<String, String>();
		if (i > 0) {
			ret.put("message", "删除研究成果成功");
		} else {
			ret.put("message", "删除研究成果失败");
		}
		return JsonResponse.newOk(ret);
	}
	
	@RequestMapping("/admin/classes_delete_teacher")
	public JsonResponse classesDeleteTeacher(@RequestBody JsonRequest jsonRequest) {
		UserClassesRel userClassesRel = objectMapper.convertValue(jsonRequest.getData(), UserClassesRel.class);
		int i = userClassesRelService.delete(userClassesRel);
		HashMap<String, String> ret = new HashMap<String, String>();
		if (i > 0) {
			ret.put("message", "删除研究成果成功");
		} else {
			ret.put("message", "删除研究成果失败");
		}
		return JsonResponse.newOk(ret);
	}
}
