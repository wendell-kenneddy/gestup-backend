package br.com.gestup.gestup.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.gestup.gestup.model.Sale;
import jakarta.transaction.Transactional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, String> {
    Optional<Sale> findByIdAndUserId(String saleId, String userId);

    @Modifying()
    @Transactional
    @Query("DELETE Sale s WHERE s.id = ?1 AND s.user.id = ?2")
    void deleteByIdAndUserId(String saleId, String userId);

    @Query("SELECT s FROM Sale s WHERE s.user.id = ?1")
    List<Sale> findAllByUserId(String userId);
}
