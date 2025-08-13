package br.com.gestup.gestup.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestup.gestup.dto.ProductDTO;
import br.com.gestup.gestup.service.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ProductDTO create(@RequestBody ProductDTO productDTO, Authentication authentication) {
        ProductDTO product = this.productService.create(productDTO, authentication);
        System.out.println(product.toString());
        return product;
    }

    @GetMapping("/products")
    public List<ProductDTO> findAll(Authentication authentication) {
        return this.productService.findAll(authentication);
    }

    @GetMapping("/products/{id}")
    public ProductDTO find(@PathVariable String id, Authentication authentication) {
        return this.productService.find(id, authentication);
    }

    @PutMapping("/products/{id}")
    public ProductDTO update(@RequestBody ProductDTO DTO, Authentication authentication) {
        return this.productService.update(DTO, authentication);
    }

    @DeleteMapping("/products/{productId}")
    public void delete(@PathVariable String productId, Authentication authentication) {
        this.productService.delete(productId, authentication);
    }

}
