package com.lallantop.Service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lallantop.Service.UserShippingService;
import com.lallantop.domain.UserShipping;
import com.lallantop.repository.userShippingRepository;


@Service
public class usershippingimpl implements UserShippingService{

	@Autowired
	private userShippingRepository usershiprepo;
	
   public UserShipping findById(Long id) {
	   
	   Optional<UserShipping> us = usershiprepo.findById(id);
	   
	   return us.get();
	   
   }
	
 	public void removeById(Long id) {
 		
 		usershiprepo.deleteById(id);
 	}
	
}
