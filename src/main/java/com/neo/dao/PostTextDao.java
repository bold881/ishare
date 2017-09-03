package com.neo.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.neo.entity.PostText;
import com.neo.entity.UserInfo;

public interface PostTextDao extends PagingAndSortingRepository<PostText, Long> {
	
	public List<PostText> getByUserInfo(UserInfo userInfo);
	
	public List<PostText> getByUserInfo(UserInfo userInfo, Pageable pageable);
}
