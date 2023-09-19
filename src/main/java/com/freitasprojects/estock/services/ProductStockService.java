package com.freitasprojects.estock.services;

import com.freitasprojects.estock.models.dtos.ProductDTO;
import com.freitasprojects.estock.models.dtos.ProductStockDTO;
import com.freitasprojects.estock.models.entities.Product;
import com.freitasprojects.estock.models.entities.ProductStock;
import com.freitasprojects.estock.repositories.ProductStockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

    public List<ProductStockDTO> listAll() {

        List<ProductStockDTO> productStockDTOList = repository.findAll()
                .stream()
                .map(productStock -> mapper.map(productStock, ProductStockDTO.class)).toList();

        return productStockDTOList;
    }

    public ProductStockDTO findById(Long id){
        Optional<ProductStock> optional = repository.findById(id);
        ProductStockDTO productStockDTO = null;

        if (optional.isPresent()) {
            productStockDTO = mapper.map(optional.get(), ProductStockDTO.class);
        }

        return productStockDTO;
    }




}
