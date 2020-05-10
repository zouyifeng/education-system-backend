package com.laboratory.dao;

import java.util.List;

import com.laboratory.po.News;
import com.laboratory.po.Picture;

public interface NewsMapper extends BaseDao<News>{
	
	public List<String> selectNewsYear();

	public void insertNewsImg(Picture pic);
	
	public List<Picture> selectNewsAllImg();
	
	public Picture selectNewsCover(Picture pic);
	
}
