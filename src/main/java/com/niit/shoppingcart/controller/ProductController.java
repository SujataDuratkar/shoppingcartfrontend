package com.niit.shoppingcart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.niit.shoppingcart.dao.ProductDAO;
import com.niit.shoppingcart.domain.Product;

@Controller
public class ProductController {

private static Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductDAO ProductDAO;
	
    @Autowired
    private Product Product;
    
    
    @RequestMapping(value ="/manage_products", method = RequestMethod.GET)
	public String listProducts(Model model)
    {
    	log.debug(" Starting of the method listProducts");
		model.addAttribute("Product", Product);
		model.addAttribute("ProductList", ProductDAO.list());
		model.addAttribute("isAdminClickedProducts", "true");
		log.debug(" End of the method listProducts");
		return "/Home";
	}
    @RequestMapping(value = "/manage_products_add", method = RequestMethod.POST)
	public String addProduct(@ModelAttribute("Product") Product Product, Model model) {
		log.debug(" Starting of the method addProduct");
		log.info("id:" + Product.getId());
		if (ProductDAO.update(Product) == true) {
			
			model.addAttribute("msg", "Successfully created/updated the product");
		} else {
			model.addAttribute("msg", "not able to created/updated the product");
		}
		model.addAttribute("Product", Product);
		model.addAttribute("ProductList", ProductDAO.list());
		model.addAttribute("isAdminClickedproducts", "true");
		log.debug(" Ending of the method addProduct");
		return "/Home";
	}
    
    @RequestMapping("manage_products_remove/{id}")

	public String deleteProduct(@PathVariable("id") String id, Model model) throws Exception {
		boolean flag = ProductDAO.delete(id);

		String msg = "Successfully done the operation";
		if (flag != true) {
			msg = "The operation could not success";
		}
		
		model.addAttribute("msg", msg);
	
		return "forward:/manage_products";
	}

	@RequestMapping("manage_products_edit/{id}")
	public String editProduct(@PathVariable("id") String id, Model model) throws Exception
	{
		log.debug(" Starting of the method editProduct");
		Product = ProductDAO.getProductByID(id);
		model.addAttribute("selectedProduct", Product);
		log.debug("End of the method editProduct");
		return "forward:/manage_products";
	}
	
	@RequestMapping("manage_products_get/{id}")
	public ModelAndView getSelectedProduct(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		log.debug(" Starting of the method getSelectedProduct");
		ModelAndView mv = new ModelAndView("redirect:/");
		redirectAttributes.addFlashAttribute("SelectedProduct",id);
		log.debug(" End of the method getSelectedProduct");
		return mv;
	}
	
	
	
	
	
}
