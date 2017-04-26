package com.niit.shoppingcart.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.dao.ProductDAO;
import com.niit.shoppingcart.dao.SupplierDAO;
import com.niit.shoppingcart.dao.UserDAO;
import com.niit.shoppingcart.domain.Category;
import com.niit.shoppingcart.domain.Product;
import com.niit.shoppingcart.domain.Supplier;
import com.niit.shoppingcart.domain.User;

@Controller
public class HomeController {
	
	private static Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private User user;
	
	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private Category category;
	
	@Autowired
	private SupplierDAO supplierDAO;

	@Autowired
	private Supplier supplier;

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private Product product;
	
	@Autowired
	private HttpSession session;

	//   http://localhost:8080/shoppingcart/
	@RequestMapping("/")
	public ModelAndView onLoad() {
		log.debug("Starting of the method onLoad");
		ModelAndView mv = new ModelAndView("/Home");
		session.setAttribute("category", category); // domain object names
		session.setAttribute("product", product);
		session.setAttribute("supplier", supplier);
		session.setAttribute("user", user);
		
		session.setAttribute("categoryList", categoryDAO.list());
		
		session.setAttribute("supplierList", supplierDAO.list());
		
		session.setAttribute("productList", productDAO.list());
		
		session.setAttribute("userList", userDAO.list());
	
		log.debug("Ending of the method onLoad");
		return mv;
	}

	@RequestMapping("/registerHere")
	public ModelAndView registerHere() {
		log.debug("Starting of the method registerHere");
		ModelAndView mv = new ModelAndView("/Home");
		mv.addObject("user", user);
		mv.addObject("isUserClickedRegisterHere", "true");
		log.debug("Ending of the method registerHere");
		return mv;
	}

	@RequestMapping("/loginHere")
	public ModelAndView loginHere() {
		log.debug("Starting of the method loginHere");
		System.out.println("loginHere");
		ModelAndView mv = new ModelAndView("/Home");
		mv.addObject("user", user);
		mv.addObject("isUserClickedLoginHere", "true");
		log.debug("Ending of the method loginHere");
		return mv;
	}
	
	@RequestMapping("/Home")
	public String reDirectToHome(@ModelAttribute("selectedProduct") final Product selectedProduct, final Model model) {
		log.debug("Starting of the method reDirectToHome");
		model.addAttribute("selectedProduct", selectedProduct);
	   
		log.debug("Ending of the method reDirectToHome");
		return "/Home";
	}
}