package com.laboratory.po;

import org.apache.ibatis.type.Alias;

@Alias("Member")
public class Member {
	private Integer id;
	
	private String name;
	
	private String telephone;
	
	private String email;
	
	private String direction;
	
	private String introduction;
	
	private String face;
	
	private String type;
	
	private String classesId;
	
	
	public String getClassesId() {
		return classesId;
	}

	public void setClassesId(String classesId) {
		this.classesId = classesId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	
	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	
}
