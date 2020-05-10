package com.laboratory.dao;

import java.util.List;

import com.laboratory.po.ClassesPicture;

public interface ClassesPictureMapper extends BaseDao<ClassesPicture>{
	List<ClassesPicture> selectPictureByClassesId(int pageNum, int pageSize, int classesId);
	List<ClassesPicture> selectPictureByClassesId(int classesId);
}
