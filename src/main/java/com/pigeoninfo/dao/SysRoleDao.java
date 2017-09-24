package com.pigeoninfo.dao;

import org.springframework.data.repository.CrudRepository;

import com.pigeoninfo.entity.SysRole;

public interface SysRoleDao extends CrudRepository<SysRole, Integer> {
	public SysRole findById(Integer id);
}
