package com.lallantop.Service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lallantop.Service.CartItemService;
import com.lallantop.Service.ShoppingCartService;
import com.lallantop.domain.CartItem;
import com.lallantop.domain.ShoppingCart;
import com.lallantop.repository.ShoppingCartRepository;

@Service
public class ShopingCartServiceimpl implements ShoppingCartService {
	
	@Autowired
	private CartItemService cartItemservice;
	
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	@Override
  public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart)
  {
	  BigDecimal cartTotal = new BigDecimal(0);
	  
	  List<CartItem> cartItemList = cartItemservice.findByShoppingCart(shoppingCart);
 
	  for(CartItem cartItem : cartItemList)
	  {
		  if(cartItem.getMen().getInStockNumber() - cartItem.getQty() >= 0)
		  {
			  cartItemservice.updateCartItem(cartItem);
			  cartTotal = cartTotal.add(cartItem.getSubtotal());
		  }
	  }
	  
	  shoppingCart.setGrandToptal(cartTotal);
	  
	  
	  shoppingCartRepository.save(shoppingCart);
	  
	  return shoppingCart;
  }
	
	@Override
	public void clearShoppingCart(ShoppingCart shoppingCart) {
		List<CartItem> cartItemList = cartItemservice.findByShoppingCart(shoppingCart);
		
		for (CartItem cartItem : cartItemList) {
			cartItem.setShoppingCart(null);
			cartItemservice.save(cartItem);
		}
		
		shoppingCart.setGrandToptal(new BigDecimal(0));
		
		shoppingCartRepository.save(shoppingCart);
	}
}
