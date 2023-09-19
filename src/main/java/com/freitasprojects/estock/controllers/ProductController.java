package com.freitasprojects.estock.controllers;

import com.freitasprojects.estock.models.dtos.ProductDTO;
import com.freitasprojects.estock.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/products")
//@Tag() SWAGGER DOCUMENTATION
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping
    public List<ProductDTO> listAll() {
        return service.listAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        ProductDTO product = service.findById(id);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(Long id) {
        service.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody ProductDTO productDTO) {
        try {
            return ResponseEntity.ok(service.create(productDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@RequestBody @Validated ProductDTO productDTO) {
        try {
            Long updatedProductId = service.update(productDTO);
            return ResponseEntity.ok("Produto atualizado com sucesso. ID: " + updatedProductId);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao atualizar o produto: " + e.getMessage());
        }
    }


}
