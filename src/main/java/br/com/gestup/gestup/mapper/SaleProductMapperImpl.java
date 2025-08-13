package br.com.gestup.gestup.mapper;

import org.springframework.stereotype.Component;

import br.com.gestup.gestup.dto.ProductDTO;
import br.com.gestup.gestup.dto.SaleProductDTO;
import br.com.gestup.gestup.model.Product;
import br.com.gestup.gestup.model.SaleProduct;

@Component
public class SaleProductMapperImpl implements IMapper<SaleProduct, SaleProductDTO> {
    private final IMapper<Product, ProductDTO> productMapper;

    public SaleProductMapperImpl(IMapper<Product, ProductDTO> productMapper) {
        this.productMapper = productMapper;
    }

    public SaleProductDTO entityToDTO(SaleProduct entity) {
        return new SaleProductDTO(
                entity.getId(),
                this.productMapper.entityToDTO(entity.getProduct()),
                entity.getQuantity(),
                entity.getDiscountPercentage());
    }

    public SaleProduct DTOToEntity(SaleProductDTO DTO) {
        return new SaleProduct(DTO.quantity(), DTO.discountPercentage(), this.productMapper.DTOToEntity(DTO.product()));
    }
}
