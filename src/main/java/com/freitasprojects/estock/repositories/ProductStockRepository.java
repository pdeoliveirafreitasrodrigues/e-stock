package com.freitasprojects.estock.repositories;

import com.freitasprojects.estock.models.entities.Product;
import com.freitasprojects.estock.models.entities.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long > {

    @Query("SELECT ps.product, SUM(ps.quantity) FROM ProductStock ps GROUP BY ps.product")
    List<Object[]> findAllProductsAndQuantitiesInStock();

    @Query("SELECT p.code, p.description, ps.deposit, SUM(ps.quantity) " +
            "FROM ProductStock ps " +
            "JOIN ps.product p " +
            "GROUP BY p.code, p.description, ps.deposit")
    List<Object[]> findProductDetailsInStock();



}
