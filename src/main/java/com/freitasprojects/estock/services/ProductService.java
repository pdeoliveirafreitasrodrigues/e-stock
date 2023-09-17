package com.freitasprojects.estock.services;

import com.freitasprojects.estock.models.dtos.ProductDTO;
import com.freitasprojects.estock.models.entities.Product;
import com.freitasprojects.estock.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.freitasprojects.estock.consts.ExceptionConsts.PRODUCT_INSERT_ERROR;
import static java.util.stream.Collectors.toList;

public class ProductService{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper mapper;

    public List<ProductDTO> listAll() {
        List<ProductDTO> productDTOList = productRepository.findAll()
                .stream()
                .map(product -> mapper.map(product, ProductDTO.class)).toList();

        return productDTOList;
    }

    public ProductDTO findById(Long id) {
        Optional<Product> optional = productRepository.findById(id);
        ProductDTO productDTO = null;

        if (optional.isPresent()) {
            productDTO = mapper.map(optional.get(), ProductDTO.class);
        }

        return productDTO;
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Long save(ProductDTO productDTO) throws Exception {
        try {
            Product product = mapper.map(productDTO, Product.class);
            Product created = productRepository.save(product);

            return created.getId();
        } catch (Exception e) {
            throw new Exception(PRODUCT_INSERT_ERROR);
        }
    }

    public Long create(ProductDTO productDTO) throws Exception{
        return save(productDTO);
    }

    public Long update(ProductDTO productDTO) throws Exception {
        var optional = productRepository.findById(productDTO.getId());

        if(optional.isPresent()) {
            Product product = optional.get();

            if(!productDTO.getDescription().isEmpty())
                product.setDescription(productDTO.getDescription());
            if (productDTO.getCostPrice() != null && productDTO.getCostPrice() != 0.0f) {
                product.setCostPrice(productDTO.getCostPrice());
            }
            // ...

            productRepository.save(product);
            return product.getId();
        } else {
            throw new Exception("");
        }
    }



}
