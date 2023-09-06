package com.gcu.fresh.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gcu.fresh.models.UserModel;

@Service
public class UserBusinessService implements UserDetailsService {
	//Used to access database
    @Autowired
    LoginDataService lds;
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//If the person is in the database this is the user
		UserModel user = lds.findByUsername(username);
		if(user!=null) {
			System.out.println("Username :"+user.getUsername());
			
			//Set an authority
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ADMIN"));			
			
			//Create a new user with the new authority
			return new User(user.getUsername(), user.getPassword(), authorities);
		} else {
			System.out.println("UserBusinessService: User was null");
			//Give exception in even of no user-name.
			throw new UsernameNotFoundException("Username not found.");
		}
	}
}
