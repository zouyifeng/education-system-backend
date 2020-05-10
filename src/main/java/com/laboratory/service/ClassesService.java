package com.laboratory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laboratory.dao.ClassesPictureMapper;
import com.laboratory.po.Classes;
import com.laboratory.po.ClassesPicture;
import com.laboratory.dao.ClassesMapper;
import com.laboratory.util.UploadUtil;

@Service
public class ClassesService {
	
	@Autowired
	private ClassesMapper classesMapper;
	
	@Autowired
	private ClassesPictureMapper pictureMapper;
	
	//分页查询研究课题显示在主页 
	public List<Classes> findByPage(int pageNum,int pageSize){
		return classesMapper.selectByPage(pageNum,pageSize);
	}

	//后台分页查询全部(这个是没有查询图片的)
	public List<Classes> findAllByPage(int pageNum,int pageSize){
		return classesMapper.selectAll(pageNum,pageSize);
	}

	//单独查找一个研究课题
	public Classes findOne(int id){
		return classesMapper.selectByPrimaryKey(id);
	}
	

	//查找全部研究成果(没有查询图片 但是这个是没有分页的)
	public List<Classes> findAll() {
		return classesMapper.selectAll();
	}
	
 
	//根据条件查询研究成果
	public List<Classes> findClassesByCondition(Classes classes,int pageNum,int pageSize) {
		return classesMapper.selectSelective(classes, pageNum, pageSize);
	}

	//添加研究成果
	public int saveClasses(Classes classes) {
		return classesMapper.insert(classes);
	}

	//修改研究成果
	public int updateClasses(Classes classes) {
		return classesMapper.update(classes);
	}

	
	//删除研究成果以及对应的研究成果
	public int deleteClasses(Integer id) {
		List<ClassesPicture> pictures = pictureMapper.selectPictureByClassesId(id);
		int i = classesMapper.delete(id);
		if(i>0&&pictures.size()>0){
			for(ClassesPicture p:pictures){
				UploadUtil.deletePicture(p.getPath());
			}
		}
		return i;
	}
	
	
}
