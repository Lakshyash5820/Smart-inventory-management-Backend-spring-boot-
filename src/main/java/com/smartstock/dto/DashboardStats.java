package com.smartstock.dto;

import lombok.Data;

@Data
public class DashboardStats {
    private Long totalProducts;
    private Long totalBrands;
    private Double monthlySales;
    private Integer lowStockItems;
}