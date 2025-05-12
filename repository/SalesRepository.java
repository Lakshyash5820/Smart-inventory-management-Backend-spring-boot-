package com.smartstock.repository;

import com.smartstock.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT s FROM Sale s WHERE YEAR(s.saleDate) = YEAR(CURRENT_DATE) AND MONTH(s.saleDate) = MONTH(CURRENT_DATE)")
    List<Sale> findMonthlySales();
    
    @Query("SELECT s.product.name, SUM(s.quantity), SUM(s.salePrice * s.quantity) FROM Sale s " +
           "WHERE YEAR(s.saleDate) = YEAR(CURRENT_DATE) AND MONTH(s.saleDate) = MONTH(CURRENT_DATE) " +
           "GROUP BY s.product.name ORDER BY SUM(s.quantity) DESC")
    List<Object[]> findTopProducts();
}