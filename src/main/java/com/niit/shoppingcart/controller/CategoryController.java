package com.niit.shoppingcart.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.domain.Category;

@Controller
public class CategoryController {
	
	private static Logger log = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryDAO categoryDAO;
	
    @Autowired
    private Category category;
    
    
    @RequestMapping(value ="/manage_categories", method = RequestMethod.GET)
	public String listCategories(Model model)
    {
    	log.debug(" Starting of the method listCategories");
		model.addAttribute("category", category);
		model.addAttribute("categoryList", categoryDAO.list());
		model.addAttribute("isAdminClickedCategories", "true");
		log.debug(" End of the method listCategories");
		return "/Home";
	}
    @RequestMapping(value = "/manage_category_add", method = RequestMethod.POST)
	public String addCategory(@ModelAttribute("category") Category category, Model model) {
		log.debug(" Starting of the method addCategory");
		log.info("id:" + category.getId());
		if (categoryDAO.update(category) == true) {
			
			model.addAttribute("msg", "Successfully created/updated the caetgory");
		} else {
			model.addAttribute("msg", "not able to created/updated the caetgory");
		}
		model.addAttribute("category", category);
		model.addAttribute("categoryList", categoryDAO.list());
		model.addAttribute("isAdminClickedCategories", "true");
		log.debug(" Ending of the method addCategory");
		return "/Home";
	}
    
    @RequestMapping("manage_category_remove/{id}")

	public String deleteCategory(@PathVariable("id") String id, Model model) throws Exception {
		boolean flag = categoryDAO.delete(id);

		String msg = "Successfully done the operation";
		if (flag != true) {
			msg = "The operation could not success";
		}
		
		model.addAttribute("msg", msg);
	
		return "forward:/manage_categories";
	}

	@RequestMapping("manage_category_edit/{id}")
	public String editCategory(@PathVariable("id") String id, Model model) {
		
		log.debug(" Starting of the method editCategory");

		category = categoryDAO.getCategoryByID(id);
		
		log.debug(" End of the method editCategory");
		return "forward:/manage_categories";
	}

}
