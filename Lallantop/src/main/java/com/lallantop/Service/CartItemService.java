package com.lallantop.Service;

import java.util.List;

import com.lallantop.domain.CartItem;
import com.lallantop.domain.ShoppingCart;
import com.lallantop.domain.User;
import com.lallantop.domain.men;

public interface CartItemService {

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	CartItem updateCartItem(CartItem cartItem);
	
 	CartItem addmenToCartItem(men men,User user,int qty,String size);
 	
 	CartItem findOne(Long id);
 	
 	void deleteById(Long id);
 	
    CartItem save(CartItem cartitem);
}
