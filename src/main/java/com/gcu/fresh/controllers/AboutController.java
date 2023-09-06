package com.gcu.fresh.controllers;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutController {

		@RequestMapping({"/","about"})
		public String index() {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			String bCryptedPassword = bCryptPasswordEncoder.encode("pass");
			System.out.println(bCryptedPassword);
			return "about";
		}
}
