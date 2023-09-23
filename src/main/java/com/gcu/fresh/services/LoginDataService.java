package com.gcu.fresh.services;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gcu.fresh.mapper.UserRowMapper;
import com.gcu.fresh.models.UserModel;

@Service
public class LoginDataService implements LoginAccessInterface {

	//Template for executing SQL queries
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	//Session to persist user data
	@Autowired
	HttpSession session;
	
	//Default constructor
    public LoginDataService(DataSource dataSource)
    {
    }

    //Verifies that the user-name and password exists
    public void login(String username)
    {
        String sql = "SELECT * FROM user WHERE username = ?";
                
        //Map user data to Model
        List<UserModel> umlist = jdbcTemplate.query(sql, new UserRowMapper(), username);
        
        //Set model as attribute for session
        session.setAttribute("user",umlist.get(0));
    }
    
    public UserModel findByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = \"" + username+"\"";
        
        //Map user data to Model
        List<UserModel> umlist = jdbcTemplate.query(sql, new UserRowMapper());
		return umlist.get(0);
    }
	
}
