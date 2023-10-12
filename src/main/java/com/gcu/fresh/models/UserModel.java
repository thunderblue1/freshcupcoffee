package com.gcu.fresh.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

//Used with form and various places for logging in
@Table("user")
public class UserModel {
	@Column("user_id")
	@Id
	private Long id;
	
	@Column("username")
    @NotNull(message="Username is a required field")
    @Size(min=1, max=32, message="Username must be between 1 and 32 characters")
    private String username;
	
	@Column("pass")
    @NotNull(message="Password is a required field")
    @Size(min=1, max=32, message="Password must be between 1 and 32 characters")
    private String password;
	
	public UserModel() {
		super();
	}

	public UserModel(Long id,
			@NotNull(message = "Username is a required field") @Size(min = 1, max = 32, message = "Username must be between 1 and 32 characters") String username,
			@NotNull(message = "Password is a required field") @Size(min = 1, max = 32, message = "Password must be between 1 and 32 characters") String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
