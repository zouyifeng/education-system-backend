package com.laboratory.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.laboratory.util.PageBean;
import com.laboratory.po.Member;
import com.laboratory.service.MemberService;
import com.laboratory.util.CharUtil;
import com.laboratory.util.JsonRequest;
import com.laboratory.util.JsonResponse;
import com.laboratory.util.UploadUtil;
import com.laboratory.util.PageInfo;

@RestController
public class MemberController extends AbstractController {
	
	@Autowired
	private MemberService memberService;
	
//	@Autowired
//	private ObjectMapper objectMapper;
	
	
	@RequestMapping("/member_list")
//	public ModelAndView showMembers(@RequestParam(value="pageNum", defaultValue="1") int pageNum){
	public JsonResponse showMembers(@RequestBody JsonRequest jsonRequest){
//		ModelAndView mv = new ModelAndView();
		
//		List<Member> members = memberService.findByPage(jsonRequest.getPageInfo().getPageNum(), 4);
//		PageBean<Member> page= new PageBean<Member>(members); 
		PageInfo pageInfo = objectMapper.convertValue(jsonRequest.getPageInfo(), PageInfo.class);
		Member member = objectMapper.convertValue(jsonRequest.getData(), Member.class);
		List<Member> members = memberService.findMembersByCondition(member, 1, 16);
		PageBean<Member> page= new PageBean<Member>(members);
		
//		mv.addObject("page", page);
//		mv.setViewName("/member");
//		return mv;
		return JsonResponse.newOk(page);
	}
	
	@RequestMapping("/member")
	public JsonResponse showMember(@RequestBody JsonRequest jsonRequest){
//		ModelAndView mv = new ModelAndView();
		Member member = objectMapper.convertValue(jsonRequest.getData(), Member.class);
		Member newMember = memberService.findById(member.getId());
		String introduction = CharUtil.dealChar(newMember.getIntroduction());
		newMember.setIntroduction(introduction);
//		mv.addObject("member",member);
//		mv.setViewName("/mem_intr");
//		return mv;
		return JsonResponse.newOk(newMember);
	}
	
	/**
	 * 后台管理展示实验室成员*
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/admin/member_list.action")
	public JsonResponse showMembersByAdmin(@RequestBody JsonRequest jsonRequest){
		PageInfo pageInfo = objectMapper.convertValue(jsonRequest.getPageInfo(), PageInfo.class);
		Member member = objectMapper.convertValue(jsonRequest.getData(), Member.class);
		List<Member> members = memberService.findMembersByCondition(member, pageInfo.getPageNum(), 6);
		PageBean<Member> page= new PageBean<Member>(members);
		return JsonResponse.newOk(page);
	}
	
	 
	/**
	 * 后台管理通过名字或者研究方向查询*
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/admin/member_select")
	public JsonResponse findMembers(@RequestBody JsonRequest jsonRequest){
		Member member = objectMapper.convertValue(jsonRequest.getData(), Member.class);
		List<Member> members = memberService.findMembersByCondition(member, 1, 6);
//		Map<String, Object> ret = new HashMap<String, Object>();
		PageBean<Member> page = new PageBean<Member>(members);
		
//		if(member.getName() != null) {
//			mv.addObject("name", member.getName());
//			ret.put("name", member.getName());
//		}
		
//		if(member.getDirection() != null){
//			ret.put("direction", member.getDirection());
//		}
//		ret.put("page", page);
		
		return JsonResponse.newOk(page);
	}
	
//	public ModelAndView findMembers(@requestBody Member member){z
//		ModelAndView mv = new ModelAndView();
//		List<Member> members = memberService.findMembersByCondition(member,1,6);
//		
//		//显示没有查找到相应的记录(再看看需不需要)
//		//if(members.size()<=0){
//			//mv.addObject("message", "对不起，没有找到符合该条件的记录");
//		//}
//	//	else{
//			PageBean<Member> page= new PageBean<Member>(members);
//			if(member.getName()!=null){
//				mv.addObject("name", member.getName());
//			}
//			if(member.getDirection()!=null){
//				mv.addObject("direction", member.getDirection());
//			}
//			mv.addObject("page", page);
//		//}
//		mv.setViewName("/admin/member_select");
//		return mv;
//	}
	
	//后台分页显示成员
	@RequestMapping("/admin/member_page")
	public JsonResponse showMemberByPage(@RequestBody JsonRequest jsonRequest){
		Member member = objectMapper.convertValue(jsonRequest.getData(), Member.class);
		PageInfo pageInfo = objectMapper.convertValue(jsonRequest.getPageInfo(), PageInfo.class);
		List<Member> members = null;
		
		if(member.getName() != null || member.getDirection() != null){
			members = memberService.findMembersByCondition(member, pageInfo.getPageNum(), 6);
//			if(member.getName() != null){
//				mv.addObject("name", member.getName());
//			}
//			if(member.getDirection()!= null){
//				mv.addObject("direction", member.getDirection());
//			}
		}else{
			members = memberService.findByPage(pageInfo.getPageNum(), 6);
		}
		
		PageBean<Member> page = new PageBean<Member>(members);
		
//		return JsonResponse.newOk(page, members);
		return JsonResponse.newOk(page);
		
	}
	
//	@RequestMapping("/admin/member_page")
//	public ModelAndView showMemberByPage(@RequestParam(required=false)String name, @RequestParam(required=false)String direction, int pageNum){
//		ModelAndView mv = new ModelAndView();
//		List<Member> members = null;
//		Member member = new Member();
//		member.setName(name);
//		member.setDirection(direction);
//		if(member.getName()!=null||member.getDirection()!=null){
//			members = memberService.findMembersByCondition(member, pageNum, 6);
//			if(member.getName()!=null){
//				mv.addObject("name", member.getName());
//			}
//			if(member.getDirection()!=null){
//				mv.addObject("direction", member.getDirection());
//			}
//		}else{
//			members = memberService.findByPage(pageNum,6);
//		}
//		PageBean<Member> page= new PageBean<Member>(members);
//		mv.addObject("page", page);
//		mv.setViewName("/admin/member_select");
//		return mv;
//	} 
	
	//删除成员 还要删除成员图片*
	@RequestMapping("/admin/member_delete")
	public JsonResponse removeMember(@RequestBody JsonRequest jsonRequest){
		Member member = objectMapper.convertValue(jsonRequest.getData(), Member.class);
		Member newMember = memberService.findById(member.getId());
//		PictureUtil.deletePicture(member.getFace());
		int i = memberService.deleteMember(member.getId());
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("state", i>0 ? "2000" : "5000");
		return JsonResponse.newOk(ret);
	} 
	
	//增加和修改的成员页面*
	@RequestMapping("/admin/member_editUI.action")
	public JsonResponse addUI(@RequestBody JsonRequest jsonRequest){
//		ModelAndView mv = new ModelAndView();
		Member member = objectMapper.convertValue(jsonRequest.getData(), Member.class);
		Member newMember = null;
		if(member.getId() != null){
			newMember = memberService.findById(member.getId());
//			mv.addObject("member", member);
		}
		
//		mv.setViewName("/admin/member_editUI");
		return JsonResponse.newOk(newMember);
	}
	
	//增加成员(还有全局异常没有处理)*
	@RequestMapping("/admin/member_add")
	public JsonResponse addMember(@RequestBody JsonRequest jsonRequest){
//		String category = "face";
//		String picName;
		Map<String, String> ret = new HashMap<String, String>();
		Member member = objectMapper.convertValue(jsonRequest.getData(), Member.class);
		
		try{
//			picName = PictureUtil.uploadPicture(pic, category);
//			member.setFace(picName);
			int i = memberService.saveMember(member);
			if(i > 0){
				ret.put("message", "添加成员成功");
			} else {
				ret.put("message", "添加成员失败");
			}
			
		}catch(IllegalStateException e){
			e.printStackTrace();
		}
		
		return JsonResponse.newOk(ret);
	}
	
	/**
	 * 图片上传*
	 * @param pic
	 * @return
	 */
	@RequestMapping("/admin/member_pic_upload.action")
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
	
//	public ModelAndView addMember(Member member,MultipartFile pic){
//		ModelAndView mv = new ModelAndView();
//		String category = "face";
//		String picName ;
//		try {
//			picName = PictureUtil.uploadPicture(pic,category);
//			member.setFace(picName);
//			int i = memberService.saveMember(member);
//			if (i>0){
//				 mv.addObject("message","添加成员成功");
//			}else{
//				 mv.addObject("message", "添加成员失败");
//			}
//			mv.setViewName("/admin/message");
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return mv;
//	}
//	
	//修改成员(全局异常没有处理)*
	@RequestMapping("/admin/member_edit.action")
	public JsonResponse editMember(@RequestBody JsonRequest jsonRequest){
//		ModelAndView mv = new ModelAndView();
		Member member = objectMapper.convertValue(jsonRequest.getData(), Member.class);
		//判断是否更新头像
//		if(pic.getSize()>0){
			try {
//				String picName = PictureUtil.uploadPicture(pic, "face");
//				member.setFace(picName);
				Member m = memberService.findById(member.getId());
				UploadUtil.deletePicture(m.getFace());
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} 
//			catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		int i = memberService.updateMember(member);
		Map<String, String> ret = new HashMap<String, String>();
		if (i>0){
//			 mv.addObject("message","修改成员成功");
			 ret.put("state", "2000");
		}else{
//			 mv.addObject("message", "修改成员失败");
			 ret.put("state", "5000");
		}
//		mv.setViewName("/admin/message");
//		return mv;
		return JsonResponse.newOk(ret);
	}
}
