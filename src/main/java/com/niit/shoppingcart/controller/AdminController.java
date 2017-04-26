package com.niit.shoppingcart.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.dao.ProductDAO;
import com.niit.shoppingcart.dao.SupplierDAO;
import com.niit.shoppingcart.domain.Category;
import com.niit.shoppingcart.domain.Product;
import com.niit.shoppingcart.domain.Supplier;

@Controller
public class AdminController {
	
	private static Logger log = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	CategoryDAO categoryDAO;
	
	@Autowired
	Category category;
	
	@Autowired
	private Supplier supplier;
	
	@Autowired
	private SupplierDAO supplierDAO;
	
	@Autowired
	private Product product;
	
	@Autowired
	private ProductDAO productDAO;
	
	//http://localhost:8080/shoppingcart/manageCategoories
	//<a href="/managecategories"> </a>
	//id,name,desscription
	
	@RequestMapping("manage_categories")
	public ModelAndView manageCategories()
	{
		log.debug("Starting of the method categories");
		ModelAndView mv = new ModelAndView("Home");
		mv.addObject("category", category);
		mv.addObject("isAdminClickedCategories", "true");
		mv.addObject("categoryList", categoryDAO.list());
		log.debug("Ending of the method categories");
		return mv;
		
	}
	
	@RequestMapping("/manage_products")
	public ModelAndView manageProducts()
	{
		log.debug("Starting of the method products");
		ModelAndView mv = new ModelAndView("Home");
		
		mv.addObject("isAdminClickedProducts", "true");
		mv.addObject("product", product);
		mv.addObject("productList", productDAO.list());
		mv.addObject("category", category);
		mv.addObject("categoryList", categoryDAO.list());
		mv.addObject("supplier", supplier);
		mv.addObject("supplierList", supplierDAO.list());
		log.debug("Ending of the method products");
		return mv;
	}
	
	@RequestMapping("/manage_suppliers")
	public ModelAndView manageSuppliers()
	{
		log.debug("Starting of the method suppliers");
		ModelAndView mv = new ModelAndView("Home");
		mv.addObject("supplier", supplier);
		mv.addObject("isAdminClickedSuppliers", "true");
		mv.addObject("supplierList", supplierDAO.list());
		log.debug("Ending of the method suppliers");
		return mv;
	}

}
