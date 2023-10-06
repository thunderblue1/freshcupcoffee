package com.gcu.fresh.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysql.cj.log.LogFactory;

import utility.MessUtil;

@Controller
public class AboutController {

		public static Logger logger = LoggerFactory.getLogger(AboutController.class);
	
		@RequestMapping({"/","about"})
		public String index() {
			//BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			//String bCryptedPassword = bCryptPasswordEncoder.encode("pass");
			//System.out.println(bCryptedPassword);
			
			logger.info(MessUtil.enter("index", "/ and about"));
			logger.info(MessUtil.exitLoading("index","/ and about","about"));
			return "about";
		}
}
