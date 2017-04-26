package com.niit.shoppingcart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.niit.shoppingcart.dao.SupplierDAO;
import com.niit.shoppingcart.domain.Supplier;

@Controller
public class SupplierController {

private static Logger log = LoggerFactory.getLogger(SupplierController.class);
	
	@Autowired
	private SupplierDAO SupplierDAO;
	
    @Autowired
    private Supplier Supplier;
    
    
    @RequestMapping(value ="/manage_suppliers", method = RequestMethod.GET)
	public String listSuppliers(Model model)
    {
    	log.debug(" Starting of the method listSuppliers");
		model.addAttribute("Supplier", Supplier);
		model.addAttribute("SupplierList", SupplierDAO.list());
		model.addAttribute("isAdminClickedSuppliers", "true");
		log.debug(" End of the method listSuppliers");
		return "/Home";
	}
    @RequestMapping(value = "/manage_suppliers_add", method = RequestMethod.POST)
	public String addSupplier(@ModelAttribute("Supplier") Supplier Supplier, Model model) {
		log.debug(" Starting of the method addSupplier");
		log.info("id:" + Supplier.getId());
		if (SupplierDAO.update(Supplier) == true) {
			
			model.addAttribute("msg", "Successfully created/updated the Supplier");
		} else {
			model.addAttribute("msg", "not able to created/updated the Supplier");
		}
		model.addAttribute("Supplier", Supplier);
		model.addAttribute("SupplierList", SupplierDAO.list());
		model.addAttribute("isAdminClickedSuppliers", "true");
		log.debug(" Ending of the method addSupplier");
		return "/Home";
	}
    
    @RequestMapping("manage_suppliers_remove/{id}")

	public String deleteSupplier(@PathVariable("id") String id, Model model) throws Exception {
		boolean flag = SupplierDAO.delete(id);

		String msg = "Successfully done the operation";
		if (flag != true) {
			msg = "The operation could not success";
		}
		
		model.addAttribute("msg", msg);
	
		return "forward:/manage_suppliers";
	}

	@RequestMapping("manage_suppliers_edit/{id}")
	public String editSupplier(@PathVariable("id") String id, Model model) {
		
		log.debug(" Starting of the method editSupplier");

		Supplier = SupplierDAO.getSupplierByID(id);
		model.addAttribute("selectedSupplier", Supplier);
		log.debug(" End of the method editSupplier");
		return "forward:/manage_suppliers";
	}
	
	@RequestMapping("manage_suppliers_get/{id}")
	public ModelAndView getSelectedSupplier(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		log.debug(" Starting of the method getSelectedSupplier");
		ModelAndView mv = new ModelAndView("redirect:/");
		redirectAttributes.addFlashAttribute("SelectedSupplier",id);
		log.debug(" End of the method getSelectedSupplier");
		return mv;
	}
}