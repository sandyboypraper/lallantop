package com.lallantop.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lallantop.Service.BillingAddressService;
import com.lallantop.Service.CartItemService;
import com.lallantop.Service.OrderService;
import com.lallantop.Service.PaymentService;
import com.lallantop.Service.ShippingAddressService;
import com.lallantop.Service.ShoppingCartService;
import com.lallantop.Service.UserService;
import com.lallantop.domain.BillingAddress;
import com.lallantop.domain.CartItem;
import com.lallantop.domain.Order;
import com.lallantop.domain.Payment;
import com.lallantop.domain.ShippingAddress;
import com.lallantop.domain.ShoppingCart;
import com.lallantop.domain.User;
import com.lallantop.domain.UserPayments;
import com.lallantop.domain.UserShipping;
import com.lallantop.utility.MailConstructor;
import com.lallantop.utility.indiaConstants;

@Controller
public class checkoutcontroller {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailConstructor mailconstructer;
	
	@Autowired
	private CartItemService cartitemService;
	
	 @Autowired 
	 private ShoppingCartService shoppingCartService;
	
	@Autowired
	private ShippingAddressService shippingAddressService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private BillingAddressService billingAddressService;
	
	private ShippingAddress shipadder = new ShippingAddress();
	private BillingAddress billader = new BillingAddress();
	private Payment payment = new Payment();
	
	
	@RequestMapping("/checkout")
	public String checkout(
			@RequestParam("id") Long cartId,
			@RequestParam(value = "missingRequiredField" , required = false) boolean missingRequiredField,
			Model model, Principal principal
			)
	{
		User user = userService.findByUsername(principal.getName());
		
		if(cartId != user.getShoppingCart().getId()) {
			return "vadRequestPage";
		}
		
		List<CartItem> cartItemList = cartitemService.findByShoppingCart(user.getShoppingCart());
		
		model.addAttribute("cartId",cartId);
		
		if(cartItemList.isEmpty())
		{
			model.addAttribute("emptyCart",true);
			return "forward:/shoppingCart/cart";
		}
		
		for(CartItem cartItem : cartItemList)
		{
			if(cartItem.getMen().getInStockNumber()< cartItem.getQty()) {
				model.addAttribute("notEnoughStock",true);
				return "forward:/shoppingCart/cart";
			}
		}
		
		List<UserShipping> userShippingList = user.getUserShippingList();
		
		List<UserPayments> userPaymentList = user.getUserPaymentlist();
		
		model.addAttribute("userShippingList",userShippingList);
		model.addAttribute("userPaymentList", userPaymentList);
		
		
		if(userPaymentList.isEmpty())
		{
			model.addAttribute("emptypaylist",true);
		}else {
			model.addAttribute("emptypaylist",false);
		} 
		
		
		if(userShippingList.isEmpty())
		{
			model.addAttribute("emptyshiplist",true);
		}else {
			model.addAttribute("emptyshiplist",false);
		}
		
		ShoppingCart shoppingCart = user.getShoppingCart();
		
		for(UserShipping userShipping : userShippingList) {
			if(userShipping.isUserShippingdefault()) {
				shippingAddressService.setByUserShipping(userShipping,shipadder);
			}
		}
		
		for(UserPayments userpayment : userPaymentList) {
			if(userpayment.isDefaultPayment()) {
				paymentService.setByUserPayment(userpayment,payment);
				billingAddressService.setByUserBilling(userpayment.getUserBilling(),billader);
				
			}
		}
	
		model.addAttribute("shipadder" ,shipadder);
		model.addAttribute("userPayment",payment);
		model.addAttribute("billingAddress",billader);
		model.addAttribute("cartItemList",cartItemList);
		model.addAttribute("shoppingCart",user.getShoppingCart());
		
		
		List<String> stateList = new ArrayList<>();
	    String[] arr = indiaConstants.STATES;
	    
	    for(int i=0;i<arr.length;i++)
	    {
	    	stateList.add(arr[i]);
	    }
	    
	    Collections.sort(stateList);
	    
	    model.addAttribute("stateList",stateList);
	    
	    
	    model.addAttribute("classActiveShipping",true);
	    
	    if(missingRequiredField)
	    {
	    	model.addAttribute("missingRequiredField" , true );
	    }
	   
	    model.addAttribute("showcreditcard" , true);
	    
	    return "cheackoutAddress";
	
	}
	
	@RequestMapping(value = "/checkout" , method = RequestMethod.POST)
	public String checkoutpost(
			
			@ModelAttribute("shipadder") ShippingAddress shippingAddress,
			@ModelAttribute("payment") Payment payment,
			Principal principal,
			Model model
			) {
		
		ShoppingCart shoppingCart = userService.findByUsername(principal.getName()).getShoppingCart();
		
				
	    List<CartItem> cartItemList = cartitemService.findByShoppingCart(shoppingCart);
		model.addAttribute("cartItemList", cartItemList);

		if (shippingAddress.getShippingAddressStreet1().isEmpty() || shippingAddress.getShippingAddressCity().isEmpty()
				|| shippingAddress.getShippingAddressState().isEmpty()
				|| shippingAddress.getShippingAddressName().isEmpty()
				|| shippingAddress.getShippingAddressZipcode().isEmpty() || payment.getCardNumber().isEmpty()
				|| payment.getCVC() == 0 )
			return "redirect:/checkout?id=" + shoppingCart.getId() + "&missingRequiredField=true";
		
		User user = userService.findByUsername(principal.getName());
		
		Order order = orderService.createOrder(shoppingCart, shippingAddress, payment, user);
		
		mailSender.send(mailconstructer.constructOrderConfirmationEmail(user, order, Locale.ENGLISH));
		
		shoppingCartService.clearShoppingCart(shoppingCart);
		
		LocalDate today = LocalDate.now();
		LocalDate estimatedDeliveryDate;
		
	    estimatedDeliveryDate = today.plusDays(5);

		
		model.addAttribute("estimatedDeliveryDate", estimatedDeliveryDate);
		
		
		return "orderSubmittedPage";
		
		
	}
	
	
	
}
