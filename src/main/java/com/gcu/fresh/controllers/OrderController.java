package com.gcu.fresh.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.fresh.data.OrderAccessInterface;
import com.gcu.fresh.data.PurchaseDAO;
import com.gcu.fresh.models.OrderModel;
import com.gcu.fresh.models.SearchForm;

import utility.MessUtil;

@Controller
public class OrderController {
	
	public static Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	HttpSession session; 
	
	@Autowired
	OrderAccessInterface<OrderModel> oai;
	
	@RequestMapping("ordermanage")
	public String order(Model model,  @ModelAttribute("searchform") SearchForm searchform) {
		logger.info(MessUtil.enter("order","ordermanage"));
		if(searchform.getSearch()==null) {
			List<OrderModel> filled = new ArrayList<OrderModel>();
			filled.addAll(oai.getOrders());
			session.setAttribute("ordercart", filled);
		} else {
			session.setAttribute("ordercart", oai.findOrdersAsList(searchform.getSearch()));
		}
		logger.info(MessUtil.exitLoading("order", "ordermanage", "ordermanage"));
		return "ordermanage";
	}
	
	@RequestMapping("deleteOrder")
	public String deleteOrder(Model model, @RequestParam(value="ordernumber") Long ordernumber,@ModelAttribute("searchform") SearchForm searchform) {
		logger.info(MessUtil.enter("deleteOrder", "deleteOrder"));
		oai.deleteOrder(ordernumber);
		if(searchform.getSearch()==null) {
			List<OrderModel> filled = new ArrayList<OrderModel>();
			filled.addAll(oai.getOrders());
			session.setAttribute("ordercart", filled);
		} else {
			session.setAttribute("ordercart", oai.findOrdersAsList(searchform.getSearch()));
		}
		logger.info(MessUtil.exitLoading("deleteOrder", "deleteOrder", "ordermanage"));
		return "ordermanage";
	}
}
