package com.freitasprojects.estock.services;

import com.freitasprojects.estock.repositories.ProductStockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductStockService {

    @Autowired
    ProductStockRepository repository;

    @Autowired
    ModelMapper mapper;

    public List<Object[]> findProductDetailsInStock() {
        return repository.findProductDetailsInStock();
    }

    public List<Object[]> findAllProductsAndQuantitiesInStock() {
        return repository.findAllProductsAndQuantitiesInStock();
    }





}
