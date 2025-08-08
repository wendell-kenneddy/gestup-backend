package br.com.gestup.gestup.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestup.gestup.dto.request.CreateUserRequest;
import br.com.gestup.gestup.dto.request.LoginRequest;
import br.com.gestup.gestup.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public String register(@RequestBody CreateUserRequest request) {
        return this.authenticationService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return this.authenticationService.login(request);
    }
}
