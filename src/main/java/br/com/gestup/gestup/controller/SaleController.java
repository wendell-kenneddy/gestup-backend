package br.com.gestup.gestup.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestup.gestup.dto.SaleDTO;
import br.com.gestup.gestup.dto.request.CreateSaleRequest;
import br.com.gestup.gestup.service.SaleService;

@RestController
@RequestMapping("/sales")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping("")
    public String create(@RequestBody CreateSaleRequest request, Authentication authentication) {
        return this.saleService.createSale(request, authentication);
    }

    @GetMapping("/{saleId}")
    public SaleDTO find(@PathVariable String saleId, Authentication authentication) {
        return this.saleService.find(saleId, authentication);
    }

    @GetMapping("")
    public List<SaleDTO> findAll(Authentication authentication) {
        return this.saleService.findAll(authentication);
    }

    @DeleteMapping("/{saleId}")
    public void delete(@PathVariable String saleId, Authentication authentication) {
        this.saleService.delete(saleId, authentication);
    }

}
