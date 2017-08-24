package com.neo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.neo.entity.PostText;
import com.neo.entity.UserInfo;

public interface PostTextDao extends CrudRepository<PostText, Long> {
	
	public List<PostText> getByUserInfo(UserInfo userInfo);
	
}
