package com.laboratory.util;

import java.io.File;
import java.io.IOException;

import javax.security.auth.login.Configuration;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public class QiniuImageUpload {
		
	private String ACCESS_KEY = "FMp0z0Sktd_KixIaVFTJAEILXS1gxZDzo_alH7xF";  
	  
	private String SECRET_KEY = "bqHS4hf-H9lforxTsx7GuRPbNicMi0ArxJmHzTl4";  
	
	private String IMAGE_DOMAIN = "http://7xo8y0.com1.z0.glb.clouddn.com/";
	
	private String FILE_DOMAIN = "http://onxnl5xw4.bkt.clouddn.com/";
  
    // 要上传的空间（mybucket是刚刚设置的空间名称）  
	private String imageBucketname = "images"; 
	
	private String filesBucketname = "files";
  
    //密钥配置  
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);  
    
    
    //创建上传对象  
    UploadManager uploadManager = new UploadManager();  
  
    //上传策略中设置persistentOps字段和persistentPipeline字段  
    private String getUpToken(String bucketname){  
        return auth.uploadToken(bucketname);  
    }  
  
    public String uploadImage(String FilePath) throws IOException{  
		String ret = "";
		try {
			Response res = uploadManager.put(FilePath, null, getUpToken(imageBucketname));  
			DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
		    ret = IMAGE_DOMAIN + putRet.key; 
		} catch (QiniuException e) {  
        	Response r = e.response;   
	        try {  
	          System.out.println(r.bodyString());  
	        } catch (QiniuException e1) {  
		        	
	        }   
		}    
		return ret; 
    }

	public String uploadImageByByte(File file) throws IOException{
		String ret = "";
		try {
			Response res = uploadManager.put(file, null, getUpToken(imageBucketname));
			DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
			ret = IMAGE_DOMAIN + putRet.key;
		} catch (QiniuException e) {
			Response r = e.response;
			try {
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {

			}
		}
		return ret;
	}
    
    public String uploadFile(String FilePath, String FileName) throws IOException{  
		String ret = "";
		try {  
			Response res = uploadManager.put(FilePath, FileName, getUpToken(filesBucketname));  
			DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
		    ret = FILE_DOMAIN + putRet.key; 
		} catch (QiniuException e) {  
        	Response r = e.response;   
		}    
		return ret;
    } 
}

