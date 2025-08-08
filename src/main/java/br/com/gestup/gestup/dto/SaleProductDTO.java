package br.com.gestup.gestup.dto;

public record SaleProductDTO(String id, String productId, double price, int quantity, double discountPercentage) {
}
