package com.gcu.fresh.services;

import com.gcu.fresh.models.UserModel;

public interface LoginAccessInterface {
    public void login(String username);
    public UserModel findByUsername(String username);
}
