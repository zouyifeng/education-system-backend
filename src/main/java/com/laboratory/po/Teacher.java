package com.laboratory.po;

import org.apache.ibatis.type.Alias;

@Alias("Teacher")
public class Teacher {
	public int id;
	private String name;
	private String telephone;
	private String email;
	private String face;
	private String introduction;
	private String majorName;
	private String classesCount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public String getClassesCount() {
		return classesCount;
	}
	public void setClassesCount(String classesCount) {
		this.classesCount = classesCount;
	}  
	

}
