package com.lallantop.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lallantop.Service.CartItemService;
import com.lallantop.Service.ShoppingCartService;
import com.lallantop.Service.UserService;
import com.lallantop.Service.menService;
import com.lallantop.domain.CartItem;
import com.lallantop.domain.ShoppingCart;
import com.lallantop.domain.User;
import com.lallantop.domain.men;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartcontroller {

	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private menService menservice;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/cart")
	public String shoppingCart(Model model, Principal principal)
	{
		User user = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();
		
		if(shoppingCart != null)
		{
			List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);    
			
			shoppingCart =  shoppingCartService.updateShoppingCart(shoppingCart);
			
			model.addAttribute("cartItemList",cartItemList);
			model.addAttribute("shoppingCart",shoppingCart);
			
			if(cartItemList.isEmpty())
				model.addAttribute("emptyCart",true);
				else
					model.addAttribute("nonemptyCart" , true);
		}
		else
		{
			model.addAttribute("emptyCart",true);
		}
		
		
		return "ShoppingCart";
	}
	
	
	
	
	
	
	
	
	@RequestMapping("/addItem")
	public String addItem(
			@ModelAttribute("menitem") com.lallantop.domain.men men,
			@ModelAttribute("qnty") int qty,
			@ModelAttribute("size") String size,
			Model model, Principal principal
			)
	{
		User user = userService.findByUsername(principal.getName());
		men = menservice.findOne(men.getId());
		
		if(qty > men.getInStockNumber()) {
			model.addAttribute("notEnoughStock",true);
			return "forward:/menDetail?id=" + men.getId();
		}
		
		CartItem cartItem = cartItemService.addmenToCartItem(men,user,qty,size);
		model.addAttribute("addBookSuccess",true);
		
		return "forward:/menDetail?id=" + men.getId();
		
	}
	
	
	
	
	
	
	
	
	@RequestMapping("/updatecartitem")
	public String updateitem(
			@ModelAttribute("id") Long cartitemid
			)
	{
		men menitem = menservice.findOne(cartItemService.findOne(cartitemid).getMen().getId());
		
		cartItemService.deleteById(cartitemid);
		
		return "forward:/menDetail?id="+menitem.getId();
	}
	
	
	@RequestMapping("/deletecartitem")
	public String Delete(
			@ModelAttribute("id") Long cartitemid
			)
	{
		cartItemService.deleteById(cartitemid);
		return "redirect:/shoppingCart/cart";
	}
	
	
}
