package com.gcu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gcu.fresh.services.UserBusinessService;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserBusinessService service;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//@Autowired
	//PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Allow REST API, login and registration to be permitted and nothing else
		http.csrf().disable()
		//.httpBasic().and()			
		.authorizeRequests()
			.antMatchers("/","/img/**","/css/**","/about","/shopping","/shoppingcart","/addToCart","/removeAllFromCart","/removeOneFromCart","/addOneMore","/createPurchase","/thankyou").permitAll()
			.antMatchers("/manage").hasAuthority("ADMIN")
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/login")
			.usernameParameter("username")
			.passwordParameter("password")
			.permitAll()
			.defaultSuccessUrl("/goToManage",true)
			.and()
		.logout()
			.logoutUrl("/logout")
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.permitAll()
			.logoutSuccessUrl("/");
	}
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//Pass service as callback function to userDetailsService and encrypt password
		auth.userDetailsService(service);//.passwordEncoder(passwordEncoder);
	}
}