package com.freitasprojects.estock.repositories;

import com.freitasprojects.estock.models.entities.Product;
import com.freitasprojects.estock.models.entities.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long > {

    @Query(value = "SELECT p.id, p.description, SUM(ps.quantity) AS total_quantity " +
            "FROM product_stock ps " +
            "JOIN product p ON ps.product_id = p.id " +
            "GROUP BY p.id, p.description", nativeQuery = true)
    List<Object[]> findAllProductsAndQuantitiesInStock();

    @Query(value = "SELECT p.code, p.description, d.name AS deposit_name, SUM(ps.quantity) AS total_quantity " +
            "FROM product_stock ps " +
            "JOIN product p ON ps.product_id = p.id " +
            "JOIN deposit d ON ps.deposit_id = d.id " +
            "GROUP BY p.code, p.description, d.name", nativeQuery = true)
    List<Object[]> findProductDetailsInStock();



}
