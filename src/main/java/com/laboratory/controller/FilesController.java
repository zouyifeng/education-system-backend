package com.laboratory.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboratory.po.Files;
import com.laboratory.dto.FilesListDto;
import com.laboratory.po.DeviceType;
import com.laboratory.service.FilesService;
import com.laboratory.util.JsonRequest;
import com.laboratory.util.JsonResponse;
import com.laboratory.util.PageBean;
import com.laboratory.util.PageInfo;
import com.laboratory.util.UploadUtil;

@RestController
public class FilesController {

	@Autowired
	FilesService fileService;

	@Autowired
	private ObjectMapper objectMapper;


	//按照设备类型分类
	@RequestMapping("/file_list")
	public JsonResponse showDeviceByType(@RequestBody JsonRequest jsonRequest){
		Files files = objectMapper.convertValue(jsonRequest.getData(), Files.class);
		List<Files> filesList = fileService.findAllByType(files);
		return JsonResponse.newOk(filesList);
	}

	//分页显示
//	@RequestMapping("/device_page")
//	public ModelAndView showDeviceByPage(int pageNum,@RequestParam(required=false,defaultValue="0")int typeId){
//	public JsonResponse showDeviceByPage(@RequestBody JsonRequest jsonRequest){
//		Files device = objectMapper.convertValue(jsonRequest.getData(), Files.class);
//		List<Files> devices = null;
//		if(device.getType() != null){
//			devices = fileService.findByType(jsonRequest.getPageInfo().getPageNum(), 5, device.getType());
//		}else{
//			devices = fileService.findByPage(jsonRequest.getPageInfo().getPageNum(), 5);
//		}
//		PageBean<Files> page = new PageBean<Files>(devices);
////		ModelAndView mv = new ModelAndView();
//		Map<String, String> ret = new HashMap<String, String>();
////		mv.addObject("type", typeId);
//		ret.put("typeId", device.getType().toString());
////		mv.addObject("page", page);
////		mv.setViewName("/equipment");
////		return mv;
//		return JsonResponse.newOk(ret, page);
//	}

	//查找所有的设备类型
	@RequestMapping("/type")
	@ResponseBody
//	public List<DeviceType> showTypes(){
	public JsonResponse showTypes(){
		List<DeviceType> types = fileService.findAllTypes();
		return JsonResponse.newOk(types);
	}


	//#########################################################################
	//############################################################
	//后台管理展示数据
	@RequestMapping("/admin/device_list")
	public JsonResponse showDeivceByAdmin(){
		List<Files> devices = fileService.findByPage(1,6);
		PageBean<Files> page = new PageBean<Files>(devices);
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("page",page);
//		mv.setViewName("/admin/equipment_select");
//		return mv;
		return JsonResponse.newOk(page);
	}

	@RequestMapping("/admin/file_upload")
	public JsonResponse uploadFile(MultipartFile file) {
		String category = "file";
		Files files = null;
		try{
			files = UploadUtil.uploadFile(file, category);
		}catch(IOException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return JsonResponse.newOk(files);
	}

	//后台根据条件查询实验设备
	@RequestMapping("/admin/file_select")
	public JsonResponse findDeviceByCondition(@RequestBody JsonRequest jsonRequest){
		Files device = objectMapper.convertValue(jsonRequest.getData(), Files.class);
		PageInfo pageInfo = objectMapper.convertValue(jsonRequest.getPageInfo(), PageInfo.class);
		List<Files> devices = fileService.findDeviceByCondition(device,pageInfo.getPageNum(),6);
		PageBean<Files> page =  new PageBean<Files>(devices);
		Map<String, String> ret = new HashMap<String, String>();
		return JsonResponse.newOk(ret, page);
	}

	//后台管理分页显示设备
	@RequestMapping("/admin/device_page")
	public JsonResponse listDeviceByPage(@RequestBody JsonRequest jsonRequest){
//		Device device = null;
		Files device = objectMapper.convertValue(jsonRequest.getData(), Files.class);
		List<Files> devices = null;
//		if(name!=null||description!=null){
//			 device = new Device();
//			 device.setName(name);
//			 device.setDescription(description);
//		}
		if(device!=null){
			devices = fileService.findDeviceByCondition(device, jsonRequest.getPageInfo().getPageNum(), 6);
		}else{
			devices = fileService.findByPage(jsonRequest.getPageInfo().getPageNum(), 6);
		}
		PageBean<Files> page = new PageBean<Files>(devices);
//		ModelAndView mv = new ModelAndView();
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("target", device);
//		if(name!=null){
//			mv.addObject("name", name);
//		}
//		if(description!=null){
//			mv.addObject("description", description);
//		}
//		mv.addObject("page", page);
//		mv.setViewName("/admin/equipment_select");
//		return mv;
		return JsonResponse.newOk(ret, page);
	}

	//后台增加或者修改设备的页面
//	@RequestMapping("/admin/device_editUI")
//	public JsonResponse editUI(@RequestBody JsonRequest jsonRequest){
////		ModelAndView mv = new ModelAndView();
//
//		File device = objectMapper.convertValue(jsonRequest.getData(), File.class);
//		Map<String, Object> ret = new HashMap<String, Object>();
//		File newDevice = null;
//		if(device.getId() != null){
//			newDevice = fileService.findDevice(device.getId());
//		}
//		if(newDevice != null){
//			DeviceType type = fileService.findDeviceType(device.getType());
////			mv.addObject("type", type);
////			mv.addObject("device", device);
//			ret.put("type", type);
//			ret.put("device", newDevice);
//		}
////		mv.setViewName("/admin/equipment_editUI");
////		return mv;
//		return JsonResponse.newOk(ret);
//	}

	//后台增加实验设备(全局异常没有处理)
	@RequestMapping("/admin/add_files")
	public JsonResponse addDevice(@RequestBody JsonRequest jsonRequest){
		FilesListDto files = objectMapper.convertValue(jsonRequest.getData(), FilesListDto.class);
		List<Files> f = files.getFilesList();

//		List<Files> files = jsonRequest.getd
		for(Files ff: f) {
			fileService.saveDevice(ff);
		}
		HashMap<String, String> ret = new HashMap<String, String>();
		ret.put("msg", "上传成功！");
		return JsonResponse.newOk(ret);
	}


	//后台修改实验设备(全局异常没有处理)
//	@RequestMapping("/admin/device_edit")
//	public ModelAndView eidtDevice(File device,MultipartFile pic){
//		ModelAndView mv = new ModelAndView();
//		if(pic.getSize()>0){
//			try {
//
//				String path = PictureUtil.uploadPicture(pic, "equipment");
//				device.setPath(path);
//				File d = fileService.findDevice(device.getId());
//				PictureUtil.deletePicture(d.getPath());
//			} catch (IllegalStateException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		int i = fileService.updateDevice(device);
//		if(i>0){
//			mv.addObject("message", "修改实验设备成功");
//		}else{
//			mv.addObject("message", "修改实验设备失败");
//		}
//		mv.setViewName("/admin/message");
//		return mv;
//	}

	@RequestMapping("/admin/delete_file")
	public JsonResponse deleteDevice(@RequestBody JsonRequest jsonRequest){
		Files file = objectMapper.convertValue(jsonRequest.getData(), Files.class);
		int i = fileService.deleteDevice(file.getId());
		Map<String, String> ret = new HashMap<String, String>();
		if(i>0){
			ret.put("state", "删除文件失败！");
		}else{
			ret.put("state", "删除文件失败！");
		}
		return JsonResponse.newOk(ret);
	}
}