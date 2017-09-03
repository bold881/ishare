package com.neo.sevice;

import java.util.List;

import org.springframework.data.domain.Pageable;
import com.neo.entity.PostText;
import com.neo.entity.UserInfo;

public interface PostTextService {
	public boolean save(PostText postText);
	
	public List<PostText> getByUserInfo(UserInfo userInfo);
	
	public List<PostText> getByUserInfo(UserInfo userInfo,
			Pageable pageable);
}
