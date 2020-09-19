package com.myclass.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
	String uploadFile(MultipartFile[] fileDatas);

	String uploadFileUser(MultipartFile[] fileDatas, String name, String key, String secret);
	public boolean checkFile(MultipartFile[] fileDatas) ;
}
