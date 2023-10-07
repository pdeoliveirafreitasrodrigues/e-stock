package com.freitasprojects.estock.services;

import com.freitasprojects.estock.AplicationConfigTest;
import com.freitasprojects.estock.models.dtos.ProductDTO;
import com.freitasprojects.estock.models.entities.Product;
import com.freitasprojects.estock.repositories.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;



@DisplayName("ProductServiceTest")
public class ProductServiceTest extends AplicationConfigTest {

    @MockBean
    ProductRepository productRepository;

    @MockBean
    ModelMapper mapper;

    @Autowired
    private ProductService productService;

    @Test
    public void deveFuncionarCorretamente(){
        System.out.println("ok");
    }

    @Test
    public void test_save_new_product_successfully() throws Exception {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCode("123");
        productDTO.setDescription("New Product");
        productDTO.setCostPrice(10.0);

        Product product = new Product();
        product.setId(1L);
        product.setCode("123");
        product.setDescription("New Product");
        product.setCostPrice(10.0);

        when(mapper.map(productDTO, Product.class)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        // Act
        Long result = productService.save(productDTO);

        // Assert
        assertEquals(1L, result.longValue());
    }

    @Test
    public void test_throw_exception_when_saving_product_with_existing_code() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCode("123");
        productDTO.setDescription("New Product");
        productDTO.setCostPrice(10.0);

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setCode("123");
        existingProduct.setDescription("Existing Product");
        existingProduct.setCostPrice(20.0);

        when(productRepository.findByCode("123")).thenReturn(Optional.of(existingProduct));

        // Act and Assert
        assertThrows(Exception.class, () -> productService.save(productDTO));
    }

    @Test
    public void test_valid_code() {
        // Arrange
        String code = "ABC123";
        ProductDTO expectedProductDTO = new ProductDTO();
        // Mock the productRepository.findByCode method to return an Optional containing a Product entity
        when(productRepository.findByCode(code)).thenReturn(Optional.of(new Product()));
        // Mock the mapper.map method to return the expectedProductDTO
        when(mapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(expectedProductDTO);

        // Act
        ProductDTO result = productService.findByCode(code);

        // Assert
        assertEquals(expectedProductDTO, result);
    }

    @Test
    public void test_returnProductDTOWithSameId() {
        // Arrange
        Long id = 1L;
        Product product = new Product(id, "code", "description", 10.0);
        ProductDTO expectedProductDTO = new ProductDTO();
        expectedProductDTO.setId(id);

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Mockito.when(mapper.map(product, ProductDTO.class)).thenReturn(expectedProductDTO);

        // Act
        ProductDTO result = productService.findById(id);

        // Assert
        assertEquals(expectedProductDTO.getId(), result.getId());
    }

    @Test
    public void test_successfully_deletes_product_with_valid_id() {
        // Arrange
        Long id = 1L;

        // Act
        productService.deleteById(id);

        // Assert
        // Verify that the product with the given id is deleted from the repository
        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    public void test_create_valid_data() throws Exception {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCode("123");
        productDTO.setDescription("Test Product");
        productDTO.setCostPrice(10.0);

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setCode("123");
        expectedProduct.setDescription("Test Product");
        expectedProduct.setCostPrice(10.0);

        when(mapper.map(productDTO, Product.class)).thenReturn(expectedProduct);
        when(productRepository.save(expectedProduct)).thenReturn(expectedProduct);

        // Act
        Long productId = productService.create(productDTO);

        // Assert
        assertNotNull(productId);
        assertEquals(1L, productId.longValue());
    }


    @Test
    public void test_update_description_and_cost_price() throws Exception {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setDescription("New Description");
        productDTO.setCostPrice(10.0);

        Product product = new Product();
        product.setId(1L);
        product.setDescription("Old Description");
        product.setCostPrice(5.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        Long updatedProductId = productService.update(productDTO);

        // Assert
        assertEquals(1L, updatedProductId);
        assertEquals("New Description", product.getDescription());
        assertEquals(10.0, product.getCostPrice(), 0.001);
    }

}
