package com.lallantop.Service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lallantop.Service.UserPaymentService;
import com.lallantop.domain.UserPayments;
import com.lallantop.repository.UserPaymentRepository;

@Service
public class userPaymentServiceimpl implements UserPaymentService {

	@Autowired
	private UserPaymentRepository userpayrepo;
	
	@Override
	public UserPayments findById(Long id) {
	    Optional<UserPayments> u = userpayrepo.findById(id);
	    UserPayments uobj = u.get();
	    return uobj;
	} 
	
	@Override
	public void removeById(Long id)
	{
	   userpayrepo.deleteById(id);	
	}

	
	
}
