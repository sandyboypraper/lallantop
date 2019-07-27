package com.lallantop.Service;

import com.lallantop.domain.UserPayments;

public interface UserPaymentService {

	UserPayments findById(Long id);
	
	void removeById(Long id);
	
}
