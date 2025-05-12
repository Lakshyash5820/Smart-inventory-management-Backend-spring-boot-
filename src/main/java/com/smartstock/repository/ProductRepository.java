package com.smartstock.repository;

import com.smartstock.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStockQuantityLessThan(Integer threshold);
    
    @Query("SELECT p FROM Product p WHERE p.expiryDate BETWEEN CURRENT_DATE AND :futureDate")
    List<Product> findExpiringSoonProducts(Date futureDate);
    
    @Query("SELECT p.brand, COUNT(p) FROM Product p GROUP BY p.brand")
    List<Object[]> countProductsByBrand();
}