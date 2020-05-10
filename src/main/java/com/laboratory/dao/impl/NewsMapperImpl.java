package com.laboratory.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.laboratory.dao.NewsMapper;
import com.laboratory.po.News;
import com.laboratory.po.Picture;

@Repository
public class NewsMapperImpl extends BaseDaoImpl<News> implements NewsMapper{

	@Override
	public List<String> selectNewsYear() {
		return template.selectList(getStatement("selectNewsYear"));
	}
	
	
	@Override
	public void insertNewsImg(Picture pic) {
		template.insert(getStatement("insertNewsImg"), pic);
	}


	@Override
	public List<Picture> selectNewsAllImg() {
		return template.selectList(getStatement("selectNewsAllImg"));
	}


	@Override
	public Picture selectNewsCover(Picture pic) {
		// TODO Auto-generated method stub
		return template.selectOne(getStatement("selectNewsCover"), pic);
	}

}
