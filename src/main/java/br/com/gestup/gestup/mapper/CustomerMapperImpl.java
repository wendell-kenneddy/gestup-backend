package br.com.gestup.gestup.mapper;

import org.springframework.stereotype.Component;

import br.com.gestup.gestup.dto.CustomerDTO;
import br.com.gestup.gestup.model.Customer;

@Component
public class CustomerMapperImpl implements IMapper<Customer, CustomerDTO> {
    @Override
    public Customer DTOToEntity(CustomerDTO DTO) {
        return new Customer(DTO.name());
    }

    public CustomerDTO entityToDTO(Customer entity) {
        return new CustomerDTO(entity.getId(), entity.getName());
    }
}
