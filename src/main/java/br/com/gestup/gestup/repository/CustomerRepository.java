package br.com.gestup.gestup.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.gestup.gestup.model.Customer;
import jakarta.transaction.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByIdAndUserId(String customerId, String userId);

    @Modifying
    @Transactional
    @Query("DELETE Customer c WHERE c.id = ?1 AND c.user.id = ?2")
    void deleteByIdAndUserId(String customerId, String userId);

    @Query("SELECT c FROM Customer c WHERE c.user.id = ?1")
    List<Customer> findAllByUserId(String customerId);
}
