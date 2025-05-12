package com.smartstock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private String category;
    private String subCategory;
    private Integer stockQuantity;
    private Double price;
    private Integer threshold;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;
    
    private String supplier;
    private String imageUrl;
    private Long createdBy;
}