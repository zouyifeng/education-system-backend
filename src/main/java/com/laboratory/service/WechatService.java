package com.laboratory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laboratory.po.Admin;
import com.laboratory.po.News;
import com.laboratory.po.Picture;

@Service
public class WechatService {
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private AdminService adminService;
	
	public News getLastestNew() {
    	return newsService.findByPage(1, 1).get(0);
	}
	
	public List<News> findRecentNews() {
		return newsService.findByPage(1, 10);
	}
	
	public String getLatestNewCover() {
		News news = newsService.findByPage(1, 1).get(0);
		Picture pic = new Picture();
    	pic.setNewsId(news.getId());
    	pic = newsService.selectNewsCover(pic);
    	String cover = pic.getPath();
    	return cover;
	}
	
	
	
}
