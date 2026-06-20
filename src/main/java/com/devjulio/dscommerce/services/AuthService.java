package com.devjulio.dscommerce.services;

import com.devjulio.dscommerce.entities.User;
import com.devjulio.dscommerce.services.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public void validateSefOrAdmin(Long useId){
        User me = userService.authenticated();
        if (!me.hasRole("ROLE_ADMIN") && !me.getId().equals(useId)){
            throw  new ForbiddenException("Access denied!");
        }
    }
}
