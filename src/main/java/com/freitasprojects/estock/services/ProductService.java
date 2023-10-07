package com.freitasprojects.estock.services;

import com.freitasprojects.estock.models.dtos.ProductDTO;
import com.freitasprojects.estock.models.entities.Product;
import com.freitasprojects.estock.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.freitasprojects.estock.consts.ExceptionConsts.*;
import static java.util.stream.Collectors.toList;

@Service
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
        ProductDTO productDTO = new ProductDTO();

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

/*    public Long update(ProductDTO productDTO) throws Exception {
        var optional = productRepository.findById(productDTO.getId());

        if(optional.isPresent()) {
            Product product = optional.get();

            if(!productDTO.getDescription().isEmpty())
                product.setDescription(productDTO.getDescription());
            if(productDTO.getCostPrice() == 0.0){
                throw new Exception(PRODUCT_UPDATE_ERROR);
            }
            if (productDTO.getCostPrice() != null && productDTO.getCostPrice() != 0.0) {
                product.setCostPrice(productDTO.getCostPrice());
            }
            // ...

            productRepository.save(product);
            return product.getId();
        } else {
            throw new Exception("");
        }
    }*/

    @Transactional
    public Long update(ProductDTO productDTO) throws Exception {
        Optional<Product> optional = productRepository.findById(productDTO.getId());

        if (optional.isPresent()) {
            Product product = optional.get();

            if (!productDTO.getDescription().isEmpty()) {
                product.setDescription(productDTO.getDescription());
            }

            if (productDTO.getCostPrice() != null && productDTO.getCostPrice() != 0.0) {
                product.setCostPrice(productDTO.getCostPrice());
            } else {
                throw new Exception("Preço inválido.");
            }


            productRepository.save(product);
            return product.getId();
        } else {
            throw new Exception("Produto não encontrado.");
        }
    }


    public ProductDTO findByCode(String code) {
        Optional<Product> productOptional = productRepository.findByCode(code);

        if (productOptional.isPresent()) {
            return mapper.map(productOptional.get(), ProductDTO.class);
        } else {
            return new ProductDTO();
        }
    }

}
