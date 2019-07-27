package com.lallantop.Service;

import com.lallantop.domain.UserShipping;

public interface UserShippingService {

	UserShipping findById(Long id);
	
	void removeById(Long id);
	
}
