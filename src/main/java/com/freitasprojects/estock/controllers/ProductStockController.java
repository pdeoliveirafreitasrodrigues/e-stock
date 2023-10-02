package com.freitasprojects.estock.controllers;

import com.freitasprojects.estock.models.dtos.ProductStockDTO;
import com.freitasprojects.estock.services.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/productStock")
public class ProductStockController {

    @Autowired
    ProductStockService service;

    @GetMapping(value = "/productsAndQuantitiesInStock") //ok
    public List<Object[]> findAllProductsAndQuantitiesInStock() {
        return service.findAllProductsAndQuantitiesInStock();
    }

    @GetMapping(value = "/detailsInStock") //ok
    public List<Object[]> findProductDetailsInStock() {
        return service.findProductDetailsInStock();
    }

    @GetMapping(value = "/listAll") //ok
    public List<ProductStockDTO> listAll() {
        return service.listAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(Long id){
        ProductStockDTO productStockDTO = service.findById(id);

        if (productStockDTO == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productStockDTO);
    }

    @DeleteMapping(value = "/{id}") //ok
    public void deleteById(@PathVariable Long id){
        try {
            service.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

  /*  @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody ProductStockDTO productStockDTO){
     try {
        return ResponseEntity.ok(service.create(productStockDTO));
     } catch (Exception e) {
         return ResponseEntity.badRequest().body(e.getMessage());
     }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProductStock(@RequestBody @Validated ProductStockDTO productStockDTO) {
        try {
            Long updatedProductStockId = service.update(productStockDTO);
            return ResponseEntity.ok("Produto atualizado com sucesso. ID: " + updatedProductStockId);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao atualizar o produto em seu estoque: " + e.getMessage());
        }
    }*/

    @PostMapping("/linkProductToStock/{productCode}/{depositCode}/{quantity}")
    public ResponseEntity<Object> linkProductToStockByCode(
            @PathVariable String productCode,
            @PathVariable String depositCode,
            @PathVariable int quantity) {
        try {
            Long productStockId = service.linkProductToStockByCode(productCode, depositCode, quantity);
            return ResponseEntity.ok("Produto vinculado ao estoque com sucesso. ID: " + productStockId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/addQuantityToStock/{productCode}/{depositCode}/{quantityToAdd}")
    public ResponseEntity<String> addQuantityToStock(
            @PathVariable String productCode,
            @PathVariable String depositCode,
            @PathVariable int quantityToAdd) {
        try {
            Long updatedProductStockId = service.addQuantityToStock(productCode, depositCode, quantityToAdd);
            return ResponseEntity.ok("Quantidade adicionada ao estoque com sucesso. ID: " + updatedProductStockId);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/removeQuantityFromStock/{productCode}/{depositCode}/{quantityToRemove}")
    public ResponseEntity<String> removeQuantityFromStock(
            @PathVariable String productCode,
            @PathVariable String depositCode,
            @PathVariable int quantityToRemove) {
        try {
            Long updatedProductStockId = service.removeQuantityFromStock(productCode, depositCode, quantityToRemove);
            return ResponseEntity.ok("Quantidade removida do estoque com sucesso. ID: " + updatedProductStockId);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


}
