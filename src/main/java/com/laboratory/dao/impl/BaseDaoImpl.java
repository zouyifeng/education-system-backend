package com.laboratory.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.laboratory.dao.BaseDao;
import com.laboratory.util.NameSpaceUtil;

public class BaseDaoImpl<T> implements BaseDao<T>{

	private static final String SELECTBYPAGE = "selectByPage";
	
	private static final String SELECTALL = "selectAll";
	
	private static final String SELECTSELECTIVE = "selectSelective";
	
	private static final String SELECTBYPRIMARYKEY = "selectByPrimaryKey";
	
	private static final String UPDATESELECTIVE = "updateSelective";
	
	private static final String DELETEBYPRIMARYKEY = "deleteByPrimaryKey";
	
	private static final String DELETESELECTIVE = "deleteSelective";
	
	private static final String INSERTSELECTIVE = "insertSelective";
	
	@Autowired
	protected SqlSessionTemplate template;
	
	@Override
	public List<T> selectAll() {
		return template.selectList(getStatement(SELECTALL));
	}
	
	@Override
	public List<T> selectSelective(T t) {
		return template.selectList(getStatement(SELECTSELECTIVE), t);
	}

	@Override
	public List<T> selectSelective(T t,int pageNum,int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return template.selectList(getStatement(SELECTSELECTIVE), t);
	}
	
	@Override
	public List<T> selectByPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return template.selectList(getStatement(SELECTBYPAGE));
	}
	
	@Override
	public T selectByPrimaryKey(int id) {
		return template.selectOne(getStatement(SELECTBYPRIMARYKEY),id);
	}

	@Override
	public int update(T t) {
		return  template.update(getStatement(UPDATESELECTIVE),t);
	}

	@Override
	public int  insert(T t) {
		return template.insert(getStatement(INSERTSELECTIVE), t);
	}

	@Override
	public int delete(Integer id) {
		return template.delete(getStatement(DELETEBYPRIMARYKEY), id);
	}
	
	@Override
	public int deleteSelective(T t) {
		return template.delete(getStatement(DELETESELECTIVE), t);
	}
	
	protected String getStatement(String id) {
		return getNameSpace()+'.'+id;
	}
	
	protected String getNameSpace() {
		return NameSpaceUtil.getNameSpace(getEntity());  // com.laboratory.dao + '' + mapper
	}
	
	protected String getEntity(){
	// return ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
		ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
		return type.getActualTypeArguments()[0].toString();			//class com.laboratory.po.Research
	}

}
