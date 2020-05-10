package com.laboratory.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.laboratory.dao.FilesMapper;
import com.laboratory.po.Files;
import com.laboratory.po.DeviceType;

@Repository
public class FilesMapperImpl extends BaseDaoImpl<Files> implements FilesMapper{

	@Override
	public List<DeviceType> selectAllTypes() {
		return template.selectList(getStatement("selectAllTypes"));
	}

	//这里好像和basedao重复了 做完回来看看
	@Override
	public List<Files> selectByPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		return template.selectList(getStatement("selectByPage"));
	}

	//根据设备类型进行分页查询
	@Override
	public List<Files> selectByType(Files files) {
//		PageHelper.startPage(pageNum, pageSize);
		return template.selectList(getStatement("selectByType"), files);
	}


	@Override
	public DeviceType selectDeviceTypeByParimaryKey(Integer typeId) {
		return template.selectOne(getStatement("selectDeviceType"), typeId);
	}

}
