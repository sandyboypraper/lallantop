package com.adminportal.Service;

import java.util.Set;

import com.adminportal.domain.User;
import com.adminportal.domain.Security.UserRole;


public interface UserService {
	
	User createUser(User user , Set<UserRole> userRoles);
	
	void save(User user);
	
}
