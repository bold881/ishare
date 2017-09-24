package com.pigeoninfo.sevice;

import com.pigeoninfo.entity.UserInfo;

public interface UserInfoService {
    /**通过username查找用户信息;*/
    public UserInfo findByUsername(String username);
    
    public boolean saveUserInfo(UserInfo userInfo);
}