package com.freitasprojects.estock.repositories;

import com.freitasprojects.estock.models.dtos.ProductDTO;
import com.freitasprojects.estock.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long > {

    Optional<Product> findByName(String name);
    Optional<Product> findByCode(String code);
    Optional<Product> findByCostPrice(Float costPrice);

    @Query("SELECT p.description FROM PRODUCT p")
    List<String> findProductDescriptions();

}
