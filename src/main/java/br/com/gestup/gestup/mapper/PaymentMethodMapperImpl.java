package br.com.gestup.gestup.mapper;

import org.springframework.stereotype.Component;

import br.com.gestup.gestup.dto.PaymentMethodDTO;
import br.com.gestup.gestup.model.PaymentMethod;

@Component
public class PaymentMethodMapperImpl implements IMapper<PaymentMethod, PaymentMethodDTO> {
    public PaymentMethod DTOToEntity(PaymentMethodDTO DTO) {
        return new PaymentMethod(DTO.name());
    }

    public PaymentMethodDTO entityToDTO(PaymentMethod entity) {
        return new PaymentMethodDTO(entity.getId(), entity.getName());
    }
}
