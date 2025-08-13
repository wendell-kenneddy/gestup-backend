package br.com.gestup.gestup.mapper;

import org.springframework.stereotype.Component;

import br.com.gestup.gestup.dto.CustomerDTO;
import br.com.gestup.gestup.dto.PaymentMethodDTO;
import br.com.gestup.gestup.dto.SaleDTO;
import br.com.gestup.gestup.dto.SaleProductDTO;
import br.com.gestup.gestup.model.Customer;
import br.com.gestup.gestup.model.PaymentMethod;
import br.com.gestup.gestup.model.Sale;
import br.com.gestup.gestup.model.SaleProduct;

@Component
public class SaleMapperImpl implements IMapper<Sale, SaleDTO> {
    private IMapper<SaleProduct, SaleProductDTO> saleProductMapper;
    private IMapper<Customer, CustomerDTO> customerMapper;
    private IMapper<PaymentMethod, PaymentMethodDTO> paymentMethodMapper;

    public SaleMapperImpl(
            IMapper<SaleProduct, SaleProductDTO> saleProductMapper,
            IMapper<Customer, CustomerDTO> customerMapper,
            IMapper<PaymentMethod, PaymentMethodDTO> paymentMethodMapper) {
        this.saleProductMapper = saleProductMapper;
        this.customerMapper = customerMapper;
        this.paymentMethodMapper = paymentMethodMapper;
    }

    @Override
    public Sale DTOToEntity(SaleDTO DTO) {
        return null;
    }

    @Override
    public SaleDTO entityToDTO(Sale entity) {
        return new SaleDTO(
                entity.getId(),
                this.customerMapper.entityToDTO(entity.getCustomer()),
                this.paymentMethodMapper.entityToDTO(entity.getPaymentMethod()),
                entity.getSaleProducts().stream().map(this.saleProductMapper::entityToDTO).toList());
    }
}
