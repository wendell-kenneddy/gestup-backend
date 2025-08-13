package br.com.gestup.gestup.mapper;

import org.springframework.stereotype.Component;

import br.com.gestup.gestup.dto.ProductDTO;
import br.com.gestup.gestup.model.Product;

@Component
public class ProductMapperImpl implements IMapper<Product, ProductDTO> {
    public Product DTOToEntity(ProductDTO DTO) {
        return new Product(DTO.name(), DTO.description(), DTO.price());
    }

    public ProductDTO entityToDTO(Product entity) {
        return new ProductDTO(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice());
    }
}
