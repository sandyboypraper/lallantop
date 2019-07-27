package com.lallantop.Service;

import com.lallantop.domain.BillingAddress;
import com.lallantop.domain.Order;
import com.lallantop.domain.Payment;
import com.lallantop.domain.ShippingAddress;
import com.lallantop.domain.ShoppingCart;
import com.lallantop.domain.User;

public interface OrderService {
	Order createOrder(ShoppingCart shoppingCart,
			ShippingAddress shippingAddress,
			Payment payment,
			User user);
	
	Order findOne(Long id);
}
