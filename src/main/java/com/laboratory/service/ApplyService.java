package com.laboratory.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laboratory.dao.ApplyMapper;
import com.laboratory.po.Apply;
import com.laboratory.util.WechatUtil;

@Service
public class ApplyService {
	
	@Autowired
	ApplyMapper applyMapper;
	
	public Apply findById(Integer id) {
		return applyMapper.selectByPrimaryKey(id);
	}

	//分页显示
	public List<Apply> findByPage(int pageNum,int pageSize){
		return applyMapper.selectByPage(pageNum, pageSize);
	}
	
	//根据查询条件查找文章
	public List<Apply> findApplyByCondition(Apply news,int pageNum,int pageSize) {
		return applyMapper.selectSelective(news, pageNum, pageSize);
	}

	//保存文章
	public int saveApply(Apply apply) {
		return applyMapper.insert(apply);
	}

	//删除文章
	public int deleteApply(Integer id) {
		return applyMapper.delete(id);
	}

	//更新文章
	public int updateApply(Apply apply) throws IOException {
		int i = applyMapper.update(apply);
		WechatUtil.send_apply_template_message(findById(apply.getId()));
		return i;
	}
	
}
