package com.laboratory.dao;

import java.util.List;

import com.laboratory.po.Files;
import com.laboratory.po.DeviceType;

public interface FilesMapper extends BaseDao<Files>{

	List<DeviceType> selectAllTypes();
	
	List<Files> selectByPage(int pageNum,int pageSize);

	List<Files> selectByType(Files files);

	DeviceType selectDeviceTypeByParimaryKey(Integer typeId);

}
