package com.laboratory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laboratory.dao.TeacherMapper;
import com.laboratory.po.Teacher;

@Service
public class TeacherService {
	
	@Autowired
	private TeacherMapper teacherMapper;
	
	//通过id查找成员
	public Teacher findById(Integer id) {
		return teacherMapper.selectByPrimaryKey(id);
	}
	
	//分页查找成员
	public List<Teacher> findByPage(int pageNum, int pageSize) {
		return teacherMapper.selectByPage(pageNum, pageSize);
	}

	//通过条件查询成员
	public List<Teacher> findTeachersByCondition(Teacher teacher,int pageNum,int pageSize) {
		return teacherMapper.selectSelective(teacher,pageNum,pageSize);
	}

	//删除成员
	public int deleteTeacher(Integer id){
		return teacherMapper.delete(id);
	}
	
	//增加成员
	public int saveTeacher(Teacher teacher) {
		return teacherMapper.insert(teacher);
	}

	//修改成员
	public int updateTeacher(Teacher teacher) {
		return teacherMapper.update(teacher);
	}


}
