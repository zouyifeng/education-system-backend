package com.laboratory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laboratory.dao.MessageMapper;
import com.laboratory.po.Message;

@Service
public class MessageService {
	
	@Autowired
	MessageMapper messageMapper;
	
	public Message findById(Integer id) {
		return messageMapper.selectByPrimaryKey(id);
	}

	//分页显示
	public List<Message> findByPage(int pageNum,int pageSize){
		return messageMapper.selectByPage(pageNum, pageSize);
	}
	
	//根据查询条件查找文章
	public List<Message> findMessageByCondition(Message news,int pageNum,int pageSize) {
		return messageMapper.selectSelective(news, pageNum, pageSize);
	}

	//保存文章
	public int saveMessage(Message message) {
		return messageMapper.insert(message);
	}

	//删除文章
	public int deleteMessage(Integer id) {
		return messageMapper.delete(id);
	}

	//更新文章
	public int updateMessage(Message message) {
		return messageMapper.update(message);
	}
	
}
