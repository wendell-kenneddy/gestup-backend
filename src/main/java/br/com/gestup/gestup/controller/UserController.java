package br.com.gestup.gestup.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestup.gestup.dto.UserDTO;
import br.com.gestup.gestup.service.UserService;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/profile")
    public UserDTO getProfile(Authentication authentication) {
        System.out.println(authentication.toString());
        return this.userService.getProfile(authentication);
    }
}
