package br.com.gestup.gestup.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.gestup.gestup.dto.ProductDTO;
import br.com.gestup.gestup.mapper.IMapper;
import br.com.gestup.gestup.model.Product;
import br.com.gestup.gestup.model.User;
import br.com.gestup.gestup.repository.ProductRepository;
import br.com.gestup.gestup.repository.UserRepository;

@Service
@PreAuthorize("hasAuthority('SCOPE_seller')")
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final IMapper<Product, ProductDTO> productMapper;

    public ProductService(ProductRepository productRepository, UserRepository userRepository,
            IMapper<Product, ProductDTO> productMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productMapper = productMapper;
    }

    public ProductDTO create(ProductDTO DTO, Authentication authentication) {
        User user = this.getUserIfExists(authentication);
        Product product = new Product(DTO.name(), DTO.description(), DTO.price());
        product.setUser(user);
        return this.productMapper.entityToDTO(this.productRepository.save(product));
    }

    public ProductDTO find(String productId, Authentication authentication) {
        Product product = this.productRepository.findByIdAndUserId(productId, authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Product not found."));
        return this.productMapper.entityToDTO(product);
    }

    public List<ProductDTO> findAll(Authentication authentication) {
        List<Product> products = this.productRepository.findAllByUserId(authentication.getName());
        return products.stream().map(productMapper::entityToDTO)
                .toList();
    }

    public ProductDTO update(ProductDTO DTO, Authentication authentication) {
        Product product = this.productRepository.findByIdAndUserId(DTO.id(), authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Product not found."));
        product.setName(DTO.name());
        product.setDescription(DTO.description());
        product.setPrice(DTO.price());
        this.productRepository.save(product);
        return DTO;
    }

    public void delete(String productId, Authentication authentication) {
        this.productRepository.deleteByIdAndUserId(productId, authentication.getName());
    }

    private User getUserIfExists(Authentication authentication) throws UsernameNotFoundException {
        return this.userRepository.findById(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials."));
    }
}
