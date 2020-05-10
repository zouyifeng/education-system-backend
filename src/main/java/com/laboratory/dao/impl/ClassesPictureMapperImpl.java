package com.laboratory.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.laboratory.dao.ClassesPictureMapper;
import com.laboratory.po.ClassesPicture;

@Repository
public class ClassesPictureMapperImpl extends BaseDaoImpl<ClassesPicture> implements ClassesPictureMapper{

	
	@Override
	public List<ClassesPicture> selectPictureByClassesId(int pageNum, int pageSize, int researchId) {
		PageHelper.startPage(pageNum, pageSize);
		return template.selectList(getStatement("selectPictureByResearchId"), researchId);
	}

	//不分页显示对应研究成果的图片
	@Override
	public List<ClassesPicture> selectPictureByClassesId(int researchId) {
		return template.selectList(getStatement("selectPictureByResearchId"), researchId);
	}
}
