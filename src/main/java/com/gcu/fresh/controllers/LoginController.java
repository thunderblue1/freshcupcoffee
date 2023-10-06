package com.gcu.fresh.controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gcu.fresh.models.LoginModel;
import com.gcu.fresh.services.LoginDataService;

import utility.MessUtil;

@Controller
public class LoginController {
	
	public static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	HttpSession session;
	
	@Autowired
	LoginDataService lds;
	
    //This controller takes the user to the login page
    @RequestMapping("/login")
	public String login(Model model)
	{
    	logger.info(MessUtil.enter("login", "login"));
		model.addAttribute("loginModel", new LoginModel());
		logger.info(MessUtil.exitLoading("login", "/login","login"));
		return "login";
	}

	//This takes the user to the product page
	//or back to login if the form has errors
    @RequestMapping("/goToManage")
    public String display(Model model)
    {
    	logger.info(MessUtil.enter("display", "/goToManage"));
    	String user = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    	lds.login(user);
        logger.info(MessUtil.exitLoading("display", "/goToManage", "manage"));
    	return "redirect:manage";
    }
}
