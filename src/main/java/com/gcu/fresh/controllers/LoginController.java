package com.gcu.fresh.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gcu.fresh.models.LoginModel;
import com.gcu.fresh.services.LoginDataService;

@Controller
public class LoginController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	LoginDataService lds;
	
    //This controller takes the user to the login page
    @RequestMapping("/login")
	public String login(Model model)
	{
		model.addAttribute("loginModel", new LoginModel());
		return "login";
	}

	//This takes the user to the product page
	//or back to login if the form has errors
    @RequestMapping("/goToManage")
    public String display(Model model)
    {
    	String user = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    	lds.login(user);
        return "redirect:manage";
    }
}
