package com.adminportal.Service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.Service.UserService;
import com.adminportal.domain.User;
import com.adminportal.domain.Security.UserRole;
import com.adminportal.repository.RoleRepository;
import com.adminportal.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{
	     
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository ;

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	
	@Override
	public User createUser(User user , Set<UserRole> userRoles) {
		User localUser = userRepository.findByUsername(user.getUsername());
		
		if(localUser != null) {
			LOG.info("user {} already exists",user.getUsername());
		}else {
			for(UserRole ur: userRoles) {
				roleRepository.save(ur.getRole());
			}
			
			user.getUserRoles().addAll(userRoles);
			
			localUser = userRepository.save(user);
		}
		
		return localUser;
	}

	@Override
	public void save(User user)
	{
		userRepository.save(user);
	}
	
	
}
