package com.laboratory.po;

import org.apache.ibatis.type.Alias;

@Alias("ClassesPicture")
public class ClassesPicture {
	private Integer id;
	
	private String path;
	
	private String description;
	
	private Integer state;
	
	private Integer classesId;
	
	public Integer getClassesId() {
		return classesId;
	}

	public void setClassesId(Integer researchId) {
		this.classesId = researchId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	
}
