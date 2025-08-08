package br.com.gestup.gestup.dto;

import java.util.Collection;

public record SaleDTO(String id, String customerId, Collection<SaleProductDTO> saleProducts) {
}
