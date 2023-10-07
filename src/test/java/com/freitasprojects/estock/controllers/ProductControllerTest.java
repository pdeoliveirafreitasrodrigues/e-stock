package com.freitasprojects.estock.controllers;

import com.freitasprojects.estock.AplicationConfigTest;
import com.freitasprojects.estock.models.dtos.ProductDTO;
import com.freitasprojects.estock.services.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ProductControllerTest")
public class ProductControllerTest extends AplicationConfigTest {

    @MockBean
    ProductService service;

    @Autowired
    ProductController productController;


    @Test
    public void test_returns_all_products_when_database_has_products() {
        // Arrange
        List<ProductDTO> expectedProducts = new ArrayList<>();
        expectedProducts.add(new ProductDTO(1L, "code1", "description1", 10.0));
        expectedProducts.add(new ProductDTO(2L, "code2", "description2", 20.0));
        Mockito.when(service.listAll()).thenReturn(expectedProducts);

        // Act
        List<ProductDTO> actualProducts = productController.listAll();

        // Assert
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void test_valid_id() {
        // Arrange
        Long id = 1L;
        ProductDTO productDTO = new ProductDTO();
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok(productDTO);
        Mockito.when(service.findById(id)).thenReturn(productDTO);

        // Act
        ResponseEntity<Object> actualResponse = productController.findById(id);

        // Assert
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
    }

    @Test
    public void test_deleteById_success() {
        // Arrange
        Long id = 1L;

        // Act
        productController.deleteById(id);

        // Assert
        // Verify that the product with the given id is deleted successfully
    }

    @Test
    public void test_create_valid_input() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCode("123");
        productDTO.setDescription("Test Product");
        productDTO.setCostPrice(10.0);

        // Act
        ResponseEntity<Object> response = productController.create(productDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
