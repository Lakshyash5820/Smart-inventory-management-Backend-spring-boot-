package com.smartstock.controller;

import com.smartstock.dto.SalesDto;
import com.smartstock.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesController {
    private final SalesService salesService;

    @GetMapping
    public ResponseEntity<List<SalesDto>> getAllSales() {
        return ResponseEntity.ok(salesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesDto> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(salesService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SalesDto> createSale(@RequestBody SalesDto salesDto) {
        return ResponseEntity.ok(salesService.save(salesDto));
    }

    @GetMapping("/monthly")
    public ResponseEntity<List<SalesDto>> getMonthlySales() {
        return ResponseEntity.ok(salesService.findMonthlySales());
    }
}