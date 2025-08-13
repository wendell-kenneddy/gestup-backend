package br.com.gestup.gestup.dto;

import java.util.List;

public record SaleDTO(
        String id,
        CustomerDTO customer,
        PaymentMethodDTO paymentMethod,
        List<SaleProductDTO> saleProducts) {
}
