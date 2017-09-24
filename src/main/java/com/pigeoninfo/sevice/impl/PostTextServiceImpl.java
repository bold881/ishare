package com.pigeoninfo.sevice.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pigeoninfo.dao.PostTextDao;
import com.pigeoninfo.entity.PostText;
import com.pigeoninfo.entity.UserInfo;
import com.pigeoninfo.sevice.PostTextService;

@Service
public class PostTextServiceImpl implements PostTextService {

	@Resource
	private PostTextDao postTextDao;
	
	@Override
	public boolean save(PostText postText) {
		
		if(postText != null) {
			System.out.println(postText.getContent());
			if(postTextDao.save(postText) != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<PostText> getByUserInfo(UserInfo userInfo) {
		return postTextDao.getByUserInfo(userInfo);
	}

	@Override
	public List<PostText> getByUserInfo(UserInfo userInfo, Pageable pageable) {
		return postTextDao.getByUserInfo(userInfo, pageable);
	}

}
