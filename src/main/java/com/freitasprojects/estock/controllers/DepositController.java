package com.freitasprojects.estock.controllers;

import com.freitasprojects.estock.models.dtos.DepositDTO;
import com.freitasprojects.estock.services.DepositService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/deposits")
public class DepositController {

    @Autowired
    DepositService service;

    @GetMapping(value = "/listAll")
    public List<DepositDTO> listAll() {
        return service.listAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) throws Exception {
        DepositDTO depositDTO = service.findById(id);

        if (depositDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(depositDTO);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody DepositDTO depositDTO) {
        try {
            return ResponseEntity.ok(service.create(depositDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@RequestBody @Validated DepositDTO depositDTO) {
        try {
            Long updatedDepositId = service.update(depositDTO);
            return ResponseEntity.ok("Produto atualizado com sucesso. ID: " + updatedDepositId);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao atualizar o produto: " + e.getMessage());
        }
    }

}
