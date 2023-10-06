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
	
	@RequestMapping("shopping")
	public String shopping (Model model,  @ModelAttribute("searchform") SearchForm searchform) {
		logger.info(MessUtil.enter("shopping", "shopping"));
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
		logger.info(MessUtil.exitLoading("shopping", "shopping", "shopping"));
		return "shopping";
	}
	
	@RequestMapping("shoppingcart")
	public String shoppingCart (Model model) {
		logger.info(MessUtil.enter("shoppingCart","shoppingcart"));
		if(session.getAttribute("shoppingcart")==null) {
			List<OrderModel> shoppingcart = new ArrayList<OrderModel>();
			session.setAttribute("shoppingcart", shoppingcart);
		}
		PurchaseModel purchaseModel = new PurchaseModel();
		model.addAttribute("purchaseModel",purchaseModel);
		logger.info(MessUtil.exitLoading("shoppingCart", "shoppingcart", "shoppingcart"));
		return "shoppingcart";
	}
	
	@RequestMapping("addToCart")
	public String addToCart (@RequestParam(value="productid") Long productid, Model model) {	
		logger.info(MessUtil.enter("addToCart", "addToCart"));
		
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
		logger.info(MessUtil.exitLoading("addToCart", "addToCart", "shopping"));
		return "shopping";
	}

	@RequestMapping("addOneMore")
	public String addOneMore (@RequestParam(value="productid") Long productid, Model model) {
		logger.info(MessUtil.enter("addOneMore", "addOneMore"));
		
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
		
		logger.info(MessUtil.exitLoading("addOneMore", "addOneMore", "shoppingcart"));
		return "shoppingcart";
	}
	
	@RequestMapping("removeAllFromCart")
	public String removeAllFromCart (@RequestParam(value="productid") Long productid, Model model) {
		
		logger.info(MessUtil.enter("removeAllFromCart","removeAllFromCart"));
		
		@SuppressWarnings("unchecked")
		List<OrderModel> shoppingcart = (List<OrderModel>) session.getAttribute("shoppingcart");
		
		OrderModel found = new OrderModel();
		boolean inside = false;
		for(OrderModel order : shoppingcart) {
			if(order.getItemNumber()==productid) {
				found = order;
				inside = true;
			}	
		}
		if(inside) {
			shoppingcart.remove(found);
		}
		session.setAttribute("shoppingcart", shoppingcart);
		model.addAttribute("purchaseModel",new PurchaseModel());
		
		logger.info(MessUtil.exitLoading("removeAllFromCart", "removeAllFromCart", "shoppingcart"));
		return "shoppingcart";
	}
	
	@RequestMapping("removeOneFromCart")
	public String removeOneFromCart (@RequestParam(value="productid") Long productid, Model model) {
		
		logger.info(MessUtil.enter("removeOneFromCart", "removeOneFromCart"));
		@SuppressWarnings("unchecked")
		List<OrderModel> shoppingcart = (List<OrderModel>) session.getAttribute("shoppingcart");
		
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
		if(inside) {
			shoppingcart.remove(found);
		}
		model.addAttribute("purchaseModel",new PurchaseModel());
		logger.info(MessUtil.exitLoading("removeOneFromCart", "removeOneFromCart", "shoppingcart"));
		return "shoppingcart";
	}
	
	@PostMapping("/createPurchase")
	public String createPurchase(@Valid PurchaseModel purchaseModel,  BindingResult bindingResult, Model model) {
		logger.info(MessUtil.enter("createPurchase", "/createPurchase"));
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
				logger.info(MessUtil.exitLoading("createPurchase", "/createPurchase", "thankyou"));
				return "thankyou";
			}
			
		}
		model.addAttribute("purchaseModel",new PurchaseModel());
		logger.info(MessUtil.exitLoading("createPurchase", "/createPurchase", "shoppingcart"));
		return "shoppingcart";
	}
}
