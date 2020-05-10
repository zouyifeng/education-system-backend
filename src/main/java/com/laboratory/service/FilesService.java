package com.laboratory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.laboratory.dao.FilesMapper; 
import com.laboratory.po.Files;
import com.laboratory.po.DeviceType;

@Service
public class FilesService {
	
	@Autowired
	FilesMapper fileMapper;
	
	
	//分页查询
	public List<Files> findByPage(int pageNum, int pageSize) {
		return fileMapper.selectByPage(pageNum, pageSize);
	}

	//寻找所有设备的分类
	public List<DeviceType> findAllTypes() {
		return fileMapper.selectAllTypes();
	}

	//通过设备分类查找设备
//	public List<Files> findByType(int pageNum,int pageSize,int typeId) {
//		return fileMapper.selectByType(pageNum,pageSize,typeId);
//	}
	
	public List<Files> findAllByType(Files files) {
		return fileMapper.selectByType(files);
	}
	
	//根据查询条件查询
	public List<Files> findDeviceByCondition(Files device, int pageNum, int pageSize) {
		return fileMapper.selectSelective(device, pageNum, pageSize);
	}

	//增加实验设备
	public int saveDevice(Files device){
		return  fileMapper.insert(device);
	}

	//根据实验设备id查找实验设备
	public Files findDevice(Integer id) {
		return fileMapper.selectByPrimaryKey(id);
	}

	public int deleteDevice(Integer id) {
		return fileMapper.delete(id);
	}
}
