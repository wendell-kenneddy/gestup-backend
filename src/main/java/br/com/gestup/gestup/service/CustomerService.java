package br.com.gestup.gestup.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.gestup.gestup.dto.CustomerDTO;
import br.com.gestup.gestup.dto.request.UpsertCustomerRequest;
import br.com.gestup.gestup.mapper.IMapper;
import br.com.gestup.gestup.model.Customer;
import br.com.gestup.gestup.model.User;
import br.com.gestup.gestup.repository.CustomerRepository;
import br.com.gestup.gestup.repository.UserRepository;

@Service
@PreAuthorize("hasAuthority('SCOPE_seller')")
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final IMapper<Customer, CustomerDTO> customerMapper;

    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository,
            IMapper<Customer, CustomerDTO> customerMapper) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDTO create(UpsertCustomerRequest request, Authentication authentication) {
        User user = this.findUserIfExists(authentication);
        Customer customer = new Customer(request.name());
        customer.setUser(user);
        return this.customerMapper.entityToDTO(this.customerRepository.save(customer));
    }

    public CustomerDTO find(String customerId, Authentication authentication) throws UsernameNotFoundException {
        Customer customer = this.customerRepository
                .findByIdAndUserId(customerId, authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found."));
        return this.customerMapper.entityToDTO(customer);
    }

    public List<CustomerDTO> findAll(Authentication authentication) {
        return this.customerRepository
                .findAllByUserId(authentication.getName())
                .stream()
                .map(this.customerMapper::entityToDTO)
                .toList();
    }

    public CustomerDTO update(String customerId, UpsertCustomerRequest request, Authentication authentication) {
        Customer customer = this.customerRepository
                .findByIdAndUserId(customerId, authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found."));
        customer.setName(request.name());
        return this.customerMapper.entityToDTO(this.customerRepository.save(customer));
    }

    public void delete(String customerId, Authentication authentication) {
        this.customerRepository.deleteByIdAndUserId(customerId, authentication.getName());
    }

    private User findUserIfExists(Authentication authentication) throws UsernameNotFoundException {
        User user = this.userRepository
                .findById(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials."));
        return user;
    }

}
