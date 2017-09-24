package com.pigeoninfo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class PostText {
	
	@Id
	@GeneratedValue
	private Integer textid;
	
	private String content;
	
	@ManyToOne
	@JoinColumn(name="uid", nullable=false)
	private UserInfo userInfo;
	
	@ManyToMany
	@JoinTable(name = "PostTextImg", joinColumns = { @JoinColumn(name = "textid") }, inverseJoinColumns ={@JoinColumn(name = "imgid") })
	private List<PostImg> postImgs;
	
	private Date date;

	public Integer getTextid() {
		return textid;
	}

	public void setTextid(Integer textid) {
		this.textid = textid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public List<PostImg> getPostImgs() {
		return postImgs;
	}

	public void setPostImgs(List<PostImg> postImgs) {
		this.postImgs = postImgs;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
