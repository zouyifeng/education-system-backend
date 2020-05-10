package com.laboratory.po;

import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("Classes")
public class Classes {
	private Integer id;
	
	private String subject;
	
	private String context;
	
	private String sumary;

	private String date;
	
	private List<Student> students;
	
	private List<Teacher> teachers;
 	
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	private List<ClassesPicture> pictures; 
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<ClassesPicture> getPictures() {
		return pictures;
	}

	public void setPictures(List<ClassesPicture> pictures) {
		this.pictures = pictures;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getSumary() {
		return sumary;
	}

	public void setSumary(String sumary) {
		this.sumary = sumary;
	}
	
	
}
