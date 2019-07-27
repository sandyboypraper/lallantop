package com.lallantop.repository;

import org.springframework.data.repository.CrudRepository;

import com.lallantop.domain.Security.Role;

public interface RoleRepository extends CrudRepository<Role,Long> {
  Role findByname(String name);
}
