package com.laboratory.dao;

import java.util.List;

import com.laboratory.po.Classes;
import com.laboratory.po.ClassesPicture;
import com.laboratory.po.Member;

public interface ClassesMapper extends BaseDao<Classes>{

	List<Classes> selectAll(int pageNum,int pageSize);
	
	List<Member> selectClassMember(Classes classes);
}
