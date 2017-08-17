package com.neo.sevice.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.neo.dao.SysRoleDao;
import com.neo.entity.SysRole;
import com.neo.sevice.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	private SysRoleDao sysRoleDao;
	
	@Override
	public SysRole findById(Integer id) {
		return sysRoleDao.findById(id);
	}

}
