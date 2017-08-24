package com.neo.dao;

import org.springframework.data.repository.CrudRepository;

import com.neo.entity.PostImg;

public interface PostImgDao extends CrudRepository<PostImg, Long> {
	public PostImg findByHashedFilename(String hashedFilename);
}
