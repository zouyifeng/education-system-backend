package com.laboratory.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laboratory.dao.StudentMapper;
import com.laboratory.dao.TeacherMapper;
import com.laboratory.dao.UserClassesRelMapper;
import com.laboratory.po.Student;
import com.laboratory.po.Teacher;
import com.laboratory.po.UserClassesRel;

@Service
public class UserClassesRelService {
	
	@Autowired
	private UserClassesRelMapper userClassesRelMapper;
	
	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private TeacherMapper teacherMapper;
	
	public List<UserClassesRel> selectSelective(UserClassesRel userClassesRel) {
		return userClassesRelMapper.selectSelective(userClassesRel);
	}
	
	public List<UserClassesRel> selectSelective(UserClassesRel userClassesRel, int pageNum, int pageSize) {
		return userClassesRelMapper.selectSelective(userClassesRel, pageNum, pageSize);
	}
	
	public int upDateSelective(UserClassesRel userClassesRel) {
		return userClassesRelMapper.update(userClassesRel);
	}
	
	public int delete(int id) {
		return userClassesRelMapper.delete(id);
	}
	
	public int delete(UserClassesRel userClassesRel) {
		return userClassesRelMapper.deleteSelective(userClassesRel);
	}
	
	public int add(UserClassesRel userClassesRel) {
		return userClassesRelMapper.insert(userClassesRel);
	}
	
	public void batchAdd(List<UserClassesRel> userClassesRelList) {
		for(UserClassesRel i: userClassesRelList) {
			add(i);
		}
	}
	
	public List<Student> findClassesStudents(List<UserClassesRel> userClassesRelList) {
		List<Student> studentList = new ArrayList<Student>();
		for(UserClassesRel i : userClassesRelList) {
			int q = i.getUserId().intValue();
			Student s = studentMapper.selectByPrimaryKey(i.getUserId());
			if( s != null) {
				studentList.add(s);
			}
		}
		return studentList;
	}
	
	public List<Teacher> findClassesTeachers(List<UserClassesRel> userClassesRelList) {
		List<Teacher> teacherList = new ArrayList<Teacher>();
		for(UserClassesRel i : userClassesRelList) {
			Teacher t = teacherMapper.selectByPrimaryKey(i.getUserId());
			if(t != null) {
				teacherList.add(t);
			}
		}
		return teacherList;
	}
}
