package br.com.gestup.gestup.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestup.gestup.dto.PaymentMethodDTO;
import br.com.gestup.gestup.dto.request.UpsertPaymentMethodRequest;
import br.com.gestup.gestup.service.PaymentMethodService;

@RestController
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @PostMapping("/payment-methods")
    public PaymentMethodDTO create(@RequestBody UpsertPaymentMethodRequest request) {
        return this.paymentMethodService.create(request);
    }

    @GetMapping("/payment-methods/{id}")
    public PaymentMethodDTO find(@PathVariable String id) {
        return this.paymentMethodService.find(id);
    }

    @GetMapping("/payment-methods")
    public List<PaymentMethodDTO> findAll() {
        return this.paymentMethodService.findAll();
    }

    @PutMapping("/payment-methods/{id}")
    public PaymentMethodDTO update(@PathVariable String id, @RequestBody UpsertPaymentMethodRequest request) {
        return this.paymentMethodService.update(id, request);
    }

    @DeleteMapping("/payment-methods/{id}")
    public void delete(@PathVariable String id) {
        this.paymentMethodService.delete(id);
    }
}
