package com.lallantop.Service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lallantop.Service.CartItemService;
import com.lallantop.domain.CartItem;
import com.lallantop.domain.ShoppingCart;
import com.lallantop.domain.User;
import com.lallantop.domain.men;
import com.lallantop.domain.menToCartItem;
import com.lallantop.repository.CartItemRepository;

@Service
public class cartItemServiceimpl implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private com.lallantop.repository.menToCartItemRepository menToCartItemRepository;

	@Override
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart){
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}
 
	@Override
	public CartItem updateCartItem(CartItem cartItem)
	{
		BigDecimal bigDecimal = new BigDecimal(cartItem.getMen().getOurPrice()).multiply(new BigDecimal(cartItem.getQty()));
	
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		cartItem.setSubtotal(bigDecimal);
		
		cartItemRepository.save(cartItem);
		
		return cartItem;
	}
	
	@Override
	public CartItem addmenToCartItem(men men,User user,int qty,String size)
	{
		List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());
	    
		for(CartItem cartItem : cartItemList)
		{ 
			if(men.getId() == cartItem.getMen().getId())
			{
				if(cartItem.getSize().equals(size)) {
				cartItem.setQty(cartItem.getQty() + qty);
				cartItem.setSubtotal(new BigDecimal(men.getOurPrice()).multiply(new BigDecimal(qty)));
				cartItemRepository.save(cartItem);
				return cartItem;
				}
			}
		}
		
		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setMen(men);
		
		cartItem.setQty(qty);
		cartItem.setSize(size);
		cartItem = cartItemRepository.save(cartItem);
		
		menToCartItem menToCartItem = new menToCartItem();
		menToCartItem.setMen(men);
		menToCartItem.setCartItem(cartItem);
		menToCartItemRepository.save(menToCartItem);
		
		return cartItem;
	}
	
	@Override
	public CartItem findOne(Long id)
	{
	   Optional<CartItem> ci = cartItemRepository.findById(id);
	   return ci.get();
	}
	
	@Override
	public void deleteById(Long id)
	{
		cartItemRepository.deleteById(id);
	}

	@Override
	public CartItem save(CartItem cartitem)
	{
		return cartItemRepository.save(cartitem);
	}
}
