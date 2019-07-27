package com.lallantop.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lallantop.domain.CartItem;
import com.lallantop.domain.ShoppingCart;

public interface CartItemRepository extends CrudRepository<CartItem, Long>{
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
