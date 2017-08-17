package com.neo.dao;

import org.springframework.data.repository.CrudRepository;

import com.neo.entity.SysRole;

public interface SysRoleDao extends CrudRepository<SysRole, Integer> {
	public SysRole findById(Integer id);
}
