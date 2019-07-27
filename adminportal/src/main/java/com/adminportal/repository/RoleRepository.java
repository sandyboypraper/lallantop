package com.adminportal.repository;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.domain.Security.Role;

public interface RoleRepository extends CrudRepository<Role,Long> {
  Role findByname(String name);
}
