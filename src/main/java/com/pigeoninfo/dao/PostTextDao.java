package com.pigeoninfo.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pigeoninfo.entity.PostText;
import com.pigeoninfo.entity.UserInfo;

public interface PostTextDao extends PagingAndSortingRepository<PostText, Long> {
	
	public List<PostText> getByUserInfo(UserInfo userInfo);
	
	public List<PostText> getByUserInfo(UserInfo userInfo, Pageable pageable);
}
