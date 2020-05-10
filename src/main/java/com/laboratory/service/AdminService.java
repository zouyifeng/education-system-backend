package com.laboratory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laboratory.dao.AdminMapper;
import com.laboratory.po.Admin;

@Service
public class AdminService {

	@Autowired
	private AdminMapper adminMapper;
	
	public Admin findByPrinciple(Admin admin) {
		return adminMapper.selectByPrinciple(admin);
	} 
	
	public List<Admin> findAllAdmin(){
		return adminMapper.selectAll();
	}
	
	public int insertSelective(Admin admin) {
		return adminMapper.insert(admin);
	}
	
	public int deleteByPrimaryKey(int id) {
		return adminMapper.delete(id);
	}
	
	public int updateSelective(Admin admin) {
		return adminMapper.update(admin);
	}
	
	public List<Admin> selectSelective(Admin admin) {
		return adminMapper.selectSelective(admin);
	}
}
