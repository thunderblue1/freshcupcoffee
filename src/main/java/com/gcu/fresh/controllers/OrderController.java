package com.gcu.fresh.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.fresh.data.OrderAccessInterface;
import com.gcu.fresh.models.OrderModel;
import com.gcu.fresh.models.SearchForm;

@Controller
public class OrderController {
	
	@Autowired
	HttpSession session; 
	
	@Autowired
	OrderAccessInterface<OrderModel> oai;
	
	@RequestMapping("ordermanage")
	public String order(Model model,  @ModelAttribute("searchform") SearchForm searchform) {
		if(searchform.getSearch()==null) {
			List<OrderModel> filled = new ArrayList<OrderModel>();
			filled.addAll(oai.getOrders());
			session.setAttribute("ordercart", filled);
		} else {
			session.setAttribute("ordercart", oai.findOrdersAsList(searchform.getSearch()));
		}
		
		return "ordermanage";
	}
	
	@RequestMapping("deleteOrder")
	public String deleteOrder(Model model, @RequestParam(value="ordernumber") Long ordernumber,@ModelAttribute("searchform") SearchForm searchform) {
		oai.deleteOrder(ordernumber);
		if(searchform.getSearch()==null) {
			List<OrderModel> filled = new ArrayList<OrderModel>();
			filled.addAll(oai.getOrders());
			session.setAttribute("ordercart", filled);
		} else {
			session.setAttribute("ordercart", oai.findOrdersAsList(searchform.getSearch()));
		}
		return "ordermanage";
	}
}
