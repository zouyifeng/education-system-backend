package com.laboratory.dao;

import com.laboratory.po.Admin;

public interface AdminMapper extends BaseDao<Admin>{
	
	Admin selectByPrinciple(Admin admin);
	
}
