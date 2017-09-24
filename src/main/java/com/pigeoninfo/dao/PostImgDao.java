package com.pigeoninfo.dao;

import org.springframework.data.repository.CrudRepository;

import com.pigeoninfo.entity.PostImg;

public interface PostImgDao extends CrudRepository<PostImg, Long> {
	public PostImg findByHashedFilename(String hashedFilename);
}
