package com.lallantop.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lallantop.Service.UserPaymentService;
import com.lallantop.Service.UserService;
import com.lallantop.Service.UserShippingService;
import com.lallantop.Service.menService;
import com.lallantop.Service.impl.UserSecurityService;
import com.lallantop.domain.User;
import com.lallantop.domain.UserBilling;
import com.lallantop.domain.UserPayments;
import com.lallantop.domain.UserShipping;
import com.lallantop.domain.Security.PasswordResetToken;
import com.lallantop.domain.Security.Role;
import com.lallantop.domain.Security.UserRole;
import com.lallantop.utility.MailConstructor;
import com.lallantop.utility.SecurityUtility;
import com.lallantop.utility.indiaConstants;
import com.lallantop.domain.men;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailConstructor mailConstructor;
	
	@Autowired
	private menService menservice;
	
	@Autowired
	private UserPaymentService userpaymentservice;
	
	@Autowired
	private UserShippingService usershippingservice;
	
	@RequestMapping(value = "/")
	public String hc()
	{
		return "index";
	}
	
	@RequestMapping(value = "/login")
	public String hcd(Model model)
	{
		model.addAttribute("openlogin" , true);
		return "index";
	}
	
	@RequestMapping(value = "/myaccount")
	public String myac(Model model , Principal principal) {
		User user = userService.findByUsername(principal.getName());
		
		model.addAttribute("user",user);
		model.addAttribute("userPaymentList",user.getUserPaymentlist());
		model.addAttribute("userShippingList",user.getUserShippingList());
//		model.addAttribute("orderList",user.getOrderList());
		
		model.addAttribute("classactiveOrders",true);
		
		return "myaccount";
	}
	
	
	
	
	
	// Listofcreditcards
	 
	@RequestMapping("/listofCreditCards")
	public String listOfCreditCards(
			Model model, Principal principal,HttpServletRequest request
			)
	{
		User user = userService.findByUsername(principal.getName());	
 		model.addAttribute("user",user);
 		model.addAttribute("userPaymentList",user.getUserPaymentlist());
       model.addAttribute("classactivebilling",true);
       model.addAttribute("listofcreditcard",true);
		  
       List<UserPayments> List = user.getUserPaymentlist();
 		   
		   if(List.isEmpty())
				model.addAttribute("emptyshippinglist",true);
		   else
			  model.addAttribute("nonemptyshippinglist",true);
		   
			
       
       return "myaccount";
	}
	
	
	
	
	
	
	
	
	@RequestMapping("/listofShippingAdresses")
	public String listOfShippingAddresses(
			Model model, Principal principal,HttpServletRequest request
			)
	{
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user",user);
		model.addAttribute("userShippingList",user.getUserShippingList());
//		model.addAttribute("orderlist",user.orderList());
		
		model.addAttribute("classactiveshipping",true);
		model.addAttribute("listofShippingAddresses",true);
		
		 List<UserShipping> List = user.getUserShippingList();
		   
		   if(List.isEmpty())
				model.addAttribute("emptyshippinglist1",true);
		   else
			  model.addAttribute("nonemptyshippinglist1",true);
		
		return "myaccount";
	}
	
	
	
	
	
	
	
	
	
	
 	@RequestMapping("/addNewCreditCard")
 	public String addnewcreditcard(
 			Model model, Principal principal
 			)
 	{
 		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user",user);
		
		model.addAttribute("classactivebilling",true);
		model.addAttribute("addcreditcard",true);
		
		UserBilling userBilling = new UserBilling();
		UserPayments userpayment = new UserPayments();
		
		model.addAttribute("userBilling" , userBilling);
		model.addAttribute("userPayment",userpayment);
		
		List<String> stateList = new ArrayList<>();
	    String[] arr = indiaConstants.STATES;
	    
	    for(int i=0;i<arr.length;i++)
	    {
	    	stateList.add(arr[i]);
	    }
	    
	    Collections.sort(stateList);
	    
	    model.addAttribute("stateList",stateList);
	    
		model.addAttribute("userPaymentList",user.getUserPaymentlist());
		
		model.addAttribute("showcreditcard",true);
//		model.addAttribute("orderlist",user.orderList());
 		
		return "myaccount";
 	}
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	@RequestMapping("/addNewShippingAddress")
 	public String addnewShippingAdderess(
 			Model model, Principal principal
 			)
 	{
 		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user",user);
		
		model.addAttribute("classactiveshipping",true);
		model.addAttribute("addNewShippingAddress",true);
		
        UserShipping usershipping = new UserShipping();
        
        model.addAttribute("userShipping",usershipping);
		
		List<String> stateList = new ArrayList<>();
	    String[] arr = indiaConstants.STATES;
	    
	    for(int i=0;i<arr.length;i++)
	    {
	    	stateList.add(arr[i]);
	    }
	    
	    Collections.sort(stateList);
	    
	    model.addAttribute("stateList",stateList);

		model.addAttribute("userShippingList",user.getUserShippingList());
//		model.addAttribute("orderlist",user.orderList());
		
 		
		return "myaccount";
 	}
 	
 	
 	
 	
 	
 	
 	
 	
 	

 	
 	
 	@RequestMapping(value = "/addNewShippingAddress" ,method = RequestMethod.POST)
 	public String addnewShippingAdderesspost(
 			@ModelAttribute("userShipping") UserShipping userShipping,
// 			@RequestParam("id") Long userShippingid,
 			Model model, Principal principal
 			)
 	{
 		User user = userService.findByUsername(principal.getName());
// 		if(userShippingid!=null)
// 		{
// 			UserShipping uship = usershipingservice.findById(userShippingid);
// 			
// 			if(uship.getUser().getId()!=user.getId())
// 				return "badRequestPage";
// 			
// 			Long ub = usershipingservice.findById(userShippingid).getUserBilling().getId();
// 			userShipping.setId(ub);
// 		}
		model.addAttribute("user",user);		
 		userService.updateUserShipping(userShipping,user);
       
		return "redirect:/listofShippingAdresses";
 	}
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	@RequestMapping(value="/SetDefaultPayment" , method = RequestMethod.POST)
 	public String setDefaultShipping(
 			@ModelAttribute("defaultuserpaymentid") Long defaultShippingId,Principal principal, Model model
 			)
 	{
 		User user = userService.findByUsername(principal.getName());
		userService.setUserDefaultPayment(defaultShippingId,user);
 	    return "redirect:/listofCreditCards";
 	}
	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	@RequestMapping(value="/SetDefaultShippingAddress" , method = RequestMethod.POST)
 	public String setDefault(
 			@ModelAttribute("SetDefaultShippingAddressId") Long defaultPaymentId,Principal principal, Model model
 			)
 	{
 		User user = userService.findByUsername(principal.getName());
		userService.setUserDefaultShipping(defaultPaymentId,user);
		return "redirect:/listofShippingAdresses";
 	}
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	@RequestMapping(value = "/addNewCreditCard" , method = RequestMethod.POST)
 	public String addNewCreditCard(
 			@ModelAttribute("userPayment") UserPayments userPayment,
 			@ModelAttribute("userBilling") UserBilling userBilling,
 			@RequestParam("id") Long userpayid,
 			Model model, Principal principal
 			)
 	{
 		User user = userService.findByUsername(principal.getName());
 		if(userpayid!=null)
 		{
 			UserPayments upay = userpaymentservice.findById(userpayid);
 			
 			if(upay.getUser().getId()!=user.getId())
 				return "badRequestPage";
 			
 			Long ub = userpaymentservice.findById(userpayid).getUserBilling().getId();
 		    userBilling.setId(ub);
 		}
		model.addAttribute("user",user);		
 		userService.updateUserBilling(userBilling,userPayment,user);
       
       return "redirect:/listofCreditCards";
 	}
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	@RequestMapping(value="/updateCardDetails" )
 	public String updateCreditCard(
 			@RequestParam("id") Long creditCardId, Principal principal, Model model
 			) {
 		
 		User user = userService.findByUsername(principal.getName());
 		UserPayments userPayment = userpaymentservice.findById(creditCardId);
 		
 		if(user.getId() != userPayment.getUser().getId()) {
 			return "badRequestPage";
 		}
 		else
 		{
 			model.addAttribute("user",user);
 			UserBilling userBilling = userPayment.getUserBilling();
 			model.addAttribute("userPayment",userPayment);
 			model.addAttribute("userBilling",userBilling);
 			
 			List<String> stateList = new ArrayList<>();
 		    String[] arr = indiaConstants.STATES;
 		    
 		    for(int i=0;i<arr.length;i++)
 		    {
 		    	stateList.add(arr[i]);
 		    }
 		    
 		    Collections.sort(stateList);
 		    
 		    model.addAttribute("stateList",stateList);
 		   model.addAttribute("classactivebilling",true);
 		   model.addAttribute("showcreditcard",true);
 			model.addAttribute("addcreditcard",true);
 			
 			return "myaccount";
 		}
 		
 		
 		
 	}
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	

 	
 	
 	@RequestMapping(value="/updateShipping" )
 	public String updateShippingdetails(
 			@RequestParam("id") Long Shippingid, Principal principal, Model model
 			) {
 		
 		User user = userService.findByUsername(principal.getName());
 		UserShipping usershipping = usershippingservice.findById(Shippingid);
 		
 		if(user.getId() != usershipping.getUser().getId()) {
 			return "badRequestPage";
 		}
 		else
 		{
 			model.addAttribute("user",user);
 			model.addAttribute("userShipping",usershipping);
 			
 			List<String> stateList = new ArrayList<>();
 		    String[] arr = indiaConstants.STATES;
 		    
 		    for(int i=0;i<arr.length;i++)
 		    {
 		    	stateList.add(arr[i]);
 		    }
 		    
 		    Collections.sort(stateList);
 		    
 		    model.addAttribute("stateList",stateList);
 		   model.addAttribute("classactiveshipping",true);
 			model.addAttribute("addNewShippingAddress",true);
 			
 			return "myaccount";
 		}
 		
 		
 		
 	}
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	@RequestMapping("/removeCardDetails")
     public String removeCreditCard(
    		 @ModelAttribute("id") Long creditCardId, Principal principal, Model model
    		 )
     {
 		User user = userService.findByUsername(principal.getName());
 		UserPayments userPayment = userpaymentservice.findById(creditCardId);
 		
 		if(user.getId() != userPayment.getUser().getId()) {
 			return "badRequestPage";
 		}
 		else
 		{
 			model.addAttribute("user",user);
 			userpaymentservice.removeById(creditCardId);			
 			return "redirect:/listofCreditCards";
 		}
 		
 		
 		
     }
 	
 	
 	
 	
 	
 	
 	
 	

 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	@RequestMapping("/removeShipping")
    public String removeShipping(
   		 @ModelAttribute("id") Long Shippingid, Principal principal, Model model
   		 )
    {
		User user = userService.findByUsername(principal.getName());
		UserShipping usershipping = usershippingservice.findById(Shippingid);
		
		if(user.getId() != usershipping.getUser().getId()) {
			return "badRequestPage";
		}
		else
		{
			model.addAttribute("user",user);
			usershippingservice.removeById(Shippingid);			
			return "redirect:/listofShippingAdresses";
		}
		
		
		
    }
	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
	@RequestMapping(value = "/homepage")
	public String hcc(Model model)
	{	
		model.addAttribute("openmodal",true);
		return "index";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/men")
	public String men(Model model)
	{
		List<men> itemlist = menservice.findByBaapCat("Men");
		model.addAttribute("noitems",false);
		if(itemlist.isEmpty())
		{
			model.addAttribute("noitems",true);
		}
		model.addAttribute("itemlist",itemlist);
		return "menview";
	}
	
	
	@RequestMapping(value = "/mencat")
	public String mencat(Model model,
			@RequestParam("cat") String cat
			)
	{
		List<men> itemlistt = menservice.findByBaapCat("Men");
		
		System.out.println(cat);
		
		List<men> itemlist = new ArrayList<>();
		
		for(men item : itemlistt)
		{
			if(item.getCategory().equals(cat))
			{
				itemlist.add(item);
			}
		}
		
		model.addAttribute("noitems",false);
		if(itemlist.isEmpty())
		{
			model.addAttribute("noitems",true);
		}
		model.addAttribute("itemlist",itemlist);
		return "menview";
	}
	
	
	@RequestMapping(value = "/women")
	public String women(Model model)
	{
		List<men> itemlist = menservice.findByBaapCat("Women");
		model.addAttribute("noitems",false);
		if(itemlist.isEmpty())
		{
			model.addAttribute("noitems",true);
		}
		model.addAttribute("itemlist",itemlist);
		return "menview";
	}
	
	
	@RequestMapping(value = "/womencat")
	public String womencat(Model model,
			@RequestParam("cat") String cat
			)
	{
		List<men> itemlistt = menservice.findByBaapCat("Women");
		
		List<men> itemlist = new ArrayList<>();
		
		for(men item : itemlistt)
		{
			if(item.getCategory().equals(cat))
			{
				itemlist.add(item);
			}
		}
		
		model.addAttribute("noitems",false);
		if(itemlist.isEmpty())
		{
			model.addAttribute("noitems",true);
		}
		model.addAttribute("itemlist",itemlist);
		return "menview";
	}
	
	
	
	
	@RequestMapping(value = "/accessories")
	public String accessories(Model model)
	{
		List<men> itemlist = menservice.findByBaapCat("Acc");
		model.addAttribute("noitems",false);
		if(itemlist.isEmpty())
		{
			model.addAttribute("noitems",true);
		}
		model.addAttribute("itemlist",itemlist);
		return "menview";
	}
	
	
	@RequestMapping(value = "/accessoriescat")
	public String accessoriescat(Model model,
			@RequestParam("cat") String cat
			)
	{
		List<men> itemlistt = menservice.findByBaapCat("Acc");
		
		List<men> itemlist = new ArrayList<>();
		
		for(men item : itemlistt)
		{
			if(item.getCategory().equals(cat))
			{
				itemlist.add(item);
			}
		}
		
		model.addAttribute("noitems",false);
		if(itemlist.isEmpty())
		{
			model.addAttribute("noitems",true);
		}
		model.addAttribute("itemlist",itemlist);
		return "menview";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/menDetail")
	public String mendetails(
			@PathParam("id") Long id,
			Model model,
			Principal principal
			)
	{
		if(principal != null)
		{
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user",user);
		}
		
		men men = menservice.findOne(id);
		
		model.addAttribute("menitem",men);
		
		int no = men.getInStockNumber();
		
		if(no>10)
			no = 10;
		
		//qtylist
		Set<Integer> qtyList = new HashSet<>();
		
		for(int i = 1;i<=no;i++)
			qtyList.add(i);
		
		String sizel = men.getSizes();
		
		StringTokenizer strtkn = new StringTokenizer(sizel,"/");
		
		//sizes
		Set<String> sizes = new HashSet<>();
		
		while(strtkn.hasMoreTokens())
			sizes.add(strtkn.nextToken());
		
		model.addAttribute("qntylist",qtyList);
		model.addAttribute("sizelist",sizes);
		
		return "menitemdetails";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/newuser" , method = RequestMethod.POST)
	public String newUserPost(
			HttpServletRequest request,
			@ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username,
			Model model
			) throws Exception {
		
		model.addAttribute("classActiveNewAccount",true);
		model.addAttribute("email",userEmail);
		model.addAttribute("username",username);
		
		if(userService.findByUsername(username) != null) {
			model.addAttribute("usernameExists",true);
			model.addAttribute("openmodal",true);
			model.addAttribute("classActiveSignup",true);
			return "index";
		}
		
		if(userService.findByEmail(userEmail) != null) {
			model.addAttribute("emailExists",true);
			model.addAttribute("openmodal",true);
			model.addAttribute("classActiveSignup",true);
			return "index";
		}
		
		User user = new User();
		user.setEmail(userEmail);
		user.setUsername(username);
	
		String password = SecurityUtility.randomPassword();
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		
		userService.save(user);
		
	   String token = UUID.randomUUID().toString();
	   userService.createPasswordResetTokenForUser(user, token);
	   
	   
	   String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	   
		
	   SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl,request.getLocale(),token,user,password); 
		
	   
	   mailSender.send(email);
		
		
	   model.addAttribute("emailsent","true");
	   
	   return "index";
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/forgotpassword")
    public String forgotpass(
    		HttpServletRequest request,
			@ModelAttribute("email") String userEmail,
			Model model
    		) {
		
		
		User user = userService.findByEmail(userEmail);
		if(user == null) {
			model.addAttribute("openmodal",true);
			model.addAttribute("classActiveForgotPassword",true);
			model.addAttribute("emailNotExists",true);
			return "index";
		}
		
		String password = SecurityUtility.randomPassword();
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		
		userService.save(user);
			
	   String token = UUID.randomUUID().toString();
	   userService.createPasswordResetTokenForUser(user, token);
	   
	   
	   String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	   
		
	   SimpleMailMessage email = mailConstructor.constructResetTokenEmail1(appUrl,request.getLocale(),token,user,password); 
		
	   
	   mailSender.send(email);
		
		
	   model.addAttribute("emailsent","true");
	   
	   return "index";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/newuser")
	public String newUser(
			Locale locale,
			@RequestParam("token") String token,
			Model model)
	{
		PasswordResetToken passToken =  userService.getPasswordResetToken(token);
		
		if(passToken == null)
		{
			String message = "Invalid Token";
			model.addAttribute("message",message);
			return "redirect:/badRequest";
		}
		
		User user = passToken.getUser();
		String username = user.getUsername();
		
		UserDetails userDetails = userSecurityService.loadUserByUsername(username);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());  
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		 
		model.addAttribute("classactiveedit",true);
		
		model.addAttribute("user",user);
		
		return "myaccount";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/editdetails" ,  method = RequestMethod.POST)
	public String edit(
			@ModelAttribute("fname") String fname,
			@ModelAttribute("lname") String lname,
			@ModelAttribute("username") String username,
			@ModelAttribute("cpass") String cpass,
			@ModelAttribute("email") String email,
			@ModelAttribute("pass") String pass,
			@ModelAttribute("cnpass") String cnpass,
			Principal principal,
			Model model
			)
	{
		System.out.println(pass);
		User user = userService.findByEmail(email);
		
		User mainuser = userService.findByUsername(principal.getName());
		if(user==null)
		{
			model.addAttribute("usernotexist",true);
			model.addAttribute("classactiveedit",true);
			model.addAttribute("user",mainuser);
			return "myaccount";
		}
		
		if(!pass.equals(cnpass))
		{
			model.addAttribute("bothpassnotequal",true);
			model.addAttribute("classactiveedit",true);
			model.addAttribute("user",user);
			return "myaccount";
		}
		
		if(cpass.equals(user.getPassword()))
		{
			model.addAttribute("currentpassnotmatched",true);
			model.addAttribute("classactiveedit",true);
			model.addAttribute("user",user);
			return "myaccount";
		}
		
		userService.delete(user);
		
//		User u = new User()
		
		user.setEmail(email);
		user.setFirstName(fname);
		user.setLastName(lname);
		user.setUsername(username);
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(pass);
		user.setPassword(encryptedPassword);
		
		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user,role));
		userService.createUser(user, userRoles);
		model.addAttribute("user",user);
		model.addAttribute("edited",true);
		return "myaccount";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
