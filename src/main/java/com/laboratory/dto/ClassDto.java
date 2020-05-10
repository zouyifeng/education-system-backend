package com.laboratory.dto;

import java.util.List;

import com.laboratory.po.Classes;
import com.laboratory.po.Lesson;
import com.laboratory.po.Student;
import com.laboratory.po.Teacher;

public class ClassDto {
	private Classes classes;
	private List<Lesson> lesson;
	private List<Student> student;
	private List<Teacher> teacher;
	
	public List<Student> getStudent() {
		return student;
	}
	public void setStudent(List<Student> student) {
		this.student = student;
	}
	public List<Teacher> getTeacher() {
		return teacher;
	}
	public void setTeacher(List<Teacher> teacher) {
		this.teacher = teacher;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	public List<Lesson> getLesson() {
		return lesson;
	}
	public void setLesson(List<Lesson> lesson) {
		this.lesson = lesson;
	}
}
