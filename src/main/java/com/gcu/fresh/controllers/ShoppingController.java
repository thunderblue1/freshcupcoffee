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
import com.gcu.fresh.data.PurchaseAccessInterface;
import com.gcu.fresh.models.OrderModel;
import com.gcu.fresh.models.OrderTransferModel;
import com.gcu.fresh.models.ProductModel;
import com.gcu.fresh.models.PurchaseModel;
import com.gcu.fresh.models.SearchForm;
import com.gcu.fresh.repository.OrderRepository;

@Controller
public class ShoppingController {
	@Autowired
	ProductAccessInterface<ProductModel> pai;
	
	@Autowired
	PurchaseAccessInterface<PurchaseModel> purchaseDAO;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	OrderRepository orderRepo;
	
	@RequestMapping("shopping")
	public String shopping (Model model,  @ModelAttribute("searchform") SearchForm searchform) {
		if(session.getAttribute("shoppingcart")==null) {
			List<OrderModel> shoppingcart = new ArrayList<OrderModel>();
			session.setAttribute("shoppingcart", shoppingcart);
		}
		
		if(searchform.getSearch()==null) {
			List<ProductModel> filled = new ArrayList<ProductModel>();
			filled.addAll(pai.getProducts());
			session.setAttribute("products", filled);
		} else {
			session.setAttribute("products", pai.findProductsAsList(searchform.getSearch()));
		}
		model.addAttribute("productModel",new ProductModel());
		return "shopping";
	}
	
	@RequestMapping("shoppingcart")
	public String shoppingCart (Model model) {
		if(session.getAttribute("shoppingcart")==null) {
			List<OrderModel> shoppingcart = new ArrayList<OrderModel>();
			session.setAttribute("shoppingcart", shoppingcart);
		}
		PurchaseModel purchaseModel = new PurchaseModel();
		model.addAttribute("purchaseModel",purchaseModel);
		return "shoppingcart";
	}
	
	@RequestMapping("addToCart")
	public String addToCart (@RequestParam(value="productid") Long productid, Model model) {		
		@SuppressWarnings("unchecked")
		List<OrderModel> shoppingcart = (List<OrderModel>) session.getAttribute("shoppingcart");		
		
		OrderModel om = new OrderModel();
		
		for(OrderModel order : shoppingcart) {
			if(order.getItemNumber()==productid) {
				om=order;
				order.setQuantity(order.getQuantity()+1);
			}
		}
		if(om.getItemNumber()==null) {
			Long purchaseNumber=0l;
			Integer quantity=1;
			ProductModel pm = pai.findProduct(productid);
			om = new OrderModel(productid,purchaseNumber,quantity,pm.getName(),pm.getDescription(),pm.getPrice(),pm.getPhoto());			
			shoppingcart.add(om);
		}
		session.setAttribute("shoppingcart", shoppingcart);				
		model.addAttribute("productModel", new ProductModel());
		model.addAttribute("updateModel", new ProductModel());
		return "shopping";
	}

	@RequestMapping("addOneMore")
	public String addOneMore (@RequestParam(value="productid") Long productid, Model model) {		
		@SuppressWarnings("unchecked")
		List<OrderModel> shoppingcart = (List<OrderModel>) session.getAttribute("shoppingcart");		
		
		OrderModel om = new OrderModel();
		
		for(OrderModel order : shoppingcart) {
			if(order.getItemNumber()==productid) {
				om=order;
				order.setQuantity(order.getQuantity()+1);
			}
		}
		if(om.getItemNumber()==null) {
			Long purchaseNumber=0l;
			Integer quantity=1;
			ProductModel pm = pai.findProduct(productid);
			om = new OrderModel(productid,purchaseNumber,quantity,pm.getName(),pm.getDescription(),pm.getPrice(),pm.getPhoto());			
			shoppingcart.add(om);
		}
		session.setAttribute("shoppingcart", shoppingcart);				
		model.addAttribute("productModel", new ProductModel());
		model.addAttribute("updateModel", new ProductModel());
		model.addAttribute("purchaseModel",new PurchaseModel());
		return "shoppingcart";
	}
	
	@RequestMapping("removeAllFromCart")
	public String removeAllFromCart (@RequestParam(value="productid") Long productid, Model model) {
		@SuppressWarnings("unchecked")
		List<OrderModel> shoppingcart = (List<OrderModel>) session.getAttribute("shoppingcart");
		
		for(OrderModel order : shoppingcart) {
			if(order.getItemNumber()==productid) {
				shoppingcart.remove(order);
			}
		}
		
		session.setAttribute("shoppingcart", shoppingcart);
		model.addAttribute("purchaseModel",new PurchaseModel());
		return "shoppingcart";
	}
	
	@RequestMapping("removeOneFromCart")
	public String removeOneFromCart (@RequestParam(value="productid") Long productid, Model model) {
		@SuppressWarnings("unchecked")
		List<OrderModel> shoppingcart = (List<OrderModel>) session.getAttribute("shoppingcart");
		
		for(OrderModel order : shoppingcart) {
			if(order.getItemNumber()==productid) {
				if(order.getQuantity()>1) {
					order.setQuantity(order.getQuantity()-1);
				} else {
					shoppingcart.remove(order);					
				}
			}
		}
		model.addAttribute("purchaseModel",new PurchaseModel());
		return "shoppingcart";
	}
	
	@PostMapping("/createPurchase")
	public String createPurchase(@Valid PurchaseModel purchaseModel,  BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("shoppingcart: Failed to process purchase form");
		} else {
			@SuppressWarnings("unchecked")
			List<OrderModel> productlist = (List<OrderModel>) session.getAttribute("shoppingcart");
			
			if(productlist.size()>0) {
				Integer total = 0;
				Integer temp = 0;
				for(OrderModel om : productlist) {
					temp= om.getQuantity()*om.getPrice();
					total+=temp;
				}
				purchaseModel.setTotalPrice(total);
				purchaseDAO.createPurchase(purchaseModel);
				System.out.println("Purchase Number: "+purchaseModel.getPurchaseNumber());
			
				for(OrderModel om : productlist) {
					om.setPurchaseNumber(purchaseModel.getPurchaseNumber());
					orderRepo.save(new OrderTransferModel(om.getItemNumber(),om.getPurchaseNumber(),om.getQuantity()));
				}
				model.addAttribute("purchase",purchaseModel);
				model.addAttribute("orderlist", productlist);
				session.setAttribute("shoppingcart", new ArrayList<OrderModel>());
				return "thankyou";
			}
			
		}
		model.addAttribute("purchaseModel",new PurchaseModel());
		return "shoppingcart";
	}
	
	@RequestMapping("/thankyou")
	public String thankYou() {
		return "thankyou";
	}
	
}
