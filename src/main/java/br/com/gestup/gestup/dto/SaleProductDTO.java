package br.com.gestup.gestup.dto;

public record SaleProductDTO(String id, ProductDTO product, int quantity, double discountPercentage) {
}
