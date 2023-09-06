package com.gcu.fresh.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.fresh.data.ProductAccessInterface;
import com.gcu.fresh.models.ProductModel;
import com.gcu.fresh.models.SearchForm;

@Controller
public class ManageController {
	@Autowired
	HttpSession session;
	
	@Autowired
	ProductAccessInterface<ProductModel> pai;
	
	@RequestMapping("manage")
	public String manage(Model model,  @ModelAttribute("searchform") SearchForm searchform) {
		if(searchform.getSearch()==null) {
			List<ProductModel> filled = new ArrayList<ProductModel>();
			filled.addAll(pai.getProducts());
			session.setAttribute("products", filled);
		} else {
			session.setAttribute("products", pai.findProductsAsList(searchform.getSearch()));
		}
		model.addAttribute("productModel",new ProductModel());
		
		return "manage";
	}
	
	@PostMapping("/createProduct")
	public String createProduct(@Valid ProductModel productModel,  BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("manage: Failed to process create product form.");
		} else {
			@SuppressWarnings("unchecked")
			List<ProductModel> productlist = (List<ProductModel>) session.getAttribute("products");
			
			ProductModel pm = null;			
			if(productModel.getItemNumber()!=null) {
				
				//Get database entry (if exists)
				if(pai.findProduct(productModel.getItemNumber())!=null) {
					pm = pai.findProduct(productModel.getItemNumber()); 				
				}
				
				if(pm!=null) {
					for(ProductModel product : productlist) {
						if(product.getItemNumber() == pm.getItemNumber()) {
							productlist.remove(product);
							break;
						}
					}			
				}
			}

			if(pm!=null || productModel.getItemNumber()==null) {
				//Create product
				pai.createProduct(productModel);			
				
				//Add product to product display
				productlist.add(productModel);
			}
			session.setAttribute("products", productlist);
	
			model.addAttribute("productModel",new ProductModel());
		}
		return "manage";
	}
	
	@PostMapping("/updateProduct")
	public String updateProduct (@Valid @ModelAttribute("productModel") ProductModel productModel,BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("manage: Failed to update product with form.");
		} else {
			@SuppressWarnings("unchecked")
			List<ProductModel> productlist = (List<ProductModel>) session.getAttribute("products");
			
			ProductModel pm = null;			
			if(productModel.getItemNumber()!=null) {
				
				//Get database entry (if exists)
				if(pai.findProduct(productModel.getItemNumber())!=null) {
					pm = pai.findProduct(productModel.getItemNumber()); 				
				}
				
				if(pm!=null) {
					for(ProductModel product : productlist) {
						if(product.getItemNumber() == pm.getItemNumber()) {
							productlist.remove(product);
							break;
						}
					}			
				}
			}

			if(pm!=null || productModel.getItemNumber()==null) {
				//Update product if it exists
				pai.createProduct(productModel);			
				
				//Add product to product display
				productlist.add(productModel);
			}
			session.setAttribute("products", productlist);
	
			model.addAttribute("productModel",new ProductModel());
		}
		
		return "manage";
	}
	
	@RequestMapping("delete")
	public String delete (@RequestParam(value="productid") Long productid, Model model) {
		//Get database entry
		ProductModel pm = pai.findProduct(productid);
		
		//Remove database entry
		pai.deleteProduct(pm);

		//Remove from search results
		@SuppressWarnings("unchecked")
		List<ProductModel> productlist = (List<ProductModel>) session.getAttribute("products");
		
		for(ProductModel product : productlist) {
			if(product.getItemNumber() == pm.getItemNumber()) {
				productlist.remove(product);
				break;
			}
		}
		
		session.setAttribute("products", productlist);
		
		model.addAttribute("productModel", new ProductModel());
		model.addAttribute("updateModel", new ProductModel());
		
		System.out.println("Deleting: "+productid);
		
		return "manage";
	}
	
}
