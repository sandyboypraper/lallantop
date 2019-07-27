package com.adminportal.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.adminportal.Service.MenService;
import com.adminportal.domain.men;


@Controller
public class MenController {
		
		public static String uploadedpath = System.getProperty("user.dir");
	
		@Autowired
		private MenService menser;
	
		@RequestMapping(value = "/addMen" , method=RequestMethod.GET)
		public String addMen(Model model)
		{
			men men1 = new men();
//			men1.setTitle("ye hui na baat");
			Set<String> c = new HashSet<>();
			
			
			c.add("Plain T Shirts");
			c.add("Graphic T Shirts");
			c.add("Full Sleeves T Shirts");
			c.add("Shirts");
			c.add("Jackets");
			c.add("Joggers");
			c.add("Shorts");
			c.add("Bags");
			
			model.addAttribute("catlist" , c);
			model.addAttribute("men" , men1);
			return "Men";
		}
		

		@RequestMapping(value = "/addWomen" , method=RequestMethod.GET)
		public String addWomen(Model model)
		{
			men men1 = new men();
//			men1.setTitle("ye hui na baat");
			Set<String> c = new HashSet<>();
			
			
			c.add("Plain T Shirts");
			c.add("Graphic T Shirts");
			c.add("Full Sleeves T Shirts");
			c.add("Shirts");
			c.add("Joggers");
			c.add("Shorts");
			c.add("Bags");
			
			model.addAttribute("catlist" , c);
			model.addAttribute("men" , men1);
			return "Men";
		}
		

		@RequestMapping(value = "/addAcc" , method=RequestMethod.GET)
		public String addAcc(Model model)
		{
			men men1 = new men();
//			men1.setTitle("ye hui na baat");
			Set<String> c = new HashSet<>();
			
			
			c.add("Xiomi");
			c.add("Samsung");
			c.add("Motorola");
			c.add("Apple");
			c.add("Nokia");
			c.add("Oppo");
			
			model.addAttribute("catlist" , c);
			model.addAttribute("men" , men1);
			return "Men";
		}
		
		@RequestMapping(value = "/addMen" , method=RequestMethod.POST)
		public String addMenP(
				@ModelAttribute("title") String title
				,@ModelAttribute("description") String des
				,@ModelAttribute("listprice") double listp
				,@ModelAttribute("ourprice") double ourp
				,@ModelAttribute("instocknumber") int instocknumber
				,@ModelAttribute("isbn") int isbn
				,@ModelAttribute("colorcode") String colorcode
				,@RequestParam("menimage") MultipartFile image
				,@ModelAttribute("cat") String SubCat
				,@ModelAttribute("sizes") String sizes
				,@RequestParam("Baapcat") String baapcat
				, HttpServletRequest request
				)
		{
			
			men men1 = new men();
			men1.setTitle(title);
			men1.setDescription(des);
			men1.setListPrice(listp);
			men1.setOurPrice(ourp);
			men1.setInStockNumber(instocknumber);
			men1.setIsbn(isbn);
			men1.setColorcode(colorcode);
			men1.setImage(image);
			men1.setCategory(SubCat);
			men1.setSizes(sizes);
			
			if(baapcat.equals("men"))
				men1.setBaapCat("Men");
			else if(baapcat.equals("women"))
				men1.setBaapCat("Women");
			else
				men1.setBaapCat("Acc");
			
			System.out.println(image);
			
			menser.save(men1);
			System.out.println(image);
			System.out.println(image.getSize());
			MultipartFile menImage = image;
			
			try {
				byte[] bytes = menImage.getBytes();
				String name = men1.getId() + ".png";
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/men/" + name)));
				stream.write(bytes);
				stream.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return "redirect:/menList";
		}
		
		
		
		@RequestMapping("/menList")
		public String menList(Model model) {
			List<men> menList = menser.findAll();
			model.addAttribute("menList", menList);		
			return "menList";
		}
	
}
