package com.laboratory.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laboratory.dao.NewsMapper;
import com.laboratory.po.News;
import com.laboratory.po.Picture;
import com.laboratory.po.WechatUser;
import com.laboratory.util.WechatUtil;

@Service
public class NewsService {
	
	@Autowired
	NewsMapper newsMapper;
	
	@Autowired
	WechatUserService wechatUserService;
	
	//根据文章id查找文章
	public News findOne(Integer id){
		return newsMapper.selectByPrimaryKey(id);
	}
	
	//分页显示
	public List<News> findByPage(int pageNum,int pageSize){
		return newsMapper.selectByPage(pageNum, pageSize);
	}

	//查找所有文章所在的年段
	public List<String> findNewsYear() {
		return newsMapper.selectNewsYear();
	}
	
	//根据查询条件查找文章
	public List<News> findNewsByCondition(News news,int pageNum,int pageSize) {
		return newsMapper.selectSelective(news, pageNum, pageSize);
	}

	//保存文章
	public int saveNews(News news) throws IOException {
		int i = newsMapper.insert(news);
		List<WechatUser> users = wechatUserService.selectAll();
		for(WechatUser u : users) {
			WechatUtil.send_template_message(news, u.getOpenId());
		}
		return i;
	}

	//删除文章
	public int deleteNews(Integer id) {
		return newsMapper.delete(id);
	}

	//更新文章
	public int updateNews(News news) {
		return newsMapper.update(news);
	}
	
	public void insertNewsImg(Picture pic) {
		newsMapper.insertNewsImg(pic);
	}
	
	public List<Picture> selectNewsAllImg() {
		return newsMapper.selectNewsAllImg();
	}
	
	public Picture selectNewsCover(Picture pic) {
		return newsMapper.selectNewsCover(pic);
	}
}
