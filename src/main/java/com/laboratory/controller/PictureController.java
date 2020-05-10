package com.laboratory.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.laboratory.po.Classes;
import com.laboratory.po.ClassesPicture;
import com.laboratory.service.PictureService;
import com.laboratory.util.JsonRequest;
import com.laboratory.util.JsonResponse;
import com.laboratory.util.UploadUtil;

@RestController
public class PictureController extends AbstractController{
	
	@Autowired
	private PictureService pictureService;
	
	//单独展示图片
	@RequestMapping("/picture")
//	public ModelAndView showPicture(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
	public JsonResponse showPicture(@RequestBody JsonRequest jsonRequest) {
//		ModelAndView mv = new ModelAndView();
		List<ClassesPicture> pictures = pictureService.findPicturesByPage(jsonRequest.getPageInfo().getPageNum(), 5);
		PageInfo<ClassesPicture> page = new PageInfo<ClassesPicture>(pictures);
//		mv.addObject("page", page);
//		mv.setViewName("/gallery");
//		return mv;
		return JsonResponse.newOk(page);
	}
	
	
	//研究图片  增加或者修改页面
	@RequestMapping("/admin/picture_editUI")
//	public ModelAndView picture_editUI(@RequestParam(required=false)Integer id, @RequestParam(required=false)Integer researchId){
	public JsonResponse picture_editUI(@RequestBody JsonRequest jsonRequest){
//		ModelAndView mv = new ModelAndView("/admin/picture_editUI");
		Classes classes = objectMapper.convertValue(jsonRequest.getData(), Classes.class);
		PageInfo pageInfo = objectMapper.convertValue(jsonRequest.getPageInfo(), PageInfo.class);
		HashMap<String, Object> ret = new HashMap<String, Object>();
		if(null != classes.getId()){
			ClassesPicture picture = this.pictureService.findById(classes.getId());
//			mv.addObject("picture", picture);
			ret.put("picture", picture);
		}
//		mv.addObject("researchId", researchId);
		return JsonResponse.newOk(ret);
	}
	
	//修改研究图片
	@RequestMapping("/admin/picture_edit")
	public JsonResponse editMember(@RequestBody JsonRequest jsonRequest){
//		ModelAndView mv = new ModelAndView();
		ClassesPicture classesPicture = objectMapper.convertValue(jsonRequest.getData(), ClassesPicture.class);
		int i = this.pictureService.updatePicture(classesPicture);
		HashMap<String, String> ret = new HashMap<String, String>();
		if (i>0){
//			 mv.addObject("message","修改图片成功");
			ret.put("message", "修改图片成功");
		}else{
//			mv.addObject("message", "修改图片失败");
			ret.put("message", "修改图片失败");
		}
//		mv.setViewName("/admin/message");
//		return mv;
		return JsonResponse.newOk(ret);
	}
	
	//增加研究图片
	@RequestMapping("/admin/picture_add")
	public ModelAndView research_add(ClassesPicture picture ,MultipartFile pic){
		ModelAndView mv = new ModelAndView();
		String picName ;
		try {
			picName = UploadUtil.uploadPicture(pic,"research");
			picture.setPath(picName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int i = pictureService.savePicture(picture);
		if(i>0){
			mv.addObject("message", "添加图片成功");
		}else{
			mv.addObject("message", "添加图片失败");
		}
		mv.setViewName("/admin/message");
		return mv;
	}
	
	

	/**
	 * 图片上传*
	 * @param pic
	 * @return
	 */
	@RequestMapping("/admin/pic_upload.action")
	public JsonResponse picUpload(MultipartFile pic) {
		String category = "face";
		String picName = "";
		Map<String, String> ret = new HashMap<String, String>();
		try{
			picName = UploadUtil.uploadPicture(pic, category);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		ret.put("picUrl", picName);
		
		return JsonResponse.newOk(ret);
	}
	
	//条件查询出某研究的图片集
	@RequestMapping("/admin/picture_select")
	public ModelAndView findPictures(ClassesPicture picture){
		ModelAndView mv = new ModelAndView();
		
		List<ClassesPicture> pictures = this.pictureService.findPictureByCondition(picture, 1, 6);
		PageInfo<ClassesPicture> page = new PageInfo<ClassesPicture>(pictures);
		mv.addObject("page", page);
		mv.addObject("researchId", picture.getClassesId().toString());
		mv.setViewName("/admin/picture_select");
		
		return mv;
	}
	
	
	//后台分页显示某研究图片
	@RequestMapping("/admin/picture_page")
	public ModelAndView showPictureByPage(Integer researchId,int pageNum){
		
		ModelAndView mv = new ModelAndView();
		ClassesPicture picture = new ClassesPicture();
		List<ClassesPicture> pictures = null;
		picture.setClassesId(researchId);
		pictures = pictureService.findPictureByCondition(picture, pageNum, 6);
		PageInfo<ClassesPicture> page = new PageInfo<ClassesPicture>(pictures);
		
		mv.addObject("page", page);
		mv.addObject("researchId", researchId);
		mv.setViewName("/admin/picture_select");
		return mv;
	} 
	
	
	//后台删除图片
	@RequestMapping("/admin/picture_delete")
	@ResponseBody
	public boolean deletePicture(Integer id){
		ClassesPicture picture = pictureService.findById(id);
		int i = pictureService.deletePicture(id);
		UploadUtil.deletePicture(picture.getPath());
		return i>0 ? true : false;
	}
	
}
