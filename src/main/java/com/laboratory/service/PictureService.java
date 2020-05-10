package com.laboratory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laboratory.dao.ClassesPictureMapper;
import com.laboratory.po.ClassesPicture;

@Service
public class PictureService {
	
	@Autowired
	private ClassesPictureMapper pictureMapper;

	// 更新研究图片
	public int updatePicture(ClassesPicture picture) {
		return pictureMapper.update(picture);
	}
	
	//添加研究成果
	public int savePicture(ClassesPicture picture) {
		return pictureMapper.insert(picture);
	}
	
	//删除研究图片
	public int deletePicture(Integer id){
		return pictureMapper.delete(id);
	}
	
	//通过id查找图片
	public ClassesPicture findById(Integer id) {
		return pictureMapper.selectByPrimaryKey(id);
	}
	
	// 通过研究id查找研究图片集
	public List<ClassesPicture> selectPictureByResearchId(int pageNum, int pageSize, int researchId) {
		return pictureMapper.selectPictureByClassesId(pageNum, pageSize, researchId);
	}
	
	//根据条件查询图片
	public List<ClassesPicture> findPictureByCondition(ClassesPicture picture,int pageNum,int pageSize) {
		return pictureMapper.selectSelective(picture, pageNum, pageSize);
	}
	
	
	public List<ClassesPicture> findPicturesByPage(int pageNum, int pageSize) {
		return pictureMapper.selectByPage(pageNum,pageSize);
	}
	
	
}
