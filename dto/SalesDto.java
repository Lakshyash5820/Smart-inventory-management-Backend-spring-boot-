package com.smartstock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDto {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double salePrice;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date saleDate;
    
    private Long userId;
    private String userName;
}