package com.pigeoninfo.sevice.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pigeoninfo.dao.PostImgDao;
import com.pigeoninfo.entity.PostImg;
import com.pigeoninfo.sevice.PostImgService;

@Service
public class PostImgServiceImpl implements PostImgService {

	@Resource
	private PostImgDao postImgDao;
	
	@Override
	public PostImg save(PostImg postImg) {
		PostImg existPostImg = null;
		if(postImg != null) {
			existPostImg = postImgDao.findByHashedFilename(postImg.getHashedFilename());
			if(existPostImg != null) {
				return existPostImg;
			}
			
			existPostImg = postImgDao.save(postImg);
		}
		
		return existPostImg;
	}

}
