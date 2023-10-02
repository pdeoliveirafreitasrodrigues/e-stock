package com.freitasprojects.estock.repositories;

import com.freitasprojects.estock.models.entities.Product;
import com.freitasprojects.estock.models.entities.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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


    @Query("SELECT ps FROM ProductStock ps WHERE ps.productId = :productId AND ps.depositId = :depositId")
    Optional<ProductStock> findByProductIdAndDepositId(Long productId, Long depositId);

    @Query("SELECT ps FROM ProductStock ps " +
            "JOIN Product p ON ps.productId = p.id " +
            "JOIN Deposit d ON ps.depositId = d.id " +
            "WHERE p.code = :productCode AND d.code = :depositCode")
    Optional<ProductStock> findByProductCodeAndDepositCode(
            @Param("productCode") String productCode,
            @Param("depositCode") String depositCode);


}
