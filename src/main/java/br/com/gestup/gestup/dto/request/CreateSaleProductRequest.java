package br.com.gestup.gestup.dto.request;

public record CreateSaleProductRequest(String productId, int quantity, double discountPercentage) {
}
