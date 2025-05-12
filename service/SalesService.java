package com.smartstock.service;

import com.smartstock.dto.SalesDto;
import com.smartstock.exception.CustomException;
import com.smartstock.model.Product;
import com.smartstock.model.Sale;
import com.smartstock.model.User;
import com.smartstock.repository.ProductRepository;
import com.smartstock.repository.SalesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SalesService {
    private final SalesRepository salesRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public List<SalesDto> findAll() {
        return salesRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public SalesDto findById(Long id) {
        Sale sale = salesRepository.findById(id)
                .orElseThrow(() -> new CustomException("Sale not found"));
        return convertToDto(sale);
    }

    public SalesDto save(SalesDto salesDto) {
        User currentUser = userService.getCurrentUser();
        Product product = productRepository.findById(salesDto.getProductId())
                .orElseThrow(() -> new CustomException("Product not found"));

        // Update product stock
        product.setStockQuantity(product.getStockQuantity() - salesDto.getQuantity());
        productRepository.save(product);

        Sale sale = new Sale();
        sale.setProduct(product);
        sale.setQuantity(salesDto.getQuantity());
        sale.setSalePrice(salesDto.getSalePrice());
        sale.setUser(currentUser);

        Sale savedSale = salesRepository.save(sale);
        return convertToDto(savedSale);
    }

    public List<SalesDto> findMonthlySales() {
        return salesRepository.findMonthlySales().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private SalesDto convertToDto(Sale sale) {
        SalesDto dto = new SalesDto();
        dto.setId(sale.getId());
        dto.setProductId(sale.getProduct().getId());
        dto.setProductName(sale.getProduct().getName());
        dto.setQuantity(sale.getQuantity());
        dto.setSalePrice(sale.getSalePrice());
        dto.setSaleDate(sale.getSaleDate());
        dto.setUserId(sale.getUser().getId());
        dto.setUserName(sale.getUser().getName());
        return dto;
    }
}