package com.gcu.fresh.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.gcu.fresh.data.PurchaseDAO;
import com.gcu.fresh.models.OrderModel;
import com.gcu.fresh.models.OrderTransferModel;
import com.gcu.fresh.models.ProductModel;
import com.gcu.fresh.models.PurchaseModel;
import com.gcu.fresh.models.SearchForm;
import com.gcu.fresh.repository.OrderRepository;

import utility.MessUtil;

@Controller
public class ShoppingController {
	
	public static Logger logger = LoggerFactory.getLogger(ShoppingController.class);
	
	@Autowired
	ProductAccessInterface<ProductModel> pai;
	
	@Autowired
	PurchaseAccessInterface<PurchaseModel> purchaseDAO;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	OrderRepository orderRepo;
	
	//Return shopping page with products based on value of property in searchform
	@RequestMapping("shopping")
	public String shopping (Model model,  @ModelAttribute("searchform") SearchForm searchform) {
		logger.info(MessUtil.enter("shopping", "shopping"));
		//Create shopping cart if it doesn't exist
		if(session.getAttribute("shoppingcart")==null) {
			List<OrderModel> shoppingcart = new ArrayList<OrderModel>();
			session.setAttribute("shoppingcart", shoppingcart);
		}
		//Set products attribute based on value of search term stored in searchform
		if(searchform.getSearch()==null) {
			List<ProductModel> filled = new ArrayList<ProductModel>();
			filled.addAll(pai.getProducts());
			session.setAttribute("products", filled);
		} else {
			session.setAttribute("products", pai.findProductsAsList(searchform.getSearch()));
		}
		logger.info(MessUtil.exitLoading("shopping", "shopping", "shopping"));
		return "shopping";
	}
	
	//Return shoppingcart page based on items in shoppingcart attribute
	@RequestMapping("shoppingcart")
	public String shoppingCart (Model model) {
		logger.info(MessUtil.enter("shoppingCart","shoppingcart"));
		//Create a cart if there is not one yet
		if(session.getAttribute("shoppingcart")==null) {
			List<OrderModel> shoppingcart = new ArrayList<OrderModel>();
			session.setAttribute("shoppingcart", shoppingcart);
		}
		//Create a purchaseModel in case we are ready to checkout
		PurchaseModel purchaseModel = new PurchaseModel();
		model.addAttribute("purchaseModel",purchaseModel);
		logger.info(MessUtil.exitLoading("shoppingCart", "shoppingcart", "shoppingcart"));
		return "shoppingcart";
	}
	
	//Add an item to the shopping cart if it is not in the cart already
	//or add one to the quantity of item based on productid then
	//return the shopping page
	@RequestMapping("addToCart")
	public String addToCart (@RequestParam(value="productid") Long productid, Model model) {	
		logger.info(MessUtil.enter("addToCart", "addToCart"));
		
		//Get the shoppingcart for shopping
		@SuppressWarnings("unchecked")
		List<OrderModel> shoppingcart = (List<OrderModel>) session.getAttribute("shoppingcart");		
		
		//Check if the item is in the cart and increment quantity if it is
		OrderModel om = new OrderModel();
		for(OrderModel order : shoppingcart) {
			if(order.getItemNumber()==productid) {
				om=order;
				order.setQuantity(order.getQuantity()+1);
			}
		}
		//If item is not in cart then add is to the cart
		if(om.getItemNumber()==null) {
			Long purchaseNumber=0l;
			Integer quantity=1;
			ProductModel pm = pai.findProduct(productid);
			om = new OrderModel(productid,purchaseNumber,quantity,pm.getName(),pm.getDescription(),pm.getPrice(),pm.getPhoto());			
			shoppingcart.add(om);
		}
		
		//Update shoppingcart attribute
		session.setAttribute("shoppingcart", shoppingcart);		
		logger.info(MessUtil.exitLoading("addToCart", "addToCart", "shopping"));
		return "shopping";
	}

	//In shoppingcart page, add one to the quantity of item in shoppingcart with id of productid
	//Return shoppingcart page
	@RequestMapping("addOneMore")
	public String addOneMore (@RequestParam(value="productid") Long productid, Model model) {
		logger.info(MessUtil.enter("addOneMore", "addOneMore"));
		
		//Get the shoppingcart
		@SuppressWarnings("unchecked")
		List<OrderModel> shoppingcart = (List<OrderModel>) session.getAttribute("shoppingcart");		
		
		OrderModel om = new OrderModel();
		
		//Find the item in cart by productid
		for(OrderModel order : shoppingcart) {
			if(order.getItemNumber()==productid) {
				om=order;
				order.setQuantity(order.getQuantity()+1);
			}
		}
		//If found then increment quantity for item
		if(om.getItemNumber()==null) {
			Long purchaseNumber=0l;
			Integer quantity=1;
			ProductModel pm = pai.findProduct(productid);
			om = new OrderModel(productid,purchaseNumber,quantity,pm.getName(),pm.getDescription(),pm.getPrice(),pm.getPhoto());			
			shoppingcart.add(om);
		}
		//Update shoppingcart attribute
		session.setAttribute("shoppingcart", shoppingcart);	
		
		//Create a purchaseModel in case we are ready to checkout
		model.addAttribute("purchaseModel",new PurchaseModel());
		
		logger.info(MessUtil.exitLoading("addOneMore", "addOneMore", "shoppingcart"));
		return "shoppingcart";
	}
	
	//In shoppingcart, remove all of an item in shoppingcart by productid
	@RequestMapping("removeAllFromCart")
	public String removeAllFromCart (@RequestParam(value="productid") Long productid, Model model) {
		logger.info(MessUtil.enter("removeAllFromCart","removeAllFromCart"));
		
		//Get shoppingcart
		@SuppressWarnings("unchecked")
		List<OrderModel> shoppingcart = (List<OrderModel>) session.getAttribute("shoppingcart");
		
		//Get item if inside of shoppingcart
		OrderModel found = new OrderModel();
		boolean inside = false;
		for(OrderModel order : shoppingcart) {
			if(order.getItemNumber()==productid) {
				found = order;
				inside = true;
			}	
		}
		//Remove the item if found inside of shoppingcart
		if(inside) {
			shoppingcart.remove(found);
		}
		//Update shoppingcart attribute
		session.setAttribute("shoppingcart", shoppingcart);
		//Create a purchaseModel in case we are ready to checkout
		model.addAttribute("purchaseModel",new PurchaseModel());
		
		logger.info(MessUtil.exitLoading("removeAllFromCart", "removeAllFromCart", "shoppingcart"));
		return "shoppingcart";
	}
	
	//In shoppingcart page, remove one of an item with productid from the shoppingcart
	@RequestMapping("removeOneFromCart")
	public String removeOneFromCart (@RequestParam(value="productid") Long productid, Model model) {
		//Get shopping cart
		logger.info(MessUtil.enter("removeOneFromCart", "removeOneFromCart"));
		@SuppressWarnings("unchecked")
		List<OrderModel> shoppingcart = (List<OrderModel>) session.getAttribute("shoppingcart");
		
		//Find the item if it is in the cart and remove one from the quantity
		//if there is more than one of the item
		OrderModel found = new OrderModel();
		boolean inside = false;
		for(OrderModel order : shoppingcart) {
			if(order.getItemNumber()==productid) {
				if(order.getQuantity()>1) {
					order.setQuantity(order.getQuantity()-1);
				} else {
					found = order;
					inside=true;
				}
			}
		}
		
		//If the item is inside the cart but there is only one of them
		//then remove the item from the cart completely
		if(inside) {
			shoppingcart.remove(found);
		}
		model.addAttribute("purchaseModel",new PurchaseModel());
		logger.info(MessUtil.exitLoading("removeOneFromCart", "removeOneFromCart", "shoppingcart"));
		return "shoppingcart";
	}
	
	//Process checkout form and create a purchase then insert orders into database with purchase number
	//Return thankyou page
	@PostMapping("/createPurchase")
	public String createPurchase(@Valid PurchaseModel purchaseModel,  BindingResult bindingResult, Model model) {
		logger.info(MessUtil.enter("createPurchase", "/createPurchase"));
		
		//Return to page and show errors if the form is not valid
		if(bindingResult.hasErrors()) {
			System.out.println("shoppingcart: Failed to process purchase form");
		} else {
			
			//Get shopping cart
			@SuppressWarnings("unchecked")
			List<OrderModel> productlist = (List<OrderModel>) session.getAttribute("shoppingcart");
			
			//If cart has products then calculate total, insert purchase and orders into database
			if(productlist.size()>0) {
				//Calculate total
				Integer total = 0;
				Integer temp = 0;
				for(OrderModel om : productlist) {
					temp= om.getQuantity()*om.getPrice();
					total+=temp;
				}
				purchaseModel.setTotalPrice(total);
				
				//Insert purchase into database
				purchaseDAO.createPurchase(purchaseModel);
				System.out.println("Purchase Number: "+purchaseModel.getPurchaseNumber());
				
				//Create orders and insert them into database
				for(OrderModel om : productlist) {
					om.setPurchaseNumber(purchaseModel.getPurchaseNumber());
					orderRepo.save(new OrderTransferModel(om.getItemNumber(),om.getPurchaseNumber(),om.getQuantity()));
				}
				//Create attributes required for thankyou page
				model.addAttribute("purchase",purchaseModel);
				model.addAttribute("orderlist", productlist);
				
				//Empty shoppingcart after checkout
				session.setAttribute("shoppingcart", new ArrayList<OrderModel>());
				logger.info(MessUtil.exitLoading("createPurchase", "/createPurchase", "thankyou"));
				return "thankyou";
			}
			
		}
		model.addAttribute("purchaseModel",new PurchaseModel());
		logger.info(MessUtil.exitLoading("createPurchase", "/createPurchase", "shoppingcart"));
		return "shoppingcart";
	}
}
