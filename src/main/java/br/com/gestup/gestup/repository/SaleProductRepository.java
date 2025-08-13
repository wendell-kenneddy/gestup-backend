package br.com.gestup.gestup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gestup.gestup.model.SaleProduct;

@Repository
public interface SaleProductRepository extends JpaRepository<SaleProduct, String> {

}
