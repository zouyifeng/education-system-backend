package com.laboratory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laboratory.dao.StudentMapper;
import com.laboratory.po.Student;

@Service
public class StudentService {
	
	@Autowired
	private StudentMapper studentMapper;
	
	//通过id查找成员
	public Student findById(Integer id) {
		return studentMapper.selectByPrimaryKey(id);
	}
	
	//分页查找成员
	public List<Student> findByPage(int pageNum, int pageSize) {
		return studentMapper.selectByPage(pageNum, pageSize);
	}

	//通过条件查询成员
	public List<Student> findStudentsByCondition(Student student,int pageNum,int pageSize) {
		return studentMapper.selectSelective(student,pageNum,pageSize);
	}

	//删除成员
	public int deleteStudent(Integer id){
		return studentMapper.delete(id);
	}
	
	//增加成员
	public int saveStudent(Student student) {
		return studentMapper.insert(student);
	}

	//修改成员
	public int updateStudent(Student student) {
		return studentMapper.update(student);
	}


}
