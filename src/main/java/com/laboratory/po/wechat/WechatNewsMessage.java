package com.laboratory.po.wechat;

import java.util.List;

public class WechatNewsMessage extends WechatBaseMessage{
	private int ArticleCount;
	private List<WechatNews> Articles;
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<WechatNews> getArticles() {
		return Articles;
	}
	public void setArticles(List<WechatNews> articles) {
		Articles = articles;
	}
}
