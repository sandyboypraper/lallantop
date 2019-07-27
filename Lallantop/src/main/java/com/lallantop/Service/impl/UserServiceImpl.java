package com.lallantop.Service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lallantop.Service.UserService;
import com.lallantop.domain.ShoppingCart;
import com.lallantop.domain.User;
import com.lallantop.domain.UserBilling;
import com.lallantop.domain.UserPayments;
import com.lallantop.domain.UserShipping;
import com.lallantop.domain.Security.PasswordResetToken;
import com.lallantop.domain.Security.UserRole;
import com.lallantop.repository.PasswordResetTokenRepository;
import com.lallantop.repository.RoleRepository;
import com.lallantop.repository.UserPaymentRepository;
import com.lallantop.repository.UserRepository;
import com.lallantop.repository.userShippingRepository;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserPaymentRepository userPaymentRepository;
	
	@Autowired
	private userShippingRepository usershiprepo;
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordResetTokenRepository.findByToken(token);
	}
	
	@Override
	public void createPasswordResetTokenForUser(final User user, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(myToken);
	}

	@Override
	public User findByUsername(String username)
	{
		return userRepository.findByUsername(username);
	}
	
	@Override
	public User findByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}
	
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
			
			ShoppingCart shoppingcart = new ShoppingCart();
			shoppingcart.setUser(user);
			user.setShoppingCart(shoppingcart);
			
			user.setUserShippingList(new ArrayList<UserShipping>());
			user.setUserPaymentlist(new ArrayList<UserPayments>());
			
			localUser = userRepository.save(user);
		}
		
		return localUser;
	}

	@Override
	public void save(User user)
	{
		userRepository.save(user);
	}
	
	@Override
	public void delete(User user)
	{
		userRepository.delete(user);
	}
	
	@Override
	public void updateUserBilling(UserBilling userBilling,UserPayments userPayment,User user)
	{
		userPayment.setUser(user);
		userPayment.setUserBilling(userBilling);
	    userPayment.setDefaultPayment(true);
	    userBilling.setUserPayment(userPayment);
	    user.getUserPaymentlist().add(userPayment);
	    save(user);
	}
	
	@Override
	public void setUserDefaultPayment(Long defaultPaymentId,User user)
	{
		List<UserPayments> userpaymentlist = (List<UserPayments>) userPaymentRepository.findAll();
	
		for(UserPayments userPayment : userpaymentlist) {
			if(userPayment.getId() == defaultPaymentId)
			{
				userPayment.setDefaultPayment(true);
				userPaymentRepository.save(userPayment);
			}
			else
			{
				userPayment.setDefaultPayment(false);
				userPaymentRepository.save(userPayment);
			}
		}
	}
	
	
	@Override
	public void setUserDefaultShipping(Long defaultPaymentId,User user)
	{
		List<UserShipping> usershippinglist = (List<UserShipping>) usershiprepo.findAll();
	
		for(UserShipping usershipping : usershippinglist) {
			if(usershipping.getId() == defaultPaymentId)
			{
				usershipping.setUserShippingdefault(true);
				usershiprepo.save(usershipping);
			}
			else
			{
				usershipping.setUserShippingdefault(false);
				usershiprepo.save(usershipping);
			}
		}
	}
	
	
	@Override
	public void updateUserShipping(UserShipping userShipping,User user)
	{
		userShipping.setUser(user);
		userShipping.setUserShippingdefault(true);
		user.getUserShippingList().add(userShipping);
		save(user);
	}
	
}
