package com.freitasprojects.estock.controllers;

import com.freitasprojects.estock.models.dtos.ProductStockDTO;
import com.freitasprojects.estock.services.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/productStock")
public class ProductStockController {

    @Autowired
    ProductStockService service;

    @GetMapping
    public List<Object[]> findAllProductsAndQuantitiesInStock() {
        return service.findAllProductsAndQuantitiesInStock();
    }

    @GetMapping
    public List<Object[]> findProductDetailsInStock() {
        return service.findProductDetailsInStock();
    }

    @GetMapping
    public List<ProductStockDTO> listAll() {
        return service.listAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        ProductStockDTO productStockDTO = service.findById(id);

        if (productStockDTO == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productStockDTO);
    }




}
