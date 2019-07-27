package com.lallantop.Service;

import java.util.Set;

import com.lallantop.domain.User;
import com.lallantop.domain.UserBilling;
import com.lallantop.domain.UserPayments;
import com.lallantop.domain.UserShipping;
import com.lallantop.domain.Security.PasswordResetToken;
import com.lallantop.domain.Security.UserRole;

public interface UserService {
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
 
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	User createUser(User user , Set<UserRole> userRoles);
	
	void save(User user);
	
	void delete(User user);
	
	void updateUserBilling(UserBilling userBilling,UserPayments userPayment,User user);

	void setUserDefaultPayment(Long defaultPaymentId, User user);
	
	void setUserDefaultShipping(Long defaultPaymentId,User user);

    void updateUserShipping(UserShipping userShipping,User user);
}
