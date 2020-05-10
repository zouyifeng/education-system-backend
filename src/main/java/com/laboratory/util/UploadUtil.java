package com.laboratory.util;


import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.laboratory.po.Files;

public class UploadUtil {
	private static final String basePath = "D:/Program/apache-tomcat-7.0.76/wtpwebapps/wechat-education-system/resources/";
//	private static final String basePath = "E:/document/apache-tomcat-7.0.73/wtpwebapps/wechat-education-system/resources/";


	/**
	 * 删除图片
	 * @param picName 图片名字
	 */
	public static void deletePicture(String picName){
		File picture = new File(basePath+picName);
		if(picture.exists()){
			picture.delete();
		}
	}

	/**
	 *
	 * @param pic 上传的图片
	 * @param category 上传图片所属类别
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public static String uploadPicture(MultipartFile pic,String category) throws IllegalStateException, IOException{

		QiniuImageUpload qiniu = new QiniuImageUpload();

		String oldPicName = pic.getOriginalFilename();
		String newPicName = category+"/"+UUID.randomUUID().toString()+oldPicName.substring(oldPicName.lastIndexOf("."));
		String realPath = basePath+category;
		File file = new File(realPath);
		String netPath = "";
		if(!(file.exists())){
			file.mkdir();
		}
		if(pic.getSize() > 0){
			pic.transferTo(new File(basePath + newPicName));
			netPath = qiniu.uploadImage(basePath + newPicName);
		}
		return netPath;
	}

	/**
	 *
	 * @param files 上传的图片
	 * @param category 上传图片所属类别
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public static Files uploadFile(MultipartFile files,String category) throws IllegalStateException, IOException{

		QiniuImageUpload qiniu = new QiniuImageUpload();


		String oldPicName = files.getOriginalFilename();
//		String newPicName = category+"/"+UUID.randomUUID().toString()+oldPicName.substring(oldPicName.lastIndexOf("."));
		String newPicName = category + "/" + oldPicName;
		String realPath = basePath+category;
		File file = new File(realPath);
		Files ret = new Files();
		if(!(file.exists())){
			file.mkdir();
		}
		if(files.getSize() > 0){
			files.transferTo(new File(basePath + newPicName));
			ret.setPath(qiniu.uploadFile(basePath + newPicName, oldPicName));
			ret.setName(oldPicName);
		}
		return ret;
	}
}
