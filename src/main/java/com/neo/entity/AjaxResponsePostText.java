package com.neo.entity;

import java.util.Date;
import java.util.List;

public class AjaxResponsePostText {
	
	private String content;
	
	private Date date;
	
	private List<String> postImages;
	
	private List<String> cpPostImages;

	public List<String> getCpPostImages() {
		return cpPostImages;
	}

	public void setCpPostImages(List<String> cpPostImages) {
		this.cpPostImages = cpPostImages;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<String> getPostImages() {
		return postImages;
	}

	public void setPostImages(List<String> postImages) {
		this.postImages = postImages;
	}
}
