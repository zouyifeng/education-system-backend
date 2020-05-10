package com.laboratory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laboratory.dao.MemberMapper;
import com.laboratory.po.Member;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	
	//通过id查找成员
	public Member findById(Integer id) {
		return memberMapper.selectByPrimaryKey(id);
	}
	
	//分页查找成员
	public List<Member> findByPage(int pageNum, int pageSize) {
		return memberMapper.selectByPage(pageNum, pageSize);
	}

	//通过条件查询成员
	public List<Member> findMembersByCondition(Member member,int pageNum,int pageSize) {
		return memberMapper.selectSelective(member,pageNum,pageSize);
	}

	//删除成员
	public int deleteMember(Integer id){
		return memberMapper.delete(id);
	}
	
	//增加成员
	public int saveMember(Member member) {
		return memberMapper.insert(member);
	}

	//修改成员
	public int updateMember(Member member) {
		return memberMapper.update(member);
	}

	
}
