package com.smartstock.service;

import com.smartstock.dto.DashboardStats;
import com.smartstock.repository.ProductRepository;
import com.smartstock.repository.SalesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DashboardService {
    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;

    public DashboardStats getDashboardStats() {
        DashboardStats stats = new DashboardStats();
        stats.setTotalProducts(productRepository.count());
        stats.setTotalBrands((long) productRepository.countProductsByBrand().size());

        
        // Calculate monthly sales
        Double monthlySales = salesRepository.findMonthlySales().stream()
                .mapToDouble(sale -> sale.getSalePrice() * sale.getQuantity())
                .sum();
        stats.setMonthlySales(monthlySales);
        
        // Count low stock products
        stats.setLowStockItems(productRepository.findByStockQuantityLessThan(10).size());
        
        return stats;
    }

    public List<Object[]> getTopProducts() {
        return salesRepository.findTopProducts();
    }
}