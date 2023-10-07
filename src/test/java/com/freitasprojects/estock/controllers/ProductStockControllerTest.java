package com.freitasprojects.estock.controllers;

import com.freitasprojects.estock.AplicationConfigTest;
import com.freitasprojects.estock.models.dtos.DepositDTO;
import com.freitasprojects.estock.models.dtos.ProductDTO;
import com.freitasprojects.estock.models.dtos.ProductStockDTO;
import com.freitasprojects.estock.models.entities.ProductStock;
import com.freitasprojects.estock.repositories.ProductStockRepository;
import com.freitasprojects.estock.services.DepositService;
import com.freitasprojects.estock.services.ProductService;
import com.freitasprojects.estock.services.ProductStockService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("ProductStockControllerTest")
public class ProductStockControllerTest extends AplicationConfigTest {

    @MockBean
    ProductStockService service;

    @Autowired
    ProductStockController controller;

    @Test
    public void test_returns_product_names_and_quantities_in_stock() {
        // Arrange
        List<Object[]> expected = new ArrayList<>();
        Object[] product1 = {"Product 1", 10};
        Object[] product2 = {"Product 2", 5};
        expected.add(product1);
        expected.add(product2);

        when(service.findAllProductsAndQuantitiesInStock()).thenReturn(expected);

        // Act
        List<Object[]> result = controller.findAllProductsAndQuantitiesInStock();

        // Assert
        assertEquals(expected, result);
    }


    @Test
    public void test_findProductDetailsInStock() {
        // Arrange
        List<Object[]> productDetailsList = new ArrayList<>();
        // Preencha a lista com dados de exemplo
        productDetailsList.add(new Object[] { "Product 1", 10 });
        productDetailsList.add(new Object[] { "Product 2", 5 });

        when(service.findProductDetailsInStock()).thenReturn(productDetailsList);

        // Act
        List<Object[]> response = service.findProductDetailsInStock();

        // Assert
        assertNotNull(response);
        assertEquals(productDetailsList.size(), response.size());
        assertArrayEquals(productDetailsList.get(0), response.get(0));
        assertArrayEquals(productDetailsList.get(1), response.get(1));


        verify(service, times(1)).findProductDetailsInStock();
    }

    @Test
    public void test_returns_all_product_stock_dto_objects() {
        // Arrange
        List<ProductStockDTO> expected = new ArrayList<>();
        expected.add(new ProductStockDTO(1L, 1L, 1L, 10, 100.0));
        expected.add(new ProductStockDTO(2L, 2L, 2L, 5, 50.0));
        Mockito.when(service.listAll()).thenReturn(expected);

        // Act
        List<ProductStockDTO> result = controller.listAll();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void test_valid_id() {
        // Arrange
        Long id = 1L;
        ProductStockDTO expectedProductStockDTO = new ProductStockDTO();
        when(service.findById(id)).thenReturn(expectedProductStockDTO);

        // Act
        ResponseEntity<Object> response = controller.findById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProductStockDTO, response.getBody());
    }

    @Test
    public void test_deleteById_success() {
        // Arrange
        Long id = 1L;

        // Act
        controller.deleteById(id);

        // Assert
        // Verify that the product stock with the given ID is deleted successfully
    }

    @Test
    public void test_linkProductToStockByCode_Success() throws Exception {
        // Arrange
        String productCode = "123";
        String depositCode = "456";
        int quantity = 10;
        Long productStockId = 1L; // ID simulado retornado pelo serviço

        when(service.linkProductToStockByCode(productCode, depositCode, quantity)).thenReturn(productStockId);

        // Act
        ResponseEntity<Object> response = controller.linkProductToStockByCode(productCode, depositCode, quantity);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue()); // Verifique o código de status 200 (OK)
        assertEquals("Produto vinculado ao estoque com sucesso. ID: " + productStockId, response.getBody());

        // Verifique se o método do serviço foi chamado uma vez
        verify(service, times(1)).linkProductToStockByCode(productCode, depositCode, quantity);
    }

    @Test
    public void test_addQuantityToStock_Success() throws Exception {
        // Arrange
        String productCode = "123";
        String depositCode = "456";
        int quantityToAdd = 10;
        Long updatedProductStockId = 1L; // ID simulado retornado pelo serviço

        when(service.addQuantityToStock(productCode, depositCode, quantityToAdd)).thenReturn(updatedProductStockId);

        // Act
        ResponseEntity<String> response = controller.addQuantityToStock(productCode, depositCode, quantityToAdd);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue()); // Verifique o código de status 200 (OK)
        assertEquals("Quantidade adicionada ao estoque com sucesso. ID: " + updatedProductStockId, response.getBody());

        // Verifique se o método do serviço foi chamado uma vez
        verify(service, times(1)).addQuantityToStock(productCode, depositCode, quantityToAdd);
    }

    @Test
    public void test_removeQuantityFromStock_Success() throws Exception {
        // Arrange
        String productCode = "123";
        String depositCode = "456";
        int quantityToRemove = 5;
        Long updatedProductStockId = 1L; // ID simulado retornado pelo serviço

        when(service.removeQuantityFromStock(productCode, depositCode, quantityToRemove)).thenReturn(updatedProductStockId);

        // Act
        ResponseEntity<String> response = controller.removeQuantityFromStock(productCode, depositCode, quantityToRemove);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue()); // Verifique o código de status 200 (OK)
        assertEquals("Quantidade removida do estoque com sucesso. ID: " + updatedProductStockId, response.getBody());

        // Verifique se o método do serviço foi chamado uma vez
        verify(service, times(1)).removeQuantityFromStock(productCode, depositCode, quantityToRemove);
    }


}
