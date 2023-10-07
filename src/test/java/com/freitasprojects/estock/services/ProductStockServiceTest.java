package com.freitasprojects.estock.services;

import com.freitasprojects.estock.AplicationConfigTest;
import com.freitasprojects.estock.models.dtos.DepositDTO;
import com.freitasprojects.estock.models.dtos.ProductDTO;
import com.freitasprojects.estock.models.dtos.ProductStockDTO;
import com.freitasprojects.estock.models.entities.ProductStock;
import com.freitasprojects.estock.repositories.ProductStockRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("ProductStockServiceTest")
public class ProductStockServiceTest extends AplicationConfigTest {

    @MockBean
    ProductStockRepository repository;
    @MockBean
    ModelMapper mapper;


    //Services auxiliares
    @MockBean
    ProductService productService;
    @MockBean
    DepositService depositService;

    @Autowired
    ProductStockService productStockService;


    @Test
    public void deveFuncionarCorretamente(){
        System.out.println("ok");
    }

    @Test
    public void test_returns_list_with_product_details_in_stock() {
        // Arrange
        List<Object[]> expected = new ArrayList<>();
        Object[] product1 = {"code1", "description1", "deposit1", 10};
        Object[] product2 = {"code2", "description2", "deposit2", 5};
        expected.add(product1);
        expected.add(product2);

        when(repository.findProductDetailsInStock()).thenReturn(expected);

        // Act
        List<Object[]> result = productStockService.findProductDetailsInStock();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void test_returns_list_with_product_details_and_quantity() {
        // Arrange
        List<Object[]> expected = new ArrayList<>();
        Object[] product1 = {1L, "Product 1", 10};
        Object[] product2 = {2L, "Product 2", 5};
        expected.add(product1);
        expected.add(product2);

        when(repository.findAllProductsAndQuantitiesInStock()).thenReturn(expected);

        // Act
        List<Object[]> result = productStockService.findAllProductsAndQuantitiesInStock();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void test_return_list_with_data() {
        // Arrange
        List<ProductStock> productStockList = new ArrayList<>();
        productStockList.add(new ProductStock());
        productStockList.add(new ProductStock());
        Mockito.when(repository.findAll()).thenReturn(productStockList);

        // Act
        List<ProductStockDTO> result = productStockService.listAll();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    public void test_delete_valid_entity() {
        // Arrange
        Long id = 1L;
        ProductStock productStock = new ProductStock();
        productStock.setId(id);
        Optional<ProductStock> optional = Optional.of(productStock);

        // Mock the repository
        ProductStockRepository repository = Mockito.mock(ProductStockRepository.class);
        Mockito.when(repository.findById(id)).thenReturn(optional);

        // Create an instance of ProductStockService and set the mocked repository
        ProductStockService productStockService = new ProductStockService();
        productStockService.repository = repository;

        // Act
        try {
            productStockService.deleteById(id);

            // Assert
            verify(repository, Mockito.times(1)).deleteById(id);
        } catch (Exception e) {
            fail("Should not throw an exception");
        }
    }


    @Test
    public void test_linkProductToStockByCode_noExistingStock() throws Exception {
        // Arrange
        String productCode = "ABC";
        String depositCode = "123";
        int quantity = 10;

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setCode(productCode);
        productDTO.setDescription("Product ABC");
        productDTO.setCostPrice(10.0);

        DepositDTO depositDTO = new DepositDTO();
        depositDTO.setId(1L);
        depositDTO.setCode(depositCode);
        depositDTO.setName("Deposit 123");

        // Configure o comportamento do mock repository para retornar um ProductStock com um ID válido ao chamar save
        when(repository.save(any(ProductStock.class))).thenAnswer(invocation -> {
            ProductStock productStock = invocation.getArgument(0);
            productStock.setId(1L); // Defina um ID válido para o objeto ProductStock
            return productStock;
        });

        when(productService.findByCode(productCode)).thenReturn(productDTO);
        when(depositService.findByCode(depositCode)).thenReturn(depositDTO);
        when(repository.findByProductIdAndDepositId(productDTO.getId(), depositDTO.getId())).thenReturn(Optional.empty());

        // Act
        Long result = productStockService.linkProductToStockByCode(productCode, depositCode, quantity);

        // Assert
        verify(productService).findByCode(productCode);
        verify(depositService).findByCode(depositCode);
        verify(repository).findByProductIdAndDepositId(productDTO.getId(), depositDTO.getId());
        verify(repository).save(any(ProductStock.class));
        assertEquals(1L, result.longValue());
    }

    @Test
    public void test_addQuantityToStock_addsQuantityToExistingProductStock() throws Exception {
        // Arrange
        String productCode = "ABC";
        String depositCode = "123";
        int quantityToAdd = 10;

        ProductStock existingProductStock = new ProductStock();
        existingProductStock.setQuantity(20);
        existingProductStock.setProductId(1L);

        when(repository.findByProductCodeAndDepositCode(productCode, depositCode)).thenReturn(Optional.of(existingProductStock));

        // Act
        Long result = productStockService.addQuantityToStock(productCode, depositCode, quantityToAdd);

        // Assert
        assertEquals(existingProductStock.getId(), result);
        assertEquals(30, existingProductStock.getQuantity());
    }

    @Test
    public void test_remove_quantity_positive() {
        // Arrange
        String productCode = "ABC";
        String depositCode = "123";
        int quantityToRemove = 5;

        ProductStock productStock = new ProductStock();
        productStock.setQuantity(10);
        productStock.setProductId(1L);
        productStock.setDepositId(1L);

        Optional<ProductStock> optional = Optional.of(productStock);

        when(repository.findByProductCodeAndDepositCode(productCode, depositCode)).thenReturn(optional);

        // Act
        Long result = null;
        try {
            result = productStockService.removeQuantityFromStock(productCode, depositCode, quantityToRemove);
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }

        // Assert
        assertEquals(productStock.getId(), result);
        assertEquals(5, productStock.getQuantity());
    }




}
