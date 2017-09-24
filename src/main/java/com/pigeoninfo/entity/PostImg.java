package com.pigeoninfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PostImg {
	
	@Id
	@GeneratedValue
	private Integer imgid;
	
	private String originFilename;
	
	@Column(unique=true)
	private String hashedFilename;
	
	private String cpFileName;
	
	private String fileExt;

	public String getCpFileName() {
		return cpFileName;
	}

	public void setCpFileName(String cpFileName) {
		this.cpFileName = cpFileName;
	}

	public Integer getImgid() {
		return imgid;
	}

	public void setImgid(Integer imgid) {
		this.imgid = imgid;
	}

	public String getOriginFilename() {
		return originFilename;
	}

	public void setOriginFilename(String originFilename) {
		this.originFilename = originFilename;
	}

	public String getHashedFilename() {
		return hashedFilename;
	}

	public void setHashedFilename(String hashedFilename) {
		this.hashedFilename = hashedFilename;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	
}
