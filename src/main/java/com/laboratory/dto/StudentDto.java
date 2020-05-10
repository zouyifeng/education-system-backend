package com.laboratory.dto;

import java.io.Serializable;
import java.util.List;

import com.laboratory.po.Classes;
import com.laboratory.po.Student;

public class StudentDto extends Student implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Classes> classes;

	public List<Classes> getClasses() {
		return classes;
	}

	public void setClasses(List<Classes> classes) {
		this.classes = classes;
	}
}
