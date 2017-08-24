package com.neo.sevice.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.neo.dao.PostTextDao;
import com.neo.entity.PostText;
import com.neo.entity.UserInfo;
import com.neo.sevice.PostTextService;

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

}
