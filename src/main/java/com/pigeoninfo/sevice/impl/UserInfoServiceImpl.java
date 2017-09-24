package com.pigeoninfo.sevice.impl;

import org.springframework.stereotype.Service;

import com.pigeoninfo.dao.UserInfoDao;
import com.pigeoninfo.entity.UserInfo;
import com.pigeoninfo.sevice.UserInfoService;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }
    
	@Override
	public boolean saveUserInfo(UserInfo userInfo) {
		if(userInfoDao.save(userInfo) != null) {
			return true;
		} else { 
			return false;
		}
	}
}