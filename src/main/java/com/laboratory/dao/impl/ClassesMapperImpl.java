package com.laboratory.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.laboratory.dao.ClassesMapper;
import com.laboratory.po.Classes;
import com.laboratory.po.ClassesPicture;
import com.laboratory.po.Member;

@Repository
public class ClassesMapperImpl extends BaseDaoImpl<Classes> implements ClassesMapper{

	//重载BaseDao的selectAll方法 通过此方法分页查询全部研究成果
	public List<Classes> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return template.selectList(getStatement("selectAll"));
	}
	
	public List<Member> selectClassMember(Classes classes) {
		return template.selectList(getStatement("selectClassMember"), classes);
	}
}
