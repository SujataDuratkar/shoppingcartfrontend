package com.niit.shoppingcart.controller;

import java.util.List;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.dao.MyCartDAO;
import com.niit.shoppingcart.dao.ProductDAO;
import com.niit.shoppingcart.dao.SupplierDAO;
import com.niit.shoppingcart.dao.UserDAO;
import com.niit.shoppingcart.domain.Category;
import com.niit.shoppingcart.domain.MyCart;
import com.niit.shoppingcart.domain.Product;
import com.niit.shoppingcart.domain.Supplier;
import com.niit.shoppingcart.domain.User;

@Controller
public class UserController {

	public static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private User user;

	@Autowired
	private MyCartDAO mycartDAO;

	@Autowired
	private MyCart mycart;

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

	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public ModelAndView validate(@RequestParam(value = "username") String userID,
			@RequestParam(value = "password") String password) {
		log.debug("Starting of the method validate");

		ModelAndView mv = new ModelAndView("/Home");
		//user =userDAO.validate(userID, password);  
		
		if (user != null) {
			log.debug("Valid Credentials");
			
			session.setAttribute("loggedInUser", user.getName());
			session.setAttribute("loggedInUserID", user.getId());

			session.setAttribute("user", user); //
			session.setAttribute("supplier", supplier);
			session.setAttribute("supplierList", supplierDAO.list());
			
			session.setAttribute("productList", productDAO.list());
			session.setAttribute("product", product);

			session.setAttribute("category", category);
			session.setAttribute("categoryList", categoryDAO.list());

			if (user.getRole().equals("ROLE_ADMIN")) {
				log.debug("Logged in as Admin");
				mv.addObject("isAdmin", "true");
				

			} else {
				log.debug("Logged in as User");
				mv.addObject("isAdmin", "false");
				
				mv.addObject("myCart", mycart);
				
				List<MyCart> cartList = mycartDAO.list(userID);
				mv.addObject("cartList", cartList);
				mv.addObject("cartSize", cartList.size());
			}

		} else {
			log.debug("Invalid Credentials");

			mv.addObject("invalidCredentials", "true");
			mv.addObject("errorMessage", "Invalid Credentials");
			
			// ${errorMessage}

		}
		log.debug("Ending of the method validate");
		return mv;
	}

	@RequestMapping("/logout")
	public ModelAndView logout() {
		log.debug("Starting of the method logout");
		ModelAndView mv = new ModelAndView("/Home");
		session.invalidate(); // will remove the attributes which are added
								// session
		session.setAttribute("category", category);
		session.setAttribute("categoryList", categoryDAO.list());

		mv.addObject("logoutMessage", "You successfully logged out");
		mv.addObject("loggedOut", "true");
		
	    log.debug("Ending of the method logout");
		return mv;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerUser(@ModelAttribute User user) {
		log.debug("Starting of the method registerUser");
		ModelAndView mv = new ModelAndView("Home");
		if (userDAO.getUser(user.getId()) == null) {
			user.setRole("ROLE_USER"); // all the users are end users by default
			userDAO.update(user);
			log.debug("You are successfully register");
			mv.addObject("successMessage", "You are successfully registered");
		} else {
			log.debug("User exist with this id");
			mv.addObject("errorMessage", "User exist with this id");
		}
		log.debug("Ending of the method registerUser");
		return mv;
	}

	//authentication-failure-forward-url="/loginError"
	@RequestMapping(value = "/loginError", method = RequestMethod.GET)
	public String loginError(Model model) {
		log.debug("Starting of the method loginError");
		model.addAttribute("errorMessage", "Invalid Credentials.  Please try again.");
		log.debug("Ending of the method loginError");
		return "Home";
	}

	//<security:access-denied-handler error-page="/accessDenied" />
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public String accessDenied(Model model) {
		log.debug("Starting of the method accessDenied");
		model.addAttribute("errorMessage", "You are not authorized to access this page");
		log.debug("Ending of the method accessDenied");
		return "Home";
	}
	
	// <security:form-login authentication-success-forward-url="/success"/>
		/*
		 * @RequestMapping("/success") public ModelAndView successForwardURL() {
		 * log.debug("Starting of the method successForwardURL"); ModelAndView mv =
		 * new ModelAndView("home");
		 * 
		 * Authentication auth =
		 * SecurityContextHolder.getContext().getAuthentication(); String
		 * loggedInUserid = auth.getName(); Collection<GrantedAuthority> authorities
		 * = (Collection<GrantedAuthority>) auth.getAuthorities(); if
		 * (authorities.contains("ROLE_ADMIN")) { mv.addObject("isAdmin", "true");
		 * log.debug("You are Admin"); } else { log.debug("You are not  Admin");
		 * mv.addObject("isAdmin", "false"); // myCart = cartDAO.list(userID);
		 * mv.addObject("myCart", myCart); // Fetch the myCart list based on user ID
		 * List<MyCart> cartList = cartDAO.list(loggedInUserid);
		 * mv.addObject("cartList", cartList); mv.addObject("cartSize",
		 * cartList.size()); }
		 * 
		 * log.debug("Ending of the method successForwardURL"); return mv;
		 * 
		 * }
		 */
	/**
	 * If we are using spring-security, this method is going to execute after login
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
		@RequestMapping(value = "validate", method = RequestMethod.GET)
		public ModelAndView validate(HttpServletRequest request, HttpServletResponse response) throws Exception {
			log.debug("starting of the method validate");
			ModelAndView mv = new ModelAndView("home");
			// session = request.getSession(true);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userID = auth.getName();
			session.setAttribute("loggedInUser", userID);

			if (request.isUserInRole("ROLE_ADMIN")) {

				session.setAttribute("isAdmin", true);

			} else {

				session.setAttribute("isAdmin", false);
				
				mv.addObject("myCart", mycart);
				// Fetch the myCart list based on user ID
				List<MyCart> cartList = mycartDAO.list(userID);
				mv.addObject("cartList", cartList);
				mv.addObject("cartSize", cartList.size());
				mv.addObject("totalAmount", mycartDAO.getTotalAmount(userID));
			}
			log.debug("Ending of the method validate");
			return mv;
		}
			
		@RequestMapping("/secure_logout")
		public ModelAndView secureLogout()
		{
			//what you attach to session at the time login need to remove.
			
			session.invalidate();
			
			ModelAndView mv = new ModelAndView("Home");
			
			//After logout also user should able to browse the categories and products
			//as we invalidated the session, need to load these data again.
			
			session.setAttribute("category", category); // domain object names
			session.setAttribute("product", product);
			session.setAttribute("supplier", supplier);
			
			
			session.setAttribute("categoryList", categoryDAO.list());
			
			session.setAttribute("supplierList", supplierDAO.list());
			
			session.setAttribute("productList", productDAO.list());
			
			//OR Simply remove only one attribute from the session.
			
			//session.removeAttribute("loggedInUser"); // you no need to load categoriees,suppliers and products
		
			return mv;
			
		}

}