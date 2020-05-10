package com.laboratory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laboratory.dao.WechatUserMapper;
import com.laboratory.po.WechatUser;

@Service
public class WechatUserService {

	@Autowired
	WechatUserMapper wechatUserMapper;
	
	public List<WechatUser> selectAll() {
		return wechatUserMapper.selectAll();
	}
	
	public int addWechatUser(WechatUser t) {
		return wechatUserMapper.insert(t);
	}
	
}
