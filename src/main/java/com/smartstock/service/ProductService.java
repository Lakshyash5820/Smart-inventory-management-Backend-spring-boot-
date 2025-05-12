package com.smartstock.service;

import com.smartstock.dto.ProductDto;
import com.smartstock.exception.CustomException;
import com.smartstock.model.Product;
import com.smartstock.model.User;
import com.smartstock.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException("Product not found"));
        return convertToDto(product);
    }

    public ProductDto save(ProductDto productDto) {
        User currentUser = userService.getCurrentUser();
        
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setSubCategory(productDto.getSubCategory());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setPrice(productDto.getPrice());
        product.setThreshold(productDto.getThreshold());
        product.setExpiryDate(productDto.getExpiryDate());
        product.setSupplier(productDto.getSupplier());
        product.setImageUrl(productDto.getImageUrl());
        product.setCreatedBy(currentUser);

        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    public ProductDto update(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new CustomException("Product not found"));

        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setBrand(productDto.getBrand());
        existingProduct.setCategory(productDto.getCategory());
        existingProduct.setSubCategory(productDto.getSubCategory());
        existingProduct.setStockQuantity(productDto.getStockQuantity());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setThreshold(productDto.getThreshold());
        existingProduct.setExpiryDate(productDto.getExpiryDate());
        existingProduct.setSupplier(productDto.getSupplier());
        existingProduct.setImageUrl(productDto.getImageUrl());

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDto(updatedProduct);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductDto> findLowStockProducts() {
        return productRepository.findByStockQuantityLessThan(10).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> findExpiringSoonProducts() {
        // Products expiring in the next 30 days
        Date futureDate = new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000);
        return productRepository.findExpiringSoonProducts(futureDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setBrand(product.getBrand());
        dto.setCategory(product.getCategory());
        dto.setSubCategory(product.getSubCategory());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setPrice(product.getPrice());
        dto.setThreshold(product.getThreshold());
        dto.setExpiryDate(product.getExpiryDate());
        dto.setSupplier(product.getSupplier());
        dto.setImageUrl(product.getImageUrl());
        dto.setCreatedBy(product.getCreatedBy() != null ? product.getCreatedBy().getId() : null);
        return dto;
    }
}