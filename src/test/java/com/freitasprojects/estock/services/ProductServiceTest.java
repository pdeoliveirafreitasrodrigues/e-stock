package com.freitasprojects.estock.services;

import com.freitasprojects.estock.models.dtos.ProductDTO;
import com.freitasprojects.estock.models.entities.Product;
import com.freitasprojects.estock.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_return_all_products() {
        // Arrange
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1L, "code1", "description1", 10.0));
        productList.add(new Product(2L, "code2", "description2", 20.0));
        Mockito.when(productRepository.findAll()).thenReturn(productList);

        // Act
        List<ProductDTO> result = productService.listAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("code1", result.get(0).getCode());
        assertEquals("description1", result.get(0).getDescription());
        assertEquals(10.0, result.get(0).getCostPrice(), 0.001);
        assertEquals("code2", result.get(1).getCode());
        assertEquals("description2", result.get(1).getDescription());
        assertEquals(20.0, result.get(1).getCostPrice(), 0.001);
    }



}
