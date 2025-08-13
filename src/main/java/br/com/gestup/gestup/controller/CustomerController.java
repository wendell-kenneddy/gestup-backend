package br.com.gestup.gestup.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestup.gestup.dto.CustomerDTO;
import br.com.gestup.gestup.dto.request.UpsertCustomerRequest;
import br.com.gestup.gestup.service.CustomerService;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public CustomerDTO create(@RequestBody UpsertCustomerRequest request, Authentication authentication) {
        return this.customerService.create(request, authentication);
    }

    @GetMapping("/customers/{customerId}")
    public CustomerDTO find(@PathVariable String customerId, Authentication authentication) {
        return this.customerService.find(customerId, authentication);
    }

    @GetMapping("/customers")
    public List<CustomerDTO> findAll(Authentication authentication) {
        return this.customerService.findAll(authentication);
    }

    @PutMapping("/customers/{customerId}")
    public CustomerDTO update(
            @PathVariable String customerId,
            @RequestBody UpsertCustomerRequest request,
            Authentication authentication) {
        return this.customerService.update(customerId, request, authentication);
    }

    @DeleteMapping("/customers/{customerId}")
    public void delete(@PathVariable String customerId, Authentication authentication) {
        this.customerService.delete(customerId, authentication);
    }
}
