package com.smartstock.controller;

import com.smartstock.dto.DashboardStats;
import com.smartstock.service.DashboardService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStats> getDashboardStats() {
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }

    @GetMapping("/top-products")
    public ResponseEntity<List<Object[]>> getTopProducts() {
        return ResponseEntity.ok(dashboardService.getTopProducts());
    }
}