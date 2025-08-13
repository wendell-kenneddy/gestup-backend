package br.com.gestup.gestup.dto.request;

import java.util.List;

public record CreateSaleRequest(String customerId, String paymentMethodId, List<CreateSaleProductRequest> products) {
}
