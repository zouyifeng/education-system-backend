package com.laboratory.dao;

import java.util.List;

public interface BaseDao<T>{
	
	public List<T> selectAll();
	
	public List<T> selectSelective(T t);
	
	public List<T> selectSelective(T t,int pageNum,int pageSize);
	
	public List<T> selectByPage(int pageNum,int pageSize);
	
	public T selectByPrimaryKey(int id);
	
	public int update(T t);
	
	public int insert(T t);
	
	public int delete(Integer id);
	
	public int deleteSelective(T t);
	
}
