package com.laboratory.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.laboratory.util.PageInfo;
import com.laboratory.dto.PictureDto;
import com.laboratory.po.News;
import com.laboratory.po.Picture;
import com.laboratory.service.NewsService;
import com.laboratory.util.DateUtil;
import com.laboratory.util.JsonRequest;
import com.laboratory.util.JsonResponse;
import com.laboratory.util.PageBean;
import com.laboratory.util.UploadUtil;

@RestController
public class NewsController extends AbstractController {

	@Autowired
	private NewsService newsService;
	
	//展示文章列表*
	@RequestMapping("/news_list")
	public JsonResponse showNewsList(@RequestBody JsonRequest jsonRequest){
//		ModelAndView mv = new ModelAndView();
		Map<String, Object> ret = new HashMap<String, Object>();
		News news =  objectMapper.convertValue(jsonRequest.getData(), News.class);
		List<News> newsList =  newsService.findNewsByCondition(news, 1, 7);
		for(News a:newsList){
			a.setContext(null);
		}
		
		PageBean<News> page = new PageBean<News>(newsList);
		List<String> years = newsService.findNewsYear();
		ret.put("years", years);
//		ret = (HashMap)JsonResponse.newOk(page);
//		ret.put(page);	
//		ret.push("page")
//		mv.addObject("years", years);
//		mv.addObject("page", page);
//		mv.setViewName("/publication-1");
//		return mv;
		return JsonResponse.newOk(ret, page);
	}
	
	//单独展示文章
	@RequestMapping("/news")
	public JsonResponse showNews(@RequestBody JsonRequest jsonRequest){
		News news = objectMapper.convertValue(jsonRequest.getData(), News.class);
		News newArticle  = newsService.findOne(news.getId());
		news.setContext(news.getContext());
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("news", newArticle);
		return JsonResponse.newOk(ret);
	}
	
	@RequestMapping("/news_img") 
	public JsonResponse showAllImg() {
		List<Picture> imgList = newsService.selectNewsAllImg();
		return JsonResponse.newOk(imgList);
	}
	
	//按年份选择
	@RequestMapping("/news_year")
	public JsonResponse showNewsByYear(@RequestBody JsonRequest jsonRequest){
//		ModelAndView mv = new ModelAndView();
		News _new = objectMapper.convertValue(jsonRequest.getData(), News.class);  //date
//		Article article = new Article();
//		article.setDate(year);
		List<News> news = newsService.findNewsByCondition(_new, 1, 7);
		PageBean<News> page = new PageBean<News>(news);
		List<String> years = newsService.findNewsYear();
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("year", _new.getDate());
		ret.put("years", years);
//		mv.addObject("year", year);
//		mv.addObject("years", years);
//		mv.addObject("page", page);
//		mv.setViewName("/publication-1");
		return JsonResponse.newOk(ret, page);
//		return mv;
	}
	
	//分页
	@RequestMapping("/news_page")
//	public JsonResponse showByPage(int pageNum,@RequestParam(required=false,value="year") String year){
	public JsonResponse showByPage(@RequestBody JsonRequest jsonRequest){
		List<News> news = null;
		News _new = null;
		Map<String, Object> ret = new HashMap<String, Object>();
		if(jsonRequest.getData() != null){
//			Article article = new Article();
//			article.setDate(year);
			_new = objectMapper.convertValue(jsonRequest.getData(), News.class);
			news = newsService.findNewsByCondition(_new, jsonRequest.getPageInfo().getPageNum(), 7);
		}else{
			news = newsService.findByPage(jsonRequest.getPageInfo().getPageNum(), 7);
		}
//		ModelAndView mv = new ModelAndView();
		PageBean<News> page = new PageBean<News>(news);
		List<String> years = newsService.findNewsYear();
//		mv.addObject("years", years);
//		mv.addObject("page", page);
//		mv.addObject("year", year);
//		mv.setViewName("/publication-1");
//		return mv;
		ret.put("years", years);
		ret.put("year", _new.getDate());
		return JsonResponse.newOk(ret, page);
	}
	
	
	//返回所有文章所在的年份段
	@RequestMapping("year")
	@ResponseBody
//	public List<String> showYear(){
//		List<String> years = articleService.findArticleYear();
//		return years;
//	}
	public JsonResponse showYear(){
		List<String> years = newsService.findNewsYear();
		return JsonResponse.newOk(years);
	}
	
	
	
	//后台展示页面*
	@RequestMapping("/admin/news_list")
	public JsonResponse showArticleByAdmin(@RequestBody JsonRequest jsonRequest){
		PageInfo pageInfo = objectMapper.convertValue(jsonRequest.getPageInfo(), PageInfo.class);		
		List<News> news = newsService.findByPage(pageInfo.getPageNum(), pageInfo.getPageSize());
		PageBean<News> page = new PageBean<News>(news);
		return JsonResponse.newOk(page);
	}
	
	//查询文章*
	@RequestMapping("/admin/news_select")
	public JsonResponse showArticleByCondition(@RequestBody JsonRequest jsonRequest){
		News _new = objectMapper.convertValue(jsonRequest.getData(), News.class);
		PageInfo pageInfo = objectMapper.convertValue(jsonRequest.getPageInfo(), PageInfo.class);
		List<News> news = newsService.findNewsByCondition(_new, pageInfo.getPageNum(), pageInfo.getPageSize());			
		PageBean<News> page = new PageBean<News>(news);
		Map<String, Object> ret = new HashMap<String, Object>();
		return JsonResponse.newOk(ret, page);
	}
	
	//后台分页查询
	@RequestMapping("/admin/news_page")
	public JsonResponse showArticleByPae(@RequestBody JsonRequest jsonRequest){
//		ModelAndView mv = new ModelAndView();
		News _new = objectMapper.convertValue(jsonRequest.getData(), News.class);
		List<News> news = null;
//		if(author!=null){
//			article = new Article();
//			article.setAuthor(author);
//			mv.addObject("author", author);
//		}
//		if(title!=null){
//			if(article==null){
//				article = new Article();
//			}
//			article.setTitle(title);
//			mv.addObject("title", title);
//		}
		if(_new!=null){
			news = newsService.findNewsByCondition(_new, jsonRequest.getPageInfo().getPageNum(), 6);
		}else{
			news = newsService.findByPage(jsonRequest.getPageInfo().getPageNum(), 6);
		}
		PageBean<News> page = new PageBean<News>(news);
		return JsonResponse.newOk(news, page);
	}
	
	//后台增加文章*
	@RequestMapping("/admin/news_add")
	public JsonResponse addArticle(@RequestBody JsonRequest jsonRequest) throws IOException{
//		ModelAndView mv = new ModelAndView();
		News _new = objectMapper.convertValue(jsonRequest.getData(), News.class);
		Date date = new Date();
		_new.setDate(DateUtil.formatToString("yyyy-MM-dd", date));
		Map<String, String> ret = new HashMap<String, String>();
		
		if(_new.getId() == null) {
			int i = newsService.saveNews(_new);
			System.out.println(_new.getId());
			
			if(i>0){
//				mv.addObject("message", "添加文章成功");
				ret.put("message", "添加文章成功");
				ret.put("newsId", _new.getId().toString());
			}else{
				ret.put("message", "添加文章失败");
			}
		}else{
			int i = newsService.updateNews(_new);
			
			if(i>0){
//				mv.addObject("message", "添加文章成功");
				ret.put("message", "更新文章成功");
				ret.put("newsId", _new.getId().toString());
			}else{
				ret.put("message", "更新文章失败");
			}
		}
		
//		mv.setViewName("/admin/message");
//		return mv;
		return JsonResponse.newOk(ret);
	}
	
	/**
	 * 图片上传*
	 * @param pic
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/news_pic_upload.action")
	public JsonResponse picUpload(HttpServletRequest req, MultipartFile pic) throws IOException {
		String category = "news";
		String picName = "";
//		String classId = req.getParameter("classId");
		
//		Picture apic = new Picture();
//		apic.setNewsId(classId);
		Map<String, String> ret = new HashMap<String, String>();
		try{
			picName = UploadUtil.uploadPicture(pic, category);
//			apic.setPath(picName);
		}catch(IOException e){
			e.printStackTrace();
		}
		
//		newsService.insertNewsImg(apic);
		
		ret.put("picUrl", picName);
		
		return JsonResponse.newOk(ret);
	}
	
	@RequestMapping("/admin/news_pic_add")
	public JsonResponse newsPicAdd(@RequestBody JsonRequest jsonRequest) {
		Picture pics =  objectMapper.convertValue(jsonRequest.getData(), Picture.class);
//		for(Picture p : pics.getPcitureList()) {
			newsService.insertNewsImg(pics);
//		}
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("msg", "更新文章成功！");
		return JsonResponse.newOk(ret);
	}
	
	//后台删除文章*
	@RequestMapping("/admin/news_delete")
	public JsonResponse deleteArticle(@RequestBody JsonRequest jsonRequest){
//		int i = articleService.deleteArticle(id);
		News _new = objectMapper.convertValue(jsonRequest.getData(), News.class);
		int i = newsService.deleteNews(_new.getId());
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("state", i>0 ? "2000" : "5000");
//		return i>0?true:false;
		return JsonResponse.newOk(ret);
		
	}
}
