package com.myclass.entity;

import org.springframework.web.multipart.MultipartFile;

public class FilePath {
	private MultipartFile[] fileData;

	public MultipartFile[] getFileData() {
		return fileData;
	}

	public void setFileDatas(MultipartFile[] fileData) {
		this.fileData = fileData;
	}

}
