package br.com.gestup.gestup.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.gestup.gestup.model.Product;
import jakarta.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByIdAndUserId(String productId, String userId);

    @Modifying()
    @Transactional
    @Query("DELETE Product p WHERE p.id = ?1 AND p.user.id = ?2")
    void deleteByIdAndUserId(String productId, String userId);

    @Query("SELECT p FROM Product p WHERE p.user.id = ?1")
    List<Product> findAllByUserId(String userId);
}
