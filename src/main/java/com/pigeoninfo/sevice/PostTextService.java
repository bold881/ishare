package com.pigeoninfo.sevice;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.pigeoninfo.entity.PostText;
import com.pigeoninfo.entity.UserInfo;

public interface PostTextService {
	public boolean save(PostText postText);
	
	public List<PostText> getByUserInfo(UserInfo userInfo);
	
	public List<PostText> getByUserInfo(UserInfo userInfo,
			Pageable pageable);
}
