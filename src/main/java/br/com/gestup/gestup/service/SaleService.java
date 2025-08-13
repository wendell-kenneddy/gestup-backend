package br.com.gestup.gestup.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.gestup.gestup.dto.SaleDTO;
import br.com.gestup.gestup.dto.request.CreateSaleRequest;
import br.com.gestup.gestup.mapper.IMapper;
import br.com.gestup.gestup.model.Customer;
import br.com.gestup.gestup.model.PaymentMethod;
import br.com.gestup.gestup.model.Product;
import br.com.gestup.gestup.model.Sale;
import br.com.gestup.gestup.model.SaleProduct;
import br.com.gestup.gestup.model.User;
import br.com.gestup.gestup.repository.SaleProductRepository;
import br.com.gestup.gestup.repository.SaleRepository;
import br.com.gestup.gestup.repository.UserRepository;

@Service
@PreAuthorize("hasAuthority('SCOPE_seller')")
public class SaleService {
    private final IMapper<Sale, SaleDTO> saleMapper;
    private final SaleRepository saleRepository;
    private final SaleProductRepository saleProductRepository;
    private final UserRepository userRepository;

    public SaleService(
            IMapper<Sale, SaleDTO> saleMapper,
            SaleRepository saleRepository,
            UserRepository userRepository,
            SaleProductRepository saleProductRepository) {
        this.saleMapper = saleMapper;
        this.saleRepository = saleRepository;
        this.userRepository = userRepository;
        this.saleProductRepository = saleProductRepository;
    }

    public String createSale(CreateSaleRequest request, Authentication authentication) {
        User user = this.getUserIfExists(authentication);
        Customer customer = new Customer(null);
        PaymentMethod paymentMethod = new PaymentMethod(null);
        List<SaleProduct> saleProducts = request
                .products().stream()
                .map(p -> new SaleProduct(
                        p.quantity(),
                        p.discountPercentage(),
                        new Product(p.productId())))
                .toList();

        customer.setId(request.customerId());
        paymentMethod.setId(request.paymentMethodId());

        Sale sale = this.saleRepository.save(new Sale(user, customer, paymentMethod));
        saleProducts.forEach(s -> s.setSale(sale));
        this.saleProductRepository.saveAll(saleProducts);
        return sale.getId();
    }

    public SaleDTO find(String saleId, Authentication authentication) {
        Sale sale = this.saleRepository.findByIdAndUserId(saleId, authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Sale not found."));
        return this.saleMapper.entityToDTO(sale);
    }

    public List<SaleDTO> findAll(Authentication authentication) {
        return this.saleRepository.findAllByUserId(authentication.getName()).stream().map(this.saleMapper::entityToDTO)
                .toList();
    }

    public void delete(String saleId, Authentication authentication) {
        this.saleRepository.deleteByIdAndUserId(saleId, authentication.getName());
    }

    private User getUserIfExists(Authentication authentication) throws UsernameNotFoundException {
        return this.userRepository.findById(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials."));
    }

}
