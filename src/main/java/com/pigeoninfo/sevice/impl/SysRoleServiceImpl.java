package com.pigeoninfo.sevice.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pigeoninfo.dao.SysRoleDao;
import com.pigeoninfo.entity.SysRole;
import com.pigeoninfo.sevice.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	private SysRoleDao sysRoleDao;
	
	@Override
	public SysRole findById(Integer id) {
		return sysRoleDao.findById(id);
	}

}
