package com.laboratory.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.laboratory.dao.AdminMapper;
import com.laboratory.dao.NewsMapper;
import com.laboratory.po.Admin;
import com.laboratory.po.News;

@Repository
public class AdminMapperImpl extends BaseDaoImpl<Admin> implements AdminMapper{

	@Override
	public Admin selectByPrinciple(Admin admin) {
		return template.selectOne(getStatement("selectByPrinciple"), admin);
	}
	
	@Override
	public List<Admin> selectAll() {
		return template.selectList(getStatement("selectAll"));
	}

	@Override
	public int insert(Admin admin) {
		return template.insert(getStatement("insertSelective"), admin);
	}

	@Override
	public int delete(Integer id) {
		return template.delete(getStatement("deleteByPrimaryKey"), id);
	}

	@Override
	public int update(Admin admin) {
		return template.update(getStatement("updateSelective"), admin);
	}

}
