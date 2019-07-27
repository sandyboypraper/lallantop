package com.lallantop.Service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lallantop.Service.CartItemService;
import com.lallantop.domain.BillingAddress;
import com.lallantop.domain.CartItem;
import com.lallantop.domain.Order;
import com.lallantop.domain.Payment;
import com.lallantop.domain.ShippingAddress;
import com.lallantop.domain.ShoppingCart;
import com.lallantop.domain.User;
import com.lallantop.domain.men;
import com.lallantop.repository.OrderRepository;

@Service
public class OrderServiceImpl implements com.lallantop.Service.OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Override
	public synchronized Order createOrder(ShoppingCart shoppingCart,
			ShippingAddress shippingAddress,
			Payment payment,
			User user) {
		Order order = new Order();
		order.setBillingAddress(new BillingAddress());
		order.setOrderStatus("created");
		order.setPayment(payment);
		order.setShippingAddress(shippingAddress);
		order.setShippingMethod("default");
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		for(CartItem cartItem : cartItemList) {
			men men = cartItem.getMen();
			cartItem.setOrder(order);
			men.setInStockNumber(men.getInStockNumber() - cartItem.getQty());
		}
		
		order.setCartItemList(cartItemList);
		order.setOrderDate(Calendar.getInstance().getTime());
		order.setOrderTotal(shoppingCart.getGrandToptal());
		shippingAddress.setOrder(order);
		payment.setOrder(order);
		order.setUser(user);
		order = orderRepository.save(order);
		
		return order;
	}
	
	public Order findOne(Long id) {
		Optional<Order> od =  orderRepository.findById(id);
		return od.get();
	}

}
