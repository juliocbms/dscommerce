package com.devjulio.dscommerce.controllers;

import com.devjulio.dscommerce.DTO.UserDTO;
import com.devjulio.dscommerce.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;





@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController( UserService userService) {
        this.userService = userService;

    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe(){
            UserDTO dto = userService.getMe();
            return ResponseEntity.ok(dto);
    }

}
